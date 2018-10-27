package se.montesmites.sudoku;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import se.montesmites.sudoku.immutable.BitVector;
import se.montesmites.sudoku.model.GridIndexCandidateIndices;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.generate;

public class Grid {
    static Grid of(String json) {
        try {
            JsonGrid grid = new ObjectMapper()
                    .readerFor(JsonGrid.class)
                    .readValue(json);
            return Grid.of(grid.getRows(), grid.getSymbols());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Grid of(List<String> rows, String symbols) {
        return Grid.of(rows, symbols.chars().mapToObj(c -> (char) c).collect(toList()));
    }

    private static Grid of(List<String> rows, List<Character> symbols) {
        var order = (int) Math.sqrt(rows.size());
        var side = rows.size();
        var area = side * side;
        var converter = new IndexConverter(order);
        var solution = BitVector.of(area);
        var candidates = generate(() -> BitVector.of(area)).limit(side).toArray(BitVector[]::new);
        if (side != 1 && side != 4 && side != 9 && side != 16) {
            throw new IllegalArgumentException("There must be 1, 4, 9 or 16 rows.");
        }
        for (int row = 0; row < rows.size(); row++) {
            var line = rows.get(row);
            if (line.length() != side) {
                throw new IllegalArgumentException("Each row must have the same number of positions as there are rows.");
            }
            for (int col = 0; col < line.length(); col++) {
                var clueChar = line.charAt(col);
                var symbolIndex = symbols.indexOf(clueChar);
                if (symbolIndex >= 0) {
                    var index = converter.indexAt(row, col);
                    var candidate = candidates[symbolIndex];
                    solution = solution.set(index);
                    candidate = candidate.set(index);
                    candidates[symbolIndex] = candidate;
                }
            }
        }
        return new Grid(new Context(order, symbols), converter, new Neighbourhood(order), solution, candidates);
    }

    private static class JsonGrid {
        private final List<Character> symbols;
        private final List<String> rows;

        @JsonCreator
        private JsonGrid(@JsonProperty("symbols") String symbols, @JsonProperty("grid") List<String> rows) {
            this.symbols = symbols.chars().mapToObj(c -> (char) c).collect(toList());
            this.rows = rows;
        }

        private List<Character> getSymbols() {
            return symbols;
        }

        private List<String> getRows() {
            return rows;
        }
    }

    private final Context context;
    private final IndexConverter converter;
    private final Neighbourhood neighbourhood;
    private final BitVector solution;
    private final BitVector[] candidates;

    private Grid(Context context, IndexConverter converter, Neighbourhood neighbourhood, BitVector solution, BitVector[] candidates) {
        this.context = context;
        this.converter = converter;
        this.neighbourhood = neighbourhood;
        this.solution = solution;
        this.candidates = candidates;
    }

    private Optional<String> getSymbolAt(int index) {
        for (int i = 0; i < context.getSide(); i++) {
            if (candidates[i].isSet(index)) {
                return Optional.of(context.getSymbols().get(i).toString());
            }
        }
        return Optional.empty();
    }

    boolean isSolved() {
        return solution.cardinality() >= context.getArea();
    }

    IntStream unsolvedIndices() {
        return solution.not().setIndices();
    }

    int[] availableCandidatesAt(int index) {
        final var neighbours = BitVector.of(context.getArea(), neighbourhood.neighbourhoodOf(index));
        return range(0, context.getSide())
                .filter(i -> candidates[i].and(neighbours).isEmpty())
                .toArray();
    }

    GridIndexCandidateIndices nextEmptyGridIndexCandidateIndices() {
        return unsolvedIndices()
                .mapToObj(index -> new GridIndexCandidateIndices(index, availableCandidatesAt(index)))
                .min(comparing(i -> i.getCandidateIndices().length))
                .orElseThrow();
    }

    Grid unset(int index) {
        var solution = this.solution.unset(index);
        var candidates = range(0, context.getSide())
                .mapToObj(i -> this.candidates[i].unset(index))
                .toArray(BitVector[]::new);
        return new Grid(context, converter, neighbourhood, solution, candidates);
    }

    Grid set(int gridIndex, int candidateIndex) {
        var solution = this.solution.set(gridIndex);
        var candidates = range(0, context.getSide())
                .mapToObj(i -> i == candidateIndex ? this.candidates[i].set(gridIndex) : this.candidates[i])
                .toArray(BitVector[]::new);
        return new Grid(context, converter, neighbourhood, solution, candidates);
    }

    boolean isEquivalent(Grid that) {
        if (this.context.getOrder() == that.context.getOrder()) {
            for (int index = 0; index < this.context.getArea(); index++) {
                if (!this.getSymbolAt(index).equals(that.getSymbolAt(index))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    boolean isASolutionTo(Grid that) {
        if (this.context.getOrder() == that.context.getOrder()) {
            for (int index = 0; index < this.context.getArea(); index++) {
                if (that.getSymbolAt(index).isPresent() && !that.getSymbolAt(index).equals(this.getSymbolAt(index))) {
                    return false;
                }
            }
            if (this.solution.cardinality() == context.getArea()) {
                for (BitVector candidate : candidates) {
                    if (candidate.cardinality() != context.getSide()) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    String render() {
        var grid = new StringBuilder();
        for (int row = 0; row < context.getSide(); row++) {
            var line = new StringBuilder();
            for (int col = 0; col < context.getSide(); col++) {
                int index = converter.indexAt(row, col);
                var symbol = getSymbolAt(index).orElse(" ");
                line.append(symbol);
            }
            grid.append(line).append("\n");
        }
        return grid.toString();
    }

    String prettyRender() {
        var grid = new StringBuilder();
        var boxVerticalBorder = Stream.generate(() -> "-").limit(context.getOrder()).collect(joining());
        var rowVerticalBorder = Stream.generate(() -> boxVerticalBorder).limit(context.getOrder()).collect(joining("+", "\n+", "+\n"));
        for (int row1 = 0; row1 < context.getOrder(); row1++) {
            grid.append(rowVerticalBorder);
            for (int row2 = 0; row2 < context.getOrder(); row2++) {
                int row = row1 * context.getOrder() + row2;
                for (int col1 = 0; col1 < context.getOrder(); col1++) {
                    grid.append("|");
                    for (int col2 = 0; col2 < context.getOrder(); col2++) {
                        int col = col1 * context.getOrder() + col2;
                        grid.append(this.getSymbolAt(converter.indexAt(row, col)).orElse(" "));
                    }
                }
                grid.append("|");
                if (row2 != context.getOrder() - 1) {
                    grid.append("\n");
                }
            }
        }
        grid.append(rowVerticalBorder);
        return grid.toString();
    }
}

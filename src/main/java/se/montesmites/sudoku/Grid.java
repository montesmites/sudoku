package se.montesmites.sudoku;

import se.montesmites.sudoku.immutable.BitVector;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.generate;

public class Grid {
    public static Grid of(List<String> rows) {
        var order = (int) Math.sqrt(rows.size());
        var side = rows.size();
        var area = side * side;
        var converter = new IndexConverter(order);
        var solution = BitVector.of(area);
        var candidates = generate(() -> BitVector.of(area)).limit(side).collect(toList());
        if (side != 1 && side != 4 && side != 9) {
            throw new IllegalArgumentException("There must be 1, 4 och 9 rows.");
        }
        for (int row = 0; row < rows.size(); row++) {
            var line = rows.get(row);
            if (line.length() != side) {
                throw new IllegalArgumentException("Each row must have the same number of positions as there are rows.");
            }
            for (int col = 0; col < line.length(); col++) {
                var clueChar = line.charAt(col);
                if (Character.isDigit(clueChar)) {
                    var index = converter.indexAt(row, col);
                    var clue = Character.getNumericValue(clueChar);
                    if (clue > 0 && clue <= side) {
                        var candidate = candidates.get(clue - 1);
                        solution = solution.set(index);
                        candidate = candidate.set(index);
                        candidates.set(clue - 1, candidate);
                    }
                }
            }
        }
        return new Grid(new Context(order), converter, new Neighbourhood(order), solution, candidates);
    }

    private final Context context;
    private final IndexConverter converter;
    private final Neighbourhood neighbourhood;
    private final BitVector solution;
    private final List<BitVector> candidates;

    private Grid(Context context, IndexConverter converter, Neighbourhood neighbourhood, BitVector solution, List<BitVector> candidates) {
        this.context = context;
        this.converter = converter;
        this.neighbourhood = neighbourhood;
        this.solution = solution;
        this.candidates = candidates;
    }

    private Optional<Integer> getSymbolAt(int index) {
        for (int i = 0; i < context.getSide(); i++) {
            if (candidates.get(i).isSet(index)) {
                return Optional.of(i + 1);
            }
        }
        return Optional.empty();
    }

    boolean isSolved() {
        return solution.cardinality() >= context.getArea();
    }

    Stream<Integer> unsolvedIndices() {
        return solution.not().setIndices();
    }

    List<Integer> availableCandidatesAt(int index) {
        final var neighbourhood = new Neighbourhood(context.getOrder());
        final var neighbours = BitVector.of(context.getArea(), neighbourhood.neighbourhoodOf(index));
        return range(0, context.getSide())
                .filter(i -> candidates.get(i).and(neighbours).isEmpty())
                .boxed()
                .collect(toList());
    }

    Map<Integer, List<Integer>> candidateIndicesByIndex() {
        return unsolvedIndices()
                .map(index -> new IndexedCandidates(index, availableCandidatesAt(index)))
                .collect(toMap(IndexedCandidates::getIndex, IndexedCandidates::getCandidates));
    }

    Grid unset(int index) {
        var solution = this.solution.unset(index);
        var candidates = range(0, context.getSide())
                .mapToObj(i -> this.candidates.get(i).unset(index))
                .collect(toList());
        return new Grid(context, converter, neighbourhood, solution, candidates);
    }

    Grid set(int gridIndex, int candidateIndex) {
        var solution = this.solution.set(gridIndex);
        var candidates = range(0, context.getSide())
                .mapToObj(i -> i == candidateIndex ? this.candidates.get(i).set(gridIndex) : this.candidates.get(i))
                .collect(toList());
        return new Grid(context, converter, neighbourhood, solution, candidates);
    }

    private class IndexedCandidates {
        private final int index;
        private final List<Integer> candidates;

        IndexedCandidates(int index, List<Integer> candidates) {
            this.index = index;
            this.candidates = candidates;
        }

        int getIndex() {
            return index;
        }

        List<Integer> getCandidates() {
            return candidates;
        }
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

    @SuppressWarnings("unused")
    String render() {
        var grid = new StringBuilder();
        for (int row = 0; row < context.getSide(); row++) {
            var line = new StringBuilder();
            for (int col = 0; col < context.getSide(); col++) {
                int index = converter.indexAt(row, col);
                var symbol = getSymbolAt(index).map(s -> Integer.toString(s)).orElse(" ");
                line.append(symbol);
            }
            grid.append(line).append("\n");
        }
        return grid.toString();
    }
}

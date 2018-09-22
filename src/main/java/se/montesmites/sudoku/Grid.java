package se.montesmites.sudoku;

import se.montesmites.sudoku.immutable.BitVector;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Map.Entry.comparingByKey;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;
import static java.util.stream.IntStream.range;

class Grid {
    private static class ClueProviderDeployer {
        private final Context context;
        private final IndexConverter converter;
        private BitVector solution;
        private List<BitVector> candidates;

        ClueProviderDeployer(Context context, IndexConverter converter, ClueProvider clueProvider) {
            this.context = context;
            this.converter = converter;
            deployClues(clueProvider);
        }

        private void deployClues(ClueProvider clueProvider) {
            if (clueProvider.stream().count() > 0) {
                this.solution = BitVector.of(
                        context.getArea(),
                        clueProvider.stream().map(cell -> converter.indexAt(cell.getRow(), cell.getCol())));
                var candidatesClues = clueProvider.stream().collect(groupingBy(cell -> cell.getClue() - 1));
                this.candidates = candidatesClues.entrySet()
                                                 .stream()
                                                 .sorted(comparingByKey())
                                                 .map(entry -> candidatesClues.get(entry.getKey()))
                                                 .map(cells -> cells.stream().map(cell -> converter.indexAt(cell.getRow(), cell.getCol())))
                                                 .map(indices -> BitVector.of(context.getSide(), indices))
                                                 .collect(toList());
            } else {
                this.solution = BitVector.of(context.getArea());
                this.candidates = Stream.generate(() -> BitVector.of(context.getArea())).limit(context.getSide()).collect(toList());
            }
        }

        BitVector getSolution() {
            return solution;
        }

        List<BitVector> getCandidates() {
            return candidates;
        }
    }

    private final Context context;
    private final IndexConverter converter;
    private final BitVector solution;
    private final List<BitVector> candidates;

    Grid(Context context) {
        this(context, new IndexConverter(context.getOrder()));
    }

    private Grid(Context context, IndexConverter converter) {
        this(context, converter, new ClueProviderDeployer(context, converter, context.getClueProvider()));
    }

    private Grid(Context context, IndexConverter converter, ClueProviderDeployer deployer) {
        this.context = context;
        this.converter = converter;
        this.solution = deployer.getSolution();
        this.candidates = deployer.getCandidates();
    }

    private Grid(Context context, IndexConverter converter, BitVector solution, List<BitVector> candidates) {
        this.context = context;
        this.converter = converter;
        this.solution = solution;
        this.candidates = candidates;
    }

    Context getContext() {
        return context;
    }

    Optional<Integer> getValueAt(int row, int col) {
        return getValueAt(converter.indexAt(row, col));
    }

    private Optional<Integer> getValueAt(int index) {
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
                .mapToObj(i -> candidates.stream()
                                         .filter(candidate -> candidate.and(neighbours).isEmpty())
                                         .map(__ -> i))
                .flatMap(identity())
                .collect(toList());
    }

    Map<Integer, List<Integer>> candidateIndicesByIndex() {
        return unsolvedIndices()
                .map(index -> new IndexedCandidates(index, availableCandidatesAt(index)))
                .collect(toMap(IndexedCandidates::getIndex, IndexedCandidates::getCandidates))
                ;
    }

    Grid copy() {
        var solution = this.solution.copy();
        var candidates = this.candidates.stream().map(BitVector::copy).collect(toList());
        return new Grid(context, converter, solution, candidates);
    }

    Grid set(int gridIndex, int candidateIndex) {
        var solution = this.solution.set(gridIndex);
        var candidates = range(0, context.getSide())
                .mapToObj(i -> i == candidateIndex ? this.candidates.get(i).set(gridIndex) : this.candidates.get(i))
                .collect(toList());
        return new Grid(context, converter, solution, candidates);
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
}

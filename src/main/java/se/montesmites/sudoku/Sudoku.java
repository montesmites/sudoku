package se.montesmites.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

class Sudoku {
    static class SudokuBuilder {
        private final int size;

        private List<List<Integer>> lines = new ArrayList<>();

        private SudokuBuilder(int order) {
            size = order * order;
        }

        SudokuBuilder line(String line) {
            if (line.length() != size) {
                throw new IllegalArgumentException(String.format("Line should have a length of %d characters.", size));
            } else {
                lines.add(line.chars().mapToObj(c -> (char) c).map(String::valueOf).map(Integer::parseInt).collect(toList()));
            }
            return this;
        }

        Sudoku build() {
            return new Sudoku(new Context(size, clueProvider()));
        }

        private ClueProvider clueProvider() {
            return new ClueProvider() {
                @Override
                public Optional<Integer> clueAt(int row, int col) {
                    if (lines.size() > row) {
                        if (lines.get(row).size() > col) {
                            return Optional.of(lines.get(row).get(col));
                        }
                    }
                    return Optional.empty();
                }

                @Override
                public int size() {
                    return size;
                }
            };
        }
    }

    static SudokuBuilder builder(int order) {
        return new SudokuBuilder(order);
    }

    private final Grid grid;

    private Sudoku(Context context) {
        this.grid = new Grid(context);
    }

    Optional<Solution> solve() {
        return solve(grid.copy());
    }

    private Optional<Solution> solve(Grid grid) {
        if (grid.isSolved()) {
            return Optional.of(new Solution(grid));
        } else {
            var candidateCount = grid.candidateIndicesByIndex().entrySet().stream()
                                     .collect(groupingBy(entry -> entry.getValue().size()));
            if (candidateCount.containsKey(0)) {
                return Optional.empty();
            } else if (candidateCount.containsKey(1)) {
                var gridCopy = grid.copy();
                for (var entry : candidateCount.get(1)) {
                    var gridIndex = entry.getKey();
                    var candidateIndices = entry.getValue();
                    candidateIndices.forEach(candidateIndex -> gridCopy.set(gridIndex, candidateIndex));
                }
                return solve(gridCopy);
            } else {
                var candidate = candidateCount.entrySet().stream().findFirst();
                if (candidate.isPresent()) {
                    var candidateEntry = candidate.get().getValue().stream().findFirst();
                    if (candidateEntry.isPresent()) {
                        var entry = candidateEntry.get();
                        var gridIndex = entry.getKey();
                        var candidateIndices = entry.getValue().stream().findFirst();
                        if (candidateIndices.isPresent()) {
                            var candidateIndex = candidateIndices.get();
                            var gridCopy = grid.set(gridIndex, candidateIndex);
                            return solve(gridCopy);
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }
}

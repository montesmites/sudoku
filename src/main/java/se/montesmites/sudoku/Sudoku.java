package se.montesmites.sudoku;

import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;

class Sudoku {
    Optional<Grid> solve(Grid grid) {
        if (grid.isSolved()) {
            return Optional.of(grid);
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
                    for (int candidateIndex : candidateIndices) {
                        gridCopy = gridCopy.set(gridIndex, candidateIndex);
                    }
                }
                return solve(gridCopy);
            } else {
                for (var entry : candidateCount.entrySet()) {
                    var gridIndex = entry.getKey();
                    for (var candidateIndices : entry.getValue()) {
                        for (var candidateIndex : candidateIndices.getValue()) {
                            var gridCopy = grid.set(gridIndex, candidateIndex);
                            var solution = solve(gridCopy);
                            if (solution.isPresent()) {
                                return solution;
                            }
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }
}

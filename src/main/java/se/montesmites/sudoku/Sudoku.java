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

package se.montesmites.sudoku;

import java.util.Optional;

import static java.util.Map.Entry.comparingByKey;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;

class Sudoku {
    Optional<Grid> solve(Grid grid) {
        return doSolve(1, grid);
    }

    private Optional<Grid> doSolve(int depth, Grid grid) {
        if (grid.isSolved()) {
            return Optional.of(grid);
        } else {
            var candidateCount = grid.candidateIndicesByIndex().entrySet().stream()
                                     .collect(groupingBy(entry -> entry.getValue().size()));
            if (candidateCount.containsKey(0)) {
                return Optional.empty();
            } else {
                var selectedGridIndexWithCandidates
                        = candidateCount.keySet().stream()
                                        .sorted()
                                        .findFirst()
                                        .map(candidateCount::get)
                                        .map(entries -> entries.stream().min(comparingByKey()))
                                        .flatMap(identity());
                if (selectedGridIndexWithCandidates.isPresent()) {
                    var gridIndex = selectedGridIndexWithCandidates.get().getKey();
                    var candidateIndices = selectedGridIndexWithCandidates.get().getValue();
                    for (var candidateIndex : candidateIndices) {
                        var gridCopy = grid.set(gridIndex, candidateIndex);
                        var solution = doSolve(depth + 1, gridCopy);
                        if (solution.isPresent()) {
                            return solution;
                        }
                    }
                    return Optional.empty();
                } else {
                    return Optional.empty();
                }
            }
        }
    }
}

package se.montesmites.sudoku;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

class Sudoku {
    public static void main(String[] args) throws Exception {
        var path = Paths.get(args[0]);
        var lines = Files.lines(path);
        Sudoku sudoku = new Sudoku();
        var solution = sudoku.solve(Grid.of(lines.collect(toList())));
        if (solution.isPresent()) {
            System.out.println("The solution is:");
            System.out.println(solution.get().render());
        } else {
            System.out.println("There is no solution.");
        }
    }

    Optional<Grid> solve(Grid grid) {
        return doSolve(1, grid);
    }

    private Optional<Grid> doSolve(int depth, Grid grid) {
        if (grid.isSolved()) {
            return Optional.of(grid);
        } else {
            var selectedIndexWithCandidates = grid.nextEmptyGridIndexCandidateIndices();
            var gridIndex = selectedIndexWithCandidates.getGridIndex();
            var candidateIndices = selectedIndexWithCandidates.getCandidateIndices();
            if (candidateIndices.length == 0) {
                return Optional.empty();
            } else {
                for (var candidateIndex : candidateIndices) {
                    var gridCopy = grid.set(gridIndex, candidateIndex);
                    var solution = doSolve(depth + 1, gridCopy);
                    if (solution.isPresent()) {
                        return solution;
                    }
                }
                return Optional.empty();
            }
        }
    }
}

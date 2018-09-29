package se.montesmites.sudoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

class Sudoku {
    private final static Logger LOGGER = LoggerFactory.getLogger(Sudoku.class);

    public static void main(String[] args) throws Exception {
        var path = Paths.get(args[0]);
        Sudoku sudoku = new Sudoku();
        var solution = sudoku.solve(Grid.of(new String(Files.readAllBytes(path))));
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
        LOGGER.trace("Depth is {}, grid is {}", depth, grid.prettyRender());
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

package se.montesmites.sudoku;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import static java.util.Comparator.comparing;
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
            var candidatesPerIndex = grid.candidatesPerIndex().stream()
                                         .sorted(comparing(i -> i.getCandidateIndices().size()))
                                         .collect(toList());
            if (candidatesPerIndex.get(0).getCandidateIndices().isEmpty()) {
                return Optional.empty();
            } else {
                for (var candidateIndex : candidatesPerIndex.get(0).getCandidateIndices()) {
                    var gridCopy = grid.set(candidatesPerIndex.get(0).getGridIndex(), candidateIndex);
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


package se.montesmites.sudoku;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Sudoku_1x1_Test {
    final private static Grid UNSOLVED_1x1 = Grid.of(List.of(" "));
    final private static Grid SOLVED_1x1 = Grid.of(List.of("1"));

    @Test
    void solved() {
        var sudoku = new Sudoku();
        var solution = sudoku.solve(SOLVED_1x1);
        assertTrue(solution.isPresent());
        assertTrue(SOLVED_1x1.isEquivalent(solution.get()));
    }

    @Test
    void unsolved() {
        var sudoku = new Sudoku();
        var solution = sudoku.solve(UNSOLVED_1x1);
        assertTrue(solution.isPresent());
        assertTrue(SOLVED_1x1.isEquivalent(solution.get()));
    }
}

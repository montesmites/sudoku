package se.montesmites.sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Sudoku_1x1_Test {
    @Test
    void solved() {
        var sudoku = Sudoku.builder(1).line("1").build();
        var solution = sudoku.solve();
        assertTrue(solution.isPresent());
    }

    @Test
    void unsolved() {
        var sudoku = Sudoku.builder(1).build();
        var solution = sudoku.solve();
        assertTrue(solution.isPresent());
    }
}

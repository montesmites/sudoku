package se.montesmites.sudoku;

import org.junit.jupiter.api.Test;
import se.montesmites.sudoku.sample.Sudoku_1x1;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Sudoku_1x1_Test {
    @Test
    void solved() {
        var sudoku = new Sudoku();
        var solution = sudoku.solve(Sudoku_1x1.SOLVED.grid());
        assertTrue(solution.isPresent());
        assertTrue(Sudoku_1x1.SOLVED.grid().isEquivalent(solution.get()));
    }

    @Test
    void unsolved() {
        var sudoku = new Sudoku();
        var solution = sudoku.solve(Sudoku_1x1.UNSOLVED.grid());
        assertTrue(solution.isPresent());
        assertTrue(Sudoku_1x1.SOLVED.grid().isEquivalent(solution.get()));
    }
}

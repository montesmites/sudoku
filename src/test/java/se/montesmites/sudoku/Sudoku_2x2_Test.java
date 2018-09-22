package se.montesmites.sudoku;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import se.montesmites.sudoku.sample.Sudoku_2x2;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Sudoku_2x2_Test {
    @Test
    void solved() {
        var sudoku = new Sudoku();
        var solution = sudoku.solve(Sudoku_2x2.SOLVED.grid());
        assertTrue(solution.isPresent());
        assertTrue(Sudoku_2x2.SOLVED.grid().isEquivalent(solution.get()));
    }

    @ParameterizedTest
    @EnumSource(Sudoku_2x2.class)
    void unsolved(Sudoku_2x2 clues) {
        var sudoku = new Sudoku();
        var solution = sudoku.solve(clues.grid());
        assertTrue(solution.isPresent());
        assertTrue(Sudoku_2x2.SOLVED.grid().isEquivalent(solution.get()));
    }
}

package se.montesmites.sudoku;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import se.montesmites.sudoku.sample.Sudoku_2x2_8_unknown;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.montesmites.sudoku.sample.Sudoku_2x2_8_unknown.SOLVED;

class Sudoku_2x2_Test {
    @Test
    void solved() {
        var sudoku = new Sudoku();
        var solution = sudoku.solve(SOLVED.grid());
        assertTrue(solution.isPresent());
        assertTrue(SOLVED.grid().isEquivalent(solution.get()));
    }

    @ParameterizedTest
    @EnumSource(Sudoku_2x2_8_unknown.class)
    void unsolved(Sudoku_2x2_8_unknown clues) {
        var sudoku = new Sudoku();
        var solution = sudoku.solve(clues.grid());
        assertTrue(solution.isPresent());
        assertTrue(SOLVED.grid().isEquivalent(solution.get()));
    }
}

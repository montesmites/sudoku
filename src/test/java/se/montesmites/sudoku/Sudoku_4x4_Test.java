package se.montesmites.sudoku;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import se.montesmites.sudoku.sample.Sudoku_4x4;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Sudoku_4x4_Test {
    @Test
    void solved() {
        var sudoku = new Sudoku();
        var solution = sudoku.solve(Sudoku_4x4.SOLVED.grid());
        assertTrue(solution.isPresent());
        assertTrue(Sudoku_4x4.SOLVED.grid().isEquivalent(solution.get()));
    }

    @ParameterizedTest
    @EnumSource(Sudoku_4x4.class)
    void unsolved(Sudoku_4x4 clues) {
        var sudoku = new Sudoku();
        var solution = sudoku.solve(clues.grid());
        assertTrue(solution.isPresent());
        assertTrue(Sudoku_4x4.SOLVED.grid().isEquivalent(solution.get()));
    }
}

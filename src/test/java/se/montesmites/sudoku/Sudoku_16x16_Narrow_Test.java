package se.montesmites.sudoku;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import se.montesmites.sudoku.sample.Sudoku_16x16;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Sudoku_16x16_Narrow_Test {
    @Test
    void easy_16x16() {
        var clues = Sudoku_16x16.EASY.unsolved();
        var solution = new Sudoku().solve(clues);
        assertAll(
                () -> assertTrue(solution.isPresent()),
                () -> assertTrue(solution.isPresent() && solution.get().isASolutionTo(clues)));
    }

    @Disabled
    void intermediate_16x16() {
        var clues = Sudoku_16x16.INTERMEDIATE.unsolved();
        var solution = new Sudoku().solve(clues);
        assertAll(
                () -> assertTrue(solution.isPresent()),
                () -> assertTrue(solution.isPresent() && solution.get().isASolutionTo(clues)));
    }
}

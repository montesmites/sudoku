package se.montesmites.sudoku;

import org.junit.jupiter.api.Test;
import se.montesmites.sudoku.sample.Sudoku_9x9;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Sudoku_9x9_Narrow_Test {
    @Test
    void allegedlyMostDifficultSudoku() {
        var clues = Sudoku_9x9.ALLEGEDLY_MOST_DIFFICULT_SUDOKU.unsolved();
        var solution = new Sudoku().solve(clues);
        assertAll(
                () -> assertTrue(solution.isPresent()),
                () -> assertTrue(solution.isPresent() && solution.get().isASolutionTo(clues)));
    }
}

package se.montesmites.sudoku;

import ch.qos.logback.classic.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.montesmites.sudoku.sample.Sudoku_16x16;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Sudoku_16x16_Narrow_Test {
    @BeforeEach
    void beforeEach() {
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.ALL);
    }

    @Test
    void easy_16x16() {
        var clues = Sudoku_16x16.EASY.unsolved();
        var solution = new Sudoku().solve(clues);
        assertAll(
                () -> assertTrue(solution.isPresent()),
                () -> assertTrue(solution.isPresent() && solution.get().isASolutionTo(clues)));
    }
}

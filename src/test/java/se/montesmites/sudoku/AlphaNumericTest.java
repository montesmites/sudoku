package se.montesmites.sudoku;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("SpellCheckingInspection")
class AlphaNumericTest {
    private final static String SYMBOLS = "ABCD";

    @Test
    void alphanumeric_4x4() {
        var clues = List.of(
                " BD ",
                "A  C",
                "D  B",
                " AC "
        );
        var solution = List.of(
                "CBDA",
                "ADBC",
                "DCAB",
                "BACD"
        );
        var sudoku = new Sudoku();
        var exp = Grid.of(solution, SYMBOLS);
        var act = sudoku.solve(Grid.of(clues, SYMBOLS));
        assertTrue(act.isPresent());
        assertTrue(act.get().isEquivalent(exp));
    }
}

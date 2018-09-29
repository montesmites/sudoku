package se.montesmites.sudoku.sample;

import se.montesmites.sudoku.Grid;

import java.util.List;

@SuppressWarnings("unused")
public enum Sudoku_1x1 {
    SOLVED(List.of(
            "1"
    )),
    UNSOLVED(List.of(
            " "
    ));

    private final List<String> rows;

    Sudoku_1x1(List<String> rows) {
        this.rows = rows;
    }

    public Grid grid() {
        return Grid.of(rows, "1");
    }
}

package se.montesmites.sudoku.sample;

import se.montesmites.sudoku.Grid;

import java.util.List;

@SuppressWarnings("unused")
public enum Sudoku_2x2 {
    SOLVED(List.of(
            "1324",
            "2413",
            "3142",
            "4231"
    )),
    UNSOLVED_1_UNKNOWN(List.of(
            " 324",
            "2413",
            "3142",
            "4231"
    )),
    UNSOLVED_2_UNKNOWN(List.of(
            " 324",
            "24 3",
            "3142",
            "4231"
    )),
    UNSOLVED_3_UNKNOWN(List.of(
            " 324",
            "24 3",
            " 142",
            "4231"
    )),
    UNSOLVED_4_UNKNOWN(List.of(
            " 324",
            "24 3",
            " 1 2",
            "4231"
    )),
    UNSOLVED_5_UNKNOWN(List.of(
            " 324",
            "24 3",
            " 1  ",
            "4231"
    )),
    UNSOLVED_6_UNKNOWN(List.of(
            " 324",
            "24 3",
            " 1  ",
            " 231"
    )),
    UNSOLVED_7_UNKNOWN(List.of(
            " 324",
            "24 3",
            " 1  ",
            "  31"
    )),
    UNSOLVED_8_UNKNOWN(
            List.of(
                    " 324",
                    "24 3",
                    " 1  ",
                    "  3 "
            ));

    private final List<String> rows;

    Sudoku_2x2(List<String> rows) {
        this.rows = rows;
    }

    public Grid grid() {
        return Grid.of(rows, "1234");
    }
}

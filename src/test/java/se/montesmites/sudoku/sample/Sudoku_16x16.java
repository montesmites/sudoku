package se.montesmites.sudoku.sample;

import se.montesmites.sudoku.Grid;

import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
public enum Sudoku_16x16 {
    EASY(List.of(
            "F8230CB3697541AD",
            "CBD4F5768A31290E",
            "76EA319D42B0C58F",
            "5091482ACEDF7B36",
            "3D895214F6AB0CE7",
            "624F7EDB059CA813",
            "B1C09AF3748E5D62",
            "E5A7C608312D94FB",
            "895C1D4F2B06E37A",
            "A7328BE09DF4165C",
            "DE0B63C9175A8F24",
            "4F16A752E8C3D0B9",
            "2CBEDF8753196A40",
            "9365E0A1DF47B2C8",
            "0A7D2435BC68FE91",
            "14F8B96CA0E237D5"
    ), List.of(
            "F8 3   E     1 D",
            "  D4F 7 8  1 9 E",
            "          B0C   ",
            "  9 4  A  D     ",
            "      1 F6A 0  7",
            " 24   D    C  1 ",
            " 1C0     4  5 6 ",
            "E5    0 3     FB",
            " 9    4  B0  3 A",
            "  3     9      C",
            " E  6    7   F2 ",
            "       2E8C3D0 9",
            "2   D     1  A40",
            " 3 5E   D   B   ",
            "0 7D 43 BC68FE  ",
            "  F8B96C  E  7  "
    ));

    private final static String SYMBOLS = "0123456789ABCDEF";

    private final List<String> solved;
    private final List<String> unsolved;

    Sudoku_16x16(List<String> solved, List<String> unsolved) {
        this.solved = solved;
        this.unsolved = unsolved;
    }

    public Grid solved() {
        return Grid.of(solved, SYMBOLS);
    }

    public Grid unsolved() {
        return Grid.of(unsolved, SYMBOLS);
    }
}

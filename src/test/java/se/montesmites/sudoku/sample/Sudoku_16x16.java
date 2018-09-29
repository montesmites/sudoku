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
    )),
    INTERMEDIATE(List.of(
            "4E258CD76B9F301A",
            "170829EA345CFBD6",
            "CB63015F7D2A8E94",
            "DA9F643B081E572C",
            "3FDAE06C1578B249",
            "08C27FA9BE64153D",
            "6157B24DC3F9EA08",
            "E4B9158320AD6FC7",
            "A0FD58B69C4271E3",
            "B24ED7C15A36098F",
            "9531AEF0D78B4C62",
            "768C9324EF01AD5B",
            "8CE43D72A1B096F5",
            "597BF61E82D3C4A0",
            "FDA0CB9846E52371",
            "23164A05F9C7D8BE"
    ), List.of(
            "4    CD 6  F   A",
            "1      A 45   D ",
            " B      7   8 9 ",
            "  9 6 3   1 5  C",
            "   AE0  1 7   4 ",
            "0  2   9BE  1   ",
            "6      D 3     8",
            "  B9158  0AD  C7",
            "A    8B      1E ",
            "B  E 7    36 9  ",
            " 5 1 E         2",
            " 6 C  24        ",
            " CE  D 2A B0 6 5",
            "  7B  1  2D3 4  ",
            "FD     84    37 ",
            " 3     5F9     E"
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

package se.montesmites.sudoku.sample;

import se.montesmites.sudoku.Grid;

import java.util.List;

@SuppressWarnings("unused")
public enum Sudoku_3x3 {
    EASY(List.of(
            "152489376",
            "739256841",
            "468371295",
            "387124659",
            "591763428",
            "246895713",
            "914637582",
            "625948137",
            "873512964"
    ), List.of(
            "1  489  6",
            "73     4 ",
            "     1295",
            "  712 6  ",
            "5  7 3  8",
            "  6 957  ",
            "9146     ",
            " 2     37",
            "8  512  4"
    )),
    INTERMEDIATE(List.of(
            "123678945",
            "584239761",
            "967145328",
            "372461589",
            "691583274",
            "458792613",
            "836924157",
            "219857436",
            "745316892"
    ), List.of(
            " 2 6 8   ",
            "58   97  ",
            "    4    ",
            "37    5  ",
            "6       4",
            "  8    13",
            "    2    ",
            "  98   36",
            "   3 6 9 "
    )),
    HARD(List.of(
            "581672439",
            "792843651",
            "364591782",
            "438957216",
            "256184973",
            "179326845",
            "845219367",
            "913768524",
            "627435198"
    ), List.of(
            "   6  4  ",
            "7    36  ",
            "    91 8 ",
            "         ",
            " 5 18   3",
            "   3 6 45",
            " 4 2   6 ",
            "9 3      ",
            " 2    1  "
    )),
    VERY_HARD(List.of(
            "126437958",
            "895621473",
            "374985126",
            "457193862",
            "983246517",
            "612578394",
            "269314785",
            "548769231",
            "731852649"
    ), List.of(
            " 2       ",
            "   6    3",
            " 74 8    ",
            "     3  2",
            " 8  4  1 ",
            "6  5     ",
            "    1 78 ",
            "5    9   ",
            "       4 "
    )),
    DIABOLICAL(List.of(
            "421958673",
            "385674291",
            "769321458",
            "694813527",
            "538792146",
            "172465839",
            "256137984",
            "817249365",
            "943586712"
    ), List.of(
            "  1  8 73",
            "  56    1",
            "7    1   ",
            " 9 81    ",
            "53     46",
            "    65 3 ",
            "   1    4",
            "8    93  ",
            "94 5  7  "
    ));

    private final List<String> solved;
    private final List<String> unsolved;

    Sudoku_3x3(List<String> solved, List<String> unsolved) {
        this.solved = solved;
        this.unsolved = unsolved;
    }

    public Grid solved() {
        return Grid.of(solved);
    }

    public Grid unsolved() {
        return Grid.of(unsolved);
    }
}

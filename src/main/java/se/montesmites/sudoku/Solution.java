package se.montesmites.sudoku;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

class Solution {
    private final int side;
    private final Grid grid;

    Solution(Grid grid) {
        this.side = grid.getContext().getSide();
        this.grid = grid;
    }

    int[][] solution() {
        int[][] solution = new int[side][side];
        for (int row = 0; row < side; row++) {
            for (int col = 0; col < side; col++) {
                solution[row][col] = this.grid.getValueAt(row, col).orElseThrow();
            }
        }
        return solution;
    }

    String asString() {
        return stream(solution()).map(row -> stream(row).mapToObj(Integer::toString).collect(joining())).collect(joining("\n"));
    }
}

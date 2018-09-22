package se.montesmites.sudoku;

import lombok.Value;

@Value
class Cell {
    private final int row;
    private final int col;
    private final int clue;
}

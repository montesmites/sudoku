package se.montesmites.sudoku;

import lombok.Getter;

@Getter
class Context {
    private final int order;
    private final int side;
    private final int area;

    Context(int order) {
        this.order = order;
        this.side = order * order;
        this.area = side * side;
    }
}

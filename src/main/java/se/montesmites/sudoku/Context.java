package se.montesmites.sudoku;

import lombok.Getter;

@Getter
class Context {
    private final int order;
    private final int side;
    private final int area;
    private final ClueProvider clueProvider;

    Context(int order, ClueProvider clueProvider) {
        this.order = order;
        this.side = order * order;
        this.area = side * side;
        this.clueProvider = clueProvider;
    }
}

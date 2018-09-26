package se.montesmites.sudoku;

import lombok.Getter;

import java.util.List;

@Getter
class Context {
    private final int order;
    private final int side;
    private final int area;
    private final List<String> symbols;

    Context(int order, List<String> symbols) {
        this.order = order;
        this.side = order * order;
        this.area = side * side;
        this.symbols = symbols;
    }
}

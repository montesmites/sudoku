package se.montesmites.sudoku;

import java.util.List;

class Context {
    private final int order;
    private final int side;
    private final int area;
    private final List<Character> symbols;

    Context(int order, List<Character> symbols) {
        this.order = order;
        this.side = order * order;
        this.area = side * side;
        this.symbols = symbols;
    }

    int getOrder() {
        return order;
    }

    int getSide() {
        return side;
    }

    int getArea() {
        return area;
    }

    List<Character> getSymbols() {
        return symbols;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Context context = (Context) o;

        if (order != context.order) return false;
        if (side != context.side) return false;
        if (area != context.area) return false;
        return symbols.equals(context.symbols);
    }

    @Override
    public int hashCode() {
        int result = order;
        result = 31 * result + side;
        result = 31 * result + area;
        result = 31 * result + symbols.hashCode();
        return result;
    }
}

package se.montesmites.sudoku;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import static java.util.stream.IntStream.range;

class Neighbourhood {
    private final int area;
    private final IndexConverter converter;

    Neighbourhood(int order) {
        var side = order * order;
        this.area = side * side;
        this.converter = new IndexConverter(order);
    }

    IntStream neighbourhoodOf(int index) {
        return range(0, area).filter(isNeighbourOf(index));
    }

    private IntPredicate isNeighbourOf(int index) {
        final int row = converter.rowAt(index);
        final int col = converter.colAt(index);
        final int box = converter.boxAt(index);
        return i -> converter.rowAt(i) == row || converter.colAt(i) == col || converter.boxAt(i) == box;
    }
}

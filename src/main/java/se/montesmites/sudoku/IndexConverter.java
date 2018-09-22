package se.montesmites.sudoku;

class IndexConverter {
    private final int order;
    private final int side;

    IndexConverter(int order) {
        this.order = order;
        this.side = order * order;
    }

    int rowAt(int index) {
        return index / side;
    }

    int colAt(int index) {
        return index % side;
    }

    int boxAt(int index) {
        int rowTerm = rowAt(index) / order;
        int colTerm = colAt(index) / order;
        return rowTerm * order + colTerm;
    }

    int indexAt(int row, int col) {
        return row * side + col;
    }
}

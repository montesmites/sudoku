package se.montesmites.sudoku;

import java.util.Optional;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.IntStream.range;

interface ClueProvider {
    static ClueProvider empty(final int size) {
        return new ClueProvider() {
            @Override
            public Optional<Integer> clueAt(int row, int col) {
                return Optional.empty();
            }

            @Override
            public int size() {
                return size;
            }
        };
    }

    Optional<Integer> clueAt(int row, int col);

    int size();

    default Stream<Cell> stream() {
        return range(0, size())
                .mapToObj(row -> range(0, size())
                        .mapToObj(col -> clueAt(row, col)
                                .map(clue -> new Cell(row, col, clue))))
                .flatMap(identity())
                .filter(Optional::isPresent)
                .map(Optional::get);
    }
}

package se.montesmites.sudoku;

import org.junit.jupiter.api.Test;
import se.montesmites.sudoku.model.GridIndexCandidateIndices;
import se.montesmites.sudoku.sample.Sudoku_1x1;
import se.montesmites.sudoku.sample.Sudoku_2x2;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GridTest {
    @Test
    void unsolvedIndices_1x1WithNoClues() {
        var grid = Sudoku_1x1.UNSOLVED.grid();
        var exp = Set.of(0);
        var act = grid.unsolvedIndices().boxed().collect(toSet());
        assertEquals(exp, act);
    }

    @Test
    void unsolvedIndices_2x2_unsolved3Unknowns() {
        var grid = Sudoku_2x2.UNSOLVED_3_UNKNOWN.grid();
        var exp = Set.of(0, 6, 8);
        var act = grid.unsolvedIndices().boxed().collect(toSet());
        assertEquals(exp, act);
    }

    @Test
    void availableCandidates_1x1WithNoClues() {
        var grid = Sudoku_1x1.UNSOLVED.grid();
        var exp = new int[]{0};
        var act = grid.availableCandidatesAt(0);
        assertArrayEquals(exp, act);
    }

    @Test
    void availableCandidates_2x2_unsolved3Unknowns() {
        var grid = Sudoku_2x2.UNSOLVED_3_UNKNOWN.grid();
        var exp = List.of(Set.of(0), Set.of(0), Set.of(2));
        var act = Stream.of(0, 6, 8)
                        .map((index -> stream(grid.availableCandidatesAt(index))
                                .boxed()
                                .collect(toSet())
                             )
                        )
                        .collect(toList());
        assertEquals(exp, act);
    }

    @Test
    void candidateIndicesByIndex_1x1WithNoClues() {
        var grid = Sudoku_1x1.UNSOLVED.grid();
        var exp = new GridIndexCandidateIndices(0, new int[]{0});
        var act = grid.nextEmptyGridIndexCandidateIndices();
        assertEquals(exp, act);
    }

    @Test
    void candidateIndicesByIndex_2x2_unsolved3Unknowns() {
        var grid = Sudoku_2x2.UNSOLVED_3_UNKNOWN.grid();
        var exp = new GridIndexCandidateIndices(0, new int[]{0});
        var act = grid.nextEmptyGridIndexCandidateIndices();
        assertEquals(exp, act);
    }
}

package se.montesmites.sudoku;

import org.junit.jupiter.api.Test;
import se.montesmites.sudoku.sample.Sudoku_1x1;
import se.montesmites.sudoku.sample.Sudoku_2x2;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GridTest {
    @Test
    void unsolvedIndices_1x1WithNoClues() {
        var grid = Sudoku_1x1.UNSOLVED.grid();
        var exp = Set.of(0);
        var act = grid.unsolvedIndices().collect(toSet());
        assertEquals(exp, act);
    }

    @Test
    void unsolvedIndices_2x2_unsolved3Unknowns() {
        var grid = Sudoku_2x2.UNSOLVED_3_UNKNOWN.grid();
        var exp = Set.of(0, 6, 8);
        var act = grid.unsolvedIndices().collect(toSet());
        assertEquals(exp, act);
    }

    @Test
    void availableCandidates_1x1WithNoClues() {
        var grid = Sudoku_1x1.UNSOLVED.grid();
        var exp = Set.of(0);
        var act = new HashSet<>(grid.availableCandidatesAt(0));
        assertEquals(exp, act);
    }

    @Test
    void availableCandidates_2x2_unsolved3Unknowns() {
        var grid = Sudoku_2x2.UNSOLVED_3_UNKNOWN.grid();
        var exp = List.of(Set.of(0), Set.of(0), Set.of(2));
        var act = Stream.of(0, 6, 8).map(index -> new HashSet<>(grid.availableCandidatesAt(index))).collect(toList());
        assertEquals(exp, act);
    }

    @Test
    void candidateIndicesByIndex_1x1WithNoClues() {
        var grid = Sudoku_1x1.UNSOLVED.grid();
        var exp = Map.of(0, List.of(0))
                     .entrySet().stream()
                     .collect(toMap(Map.Entry::getKey, entry -> new HashSet<>(entry.getValue())));
        var act = grid.candidateIndicesByIndex()
                      .entrySet().stream()
                      .collect(toMap(Map.Entry::getKey, entry -> new HashSet<>(entry.getValue())));
        assertEquals(exp, act);
    }

    @Test
    void candidateIndicesByIndex_2x2_unsolved3Unknowns() {
        var grid = Sudoku_2x2.UNSOLVED_3_UNKNOWN.grid();
        var exp = Map.of(0, List.of(0), 6, List.of(0), 8, List.of(2))
                     .entrySet().stream()
                     .collect(toMap(Map.Entry::getKey, entry -> new HashSet<>(entry.getValue())));
        var act = grid.candidateIndicesByIndex()
                      .entrySet().stream()
                      .collect(toMap(Map.Entry::getKey, entry -> new HashSet<>(entry.getValue())));
        assertEquals(exp, act);
    }
}

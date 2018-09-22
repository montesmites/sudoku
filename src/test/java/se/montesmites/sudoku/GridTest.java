package se.montesmites.sudoku;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GridTest {
    private final static List<String> EMPTY_1x1 = List.of(" ");

    @Test
    void availableCandidates_1x1WithNoClues() {
        var grid = Grid.of(EMPTY_1x1);
        var exp = Set.of(0);
        var act = new HashSet<>(grid.availableCandidatesAt(0));
        assertEquals(exp, act);
    }

    @Test
    void unsolvedIndices_1x1WithNoClues() {
        var grid = Grid.of(EMPTY_1x1);
        var exp = Set.of(0);
        var act = grid.unsolvedIndices().collect(toSet());
        assertEquals(exp, act);
    }

    @Test
    void candidateIndicesByIndex_1x1WithNoClues() {
        var grid = Grid.of(EMPTY_1x1);
        var exp = Map.of(0, List.of(0))
                     .entrySet().stream()
                     .collect(toMap(Map.Entry::getKey, entry -> new HashSet<>(entry.getValue())));
        var act = grid.candidateIndicesByIndex()
                      .entrySet().stream()
                      .collect(toMap(Map.Entry::getKey, entry -> new HashSet<>(entry.getValue())));
        assertEquals(exp, act);
    }
}

package se.montesmites.sudoku.model;

import lombok.Value;

import java.util.List;

@Value
public class GridIndexCandidateIndices {
    private final int gridIndex;
    private final List<Integer> candidateIndices;
}

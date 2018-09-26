package se.montesmites.sudoku.model;

import lombok.Value;

@Value
public class GridIndexCandidateIndices {
    private final int gridIndex;
    private final int[] candidateIndices;
}

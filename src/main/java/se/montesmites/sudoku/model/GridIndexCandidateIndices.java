package se.montesmites.sudoku.model;

import java.util.Arrays;

public class GridIndexCandidateIndices {
    private final int gridIndex;
    private final int[] candidateIndices;

    public GridIndexCandidateIndices(int gridIndex, int[] candidateIndices) {
        this.gridIndex = gridIndex;
        this.candidateIndices = candidateIndices;
    }

    public int getGridIndex() {
        return gridIndex;
    }

    public int[] getCandidateIndices() {
        return candidateIndices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GridIndexCandidateIndices that = (GridIndexCandidateIndices) o;

        if (gridIndex != that.gridIndex) return false;
        return Arrays.equals(candidateIndices, that.candidateIndices);
    }

    @Override
    public int hashCode() {
        int result = gridIndex;
        result = 31 * result + Arrays.hashCode(candidateIndices);
        return result;
    }
}

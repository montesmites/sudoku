package se.montesmites.sudoku;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import se.montesmites.sudoku.sample.Sudoku_3x3;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class Sudoku_3x3_Extensive_Test {
    @TestFactory
    Stream<DynamicTest> easy() {
        return generate(Sudoku_3x3.EASY);
    }

    @TestFactory
    Stream<DynamicTest> intermediate() {
        return generate(Sudoku_3x3.INTERMEDIATE);
    }

    @TestFactory
    Stream<DynamicTest> hard() {
        return generate(Sudoku_3x3.HARD);
    }

    @TestFactory
    Stream<DynamicTest> veryHard() {
        return generate(Sudoku_3x3.VERY_HARD);
    }

    @TestFactory
    Stream<DynamicTest> diabolical() {
        return generate(Sudoku_3x3.DIABOLICAL);
    }

    private Stream<DynamicTest> generate(Sudoku_3x3 sudoku) {
        Stream.Builder<DynamicTest> tests = Stream.builder();
        var name = sudoku.name();
        tests.add(test(name + " – ALREADY SOLVED", sudoku.solved()));
        var grid = sudoku.solved();
        var unsolvedIndices = sudoku.unsolved().unsolvedIndices().collect(toList());
        for (int index = 0; index < unsolvedIndices.size(); index++) {
            grid = grid.unset(unsolvedIndices.get(index));
            tests.add(test(String.format("%s – %d unknown(s)", name, index + 1), grid));
        }
        return tests.build();
    }

    private DynamicTest test(String label, Grid clues) {
        return dynamicTest(label, toExecutable(clues));
    }

    private Executable toExecutable(Grid clues) {
        return () -> {
            var solution = new Sudoku().solve(clues);
            assertAll(
                    () -> assertTrue(solution.isPresent()),
                    () -> assertTrue(solution.isPresent() && solution.get().isASolutionTo(clues)));
        };
    }
}

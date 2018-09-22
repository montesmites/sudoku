package se.montesmites.sudoku.immutable;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BitVectorTest {
    private static Stream<Arguments> testdata() {
        return Stream.of(
                Arguments.of("0", "1"),
                Arguments.of("1", "0"),
                Arguments.of("00", "11"),
                Arguments.of("01", "10"),
                Arguments.of("10", "01"),
                Arguments.of("11", "00")
        );
    }

    @ParameterizedTest
    @MethodSource("testdata")
    void not(String source, String exp) {
        var size = source.length();
        var setBits = setBits(source, size);
        var vector = BitVector.of(size, setBits);
        var act = vector.not().toBinaryString();
        assertEquals(exp, act);
    }

    private IntStream setBits(String binary, int size) {
        final var setBits = new ArrayList<Integer>();
        for (var index = 0; index < size; index++) {
            if (binary.charAt(index) == '1') {
                setBits.add(index);
            }
        }
        return setBits.stream().mapToInt(i -> i);
    }
}

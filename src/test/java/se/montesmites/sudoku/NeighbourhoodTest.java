package se.montesmites.sudoku;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;
import static java.util.stream.IntStream.range;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NeighbourhoodTest {
    @Test
    void _1x1() {
        var order = 1;
        var side = order * order;
        var area = side * side;
        var rows = List.of("0");
        var cols = List.of("0");
        var boxes = List.of("0");
        var expected = neighbourhood(order, rows, cols, boxes);
        var actual = range(0, area).mapToObj(i -> new Neighbourhood(order).neighbourhoodOf(i).boxed().collect(toSet())).collect(toList());
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            var exp = expected.get(i);
            var act = actual.get(i);
            assertEquals(exp, act, String.format("at group %d", i));
        }
    }

    @Test
    void _2x2() {
        var order = 2;
        var side = order * order;
        var area = side * side;
        var rows = List.of(
                "0000",
                "1111",
                "2222",
                "3333"
        );
        var cols = List.of(
                "0123",
                "0123",
                "0123",
                "0123"
        );
        var boxes = List.of(
                "0011",
                "0011",
                "2233",
                "2233"
        );
        var expected = neighbourhood(order, rows, cols, boxes);
        var actual = range(0, area).mapToObj(i -> new Neighbourhood(order).neighbourhoodOf(i).boxed().collect(toSet())).collect(toList());
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            var exp = expected.get(i);
            var act = actual.get(i);
            assertEquals(exp, act, String.format("at group %d", i));
        }
    }

    @Test
    void _3x3() {
        var order = 3;
        var side = order * order;
        var area = side * side;
        var rows = List.of(
                "000000000",
                "111111111",
                "222222222",
                "333333333",
                "444444444",
                "555555555",
                "666666666",
                "777777777",
                "888888888"
        );
        var cols = List.of(
                "012345678",
                "012345678",
                "012345678",
                "012345678",
                "012345678",
                "012345678",
                "012345678",
                "012345678",
                "012345678"
        );
        var boxes = List.of(
                "000111222",
                "000111222",
                "000111222",
                "333444555",
                "333444555",
                "333444555",
                "666777888",
                "666777888",
                "666777888"
        );
        var expected = neighbourhood(order, rows, cols, boxes);
        var actual = range(0, area).mapToObj(i -> new Neighbourhood(order).neighbourhoodOf(i).boxed().collect(toSet())).collect(toList());
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            var exp = expected.get(i);
            var act = actual.get(i);
            assertEquals(exp, act, String.format("at group %d", i));
        }
    }

    private List<Set<Integer>> neighbourhood(int order, List<String> rows, List<String> cols, List<String> boxes) {
        var r = indicesNeighbours(order, rows);
        var c = indicesNeighbours(order, cols);
        var b = indicesNeighbours(order, boxes);
        return range(0, order * order * order * order)
                .mapToObj(i -> set(List.of(r.get(i), c.get(i), b.get(i))))
                .collect(toList());
    }

    private Set<Integer> set(List<Set<Integer>> sets) {
        return sets.stream().flatMap(Collection::stream).collect(toSet());
    }

    private Map<Integer, Set<Integer>> indicesNeighbours(int order, List<String> template) {
        var groupsIndices = range(0, order * order).mapToObj(row -> range(0, template.get(row).length())
                .mapToObj(i -> new IntTuple(Integer.parseInt(String.valueOf(template.get(row).charAt(i))), row * order * order + i)))
                                                   .flatMap(identity())
                                                   .collect(groupingBy(IntTuple::getGroup, mapping(IntTuple::getIndex, toSet())));
        return groupsIndices.entrySet().stream()
                            .map(Map.Entry::getValue)
                            .flatMap(neighbourhood -> neighbourhood.stream()
                                                                   .flatMap(n1 -> neighbourhood.stream()
                                                                                               .map(n2 -> new IntTuple(n1, n2))))
                            .collect(groupingBy(IntTuple::getGroup, mapping(IntTuple::getIndex, toSet())));
    }

    private class IntTuple {
        private final int group;
        private final int index;

        IntTuple(int group, int index) {
            this.group = group;
            this.index = index;
        }

        int getGroup() {
            return group;
        }

        int getIndex() {
            return index;
        }
    }
}

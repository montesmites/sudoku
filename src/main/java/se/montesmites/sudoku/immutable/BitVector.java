package se.montesmites.sudoku.immutable;

import java.util.BitSet;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BitVector {
    public static BitVector of(int size) {
        BitSet bitSet = new BitSet(size);
        return new BitVector(size, bitSet);
    }

    public static BitVector of(int size, IntStream indices) {
        BitSet bitSet = new BitSet(size);
        indices.forEach(bitSet::set);
        return new BitVector(size, bitSet);
    }

    public static BitVector of(int size, Stream<Integer> indices) {
        return BitVector.of(size, indices.mapToInt(Integer::intValue));
    }

    private final int size;
    private final BitSet bitSet;

    private BitVector(int size, BitSet bitSet) {
        this.size = size;
        this.bitSet = bitSet;
    }

    public int cardinality() {
        return bitSet.cardinality();
    }

    public boolean isSet(int index) {
        return bitSet.get(index);
    }

    public BitVector copy() {
        return new BitVector(size, (BitSet) bitSet.clone());
    }

    public BitVector not() {
        BitSet copy = (BitSet) bitSet.clone();
        copy.flip(0, size);
        return new BitVector(size, copy);
    }

    public BitVector and(BitVector other) {
        BitSet copy = (BitSet) bitSet.clone();
        copy.and(other.bitSet);
        return new BitVector(size, copy);
    }

    public boolean isEmpty() {
        return bitSet.isEmpty();
    }

    public Stream<Integer> setIndices() {
        return bitSet.stream().boxed();
    }

    public BitVector set(int index) {
        BitSet copy = (BitSet) bitSet.clone();
        copy.set(index);
        return new BitVector(size, copy);
    }

    String toBinaryString() {
        final StringBuilder buffer = new StringBuilder(size);
        IntStream.range(0, size).mapToObj(i -> bitSet.get(i) ? '1' : '0').forEach(buffer::append);
        return buffer.toString();
    }
}
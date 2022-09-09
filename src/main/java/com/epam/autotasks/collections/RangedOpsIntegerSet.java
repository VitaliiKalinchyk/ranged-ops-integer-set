package com.epam.autotasks.collections;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.TreeSet;

class RangedOpsIntegerSet extends AbstractSet<Integer> {

    private final TreeSet<Integer> elements = new TreeSet<>();

    public boolean add(int fromInclusive, int toExclusive) {

        boolean result = false;

        for (int i = fromInclusive; i < toExclusive; i++) {
            if (add(i)) {
                result = true;
            }
        }

        return result;
    }

    public boolean remove(int fromInclusive, int toExclusive) {
        boolean result = false;

        for (int i = fromInclusive; i < toExclusive; i++) {
            if (remove(i))
                result = true;
        }

        return result;
    }

    @Override
    public boolean add(final Integer integer) {
        return elements.add(integer);
    }

    @Override
    public boolean remove(final Object o) {
        return elements.remove(o);
    }

    @Override
    public Iterator<Integer> iterator() {
        return elements.iterator();
    }

    @Override
    public int size() {
        return elements.size();
    }
}

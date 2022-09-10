package com.epam.autotasks.collections;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

class RangedOpsIntegerSet extends AbstractSet<Integer> {

    private Integer[] elements = new Integer[0];

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
        if (elements.length == 0) {
            elements = new Integer[]{integer};
            return true;
        }
        int index = elements.length;
        for (int i = 0; i < elements.length; i++) {
            if (elements[i].equals(integer)) {
                return false;
            }
            if (elements[i] > integer) {
                index = i;
                break;
            }
        }
        Integer[] array = new Integer[elements.length + 1];
        for (int i = 0; i < array.length; i++) {
            if (i < index) {
                array[i] = elements[i];
            } else if (i == index) {
                array[i] = integer;
            } else {
                array[i] = elements[i - 1];
            }
        }
        elements = array;
        return true;
    }

    @Override
    public boolean remove(final Object o) {
        for (int i = 0; i < size(); i++) {
            if (elements[i].equals(o)) {
                Integer[] array = new Integer[elements.length - 1];
                for (int j = 0; j < elements.length; j++) {
                    if (j < i) {
                        array[j] = elements[j];
                    } else if (j > i) {
                        array[j - 1] = elements[j];
                    }
                }
                elements = array;
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<>() {
            int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor != elements.length;
            }

            @Override
            public Integer next() {
                if (cursor == elements.length)
                    throw new NoSuchElementException();
                return elements[cursor++];
            }
        };
    }

    @Override
    public int size() {
        return elements.length;
    }
}

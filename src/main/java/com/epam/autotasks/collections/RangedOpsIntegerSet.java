package com.epam.autotasks.collections;

import java.util.*;

class RangedOpsIntegerSet extends AbstractSet<Integer> {

    private Integer[] elements = new Integer[0];

    private int size;

    private static class SearchResult {
        int index;

        boolean contains;

        public SearchResult(int index, boolean contains) {
            this.index = index;
            this.contains = contains;
        }
    }

    @Override
    public boolean add(final Integer integer) {

        if (size == 0) {
            elements = new Integer[10];
            elements[0] = integer;
            size++;
            return true;
        }

        SearchResult searchResult = binarySearch(integer);
        if (searchResult.contains) {
            return false;
        }
        elementsCopy(searchResult.index, integer);
        size++;
        return true;
    }

    private void elementsCopy(int index, int integer) {
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, size * 2);
        }

        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = integer;
    }

    public boolean add(int fromInclusive, int toExclusive) {

        int indexFirst = binarySearch(fromInclusive).index;
        int indexLast = binarySearch(toExclusive, indexFirst).index;

        int range = toExclusive - fromInclusive;
        int newSize = size - indexLast + indexFirst + range;
        if (newSize == size)
            return false;

        elements = Arrays.copyOf(elements, newSize + 10);
        System.arraycopy(elements,indexLast, elements, indexFirst + range, size - indexLast);
        for (int i = fromInclusive; i < toExclusive; i++) {
            elements[indexFirst++] = i;
        }

        size = elements.length - 10;
        return true;
    }

    @Override
    public boolean remove(final Object o) {
        SearchResult searchResult = binarySearch((Integer) o);
        if (searchResult.contains) {
            int index = searchResult.index;
            System.arraycopy(elements, index + 1, elements, index, size - index - 1);
            size--;
            return true;
        }
        return false;
    }

    public boolean remove(int fromInclusive, int toExclusive) {

        int indexFirst = binarySearch(fromInclusive).index;
        int indexLast = binarySearch(toExclusive, indexFirst).index;

        int countsToRemove = indexLast - indexFirst;
        if (countsToRemove == 0)
            return false;

        System.arraycopy(elements,indexLast, elements, indexFirst, size - indexLast);
        size -= countsToRemove;
        return true;
    }

    private SearchResult binarySearch(int key) {
        return binarySearch(key, 0);
    }

    private SearchResult binarySearch(int key, int startIndex) {
        int low = startIndex;
        int high = size - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            int cmp = elements[mid].compareTo(key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return new SearchResult(mid, true);
        }
        return new SearchResult(low, false);
    }


    @Override
    public Iterator<Integer> iterator() {

        return new Iterator<>() {

            int cursor = 0;
            @Override
            public boolean hasNext() {
                return cursor != size;
            }

            @Override
            public Integer next() {
                if (cursor == size)
                    throw new NoSuchElementException();
                return elements[cursor++];
            }
        };
    }

    @Override
    public int size() {
        return size;
    }
}

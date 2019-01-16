package io.imulab.review.java.sort;

/**
 * Left of the cursor is sorted, right of the cursor is remaining. Insert the cursor item into the left portion by
 * finding its proper place.
 *
 * O(N^2), slightly less work than selection sort because the insertion process stops when the rest is sorted. In other
 * words, this sort depends on initial order of the data.
 *
 * Best case (already sorted), makes N - 1 compares and 0 swaps.
 * Worst case (reverse sorted), make about 1/2 * N^2 compares and about 1/2 * N^2 swaps.
 *
 * Insertion sort is beneficial for partially-sorted arrays (mainly sorted except for a few entries). Makes the process
 * almost linear.
 */
public class Insertion {

    static <E extends Comparable<E>> void sort(E[] elements) {
        for (int i = 0; i < elements.length; i++)
            insert(elements, i);
    }

    private static <E extends Comparable<E>> void insert(E[] elements, int c) {
        assert c > 0;
        assert c < elements.length;

        for (int i = c; i > 0 && elements[i-1].compareTo(elements[i]) > 0; i--) {
            Utility.swap(elements, i-1, i);
        }
    }

    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        Integer[] array = Utility.randomIntArray(32);

        System.out.println("Before:");
        Utility.printArray(array);

        sort(array);
        assert Utility.isSorted(array);

        System.out.println("After:");
        Utility.printArray(array);
    }
}

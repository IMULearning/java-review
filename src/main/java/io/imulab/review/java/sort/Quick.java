package io.imulab.review.java.sort;

import io.imulab.review.java.sort.application.Shuffle;

/**
 * Quick sort.
 *
 * Idea: select first item as pivot, use i to scan from left to right, and use j to scan from right to left. Stop when
 * a[i] >= pivot and/or a[j] < pivot. Swap out-of-place items a[i] and a[j]. When i and j crosses, it means we now have
 * an array which has a pivot item where left of pivot <= pivot <= right of pivot. Recursively execute this process.
 *
 * This algorithm is not stable.
 *
 * Performance: O(NlgN), on average O(1.39NlgN).
 */
public class Quick {

    static <E extends Comparable<E>> void sort(E[] elements) {
        Shuffle.knuthShuffle(elements);
        sort(elements, 0, elements.length - 1);
    }

    private static <E extends Comparable<E>> void sort(E[] elements, int lo, int hi) {
        // terminate recursion if we are down to 1 item.
        if (hi <= lo)
            return;

        // partition the array so that left of k <= k <= right of k
        int k = partition(elements, lo, hi);

        // sort left and right.
        sort(elements, lo, k - 1);
        sort(elements, k + 1, hi);
    }

    private static <E extends Comparable<E>> int partition(E[] elements, int lo, int hi) {
        assert elements.length > 0;

        int i = lo, j = hi + 1;
        while (true) {

            // while i < pivot, move right
            while (elements[++i].compareTo(elements[lo]) < 0)
                if (i == hi)
                    break;

            // while j > pivot, move left
            while (elements[lo].compareTo(elements[--j]) < 0)
                if (j == lo)
                    break;

            // stop if cursor crossed
            if (i >= j)
                break;

            // swap out of place items
            Utility.swap(elements, i, j);
        }

        // put pivot into place
        // now left of pivot <= pivot <= right of pivot
        Utility.swap(elements, lo, j);

        // return pivot position to start next round of recursion.
        return j;
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

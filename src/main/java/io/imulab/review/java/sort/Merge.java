package io.imulab.review.java.sort;

/**
 * Merge sort. Recursively divide the array into two halves until we reach sub array of size 1. Then merge two sorted
 * sub array together.
 *
 * Merge sort is optimal in terms of compare time.
 *
 * Time complexity is O(NlgN), Space complexity is O(N) due to the need of an auxiliary array in assisting merge.
 */
public class Merge {

    static <E extends Comparable<E>> void sort(E[] elements) {
        Comparable[] aux = new Comparable[elements.length];
        sort(elements, (E[]) aux, 0, elements.length - 1);
    }

    static <E extends Comparable<E>> void bottomUpSort(E[] elements) {
        Comparable[] aux = new Comparable[elements.length];

        // double the sub array size every time, executes logN times
        for (int sz = 1; sz < elements.length; sz += sz)
            // for every size * 2 segment, perform merge
            for (int low = 0; low + sz < elements.length; low += sz * 2)
                // do a Math.min on upper bound in case we overflow
                merge(elements, (E[]) aux, low, low + sz - 1, Math.min(low + sz * 2 - 1, elements.length - 1));
    }

    /**
     * Sort the array from lower bound to upper bound.
     *
     * @param a     original array
     * @param aux   auxiliary array assisting sort
     * @param lo    lower bound (inclusive)
     * @param hi    upper bound (inclusive)
     * @param <E>   type of array element
     */
    private static <E extends Comparable<E>> void sort(E[] a, E[] aux, int lo, int hi) {
        if (hi <= lo)
            return;
        int mid = (hi + lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    /**
     * Merge the two sorted arrays delimited by the middle index.
     *
     * @param a     original array
     * @param aux   auxiliary array assisting the merge
     * @param lo    lower bound (inclusive)
     * @param mid   middle index, which is the last index of the first sub array
     * @param hi    upper bound (inclusive)
     * @param <E>   type of the element
     */
    private static <E extends Comparable<E>> void merge(E[] a, E[] aux, int lo, int mid, int hi) {
        assert Utility.isSorted(a, lo, mid + 1);
        assert Utility.isSorted(a, mid + 1, hi + 1);

        // copy to aux
        if (hi + 1 - lo >= 0) System.arraycopy(a, lo, aux, lo, hi + 1 - lo);

        // merge
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)
                a[k] = aux[j++];
            else if (j > hi)
                a[k] = aux[i++];
            else if (aux[i].compareTo(aux[j]) < 0)
                a[k] = aux[i++];
            else
                a[k] = aux[j++];
        }

        assert Utility.isSorted(a, lo, mid + 1);
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

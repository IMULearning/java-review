package io.imulab.review.java.sort.application;

import io.imulab.review.java.sort.Utility;

/**
 * Use quick sort like algorithm to select kth item in an array.
 */
public class Select {

    static <E extends Comparable<E>> E select(E[] elements, int k) {
        int lo = 0, hi = elements.length - 1;

        while (lo < hi) {
            int p = partition(elements, lo, hi);

            // if there are (less than k) items below, the item is above.
            if (p < k)
                lo = p + 1;

            // if there are (more than k) items below, the item is under.
            else if (p > k)
                hi = p - 1;

            // we hit jackpot
            else
                return elements[p];
        }

        // here, we have lo = hi = k = pivot
        return elements[k];
    }

    @SuppressWarnings("Duplicates")
    private static <E extends Comparable<E>> int partition(E[] a, int lo, int hi) {
        assert a.length > 0;

        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i].compareTo(a[lo]) < 0)
                if (i == hi)
                    break;

            while (a[lo].compareTo(a[--j]) < 0)
                if (j == lo)
                    break;

            if (i >= j)
                break;

            Utility.swap(a, i, j);
        }

        Utility.swap(a, lo, j);

        return j;
    }
}

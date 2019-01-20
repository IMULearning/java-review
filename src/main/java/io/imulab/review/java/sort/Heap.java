package io.imulab.review.java.sort;

/**
 * Heap sort.
 *
 * Heap construction uses at most 2N compares and exchanges.
 * Heap sort uses at most 2NlgN compares and exchanges.
 *
 * But this sort is not stable.
 */
public class Heap {

    static <E extends Comparable<E>> void sort(E[] elements, int N) {
        // index 0 should not be used, to make indexing easier.
        assert N == elements.length - 1;

        // build heap
        for (int k = N / 2; k >= 1; k--)
            sink(elements, k, N);

        // swap first (max) with last and move it out of the heap
        while (N > 1) {
            Utility.swap(elements, 1, N--);
            sink(elements, 1, N);
        }
    }

    /**
     * Let the child swim up and switch with parent when it is larger than parent.
     *
     * @param elements  array
     * @param k         the index of child, index is 1-based.
     * @param N         the size of the effective array
     * @param <E>       array element type
     */
    private static <E extends Comparable<E>> void swim(E[] elements, int k, int N) {
        assert k <= N && k > 0;

        while (k > 1 && elements[k].compareTo(elements[parent(k)]) > 0) {
            Utility.swap(elements, k, parent(k));
            k = parent(k);
        }
    }

    /**
     * Left the parent sink down and switch with the larger one of its children when they are larger than parent.
     *
     * @param elements  array
     * @param k         the index of parent, index is 1-based.
     * @param N         the size of the effective array
     * @param <E>       array element type
     */
    private static <E extends Comparable<E>> void sink(E[] elements, int k, int N) {
        assert k <= N && k > 0;

        while (true) {
            int candidate = left(k);
            if (candidate > N)
                break;

            if (right(k) <= N && elements[candidate].compareTo(elements[right(k)]) < 0)
                candidate = right(k);

            if (elements[k].compareTo(elements[candidate]) > 0)
                break;

            Utility.swap(elements, k, candidate);
            k = candidate;
        }
    }

    private static int left(int k) {
        return 2 * k;
    }

    private static int right(int k) {
        return 2 * k + 1;
    }

    private static int parent(int k) {
        return k / 2;
    }

    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        Integer[] array = Utility.randomIntArray(33);
        array[0] = -1;          // since we are using 1-based index representation of heap, set index 0 to nil.

        System.out.println("Before:");
        Utility.printArray(array);

        sort(array, 32);
        assert Utility.isSorted(array);

        System.out.println("After:");
        Utility.printArray(array);
    }
}

package io.imulab.review.java.sort;

/**
 * Left of the cursor is sorted. Right of the cursor is remaining. Select the minimum of the remaining items
 * and exchange it with the cursor item.
 *
 * O(N^2), not intelligent about an already sorted array.
 */
public class Selection {

    static <E extends Comparable<E>> void sort(E[] elements) {
        for (int i = 0; i < elements.length; i++) {
            Utility.swap(elements, i, min(elements, i));
        }
    }

    private static <E extends Comparable<E>> int min(E[] elements, int startInclusive) {
        assert elements.length > 0;
        assert startInclusive < elements.length;

        int index = startInclusive;
        E min = elements[startInclusive];

        for (int i = startInclusive; i < elements.length; i++) {
            if (elements[i].compareTo(min) < 0) {
                index = i;
                min = elements[i];
            }
        }

        return index;
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

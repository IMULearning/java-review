package io.imulab.review.java.sort;

/**
 * Select the minimum item in the remaining array and exchange it with the cursor item.
 *
 * O(N^2)
 */
public class Selection {

    static <E extends Comparable<E>> void sort(E[] elements) {
        for (int i = 0; i < elements.length; i++) {
            swap(elements, i, min(elements, i));
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

    private static <E extends Comparable<E>> void swap(E[] elements, int i, int j) {
        E temp = elements[i];
        elements[i] = elements[j];
        elements[j] = temp;
    }

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

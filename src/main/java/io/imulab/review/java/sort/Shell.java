package io.imulab.review.java.sort;

import java.util.*;

/**
 * A sorting algorithm that skip sorts every h-th element in the array. Combined with a decreasing sequence of h which
 * ends with 1, the array will become increasingly sorted. The operation of sort every h-th element is implemented
 * with simple insertion sort.
 *
 * It is very difficult to determine the overall complexity of the algorithm. It is generally believed, with 3x+1
 * sequence, to be O(N^(3/2)) in the worst case. But generally, it will be much better than that.
 */
public class Shell {

    static <E extends Comparable<E>> void sort(E[] elements, Sequence sequenceGenerator) {
        assert elements.length > 0;

        if (elements.length == 1)
            return;

        if (sequenceGenerator == null)
            sequenceGenerator = new ThreePlusOne();
        Iterator<Integer> skipSequence = sequenceGenerator.generate(elements.length);

        while (skipSequence.hasNext()) {
            int h = skipSequence.next();

            for (int i = h; i < elements.length; i++) {
                insert(elements, i, h);
            }
        }
    }

    /**
     * Perform the insertion operation. Move the cursor item from right to left into correct place. Compare only with
     * the h-th element to its left and only swap when they are out of order.
     *
     * @param elements  array to perform insertion on.
     * @param c         the current cursor index
     * @param h         the current skip number
     * @param <E>       type of the array element
     */
    private static <E extends Comparable<E>> void insert(E[] elements, int c, int h) {
        for (int j = c; j >= h && elements[j - h].compareTo(elements[j]) > 0; j -= h) {
            Utility.swap(elements, j, j - h);
        }
    }

    /**
     * Sequence generator for the shell sort. Returns a queue whose elements are in descending order and stands
     * for the skip number in shell sort.
     */
    private interface Sequence {
        Iterator<Integer> generate(int size);
    }

    /**
     * An sequence implementing the 3*x+1 sequence.
     */
    private static class ThreePlusOne implements Sequence {
        @Override
        public Iterator<Integer> generate(int size) {
            Deque<Integer> deq = new ArrayDeque<>();
            int h = 1;

            while (h < size) {
                deq.offer(h);
                h = h * 3 + 1;
            }

            return deq.descendingIterator();
        }
    }

    /**
     * A sequence implementing 2^x-1.
     */
    private static class PowerOfTwoMinusOne implements Sequence {
        @Override
        public Iterator<Integer> generate(int size) {
            Deque<Integer> deq = new ArrayDeque<>();
            int h = 1;

            while (h < size) {
                deq.offer(h);
                h = (h + 1) * 2 - 1;
            }

            return deq.descendingIterator();
        }
    }

    /**
     * A sequence implementing the Sedgewick (1985) sequence of 4^k + 3*2^(k-1) + 1, prefixed with 1.
     *
     * https://en.wikipedia.org/wiki/Shellsort
     */
    private static class Sedgewick1985 implements Sequence {
        @Override
        public Iterator<Integer> generate(int size) {
            Deque<Integer> deq = new ArrayDeque<>();
            int h = 1;

            for (int i = 1; h < size; i++) {
                deq.offer(h);
                h = 4^i + 3*2^(i-1) + 1;
            }

            return deq.descendingIterator();
        }
    }

    public static void main(String[] args) {
        Integer[] array = Utility.randomIntArray(100);

        System.out.println("Before:");
        Utility.printArray(array);

        sort(array, new Sedgewick1985());
        assert Utility.isSorted(array);

        System.out.println("After:");
        Utility.printArray(array);
    }
}

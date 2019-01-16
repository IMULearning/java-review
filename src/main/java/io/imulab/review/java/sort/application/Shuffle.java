package io.imulab.review.java.sort.application;

import io.imulab.review.java.sort.Utility;

import java.util.concurrent.ThreadLocalRandom;

public class Shuffle {

    /**
     * Similar to insertion sort. Cursor moves from left to right. Left portion is already shuffled. Randomly select
     * and index in the left portion and swap with cursor.
     *
     * @param elements  array to be shuffled.
     * @param <E>       type of array.
     */
    static <E extends Comparable<E>> void knuthShuffle(E[] elements) {
        for (int i = 1; i < elements.length; i++) {
            int r = ThreadLocalRandom.current().nextInt(0, i);
            Utility.swap(elements, i, r);
        }
    }

    public static void main(String[] args) {
        Integer[] array = Utility.randomIntArray(100);

        System.out.println("Before:");
        Utility.printArray(array);

        knuthShuffle(array);

        System.out.println("After:");
        Utility.printArray(array);
    }
}

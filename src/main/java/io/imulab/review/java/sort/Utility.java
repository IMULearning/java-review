package io.imulab.review.java.sort;

import java.util.concurrent.ThreadLocalRandom;

class Utility {

    static <E extends Comparable<E>> boolean isSorted(E[] elements) {
        if (elements.length <= 1)
            return true;

        for (int i = 1; i < elements.length; i++) {
            if (elements[i - 1].compareTo(elements[i]) > 0)
                return false;
        }

        return true;
    }

    static void printArray(Object[] elements) {
        System.out.print("[");

        for (int i = 0; i < elements.length; i++) {
            if (i != 0)
                System.out.print(", ");
            System.out.print(elements[i]);
        }

        System.out.println("]");
    }

    static Integer[] randomIntArray(int size) {
        assert size > 0;

        Integer[] array = new Integer[size];
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(0, 100);
        }

        return array;
    }
}

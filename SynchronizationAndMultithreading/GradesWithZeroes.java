/*
Во оваа верзија во низата се ставаат нули на рандом позиции (на пример 100 нули).
Просекот треба да се пресмета на тој начин што збирот од оцените
ќе се подели со бројот на нули одземен од вкупниот број на оцени.
(sum / (numGrades - numZeroes)
*/

package SynchronizationAndMultithreading;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class GradesWithZeroes {
    static double average = 0;

    static final BoundedRandomGenerator random = new BoundedRandomGenerator();

    private static final int ARRAY_LENGTH = 10000000;

    private static final int NUM_THREADS = 10;

    static long totalSum = 0;
    static int counter = 0;


    // TODO: Define synchronization elements
    static ReentrantLock lock;
    static Semaphore semaphore;

    static void init() {
        // TODO: Initialize synchronization elements
        lock = new ReentrantLock();
        semaphore = new Semaphore(0);
    }

    // DO NOT CHANGE
    public static int[] getSubArray(int[] array, int start, int end) {
        return Arrays.copyOfRange(array, start, end);
    }

    public static void main(String[] args) {
        init();

        int[] arr = ArrayGenerator.generate(ARRAY_LENGTH);

        // TODO: Make the CalculateThread class a thread and start 10 instances
        // Each instance should take a subarray from the original array with equal length

        int elementsPerThread = ARRAY_LENGTH / NUM_THREADS;
        Thread[] threads = new Thread[NUM_THREADS];

        // TODO: Create and start 10 threads for calculating the average grade
        for (int i = 0; i < NUM_THREADS; i++) {
            int start = i * elementsPerThread;
            int end;
            end = (i + 1) * elementsPerThread;
            threads[i] = new CalculateThread(arr, start, end);
            threads[i].start();
        }

        try {
            semaphore.acquire(NUM_THREADS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Replace the call to calculateAverageGrade below with calculateAverageGradeParallel
        // TODO: Update the value of the global variable average
        average = (double) totalSum / (ARRAY_LENGTH - counter);

        // DO NOT CHANGE

        System.out.println("Your calculated average grade is: " + average);
        System.out.println("The actual average grade is: " + ArrayGenerator.actualAvg);

        SynchronizationChecker.checkResult();
    }


    // TODO: Make the CalculateThread class a thread, you can add methods and attributes
    static class CalculateThread extends Thread {
        private int[] arr;
        int startSearch;
        int endSearch;

        public CalculateThread(int[] arr, int startSearch, int endSearch) {
            this.arr = arr;
            this.startSearch = startSearch;
            this.endSearch = endSearch;
        }

        public void run() {
            calculateAverageGradeParallel();
        }

        public Double calculateAverageGrade() {
            return Arrays.stream(arr).average().getAsDouble();
        }

        public void calculateAverageGradeParallel() {
            // TODO: Implement and run this method from the thread
            // The method should not return a result
            // Take care of the proper synchronization
            long localSum = 0;
            for (int i = startSearch; i < endSearch; i++) {
                localSum += arr[i];
                if (arr[i] == 0) {
                    lock.lock();
                    counter++;
                    lock.unlock();
                }
            }
            lock.lock();
            totalSum += localSum;
            lock.unlock();

            semaphore.release();
        }
    }

    /******************************************************
     // DO NOT CHANGE THE CODE BELOW TO THE END OF THE FILE
     *******************************************************/

    static class BoundedRandomGenerator {
        static final Random random = new Random();
        static final int RANDOM_BOUND_UPPER = 10;
        static final int RANDOM_BOUND_LOWER = 6;

        public int nextInt() {
            return random.nextInt(RANDOM_BOUND_UPPER - RANDOM_BOUND_LOWER) + RANDOM_BOUND_LOWER;
        }
    }

    static class ArrayGenerator {

        private static double actualAvg = 0;

        static int[] generate(int length) {
            int[] array = new int[length];

            for (int i = 0; i < length; i++) {
                int grade = Grades.random.nextInt();
                array[i] = grade;
            }

            // added for loops
            Random rand = new Random();
            for (int i = 0; i < 100; i++) {
                int randomIndex = rand.nextInt(ARRAY_LENGTH);
                array[randomIndex] = 0;
            }

            int count = 0;
            for (int i = 0; i < length; i++) {
                if (array[i] == 0)
                    count++;
                actualAvg += array[i];
            }
            //

            actualAvg /= (array.length - count);

            return array;
        }
    }

    static class SynchronizationChecker {
        public static void checkResult() {
            if (ArrayGenerator.actualAvg != average) {
                throw new RuntimeException("The calculated result is not equal to the actual average grade!");
            }
        }
    }
}
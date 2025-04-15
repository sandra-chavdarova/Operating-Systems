/*
Во оваа верзија оцените се наоѓаат во матрица 100х100.
За секоја редица од матрицата треба да има посебна нишка.
*/

package SynchronizationAndMultithreading;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class GradesMatrix {
    static double average = 0;

    static final BoundedRandomGenerator random = new BoundedRandomGenerator();

    private static final int MATRIX_LENGTH = 100;

    private static final int NUM_THREADS = 100;

    static long totalSum = 0;


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

        int[][] matrix = ArrayGenerator.generate(MATRIX_LENGTH);

        // TODO: Make the CalculateThread class a thread and start 10 instances
        // Each instance should take a subarray from the original array with equal length

        int elementsPerThread = MATRIX_LENGTH; //
        Thread[] threads = new Thread[NUM_THREADS];

        // TODO: Create and start 10 threads for calculating the average grade
        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new CalculateThread(matrix[i]);
            threads[i].start();

        }

        try {
            semaphore.acquire(NUM_THREADS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Replace the call to calculateAverageGrade below with calculateAverageGradeParallel
        // TODO: Update the value of the global variable average
        average = (double) totalSum / (MATRIX_LENGTH * MATRIX_LENGTH);

        // DO NOT CHANGE

        System.out.println("Your calculated average grade is: " + average);
        System.out.println("The actual average grade is: " + ArrayGenerator.actualAvg);

        SynchronizationChecker.checkResult();
    }


    // TODO: Make the CalculateThread class a thread, you can add methods and attributes
    static class CalculateThread extends Thread {
        private int[] row;

        public CalculateThread(int[] arr) {
            this.row = arr;
        }

        public void run() {
            calculateAverageGradeParallel();
        }

        public Double calculateAverageGrade() {
            return Arrays.stream(row).average().getAsDouble();
        }

        public void calculateAverageGradeParallel() {
            // TODO: Implement and run this method from the thread
            // The method should not return a result
            // Take care of the proper synchronization
            long localSum = 0;
            for (int i = 0; i < row.length; i++) {
                localSum += row[i];
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

        static int[][] generate(int length) {
            int[][] matrix = new int[length][length];

            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {
                    int grade = Grades.random.nextInt();
                    actualAvg += grade;
                    matrix[i][j] = grade;
                }
            }

            actualAvg /= (matrix.length * matrix.length);

            return matrix;
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


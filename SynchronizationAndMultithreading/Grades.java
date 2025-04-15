/*
Дадени се оцени од студенти од сите факултети во Македонија. Оцените се ставени во низа од 1,000,000 елементи.
Потребно е да се имплементира пресметување на просечна оцена.
Поради големината и со цел забрзување на пресметката, имплементацијата треба да се направи со повеќе нитки.
Во продолжение е дадено секвенцијално решение на проблемот.
Модифицирајте го решението според барањата на задачата.

Дефинирајте ги сите потребни елементи за синхронизација и иницијализирајте ги во методот init().
Дозволено е да дефинирате и други глобални и локални променливи доколку ви се потребни при правење на пресметката.

Со оглед на тоа што низата е голема, оптимизацијата на пребарувањето треба да биде паралелизирана со повеќе нитки.
Да се модифицира кодот со тоа што ќе се стартуваат 10 нитки и секоја од нив ќе пребарува низ подниза со еднаква големина.
На крај, треба да се испечати просечната оцена од студентите на сите факултети (променливата average).
Имплементирајте ја функцијата calculateAverageGradeParallel.
Функцијата за паралелна пресметка не треба да враќа резултат.
Предавањето на резултатот назад кон главната нитка треба да се направи на друг начин.

Напомена: Да не се менува кодот онаму каде што е напишано DO NOT CHANGE.
Да се внимава на правилно стартување и стопирање на нитките, како и на евентуална потреба од синхронизација.
-----------------------------------------------------------------------------------------------------------------------
Grades from students across all faculties in Macedonia are given.
The grades are placed in an array of 1,000,000 elements.
It is required to implement the calculation of the average grade.
Due to the size and in order to speed up the computation, the implementation should be done using multiple threads.
Below is a sequential solution to the problem. Modify the solution according to the task requirements.

Define all necessary synchronization elements and initialize them in the init() method.
You are allowed to define other global and local variables if needed during the computation.

Given the large size of the array, the search optimization should be parallelized using multiple threads.
The code should be modified so that 10 threads are started, and each of them processes a subarray of equal size.
At the end, the average grade of students from all faculties (the variable average) should be printed.
Implement the function calculateAverageGradeParallel.
The function for parallel computation should not return a result.
Passing the result back to the main thread should be done in another way.

Note: Do not change the code where it is marked with DO NOT CHANGE.
Make sure to correctly start and stop the threads, and handle any synchronization if needed.
*/

package SynchronizationAndMultithreading;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Grades {
    static double average = 0;

    static final BoundedRandomGenerator random = new BoundedRandomGenerator();

    private static final int ARRAY_LENGTH = 10000000;

    private static final int NUM_THREADS = 10;

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
        average = (double) totalSum / ARRAY_LENGTH;

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
                actualAvg += grade;
                array[i] = grade;
            }

            actualAvg /= array.length;

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

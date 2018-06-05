/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortinghandin;

import java.util.Random;

/**
 *
 * @author kasper
 */
public class SortingHandIn {

    private Random ran = new Random();
    private int ranMax = 1000;
    private Sorter sorter = new Sorter();
    private static double prevTime;

    /**
     * Start the experiments
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        SortingHandIn runSort = new SortingHandIn();

        runSort.testInsertion(1000, 100);
        runSort.testInsertion(2000, 100);
        runSort.testInsertion(4000, 100);
        runSort.testInsertion(8000, 100);
        runSort.testInsertion(16000, 100);
        runSort.testInsertion(32000, 100);
        runSort.testInsertion(64000, 100);

        //Reset the prevTime
        prevTime = 0.0;
        runSort.testSelection(1000, 100);
        runSort.testSelection(2000, 100);
        runSort.testSelection(4000, 100);
        runSort.testSelection(8000, 100);
        runSort.testSelection(16000, 100);
        runSort.testSelection(32000, 100);
        runSort.testSelection(64000, 100);

        //Reset the prevTime
        prevTime = 0.0;
        runSort.testMerge(1000, 100);
        runSort.testMerge(2000, 100);
        runSort.testMerge(4000, 100);
        runSort.testMerge(8000, 100);
        runSort.testMerge(16000, 100);
        runSort.testMerge(32000, 100);
        runSort.testMerge(64000, 100);

    }

    /**
     * Helper method to insert data into the array
     *
     * @param num value for random number
     * @return returns the filled array
     */
    private int[] insertTestData(int num) {
        int[] temp = new int[num];
        for (int i = 0; i < num; i++) {
            temp[i] = ran.nextInt(ranMax);
        }
        return temp;
    }

    /**
     * Method to test the insertionSort
     *
     * @param num value for random number
     * @param times number of times to run experiment
     */
    private void testInsertion(int num, int times) {
        long time1, time2;
        double actualTime = 0;
        double ratio;

        int[] arr = new int[num];
        for (int i = 0; i < times; i++) {
            arr = insertTestData(num);
            time1 = System.currentTimeMillis();
            sorter.insertionSort(arr);
            time2 = System.currentTimeMillis();

            actualTime += (time2 - time1);
        }
        ratio = actualTime / prevTime;

        prevTime = actualTime;

        System.out.println("Insertion: "
                + "N: " + arr.length
                + " Elapsed time/Millisecond: " + actualTime
                + " Ratio to prev: " + ratio);
    }

    /**
     * Method to test the selectionSort
     *
     * @param num value for random number
     * @param times number of times to run experiment
     */
    private void testSelection(int num, int times) {
        long time1, time2;
        double actualTime = 0;
        double ratio;

        int[] arr = new int[num];
        for (int i = 0; i < times; i++) {
            arr = insertTestData(num);
            time1 = System.currentTimeMillis();
            sorter.selectionSort(arr);
            time2 = System.currentTimeMillis();

            actualTime += (time2 - time1);
        }
        ratio = actualTime / prevTime;

        prevTime = actualTime;

        System.out.println("Selection: "
                + "N: " + arr.length
                + " Elapsed time/Millisecond: " + actualTime
                + " Ratio to prev: " + ratio);
    }

    /**
     * Method to test the mergeSort
     *
     * @param num value for random number
     * @param times number of times to run experiment
     */
    private void testMerge(int num, int times) {
        long time1, time2;
        double actualTime = 0;
        double ratio;

        int[] arr = new int[num];
        for (int i = 0; i < times; i++) {
            arr = insertTestData(num);
            time1 = System.currentTimeMillis();
            sorter.mergeSort(arr);
            time2 = System.currentTimeMillis();

            actualTime += (time2 - time1);
        }
        ratio = actualTime / prevTime;

        prevTime = actualTime;

        System.out.println("Merge: "
                + "N: " + arr.length
                + " Elapsed time/Millisecond: " + actualTime
                + " Ratio to prev: " + ratio);
    }

}

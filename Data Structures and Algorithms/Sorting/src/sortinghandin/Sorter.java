/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortinghandin;

/**
 *
 * @author kasper
 */
public class Sorter {

    private int[] numbers;
    private int[] helper;
    private int number;

    /**
     * Sorting method using insertionSort
     *
     * @param tempArr the array to sort
     */
    public void insertionSort(int[] tempArr) {

        for (int i = 0; i < tempArr.length; i++) {
            int tempInt = tempArr[i];
            int j;
            for (j = i - 1; j >= 0 && tempInt < tempArr[j]; j--) {

                tempArr[j + 1] = tempArr[j];
            }
            tempArr[j + 1] = tempInt;
        }
    }

    /**
     * Sorting method using selectionSort
     *
     * @param tempArr the array that will be sorted
     */
    public void selectionSort(int[] tempArr) {
        int tempInt;
        for (int i = 0; i < tempArr.length - 1; i++) {
            int index = i;
            for (int j = i + 1; j < tempArr.length; j++) {
                if (tempArr[j] < tempArr[index]) {
                    index = j;
                }
            }
            tempInt = tempArr[index];
            tempArr[i] = tempArr[index];
            tempArr[index] = tempInt;
        }
    }

    /**
     * Sorting method using mergeSort
     *
     * @param tempArr taking the array in that will be sorted
     */
    public void mergeSort(int[] tempArr) {
        this.numbers = tempArr;
        number = tempArr.length;
        this.helper = new int[number];
        sortMerge(0, number - 1);
    }

    /**
     * Helper method to mergeSort. Starting the merging of the 2 sides
     *
     * @param low
     * @param high
     */
    private void sortMerge(int low, int high) {
        if (low < high) {
            int middle = low + (high - low) / 2;
            sortMerge(low, middle);
            sortMerge(middle + 1, high);
            merge(low, middle, high);
        }
    }

    /**
     * Helper method to mergeSort. Performing the merging of the 2 sides
     *
     * @param low
     * @param middle
     * @param high
     */
    private void merge(int low, int middle, int high) {
        for (int i = low; i <= high; i++) {
            helper[i] = numbers[i];
        }

        int i = low;
        int j = middle + 1;
        int k = low;
        while (i <= middle && j <= high) {
            if (helper[i] <= helper[j]) {
                numbers[k] = helper[i];
                i++;
            } else {
                numbers[k] = helper[j];
                j++;
            }
            k++;
        }
        while (i <= middle) {
            numbers[k] = helper[i];
            k++;
            i++;
        }
    }
}

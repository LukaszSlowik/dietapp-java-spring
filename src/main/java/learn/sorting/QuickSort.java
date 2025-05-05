package learn.sorting;

import java.util.Map;

public class QuickSort {


    //main method to use
    public static void sort(int[] array) {
        //check if exists
        if (array != null && array.length > 1) {
            quickSort(array, 0, array.length - 1);
        }
    }

    private static int getPivotIndexFromMedianOfThree(int[] array,int left,int right){
        int mid = left + (right- left)/2;
        if(array[left]> array[mid]) swap(array,left,mid);
        if(array[left]> array[right]) swap(array,left,right);
        if(array[mid]> array[right]) swap(array,mid,right);


        return mid;
    }


    public static void quickSort(int[] array, int left, int right) {
        //only if more than one element and not already sorted
        if (left < right) {

            //get pivot index
            int pivotIndex = getPivotIndexFromMedianOfThree(array, left, right);

            //place pivot at the end;
            swap(array, pivotIndex, right);

            //partition and get partition index
            int partitionIndex = partition(array, left, right);

            quickSort(array, left, partitionIndex - 1);
            quickSort(array, partitionIndex + 1, right);

        }
    }

    private static int partition(int[] array, int left, int right) {
        //get pivot
        int pivot = array[right];
        //get i
        int i = left -1;
        //loop every element (j)
        for(int j = left;j<right;j++){
        //if element value <= pivot then swap
            if(array[j]<=pivot){
                i++;
                swap(array,i,j);
            }
        }
        //after loop place pivot after i ( so after the small values )
        swap(array,i +1,right);
        //return pivot place ( i + 1)
        return i+1;
    }



    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}

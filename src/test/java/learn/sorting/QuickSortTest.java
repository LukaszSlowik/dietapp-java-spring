package learn.sorting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {

    @Test
    void sort() {

        int[] notSorted = {4,6,9,2,5,10};
        int[] shouldBe = {2,4,5,6,9,10};
        QuickSort.sort(notSorted);

        assertArrayEquals(notSorted,shouldBe);
    }
}
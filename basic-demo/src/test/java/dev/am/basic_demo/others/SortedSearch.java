package dev.am.basic_demo.others;

import java.util.Arrays;

public class SortedSearch {
    public static int countNumbers(int[] sortedArray, int lessThan) {
        int index = Arrays.binarySearch(sortedArray, lessThan);
        if (index < 0) {
            // Element not found.
            // Binary search returns: -(insertion point) -1
            return - (index + 1);
        } else {
            // Element found.
            // We need to find the FIRST occurrence of this value,
            // in case there are duplicates (e.g. [1,2,2,2,4], lessThan 2)
            while (index >= 0 && sortedArray[index - 1] == lessThan) {
                index--;
            }

            return index;
        }
    }

    static void main() {
        IO.println(countNumbers(new int[] {1,2,2,2,3,3,3,4}, 3));
    }
}

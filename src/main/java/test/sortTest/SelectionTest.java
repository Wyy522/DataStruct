package test.sortTest;

import sort.Bubble;
import sort.Selection;

import java.util.Arrays;

public class SelectionTest {
    public static void main(String[] args) {
        Integer[] arr ={4,5,6,1,2,3};
        Selection.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}

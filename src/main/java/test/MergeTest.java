package test;

import sort.Merge;
import sort.Selection;

import java.util.Arrays;

public class MergeTest {
    public static void main(String[] args) {
        Integer[] arr ={4,5,6,1,2,3,7,9,8};
        Merge.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}

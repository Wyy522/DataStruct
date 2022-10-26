package test;

import sort.Bubble;
import sort.Insertion;

import java.util.Arrays;

public class InsertionTest {
    public static void main(String[] args) {
        Integer[] arr ={4,5,6,1,2,3};
        Insertion.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}

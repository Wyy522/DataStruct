package test.sortTest;

import sort.Insertion;
import sort.Shell;

import java.util.Arrays;

public class ShellTest {
    public static void main(String[] args) {
        Integer[] arr ={4,5,6,1,2,3,7,9,8};
        Shell.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class APP {
    public static void main(String[] args) {
        binarySearchTest();
    }

    //二分查找测试
    private static void binarySearchTest() {
        List<Integer> a = new ArrayList<>(4);
        a.add(5);
        a.add(6);
        System.out.println("------------------");
        int loc = Collections.binarySearch(a, 4);
        int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
        System.out.println(loc);
        System.out.println(childIndex);
    }
}

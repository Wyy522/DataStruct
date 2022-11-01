import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class APP {
    public static void main(String[] args) {

    }

    //二分查找测试
    private static void binarySearchTest() {
        List<Integer> a = new ArrayList<>(4);
        a.add(5);
        a.add(6);
        a.add(8);
        a.add(25);
        System.out.println(Collections.binarySearch(a, 0));
        System.out.println(Collections.binarySearch(a, 7));
        System.out.println(Collections.binarySearch(a, 20));
        System.out.println("------------------");
        int loc = Collections.binarySearch(a, 4);
        int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
        System.out.println(childIndex);
    }
}

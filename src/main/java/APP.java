import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class APP {
    public static void main(String[] args) {
        List<String> s = new ArrayList<>();
        s.add("6");
        String s1 = "10";
        int i = Collections.binarySearch(s, s1);
        System.out.println(i);
        int valueIndex = i >= 0 ? i + 1 : -i - 1;
        System.out.println(valueIndex);

        List<Integer> x = new ArrayList<>();
        x.add(6);
        int x1 = 10;
        int ii = Collections.binarySearch(x, x1);
        System.out.println(ii);
        int valueIndexx = ii >= 0 ? ii + 1 : -ii - 1;
        System.out.println(valueIndexx);
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

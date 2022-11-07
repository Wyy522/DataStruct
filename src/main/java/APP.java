import tree.mylsmtree.FileUtils;
import tree.mylsmtree.LsmTreeDB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class APP {
    public static void main(String[] args) throws InterruptedException {
        String[] strings=new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t"};
        LsmTreeDB lsmTreeDB=new LsmTreeDB("src/LsmTreeTestFile");
        lsmTreeDB.start();
        for (int i = 0; i < 20; i++) {
            lsmTreeDB.set(strings[i],String.valueOf(i));
        }
        Thread.sleep(1000);
        lsmTreeDB.stop();
    }

    private static void CASTest() {
        AtomicBoolean a = new AtomicBoolean(true);
        if (a.compareAndSet(false, true)) {
            System.out.println("true-> a :" + a.get());
            return;
        }
        System.out.println("false-> a :" + a.get());
    }

    private static void binarySearchTest2() {
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

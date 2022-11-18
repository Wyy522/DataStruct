import org.apache.commons.lang.RandomStringUtils;
import tree.mylsmtrees.Command;
import tree.mylsmtrees.LSMTreeImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static tree.mylsmtrees.Constant.PATH;

public class APP {
    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println(4+"/"+2+"is"+4/2);
    }

    private static void extracted() throws IOException, InterruptedException {
        LSMTreeImpl lsmTreeDB=new LSMTreeImpl(PATH);
        lsmTreeDB.start();
        for (int i = 0; i <160; i++) {
            lsmTreeDB.set(RandomStringUtils.randomAlphabetic(5),String.valueOf(i));
        }
        lsmTreeDB.loadSSTableToMemory(PATH,0,0);
        lsmTreeDB.loadSSTableToMemory(PATH,0,1);
//        lsmTreeDB.loadSSTableToMemory(PATH,0,2);

//        System.out.println(lsmTreeDB.get("SsheL"));
        lsmTreeDB.merge();
//        System.out.println(command.getKey());
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

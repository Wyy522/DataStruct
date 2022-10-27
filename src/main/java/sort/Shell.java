package sort;

//希尔排序本质上是分组了的插入排序
public class Shell {

    public static void sort(Comparable[] a) {
        //1.根据数组a的长度确定增长量h的初始值
        int h = a.length / 2;
        while (h >=1) {
            for (int i = 0; i <(a.length-1)/ h; i++) {
                if (greater(a[i], a[i + h])) {
                    exChange(a, i, i + h);
                }
            }
            //3.缩短插入间隔
            h = h/2;
        }

    }

    private static boolean greater(Comparable v, Comparable w) {
        return v.compareTo(w) > 0;
    }

    private static void exChange(Comparable[] a, int i, int j) {
        Comparable temp;
        temp = a[j];
        a[j] = a[i];
        a[i] = temp;
    }
}

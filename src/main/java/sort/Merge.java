package sort;

public class Merge {

    private static Comparable[] assist;

    public static void sort(Comparable[] a) {
        assist = new Comparable[a.length];
        int lo = 0, hi = a.length - 1;
        sort(a, lo, hi);
    }

    public static void sort(Comparable[] a, int lo, int hi) {
        if (lo == hi) {
            return;
        }
        int mid = lo + (hi - lo) / 2;

        sort(a, lo, mid);
        sort(a, mid + 1, hi);

        merge(a, lo, mid, hi);
    }

    public static void merge(Comparable[] a, int lo, int mid, int hi) {
        // 定义三个指针
        int i = lo;
        int p1 = lo;
        int p2 = mid + 1;
        // 遍历，移动p1和p2，对比此索引位置大小，将小的放在辅助数组中
        while (p1 <= mid && p2 <= hi) {
            // 比较索引处的值
            if (less(a[p1], a[p2])) {
                assist[i++] = a[p1++];
            } else {
                assist[i++] = a[p2++];
            }
        }
        // 现在已经有一个指针到最大位置，让另一个数组中的值放进辅助数组
        while (p1 <= mid) {
            assist[i++] = a[p1++];
        }
        while (p2 <= hi) {
            assist[i++] = a[p2++];
        }
        // 把辅助数组中的值循环放进原数组
        for (int index = lo; index <= hi; index++) {
            a[index] = assist[index];
        }
    }


    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exChange(Comparable[] a, int i, int j) {
        Comparable temp;
        temp = a[j];
        a[j] = a[i];
        a[i] = temp;
    }
}

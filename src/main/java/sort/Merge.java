package sort;

public class Merge {

    private static Comparable[] assist;

    public static void sort(Comparable[] a) {
        assist=new Comparable[a.length];
        int lo=0,hi=a.length-1;
        sort(a,lo,hi);
    }

    public static void sort(Comparable[] a,int lo,int hi) {
        if (lo==hi){
            return;
        }
        int mid=lo+(hi-lo)/2;
        sort(a,lo,mid);
        sort(a,mid+1,hi);

    }

    public static void merge(Comparable[] a,int lo,int mid,int hi) {

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

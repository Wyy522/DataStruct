package sort;

//时间复杂度O(N^2) 适用少量元素
public class Bubble {
    public static void sort(Comparable[] a) {
        for (int i = a.length-1; i >0; i--) {
            for (int j = 0; j < i; j++) {
                //比较索引j和J+1的值
               if (greater(a[j],a[j+1])){
                   exChange(a,j,j+1);
               }
            }
        }
    }

    private static boolean greater(Comparable v, Comparable w) {
        return v.compareTo(w) > 0;
    }

    private static void exChange(Comparable[] a,int i,int j){
        Comparable temp;
        temp=a[j];
        a[j]=a[i];
        a[i]=temp;
    }

}

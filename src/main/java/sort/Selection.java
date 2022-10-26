package sort;

//时间复杂度O(N^2) 适用少量元素
public class Selection {

    public static void sort(Comparable[] a) {
        for (int i = 0; i <=a.length-2; i++) {
            //记录起始位置索引
            int min=i;
            for (int j = i+1; j <a.length; j++) {
               if (greater(a[min],a[j])){
                   //记录最小元素位置索引
                    min=j;
               }
            }
            //交换
            exChange(a,i,min);
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

package sort;

//时间复杂度O(N^2) 适用少量元素
public class Insertion {
    public static void sort(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            for (int j=i;j>0;j--){
                //逆序比较索引处j和j-1的值,如果j<j-1则交换数据,如果大于则退出本次循环
                if (greater(a[j-1],a[j])){
                    exChange(a,j-1,j);
                }else{
                    break;
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

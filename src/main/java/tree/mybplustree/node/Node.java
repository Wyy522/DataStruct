package tree.mybplustree.node;

import java.util.List;

//keys是存放key的一个数组


//binarySearch采用二分查询(必须有序查找才有效)
//	1、如果找到关键字，则返回值为关键字在数组中的位置索引(从0开始).
//	2、如果没有找到关键字(从1开始)
//		 2.1、如果该关键字比数组内所有元素都大  返回值为-(N+1)  N为数组内元素个数。
//		 2.2、如果该关键字在数组内某些元素的大小范围内，则返回第一个比该关键字大元素索引+1的负值 即返回值为-(索引+1)。
public interface Node{

    int DEFAULT_BRANCHING_FACTOR = 128;

    int keyNumber();

    String getValue(int key);

    boolean deleteValue(int key,Node root);

    Node insertValue(int key, String value,Node root);

    int getFirstLeafKey();

    boolean isOverflow();

    boolean isUnderflow();

    Node split();

    void merge(Node sibling);

    public String toString() ;

    List<String> getRange(int key1, RangePolicy policy1, int key2, RangePolicy policy2);
}

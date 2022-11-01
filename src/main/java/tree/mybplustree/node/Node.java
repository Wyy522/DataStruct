package tree.mybplustree.node;

import java.util.List;

public interface Node{

    int DEFAULT_BRANCHING_FACTOR = 128;

    int keyNumber();

    String getValue(String key);

    boolean deleteValue(String key);

    Node insertValue(String key, String value,Node root);

    String getFirstLeafKey();

    boolean isOverflow();

    boolean isUnderflow();

    Node split();

    void merge(Node sibling);

    public String toString() ;

    List<String> getRange(String key1, RangePolicy policy1, String key2, RangePolicy policy2);
}

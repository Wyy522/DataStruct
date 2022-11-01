package tree.mybplustree.node;

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

}

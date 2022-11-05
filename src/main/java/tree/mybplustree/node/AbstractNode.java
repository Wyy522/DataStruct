package tree.mybplustree.node;

import java.util.List;

public abstract class AbstractNode implements Node{

    List<Integer> keys;
    @Override
    public int keyNumber() {
        return keys.size();
    }

    @Override
    public abstract String getValue(int key);

    @Override
    public abstract List<String> getRange(int key1, RangePolicy policy1, int key2, RangePolicy policy2);

    @Override
    public abstract boolean deleteValue(int key,Node root);

    @Override
    public abstract Node insertValue(int key, String value,Node root);

    @Override
    public abstract int getFirstLeafKey() ;

    @Override
    public abstract boolean isOverflow();

    @Override
    public abstract boolean isUnderflow();

    @Override
    public abstract Node split();

    @Override
    public abstract void merge(Node sibling);

    public String toString() {
        return keys.toString();
    }

}

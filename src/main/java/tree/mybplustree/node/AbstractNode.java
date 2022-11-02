package tree.mybplustree.node;

import java.util.List;

public abstract class AbstractNode implements Node{

    List<String> keys;
    @Override
    public int keyNumber() {
        return keys.size();
    }

    @Override
    public abstract String getValue(String key);

    @Override
    public abstract List<String> getRange(String key1, RangePolicy policy1, String key2, RangePolicy policy2);

    @Override
    public abstract boolean deleteValue(String key,Node root);

    @Override
    public abstract Node insertValue(String key, String value,Node root);

    @Override
    public abstract String getFirstLeafKey() ;

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

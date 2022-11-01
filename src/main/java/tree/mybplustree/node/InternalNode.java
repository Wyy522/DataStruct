package tree.mybplustree.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InternalNode extends AbstractNode {

    public List<Node> children;

    private final int branchingFactor;

    public InternalNode() {
        this(DEFAULT_BRANCHING_FACTOR);
    }

    public InternalNode(int branchingFactor) {
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
        this.branchingFactor = branchingFactor;
    }

    @Override
    public Node insertValue(String key, String value,Node root) {

        Node child = getChild(key);
        child.insertValue(key, value,root);
        //判断叶子节点是否需要切分
        if (child.isOverflow()) {
            Node sibling = child.split();
            insertChild(key, sibling);
        }
        //判断上层节点是否需要切分
        if (root.isOverflow()) {
            Node sibling = split();
            InternalNode newRoot = new InternalNode(branchingFactor);
            newRoot.keys.add(sibling.getFirstLeafKey());
            newRoot.children.add(this);
            newRoot.children.add(sibling);
           return newRoot;
        }
        return root;
    }

    public Node getChild(String key) {
        int loc = Collections.binarySearch(keys, key);
        //要进行loc+1的原因是因为第一个节点没有存在上层节点中
        int valueIndex = loc >= 0 ? loc + 1 : -loc - 1;
        return children.get(valueIndex);
    }

    public void insertChild(String key, Node sibling) {
        int loc = Collections.binarySearch(keys, key);
        int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
        if (loc >= 0) {
            children.set(childIndex, sibling);
        } else {
            keys.add(childIndex, key);
            children.add(childIndex + 1, sibling);
        }
    }

    @Override
    public Node split() {
        int from = getKeysNum() / 2 + 1;
        int to = getKeysNum();
        InternalNode sibling = new InternalNode(branchingFactor);
        sibling.keys.addAll(keys.subList(from, to));
        sibling.children.addAll(children.subList(from, to + 1));

        keys.subList(from - 1, to).clear();
        children.subList(from, to + 1).clear();

        return sibling;
    }


    @Override
    public void merge(Node sibling) {

    }

    @Override
    public String getValue(String key) {
        return null;
    }

    @Override
    public boolean deleteValue(String key) {
        return false;
    }

    @Override
    public String getFirstLeafKey() {
        return children.get(0).getFirstLeafKey();
    }


    public int getKeysNum() {
        return this.keys.size();
    }

    @Override
    public boolean isOverflow() {
        return children.size() > branchingFactor;
    }

    @Override
    public boolean isUnderflow() {
        return false;
    }
}

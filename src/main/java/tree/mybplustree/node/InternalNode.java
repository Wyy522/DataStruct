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
    public Node insertValue(int key, String value, Node root) {

        Node child = getChild(key);
        child.insertValue(key, value, root);
        //判断叶子节点是否需要切分
        if (child.isOverflow()) {
            Node sibling = child.split();
            insertChild(sibling.getFirstLeafKey(), sibling);
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


    public void insertChild(int key, Node sibling) {
        int loc = Collections.binarySearch(keys, key);
        int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
        if (loc >= 0) {
            children.set(childIndex, sibling);
        } else {
            keys.add(childIndex, key);
            children.add(childIndex + 1, sibling);
        }
    }

    public void deleteChild(int key) {
        int loc = Collections.binarySearch(keys, key);
        if (loc >= 0) {
            keys.remove(loc);
            children.remove(loc + 1);
        }
    }

    @Override
    public String getValue(int key) {
        return getChild(key).getValue(key);
    }

    @Override
    public List<String> getRange(int key1, RangePolicy policy1, int key2, RangePolicy policy2) {
        return getChild(key1).getRange(key1, policy1, key2, policy2);
    }

    @Override
    public boolean deleteValue(int key, Node root) {
        Node child = getChild(key);
        child.deleteValue(key, root);
        if (child.isUnderflow()) {
            //获得左兄弟节点
            Node childLeftSibling = getChildLeftSibling(key);
            //获得右兄弟节点
            Node childRightSibling = getChildRightSibling(key);

            //左  中  右
            //1.左不为空  则左中合并
            //2.左为空    则中右合并
            Node left = childLeftSibling != null ? childLeftSibling : child;
            Node right = childLeftSibling != null ? child : childRightSibling;

            left.merge(right);
            deleteChild(right.getFirstLeafKey());
            if (left.isOverflow()) {
                Node sibling = left.split();
                insertChild(sibling.getFirstLeafKey(), sibling);
            }
            if (root.keyNumber() == 0)
                root = left;
        }
        return true;
    }

    //获取左兄弟叶子节点
    public Node getChildLeftSibling(int key) {
        int loc = Collections.binarySearch(keys, key);
        int childSiblingIndex = loc >= 0 ? loc + 1 : -loc - 1;
        if (childSiblingIndex > 0) {
            return children.get(childSiblingIndex - 1);
        }
        return null;
    }

    //获取右兄弟叶子节点
    public Node getChildRightSibling(int key) {
        int loc = Collections.binarySearch(keys, key);
        int childSiblingIndex = loc >= 0 ? loc + 1 : -loc - 1;
        if (childSiblingIndex < getKeysNum()) {
            return children.get(childSiblingIndex + 1);
        }
        return null;
    }


    public Node getChild(int key) {
        int loc = Collections.binarySearch(keys, key);

        //要进行loc+1的原因是因为第一个节点没有存在上层节点中
        int valueIndex = loc >= 0 ? loc + 1 : -loc - 1;
        return children.get(valueIndex);
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
        InternalNode node = (InternalNode) sibling;
        keys.add(node.getFirstLeafKey());
        keys.addAll(node.keys);
        children.addAll(node.children);
    }

    @Override
    public int getFirstLeafKey() {
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
        return children.size() < (branchingFactor + 1) / 2;
    }
}

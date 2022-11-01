package tree.mybplustree.node;

import practice.excutorservice.TaskExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeafNode extends AbstractNode {

    List<String> values;
    LeafNode next;

    private final int branchingFactor;

    public LeafNode() {
        this(DEFAULT_BRANCHING_FACTOR);
    }

    public LeafNode(int branchingFactor) {
        this.keys = new ArrayList<>();
        this.values = new ArrayList<>();
        this.branchingFactor = branchingFactor;
    }


    @Override
    public Node insertValue(String key, String value,Node root) {

        //查找插入位置
        int loc = Collections.binarySearch(keys, key);
        int valueIndex = loc >= 0 ? loc : -loc - 1;

        //插入
        if (loc >= 0) {
            values.set(valueIndex, value);
        } else {
            keys.add(valueIndex, key);
            values.add(valueIndex, value);
        }
        //判断是否需要切分
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

    @Override
    public Node split() {
        //创建兄弟节点
        LeafNode sibling = new LeafNode(branchingFactor);

        //找到需要切分数据的索引
        int from = (getKeysNum() + 1) / 2;
        int to = getKeysNum();

        //转移到兄弟节点中
        sibling.keys.addAll(keys.subList(from, to));
        sibling.values.addAll(values.subList(from, to));

        //移除原节点中的数据

        keys.subList(from, to).clear();
        values.subList(from, to).clear();

        //构成链表
        sibling.next = this.next;
        this.next = sibling;

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
        return keys.get(0);
    }

    //返回数组内key的数量
    public int getKeysNum() {
        return this.keys.size();
    }

    @Override
    public boolean isOverflow() {
        return values.size() > branchingFactor - 1;
    }

    @Override
    public boolean isUnderflow() {
        return false;
    }
}

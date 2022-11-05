package tree.mybplustree.node;

import java.util.*;

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
    public Node insertValue(int key, String value, Node root) {

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
    public String getValue(int key) {
        int loc = Collections.binarySearch(keys, key);
        return loc >= 0 ? values.get(loc) : null;
    }

    @Override
    public List<String> getRange(int key1, RangePolicy policy1, int key2, RangePolicy policy2) {
        List<String> result = new LinkedList<>();
        LeafNode thisNode = this;
        while (thisNode != null) {
            Iterator<Integer> kIter = thisNode.keys.iterator();
            Iterator<String> vIter = thisNode.values.iterator();
            while (kIter.hasNext()) {
                Integer key = kIter.next();
                String value = vIter.next();
                int cmp1 = key.compareTo(key1);
                int cmp2 = key.compareTo(key2);

                //String的compareTo返回值问题:如果key的字典顺序在key1前面,则返回负数。如果在key1后面,则返回正数。如果相等,则返回0;
                //RangePolicy是考虑范围查询的边界区间,如果是EXCLUSIVE则不包含该值(开区间),反之INCLUSIVE则是闭区间。
                if (((policy1 == RangePolicy.EXCLUSIVE && cmp1 > 0) || policy1 == RangePolicy.INCLUSIVE && cmp1 >= 0)
                        && ((policy2 == RangePolicy.EXCLUSIVE && cmp2 < 0 || policy2 == RangePolicy.INCLUSIVE && cmp2 <= 0)))
                    result.add(value);
                //当cmp2大于0时说明已查询到所有值。
                else if ((policy2 == RangePolicy.EXCLUSIVE && cmp2 >= 0) || (policy2 == RangePolicy.INCLUSIVE && cmp2 > 0))
                    return result;
            }
            thisNode = thisNode.next;
        }
        return result;
    }

    @Override
    public boolean deleteValue(int key,Node root) {
        //找到Key的索引位置
        int loc = Collections.binarySearch(keys, key);
        if (loc >= 0) {
            //删除
            keys.remove(loc);
            values.remove(loc);
            return true;
        }
        return false;
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
        LeafNode rightSiblingNode=(LeafNode) sibling;
        keys.addAll(rightSiblingNode.keys);
        values.addAll(rightSiblingNode.values);
        next=rightSiblingNode.next;
    }

    @Override
    public int getFirstLeafKey() {
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
        return values.size() < branchingFactor / 2;
    }
}

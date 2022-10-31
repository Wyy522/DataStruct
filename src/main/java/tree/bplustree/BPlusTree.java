package tree.bplustree;

import java.util.*;

    public class BPlusTree<K extends Comparable<? super K>, V> {

        public static enum RangePolicy {
            EXCLUSIVE, INCLUSIVE
        }

        //构造函数中未指定时使用的分支因子
        private static final int DEFAULT_BRANCHING_FACTOR = 128;

        //B+树的分支因子，即最大子节点数
        private final int branchingFactor;

        //根节点
        private Node root;

        //默认构造函数(分支因子为默认128)
        public BPlusTree() {
            this(DEFAULT_BRANCHING_FACTOR);
        }

        //带分支因子的构造函数
        public BPlusTree(int branchingFactor) {
            if (branchingFactor <= 2)
                throw new IllegalArgumentException("Illegal branching factor: "
                        + branchingFactor);
            this.branchingFactor = branchingFactor;

            //创建根节点
            root = new LeafNode();
        }

        //根据Key返回对应的Value，如果此树不包含该Key的Value，则返回Null,返回值为Null不一定表示树不包含Key的Value；也可能该Key的Value就为Null。
        public V search(K key) {
            return root.getValue(key);
        }

        /**
         * Returns the values associated with the keys specified by the range:
         * {@code key1} and {@code key2}.
         *
         * @param key1
         *            the start key of the range
         * @param policy1
         *            the range policy, {@link RangePolicy#EXCLUSIVE} or
         *            {@link RangePolicy#INCLUSIVE}
         * @param key2
         *            the end end of the range
         * @param policy2
         *            the range policy, {@link RangePolicy#EXCLUSIVE} or
         *            {@link RangePolicy#INCLUSIVE}
         * @return the values associated with the keys specified by the range:
         *         {@code key1} and {@code key2}
         */
        public List<V> searchRange(K key1, RangePolicy policy1, K key2,
                                   RangePolicy policy2) {
            return root.getRange(key1, policy1, key2, policy2);
        }

        //将指定值与此树中的指定键相关联。如果树先前包含键的关联，则替换旧值
        public void insert(K key, V value) {
            root.insertValue(key, value);
        }

        //从该树中删除指定Key的Value(如果存在)。
        public void delete(K key) {
            root.deleteValue(key);
        }

        public String toString() {
            Queue<List<Node>> queue = new LinkedList<List<Node>>();
            queue.add(Arrays.asList(root));
            StringBuilder sb = new StringBuilder();
            while (!queue.isEmpty()) {
                Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
                while (!queue.isEmpty()) {
                    List<Node> nodes = queue.remove();
                    sb.append('{');
                    Iterator<Node> it = nodes.iterator();
                    while (it.hasNext()) {
                        Node node = it.next();
                        sb.append(node.toString());
                        if (it.hasNext())
                            sb.append(", ");
                        if (node instanceof BPlusTree.InternalNode)
                            nextQueue.add(((InternalNode) node).children);
                    }
                    sb.append('}');
                    if (!queue.isEmpty())
                        sb.append(", ");
                    else
                        sb.append('\n');
                }
                queue = nextQueue;
            }

            return sb.toString();
        }

        private abstract class Node {

            //采用顺序表存放key
            List<K> keys;

            //获取Key的总个数
            int keyNumber() {
                return keys.size();
            }

            //根据Key获取Value
            abstract V getValue(K key);

            //根据Key删除Value
            abstract void deleteValue(K key);

            //插入Key和Value
            abstract void insertValue(K key, V value);

            //获取叶子节点的第一个Key作为上层节点的索引范围
            abstract K getFirstLeafKey();

            abstract List<V> getRange(K key1, RangePolicy policy1, K key2,
                                      RangePolicy policy2);

            abstract void merge(Node sibling);

            abstract Node split();

            //是否溢出
            abstract boolean isOverflow();

            abstract boolean isUnderflow();

            public String toString() {
                return keys.toString();
            }
        }

        private class InternalNode extends Node {
            List<Node> children;

            InternalNode() {
                //Keys数组
                this.keys = new ArrayList<K>();

                //孩子数组
                this.children = new ArrayList<Node>();
            }

            @Override
            V getValue(K key) {
                return getChild(key).getValue(key);
            }

            @Override
            void deleteValue(K key) {

                //根据Key确定叶子节点的位置
                Node child = getChild(key);

                //删除叶子节点内数据
                child.deleteValue(key);

                //判断是否需要合并
                if (child.isUnderflow()) {

                    //获取当前Key
                    Node childLeftSibling = getChildLeftSibling(key);

                    //
                    Node childRightSibling = getChildRightSibling(key);

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
            }

            @Override
            void insertValue(K key, V value) {
                //根据Key确定叶子节点的位置
                Node child = getChild(key);

                //在叶子节点内插入数据
                child.insertValue(key, value);

                //判断叶子节点是否需要进行切分(子节点内的数据达到分支因子时需要)
                if (child.isOverflow()) {
                    Node sibling = child.split();
                    insertChild(sibling.getFirstLeafKey(), sibling);
                }

                //判断上层节点是否需要进行切分
                if (root.isOverflow()) {
                    //创建一个上层兄弟节点
                    Node sibling = split();

                    //创建一个再上一层的节点
                    InternalNode newRoot = new InternalNode();
                    newRoot.keys.add(sibling.getFirstLeafKey());
                    newRoot.children.add(this);
                    newRoot.children.add(sibling);
                    root = newRoot;
                }
            }

            @Override
            K getFirstLeafKey() {
                return children.get(0).getFirstLeafKey();
            }

            @Override
            List<V> getRange(K key1, RangePolicy policy1, K key2,
                             RangePolicy policy2) {
                return getChild(key1).getRange(key1, policy1, key2, policy2);
            }

            @Override
            void merge(Node sibling) {
                @SuppressWarnings("unchecked")
                InternalNode node = (InternalNode) sibling;
                keys.add(node.getFirstLeafKey());
                keys.addAll(node.keys);
                children.addAll(node.children);

            }

            @Override
            Node split() {
                //
                int from = keyNumber() / 2 + 1 ;
                int to = keyNumber();
                InternalNode sibling = new InternalNode();
                sibling.keys.addAll(keys.subList(from, to));
                sibling.children.addAll(children.subList(from, to + 1));

                keys.subList(from - 1, to).clear();
                children.subList(from, to + 1).clear();

                return sibling;
            }

            @Override
            boolean isOverflow() {
                return children.size() > branchingFactor;
            }

            @Override
            boolean isUnderflow() {
                return children.size() < (branchingFactor + 1) / 2;
            }

            //根据Key找到孩子的索引位置
            Node getChild(K key) {
                //childIndex如果是正数说明Keys数组内有相应的Key,直接根据Index覆盖即可。若为负数则需要进行取反-1得到可插入的index;
                int loc = Collections.binarySearch(keys, key);
                //要进行loc+1的原因是因为第一个节点没有存在上层节点中
                int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
                //根据Key获取叶子节点
                return children.get(childIndex);
            }

            void deleteChild(K key) {
                int loc = Collections.binarySearch(keys, key);
                if (loc >= 0) {
                    keys.remove(loc);
                    children.remove(loc + 1);
                }
            }

            void insertChild(K key, Node child) {
                //确定插入位置
                int loc = Collections.binarySearch(keys, key);
                int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
                //如果大于0说明该key以存在直接覆盖
                if (loc >= 0) {
                    children.set(childIndex, child);
                } else {
                    //如果小于零说明key不存在需要进行添加
                    keys.add(childIndex, key);
                    children.add(childIndex + 1, child);
                }
            }

            Node getChildLeftSibling(K key) {
                int loc = Collections.binarySearch(keys, key);
                int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
                if (childIndex > 0)
                    return children.get(childIndex - 1);

                return null;
            }

            Node getChildRightSibling(K key) {
                int loc = Collections.binarySearch(keys, key);
                int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
                if (childIndex < keyNumber())
                    return children.get(childIndex + 1);

                return null;
            }
        }

        private class LeafNode extends Node {
            List<V> values;
            LeafNode next;

            LeafNode() {
                keys = new ArrayList<K>();
                values = new ArrayList<V>();
            }

            //根据Key获取Value
            @Override
            V getValue(K key) {
                int loc = Collections.binarySearch(keys, key);
                return loc >= 0 ? values.get(loc) : null;
            }

            //根据Key删除Value
            @Override
            void deleteValue(K key) {

                //找到Key的索引位置
                int loc = Collections.binarySearch(keys, key);
                if (loc >= 0) {
                    //删除
                    keys.remove(loc);
                    values.remove(loc);
                }
            }

            @Override
            void insertValue(K key, V value) {

                //keys是存放key的一个数组
                //binarySearch采用二分查询(必须有序查找才有效)
                //	1、如果找到关键字，则返回值为关键字在数组中的位置索引(从0开始).
                //	2、如果没有找到关键字(从1开始)
                //		 2.1、如果该关键字比数组内所有元素都大  返回值为-(N+1)  N为数组内元素个数。
                //		 2.2、如果该关键字在数组内某些元素的大小范围内，则返回第一个比该关键字大元素索引+1的负值 即返回值为-(索引+1)。
                int loc = Collections.binarySearch(keys, key);

                //valueIndex如果是正数说明Keys数组内有相应的Key,直接根据Index覆盖即可。若为负数则需要进行取反-1得到可插入的index;
                int valueIndex = loc >= 0 ? loc : -loc - 1;

                if (loc >= 0) {
                    //在原Key的索引位置上覆盖原值
                    values.set(valueIndex, value);
                } else {
                    //在末尾添加键值
                    keys.add(valueIndex, key);
                    values.add(valueIndex, value);
                }

                //判断当前节点的子节点总数是否大于节点因子，若大于则
                if (root.isOverflow()) {
                    //创建兄弟节点并且构成双向链表
                    Node sibling = split();

                    //创建上层节点
                    InternalNode newRoot = new InternalNode();

                    //获取叶子节点的第一个Key作为上层节点的索引范围
                    newRoot.keys.add(sibling.getFirstLeafKey());

                    //添加到上层节点的孩子数组中
                    newRoot.children.add(this);
                    newRoot.children.add(sibling);


                    root = newRoot;
                }
            }

            //判断是否需要切分
            @Override
            boolean isOverflow() {
                //子节点内的数据达到分支因子时需要切分  >3
                return values.size() > branchingFactor - 1;
            }

            @Override
            boolean isUnderflow() {
                //1<2
                return values.size() < branchingFactor / 2;
            }

            //切分兄弟节点并构成链表
            @Override
            Node split() {

                //创建兄弟节点
                LeafNode sibling = new LeafNode();
                //from = (3+1)/2 = 2
                int from = (keyNumber() + 1) / 2;
                //to = 3
                int to = keyNumber();

                //转移原节点(from,to)的数据到兄弟节点上
                sibling.keys.addAll(keys.subList(from, to));
                sibling.values.addAll(values.subList(from, to));

                //清空原节点(from,to)的数据
                keys.subList(from, to).clear();
                values.subList(from, to).clear();

                //构成链表
                sibling.next = next;
                next = sibling;

                //返回链头
                return sibling;
            }

            //获取叶子节点的第一个Key作为上层节点的索引范围
            @Override
            K getFirstLeafKey() {
                return keys.get(0);
            }

            @Override
            List<V> getRange(K key1, RangePolicy policy1, K key2, RangePolicy policy2) {
                List<V> result = new LinkedList<V>();
                LeafNode node = this;
                while (node != null) {
                    Iterator<K> kIt = node.keys.iterator();
                    Iterator<V> vIt = node.values.iterator();
                    while (kIt.hasNext()) {
                        K key = kIt.next();
                        V value = vIt.next();
                        int cmp1 = key.compareTo(key1);
                        int cmp2 = key.compareTo(key2);
                        if (((policy1 == RangePolicy.EXCLUSIVE && cmp1 > 0) || (policy1 == RangePolicy.INCLUSIVE && cmp1 >= 0))
                                && ((policy2 == RangePolicy.EXCLUSIVE && cmp2 < 0) || (policy2 == RangePolicy.INCLUSIVE && cmp2 <= 0)))
                            result.add(value);
                        else if ((policy2 == RangePolicy.EXCLUSIVE && cmp2 >= 0)
                                || (policy2 == RangePolicy.INCLUSIVE && cmp2 > 0))
                            return result;
                    }
                    node = node.next;
                }
                return result;
            }

            @Override
            void merge(Node sibling) {
                @SuppressWarnings("unchecked")
                LeafNode node = (LeafNode) sibling;
                keys.addAll(node.keys);
                values.addAll(node.values);
                next = node.next;
            }

        }

}

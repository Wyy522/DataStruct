package tree.mybplustree;

import tree.mybplustree.node.InternalNode;
import tree.mybplustree.node.LeafNode;
import tree.mybplustree.node.Node;
import tree.mybplustree.node.RangePolicy;

import java.util.*;

/*
  1.<T extends Comparable<T>>
    类型 T 必须实现 Comparable 接口，并且这个接口的类型是 T。只有这样，T 的实例之间才能相互比较大小。
    例如，在实际调用时若使用的具体类是 Dog，那么 Dog 必须 implements Comparable<Dog>
  2.<T extends Comparable<? super T>>
    类型 T 必须实现 Comparable 接口，并且这个接口的类型是 T 或 T 的任一父类。这样声明后，T 的实例之间，T 的实例和它的父类的实例之间，可以相互比较大小。
    例如，在实际调用时若使用的具体类是 Dog (假设 Dog 有一个父类 Animal），Dog 可以从 Animal 那里继承 Comparable<Animal> ，或者自己 implements Comparable<Dog> 。
*/
public class BPlusTree {

    private int branchingFactor;

    private static final int DEFAULT_BRANCHING_FACTOR=128;

    private Node root;

    public BPlusTree() {
        this(DEFAULT_BRANCHING_FACTOR);
    }

    public BPlusTree(int branchingFactor) {

        if (branchingFactor<=2){
            throw new IllegalArgumentException("Illegal branching factor: "
                    + branchingFactor);
        }
        this.branchingFactor=branchingFactor;

        root=new LeafNode(branchingFactor);
    }


    public void insert(String key,String value){
        root = root.insertValue(key, value, root);
    }

    public String search(String key){
        return root.getValue(key);
    }

    public List<String> searchRange(String key1, RangePolicy policy1, String key2, RangePolicy policy2) {
        return root.getRange(key1, policy1, key2, policy2);
    }

    public boolean delete(String key){
        return root.deleteValue(key);
    }

    @Override
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
                    if (node instanceof InternalNode)
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
}
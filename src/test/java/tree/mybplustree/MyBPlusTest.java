package tree.mybplustree;

import org.junit.Test;

public class MyBPlusTest {
    @Test
    public void testMyBPlusTreeInsert(){
        BPlusTree bPlusTree=new BPlusTree(4);
        bPlusTree.insert("0","0");
        System.out.println(bPlusTree.toString());
        bPlusTree.insert("1","1");
        System.out.println(bPlusTree.toString());
        bPlusTree.insert("2","2");
        System.out.println(bPlusTree.toString());
        bPlusTree.insert("3","3");
        System.out.println(bPlusTree.toString());
        bPlusTree.insert("4","4");
        System.out.println(bPlusTree.toString());
        bPlusTree.insert("5","5");
        System.out.println(bPlusTree.toString());
        bPlusTree.insert("6","6");
        System.out.println(bPlusTree.toString());
        bPlusTree.insert("7","7");
        System.out.println(bPlusTree.toString());
        bPlusTree.insert("8","8");
        System.out.println(bPlusTree.toString());
        bPlusTree.insert("9","9");
        System.out.println(bPlusTree.toString());

    }
}

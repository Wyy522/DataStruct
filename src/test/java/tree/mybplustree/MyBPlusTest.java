package tree.mybplustree;

import org.junit.Assert;
import org.junit.Test;
import tree.mybplustree.node.RangePolicy;

public class MyBPlusTest {
    @Test
    public void testMyBPlusTreeInsert(){
        BPlusTree bPlusTree=new BPlusTree(4);
        bPlusTree.insert("0","0");
        bPlusTree.insert("1","1");
        bPlusTree.insert("2","2");
        bPlusTree.insert("3","3");
        bPlusTree.insert("4","4");
        bPlusTree.insert("5","5");
        bPlusTree.insert("6","6");
        bPlusTree.insert("7","7");
        bPlusTree.insert("8","8");
        bPlusTree.insert("9","9");
        System.out.println(bPlusTree.toString());
        bPlusTree.delete("1");
        bPlusTree.delete("3");
        System.out.println(bPlusTree.toString());
        bPlusTree.delete("5");
        bPlusTree.delete("7");
        bPlusTree.delete("9");
        Assert.assertEquals(bPlusTree.search("0"), "0");
        Assert.assertEquals(bPlusTree.search("1"), null);
        Assert.assertEquals(bPlusTree.search("2"), "2");
        Assert.assertEquals(bPlusTree.search("3"), null);
        Assert.assertEquals(bPlusTree.search("4"), "4");
        Assert.assertEquals(bPlusTree.search("5"), null);
        Assert.assertEquals(bPlusTree.search("6"), "6");
        Assert.assertEquals(bPlusTree.search("7"), null);
        Assert.assertEquals(bPlusTree.search("8"), "8");
        Assert.assertEquals(bPlusTree.search("9"), null);

    }
    @Test
    public void testSearchRange() {
        BPlusTree bPlusTree = new BPlusTree(4);
        bPlusTree.insert("0","0");
        bPlusTree.insert("1","1");
        bPlusTree.insert("2","2");
        bPlusTree.insert("3","3");
        bPlusTree.insert("4","4");
        bPlusTree.insert("5","5");
        bPlusTree.insert("6","6");
        bPlusTree.insert("7","7");
        bPlusTree.insert("8","8");
        bPlusTree.insert("9","9");
        Assert.assertArrayEquals(
                bPlusTree.searchRange("3", RangePolicy.EXCLUSIVE, "7", RangePolicy.EXCLUSIVE).toArray()
                ,new String[] { "4", "5", "6" });
        System.out.println(bPlusTree.searchRange("3", RangePolicy.EXCLUSIVE, "7", RangePolicy.EXCLUSIVE).toString());
        Assert.assertArrayEquals(
                bPlusTree.searchRange("3", RangePolicy.INCLUSIVE, "7", RangePolicy.EXCLUSIVE).toArray()
                , new String[] { "3","4", "5", "6"});
        System.out.println(  bPlusTree.searchRange("3", RangePolicy.INCLUSIVE, "7", RangePolicy.EXCLUSIVE).toString());
        Assert.assertArrayEquals(
                bPlusTree.searchRange("3", RangePolicy.EXCLUSIVE, "7", RangePolicy.INCLUSIVE).toArray()
                ,new String[] {"4", "5", "6", "7" });
        System.out.println(bPlusTree.searchRange("3", RangePolicy.EXCLUSIVE, "7", RangePolicy.INCLUSIVE).toString());
        Assert.assertArrayEquals(
                bPlusTree.searchRange("3", RangePolicy.INCLUSIVE, "7", RangePolicy.INCLUSIVE).toArray()
                ,new String[] { "3","4", "5", "6", "7" });
        System.out.println(bPlusTree.searchRange("3", RangePolicy.INCLUSIVE, "7", RangePolicy.INCLUSIVE).toString());
    }

}

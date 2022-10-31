package test.linnearTest;

import linear.SequenceList;

public class SequenceListTest {
    public static void main(String[] args) {
        SequenceList<Integer> s = new SequenceList<>(3);
        s.insert(10);
        s.insert(20);
        s.insert(30);
        System.out.println(s.get(0));
        System.out.println(s.get(1));
        System.out.println(s.get(2));
        System.out.println("-----------------------------");
        s.insert(40);
        System.out.println(s.get(3));
        System.out.println(s.toString());
        s.remove(1);
        System.out.println(s.toString());
        s.insert(10);
        s.insert(20);
        s.insert(30);
        s.insert(10);
        s.insert(20);
        s.insert(30);

        System.out.println(s.toString());
    }
}

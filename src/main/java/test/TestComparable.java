package test;

import sort.Student;

public class TestComparable {
    public static void main(String[] args) {
        Student s1 = new Student("1",1);

        Student s2 = new Student("2",2);
        Comparable max = getMax(s1, s2);
        System.out.println(max);

    }
    public static Comparable getMax(Comparable c1,Comparable c2){
        int i = c1.compareTo(c2);

        //如果i<0 c1小于c2
        //如果i>0 c1大于c2
        //如果i==0 c1等于c2
        if (i>=0){
            return c1;
        }else
            return c2;
    }
}

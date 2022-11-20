package discipline;

import java.util.*;

public class App {
    public static void main(String[] args) {
        ArrayList<String> list=new ArrayList<>();
        list.add("cccc");
        list.add("aaa");
        list.add("dddd");
        list.add("eeee");
        list.add("bbb");
        System.out.println("List:"+list);
        System.out.println("最大值："+Collections.max(list));
        System.out.println("最小值："+Collections.min(list));
        Collections.sort(list);//排序
        System.out.println("排序后："+list);
        System.out.println("查找\"cccc\"所在的位置："+Collections.binarySearch(list,"cccc"));
        Collections.shuffle(list);//混排
        System.out.println("混排后："+list);
    }

    private static void extracted() {
        //使用Comparable选择器
        Set<Student> a=new TreeSet<Student>();
        a.add(new Student("A",25,75));
        a.add(new Student("B",23,99));
        a.add(new Student("C",26,60));
        System.out.println("TreeSet中的顺序为\n"+a.toString());
    }


    private static void comparableTest() {
        ArrayList<Student> a=new ArrayList<Student>();
        a.add(new Student("A",25,75));
        a.add(new Student("B",23,99));
        a.add(new Student("C",26,60));
        System.out.println("排序前=>\n"+a.toString());
        Collections.sort(a,new StudentAgeComparator());
        System.out.println("学生年龄比较器排序后=>\n"+a.toString());
        Collections.sort(a,new StudentScoreComparator());
        System.out.println("学生成绩比较器排序后=>\n"+a.toString());
    }

    private static void InterTest() {
        ArrayList<Integer> arrayList=new ArrayList<Integer>();
        arrayList.add(4);
        arrayList.add(2);
        arrayList.add(5);
        arrayList.add(1);
        arrayList.add(6);
        System.out.println("排序前=>"+arrayList.toString());
        Collections.sort(arrayList);
        System.out.println("排序后=>"+arrayList.toString());
    }
}

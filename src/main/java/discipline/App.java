package discipline;

import sort.Merge;

import java.util.*;
//import java.util.concurrent;

public class App {
    public static void main(String[] args) {
        extracted2();
    }

    private static void extracted2() {
        //Integer ==int 是int的包装类
        //int 基础类型
        ArrayList<Integer> arrayList=new ArrayList<>();
        Integer integer1=new Integer(2);
        Integer integer2=new Integer(1);
        //1 2
        int i = integer1.compareTo(integer2);
        //x<y
        if (i==-1){
            arrayList.add(integer1);
            arrayList.add(integer2);
        }else {
            arrayList.add(integer2);
            arrayList.add(integer1);
        }
        System.out.println(arrayList);

    }

    private static void extracted1() {
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
        System.out.println("混排1后："+list);
        Collections.shuffle(list);//混排
        System.out.println("混排2后："+list);
//        Collections.
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

    private static void studentTest() {
        ArrayList<Student> a=new ArrayList<Student>();
        a.add(new Student("A",25,75));
        a.add(new Student("B",23,99));
        a.add(new Student("C",26,60));
        System.out.println("排序前=>"+a.toString());
//        Collections.sort(a);
        System.out.println("排序前=>"+a.toString());
    }

    private static void IntegerTest() {
        //Integer == int
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

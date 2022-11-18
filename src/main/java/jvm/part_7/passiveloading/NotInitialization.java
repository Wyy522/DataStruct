package jvm.part_7.passiveloading;

//被动引用例子
public class NotInitialization {
    public static void main(String[] args) {
        //不加final的输出结果
        //superClass init!
        //123

        //加final的输出结果(没有进行类的初始化)
        //123
        System.out.println(SubClass.value);

    }
}

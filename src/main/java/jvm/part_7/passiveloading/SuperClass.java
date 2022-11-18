package jvm.part_7.passiveloading;

public class SuperClass {
    static {
        System.out.println("superClass init!");
    }

    //加final 在编译阶段就通过常量传播优化  放在了NotInitialization的常量池中
    public final static int value=123;
}

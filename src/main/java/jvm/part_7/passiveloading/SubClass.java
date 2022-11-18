package jvm.part_7.passiveloading;

public class SubClass extends SuperClass{
    static{
        System.out.println("subClass init!");
    }
}

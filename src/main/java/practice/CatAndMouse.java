package practice;

public class CatAndMouse {
    public static void main(String[] args) {
        Mouse m1 =new Mouse("m1");
        Mouse m2 =new Mouse("m2");
        Mouse m3 =new Mouse("m3");
        Cat c1 =new Cat("c1");
        c1.catchMouse(m1);
        System.out.println(m1);System.out.println(m2);System.out.println(m3);System.out.println(c1);
        System.out.println("计科191419阳凯锋");
    }
}
class Cat{
    String name;
    int record;
    public Cat(String name) {
        this.name = name;
    }
    public void catchMouse(Mouse mouse){
        mouse.isAlive=false;
        record++;
    }
    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", record=" + record +
                '}';
    }
}
class Mouse{
    String name;
    Boolean isAlive=true;
    public Mouse(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Mouse{" +
                "name='" + name + '\'' +
                ", isAlive=" + isAlive +
                '}';
    }
}
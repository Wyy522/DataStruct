package software;

/**
 * 教练
 * @author yiyewei
 * @create 2022/12/2 15:22
 **/
public class Instructor extends Participator {
    public int salary;

    public Instructor(String name, int age, int height, int weight, int salary) {
        super(name, age, height, weight);
        this.salary = salary;
    }
    public Instructor(String name) {
        this(name,0,0,0,0);
    }
}

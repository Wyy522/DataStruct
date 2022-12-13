package software;

/**
 * 足球运动员
 * @author yiyewei
 * @create 2022/12/2 15:23
 **/
public class Player extends Participator{
    public String role;
    public Player(String name, int age, int height, int weight, String role) {
        super(name, age, height, weight);
        this.role = role;
    }
}

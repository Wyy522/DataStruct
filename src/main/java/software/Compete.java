package software;

/**
 * 赛程
 * @author yiyewei
 * @create 2022/12/2 15:22
 **/
public class Compete {
    public String data;
    public String place;
    public String result;
    public Team t1;
    public Team t2;
    public Compete(String data, String place, String result, Team t1, Team t2) {
        this.data = data;
        this.place = place;
        this.result = result;
        this.t1 = t1;
        this.t2 = t2;
    }
}

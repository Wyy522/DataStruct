package software;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * 随机和必胜两种解决方案
 * @author yiyewei
 * @create 2022/12/2 15:21
 **/
// 建立一个足球联赛比赛信息处理系统。
// 共组成十五支球队。竞赛采用双循环制(任意两队间比赛两次)，共有210场比赛，
// 每支球队拥有比赛积分，1场比赛胜队得3分、负队得0分、平各得1分。
// 完成如下两项处理:
// A:依目前赛况，某球队可能获得第一名所需的最小积分是多少。
// B:依目前赛况，某球队确保获得第一名所需的最小积分是多少。
// 注:第一名至少比第二名多一分，不考虑相同积分情况。
// 依目前赛况指处理运算可在部分比赛结果已知条件下进行。
// 请用 C++或 JAVA 实现数据结构和主要流程，并对两项处理的算法思路作出解释。

    //对于A情况：
public class App {
    public static final int teamSize = 15;
    public static final int playerSize = 11;
    public static final int WIN_SCORE = 3;
    public static final int FALSE_SCORE = 0;
    public static final int EQUAL_SCORE = 1;

    public static ArrayList<Team> list = new ArrayList<Team>(teamSize);
    public static List<Compete> competes = new LinkedList<Compete>();

    public static void main(String[] args) {
        //组建球队
        for (int i = 1; i <= teamSize; i++) {
            String instructorString = "instructor" + i;
            String playerString = "player" + i;
            ArrayList<Player> players = new ArrayList<>(playerSize);
            players.add(new Player(playerString, 0, 0, 0, "前锋"));
            list.add(new Team(i, 0, new Instructor(instructorString), players));
        }
        //双循环赛制
        int winTeam = 0;
        for (int i = 0; i < teamSize; i++) {
            for (int j = 0; j < teamSize; j++) {
                //随机
                randomCompete(i,j);
                //必胜
                winCompete(i, j, winTeam);
            }
        }
        System.out.println("冠军队伍为：" + Collections.max(list, new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {
                return o1.getScore() - o2.getScore();
            }
        }).toString());
        System.out.println("一共比赛：" + competes.size() + "场次,具体如下");
        for (Compete c : competes) {
            System.out.println(c.toString()+"\n");
        }
    }

    public static void randomCompete(int i, int j) {
        String placeString = "place" + i + j;
        String res = "";
        if (i == j) {
            return;
        }
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        Random random = new Random();
        //0,1,2
        //2==win,1==eq,0=false
        if (random.nextInt(3) == 2) {
            res = "win";
            list.get(j).setScore(list.get(j).getScore() + WIN_SCORE);
        } else if (random.nextInt(3) == 1) {
            res = "eq";
            list.get(j).setScore(list.get(j).getScore() + EQUAL_SCORE);
        } else {
            res = "false";
            list.get(j).setScore(list.get(j).getScore() + FALSE_SCORE);
        }
        competes.add(new Compete(dateFormat.format(date), placeString, res, list.get(i), list.get(j)));
    }


    public static void winCompete(int i, int j, int winTeam) {
        String placeString = "place" + i + j;
        String res = "";
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        if (i == j) {
            return;
        }
        if (i == winTeam || j == winTeam) {
            res = "win";
            list.get(winTeam).setScore(list.get(winTeam).getScore() + WIN_SCORE);
        }
        competes.add(new Compete(dateFormat.format(date), placeString, res, list.get(i), list.get(j)));
    }
}

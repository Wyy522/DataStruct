package software;

import java.util.ArrayList;

/**
 * 队伍
 * @author yiyewei
 * @create 2022/12/2 15:23
 **/
public class Team {
    public int id;
    public int score;
    public Instructor instructor;
    public ArrayList<Player> players=new ArrayList<>();

    public Team(int id, int score, Instructor instructor, ArrayList<Player> players) {
        this.id = id;
        this.score = score;
        this.instructor = instructor;
        this.players = players;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
}

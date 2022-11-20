package discipline;

import java.util.Comparator;

public class Student implements Comparable<Student>{
    private String name;
    private int age;
    private int score;

    public Student(String name, int age, int score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(Student o) {
        return this.age-o.age;
    }
}

//学生年龄比较器
class StudentAgeComparator implements Comparator<Student>{
    @Override
    public int compare(Student o1, Student o2) {
      return o1.getAge()-o2.getAge();
    }
}

//学生成绩比较器
class StudentScoreComparator implements Comparator<Student>{
    @Override
    public int compare(Student o1, Student o2) {
        return o1.getScore()-o2.getScore();
    }
}

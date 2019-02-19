public class Skill {

    private String name;
    private int point;

    public Skill(String name, int point) {
        this.name = name;
        this.point = point;
    }

    public Skill() {
    }

    public String getName() {
        return name;
    }

    public int getPoint() {
        return point;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}

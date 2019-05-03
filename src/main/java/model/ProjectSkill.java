package model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.Comparator;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectSkill extends Skill{

    private int point;

    public ProjectSkill() { }

    public ProjectSkill(String name, int point) {
        super(name);
        this.point = point;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
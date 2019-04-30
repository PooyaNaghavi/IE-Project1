package model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.Comparator;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectSkill extends Skill {

    private Project project;
    private int point = 0;

    public ProjectSkill(String name, Project project, int point) {
        this.name = name;
        this.project = project;
        this.point = point;
    }

    public Project getProject() {return this.project;}

    public void setProject(Project project) {
        this.project = project;
    }

    public int getPoint() {return point;}

    public void setPoint(int point) {
        this.point = point;
    }
}
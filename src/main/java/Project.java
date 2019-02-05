import java.util.ArrayList;

public class Project {

    private String name;
    private ArrayList<Skill> skills;
    private int budget;

    public Project(String name, ArrayList<Skill> skills, int budget){
        this.name = name;
        this.skills = skills;
        this.budget = budget;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public int getBudget() {
        return budget;
    }

    public void setName(String name) {
        name = name;
    }

    public void setSkills(ArrayList<Skill> skills) {
        skills = skills;
    }

    public void setBudget(int budget) {
        budget = budget;
    }
}

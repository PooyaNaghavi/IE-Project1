import java.util.ArrayList;

public class User {

    private String name;
    private ArrayList<Skill> skills;

    public User(String name, ArrayList<Skill> skills) {
        this.name = name;
        this.skills = skills;
    }

    public int getSkillPoint(Skill skill) {
        for(Skill user_skill : skills){
            if(user_skill.getName().equals(skill.getName())){
                return skill.getPoint();
            }
        }
        return 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        skills = skills;
    }
}

package DTO;

import model.Skill;
import model.User;

import java.util.ArrayList;

public class UserDTO {
    private User user;
    private ArrayList<Skill> allSkills;

    public UserDTO(User user, ArrayList<Skill> allSkills) {
        this.user = user;
        this.allSkills = allSkills;
    }

    public UserDTO() {
    }

    public User getUser() {
        return user;
    }

    public ArrayList<Skill> getAllSkills() {
        return allSkills;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAllSkills(ArrayList<Skill> allSkills) {
        this.allSkills = allSkills;
    }
}

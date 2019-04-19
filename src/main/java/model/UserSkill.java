package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSkill extends Skill {

    private ArrayList<User> endorseUsers;

    public UserSkill(String name) {
        this.name = name;
        this.endorseUsers = new ArrayList<>();
    }

    public UserSkill(String name, ArrayList<User> endorseUsers) {
        this.name = name;
        this.endorseUsers = endorseUsers;
    }

    public ArrayList<User> getEndorseUsers() {
        return endorseUsers;
    }

    public void setEndorseUsers(ArrayList<User> endorseUsers) {
        this.endorseUsers = endorseUsers;
    }

    public void addEndorseUser(User user) {
        endorseUsers.add(user);
    }

    public int getPoint() {
        return endorseUsers.size();
    }

}

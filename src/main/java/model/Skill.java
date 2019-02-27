package model;

import java.util.ArrayList;
import java.util.Comparator;

public class Skill {

    private String name;
    private int point;
    private ArrayList<User> endorseUsers;

    public Skill(String name, int point) {
        this.name = name;
        this.point = point;
        this.endorseUsers = new ArrayList<>();
    }

    public Skill(String name, int point, ArrayList<User> endorseUser) {
        this.name = name;
        this.point = point;
        this.endorseUsers = endorseUser;
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

    public ArrayList<User> getEndorseUsers() {
        return endorseUsers;
    }

    public void setEndorseUsers(ArrayList<User> endorseUsers) {
        this.endorseUsers = endorseUsers;
    }

    public void addEndorseUser(User user) {
        endorseUsers.add(user);
    }
}


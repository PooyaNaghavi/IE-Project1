package model;

import java.util.ArrayList;

public class Bid {

    private User user;
    private Project project;
    private int amount;

    public Bid(User user, Project project, int amount){
        this.user = user;
        this.project = project;
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public Project getProject() {
        return project;
    }

    public int getAmount() {
        return amount;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean checkBidConditions(Project project){
        if(amount > project.getBudget())
            return false;

        ArrayList<Skill> projectSkills = project.getSkills();
        ArrayList<UserSkill> userSkills = user.getSkills();
        boolean find = false;

        for(Skill projectSkill : projectSkills){
            find = false;
            for(UserSkill userSkill : userSkills){
                if(projectSkill.getName().equals(userSkill.getName()))
                {
                    find = true;
                    if(projectSkill.getPoint() > userSkill.getPoint()){
                        return false;
                    }
                }
            }
            if(!find) { return false; }
        }
        return true;
    }
}

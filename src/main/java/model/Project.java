package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import exceptions.NotFoundException;
import repository.Database;

import java.sql.SQLException;
import java.util.ArrayList;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project {

    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private int budget;
    private long deadline;
    private long creationDate;
    private User winner;
    private ArrayList<ProjectSkill> skills;

    public Project(String title, ArrayList<ProjectSkill> skills, int budget){
        this.title = title;
        this.skills = skills;
        this.budget = budget;
    }

    public Project(){ }

    public Project(String id, String title, String description, String imageURL, ArrayList<ProjectSkill> skills, int budget, long deadline, long creationDate, User winner) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageURL;
        this.skills = skills;
        this.budget = budget;
        this.deadline = deadline;
        this.creationDate = creationDate;
        this.winner = winner;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<ProjectSkill> getSkills() {
        return skills;
    }

    public int getBudget() {
        return budget;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSkills(ArrayList<ProjectSkill> skills) {
        this.skills = skills;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public long getDeadline() {
        return deadline;
    }

    public User getWinner() {
        return winner;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }
}


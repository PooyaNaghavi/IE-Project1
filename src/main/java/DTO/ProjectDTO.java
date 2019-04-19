package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import model.Bid;
import model.Project;
import model.Skill;
import model.User;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDTO {
    private ArrayList<Bid> bids;
    private Project project;

    public ProjectDTO() {
    }

    public ProjectDTO(ArrayList<Bid> bids, Project project) {
        this.bids = bids;
        this.project = project;
    }

    public ArrayList<Bid> getBids() {
        return bids;
    }

    public Project getProject() {
        return project;
    }

    public void setBids(ArrayList<Bid> bids) {
        this.bids = bids;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}

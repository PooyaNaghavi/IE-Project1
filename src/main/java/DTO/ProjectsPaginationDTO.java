package DTO;

import model.Project;

import java.util.ArrayList;

public class ProjectsPaginationDTO {
    private ArrayList<Project> projects;
    private int nextPageToken;

    public ProjectsPaginationDTO(){ }
    public ProjectsPaginationDTO(ArrayList<Project> projects, int nextPageToken) {
        this.projects = projects;
        this.nextPageToken = nextPageToken;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public long getNextPageToken() {
        return nextPageToken;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
    }

    public void setNextPageToken(int nextPageToken) {
        this.nextPageToken = nextPageToken;
    }
}

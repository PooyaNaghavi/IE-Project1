package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import exceptions.NotFoundException;
import repository.Database;

import java.util.ArrayList;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String jobTitle;
    private String profilePictureURL;
    private String bio;
    private ArrayList<UserSkill> skills;

    public User(){ }

    public User(String id, String firstName, String lastName, String userName, String password, String jobTitle, String profilePictureURL, String bio, ArrayList<UserSkill> skills) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.jobTitle = jobTitle;
        this.profilePictureURL = profilePictureURL;
        this.bio = bio;
        this.skills = skills;
    }

    public User(String id, String firstName, String lastName, String userName, String password, String jobTitle, String profilePictureURL, String bio) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.jobTitle = jobTitle;
        this.profilePictureURL = profilePictureURL;
        this.bio = bio;
    }

    public String getFirstName() {
        return firstName;
    }

    public ArrayList<UserSkill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<UserSkill> skills) {
        this.skills = skills;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() { return  password; }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getBio() {
        return bio;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) { this.password = password; }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

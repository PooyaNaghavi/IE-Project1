import Exceptions.NotFoundException;

import java.util.ArrayList;

public class User {

    private String id;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String profilePictureURL;
    private String bio;
    private ArrayList<Skill> skills;

    public User(String name, ArrayList<Skill> skills) {
        this.firstName = name;
        this.skills = skills;
    }

    public User(String id, String firstName, String lastName, String jobTitle, String profilePictureURL, String bio, ArrayList<Skill> skills) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
        this.profilePictureURL = profilePictureURL;
        this.bio = bio;
        this.skills = skills;
    }

    public int getSkillPoint(Skill skill) throws NotFoundException {
        for(Skill user_skill : skills){
            if(user_skill.getName().equals(skill.getName())){
                return user_skill.getPoint();
            }
        }
        throw new NotFoundException("Skill not found");
    }

    public String getFirstName() {
        return firstName;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public String getLastName() {
        return lastName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getProfilePicureURL() {
        return profilePictureURL;
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

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
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
}

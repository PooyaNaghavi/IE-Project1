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

    public User(){

    }

    public User(String name, ArrayList<UserSkill> skills) {
        this.firstName = name;
        this.skills = skills;
    }

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

    public int getSkillPoint(Skill skill) throws NotFoundException {
        for(UserSkill user_skill : skills){
            if(user_skill.getName().equals(skill.getName())){
                return user_skill.getPoint();
            }
        }
        throw new NotFoundException("Skill not found");
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

    private boolean hasSkill(UserSkill skill) {
        for (UserSkill userSkill : skills) {
            if(userSkill.getName().equals(skill.getName())){
                return true;
            }
        }
        return false;
    }

    public void addSkill(UserSkill skill) {
        if(hasSkill(skill)) return;
        skills.add(skill);
    }

    public void deleteSkill(UserSkill skill) {
        for (UserSkill userSkill : skills){
            if(userSkill.getName().equals(skill.getName())){
               skills.remove(userSkill);
               return;
            }
        }
    }

    public void endorseSkill(UserSkill skill, User contextUser) {
        for (UserSkill userSkill: skills){
            if(userSkill.getName().equals(skill.getName())){
                userSkill.addEndorseUser(contextUser); // TODO: logic is changed, do something.
            }
        }
    }

    public HashMap<String, Boolean> getEndorseSkillsByUser(User contextUser) {
        HashMap<String, Boolean> endorseSkills = new HashMap<>();
        for (UserSkill skill : skills){
            for (Endorsement en : skill.getEndorses()){
                if(en.getEndorsed().equals(contextUser.getId())){ // TODO: logic is changed. probably easier impl.
                    endorseSkills.put(skill.getName(), true);
                } else {
                    endorseSkills.put(skill.getName(), false);
                }
            }
        }
        return endorseSkills;
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

package model;

import exceptions.NotFoundException;
import repository.Database;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private String id;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String profilePictureURL;
    private String bio;
    private ArrayList<UserSkill> skills;

    public User(String name, ArrayList<UserSkill> skills) {
        this.firstName = name;
        this.skills = skills;
    }

    public User(String id, String firstName, String lastName, String jobTitle, String profilePictureURL, String bio, ArrayList<UserSkill> skills) {
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

    public boolean checkSkillCondtions(Project project){

        ArrayList<Skill> projectSkills = project.getSkills();
        boolean find;

        for(Skill projectSkill : projectSkills){
            find = false;
            for(Skill userSkill : skills){
                if(projectSkill.getName().equals(userSkill.getName()))
                {
                    find = true;
                    if(projectSkill.getPoint() > userSkill.getPoint()){
                        return false;
                    }
                }
            }
            if(!find)
                return false;
        }
        return true;
    }

    public ArrayList<Project> getQualifiedProjects() {
        ArrayList<Project> projects = Database.getProjects();
        ArrayList<Project> qualifiedProjects = new ArrayList<>();
        for(Project project: projects) {
            if(checkSkillCondtions(project)) {
                qualifiedProjects.add(project);
            }
        }
        return qualifiedProjects;
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

    private boolean hasSkill(UserSkill skill) {
        for (UserSkill userSkill :skills) {
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
                userSkill.addEndorseUser(contextUser);
            }
        }
    }

    public HashMap<String, Boolean> getEndorseSkillsByUser(User contextUser) {
        HashMap<String, Boolean> endorseSkills = new HashMap<>();
        for (UserSkill skill : skills){
            for (User user : skill.getEndorseUsers()){
                if(user.getId().equals(contextUser.getId())){
                    endorseSkills.put(skill.getName(), true);
                } else {
                    endorseSkills.put(skill.getName(), false);
                }
            }
        }
        return endorseSkills;
    }
}

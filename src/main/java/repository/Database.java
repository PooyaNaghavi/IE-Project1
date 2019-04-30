package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataLayer.dataMappers.UserMapper;
import exceptions.BadConditionException;
import exceptions.NotFoundException;
import model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {

    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Project> projects = new ArrayList<>();
    private static ArrayList<Bid> bids = new ArrayList<>();
    private static ArrayList<Skill> skills = new ArrayList<>();

    public static void addAuthenticatedUser() throws SQLException {
        UserMapper userMapper = new UserMapper();
        ArrayList<UserSkill> skills = new ArrayList<>();
        ArrayList<User> endorsers = new ArrayList<>();

        // TODO: add after the UserSkill Table is created
        for (User u : users) {
            endorsers.add(u);
        }
        skills.add(new UserSkill("HTML", endorsers));
        skills.add(new UserSkill("Javascript", endorsers));
        skills.add(new UserSkill("C++", endorsers));
        skills.add(new UserSkill("Java", endorsers));

        userMapper.insertUser(new User("1", "علی", "شریف‌زاده","1","1234" ,"برنامه‌نویس وب", null, "روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلی کارا بکنه ولی پول نداشت"));
    }

    // DONE
    public static User findUserById(String id) throws NotFoundException, SQLException {
        UserMapper userMapper = new UserMapper();
        userMapper.findById(id);
        throw new NotFoundException("User not found!!!");
    }


    public static Project findProjectByTitle(String title) throws NotFoundException{
        for(Project project : projects){
            if(project.getTitle().equals(title)){
                return project;
            }
        }
        throw new NotFoundException("Project not found!!!");
    }

    public static Project findProjectById(String id) throws NotFoundException{
        for(Project project : projects){
            System.out.println(project.getId());
            if(project.getId().equals(id)){
                return project;
            }
        }
        throw new NotFoundException("Project not found!!!");
    }

    // DONE
    public static ArrayList<User> getUsers() throws SQLException {
        UserMapper userMapper = new UserMapper();
        return userMapper.findAll();
    }

    public static ArrayList<Project> getProjects() {
        return projects;
    }

    public static ArrayList<Bid> getBids() {
        return bids;
    }

    public static void insertProject(String title, ArrayList<Skill> projectSkills, int budget) {
        Project project = new Project(title, projectSkills, budget);
        projects.add(project);
    }

    public static void insertBid(User user, Project project, int bidAmount) throws BadConditionException {
        Bid bid = new Bid(user, project, bidAmount);
        System.out.println(bidAmount);
        System.out.println(user.getId());
        System.out.println(project.getId());
        if(!bid.checkBidConditions(project))
            throw new BadConditionException("bid conditions not satistfied");
        for(Bid prev_bid : bids) {
            if(prev_bid.getUser().getFirstName().equals(bid.getUser().getFirstName()) && prev_bid.getProject().getTitle().equals(bid.getProject().getTitle())) {
                prev_bid.setAmount(bid.getAmount());
                return;
            }
        }
        bids.add(bid);
    }

    public static void insertUser(String username, ArrayList<UserSkill> userSkills) {
        User user = new User(username, userSkills);
        users.add(user);
    }

    public static Bid findBid(User user, Project project){

        for(Bid bid : bids){
            if(bid.getProject().getId().equals(project.getId()) && bid.getUser().getId().equals(user.getId())){
                return bid;
            }
        }
        throw new NotFoundException("Bid not found");
    }

    public static ArrayList<User> findBiddingUserInProject(Project project){
        ArrayList<User> biddingUser = new ArrayList<>();

        for(Bid bid : bids){
            if(bid.getProject().getTitle().equals(project.getTitle())){
                biddingUser.add(bid.getUser());
            }
        }
        return biddingUser;
    }

    public static Bid findUserOffer(User biddingUser, Project project) throws NotFoundException {
        for(Bid bid : bids){
            if(bid.getUser().getFirstName().equals(biddingUser.getFirstName()) && bid.getProject().getTitle().equals(project.getTitle())){
                return bid;
            }
        }
        throw new NotFoundException("Bid not found");
    }

    public static void insertMultipleUsers(ArrayList<User> users) {
        Database.users.addAll(users);
    }

    public static void insertMultipleProjects(ArrayList<Project> projects) {
        Database.projects.addAll(projects);
    }

    public static void insetMultipleBids(ArrayList<Bid> bids) {
        Database.bids.addAll(bids);
    }

    public static void setUsers(ArrayList<User> users) {
        Database.users = users;
    }

    public static void setProjects(ArrayList<Project> projects) {
        Database.projects = projects;
    }

    public static void setBids(ArrayList<Bid> bids) {
        Database.bids = bids;
    }

    public static ArrayList<Skill> getSkills() {
        return skills;
    }

    public static void setSkills(ArrayList<Skill> skills) {
        Database.skills = skills;
    }

    public static Skill findSkillByName(String skillName) {
        for(Skill skill : skills){
            if(skill.getName().equals(skillName)){
                return skill;
            }
        }
        throw new NotFoundException("Skill not found");
    }

    public static void addSomeUsersAndEndorsements() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<User> initialUsers = new ArrayList<>();
        ArrayList<UserSkill> pooyaSkills = new ArrayList<>();
        pooyaSkills.add(new UserSkill("C"));
        initialUsers.add(new User(
                "2",
                "pooya",
                "naghavi",
                "2",
                "1234",
                "bikar",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRQAfJWoANWz9aJr2R4ke04WLbaZYrlx3dahOzNYtAiTARLy-KGyw",
                "pooooooya hastam",
                pooyaSkills));
        ArrayList<UserSkill> mamadSkills = new ArrayList<>();
        mamadSkills.add(new UserSkill("C"));
        mamadSkills.add(new UserSkill("C++"));
        initialUsers.add(new User(
                "3",
                "mohammad",
                "ganji",
                "3",
                "1234",
                "bakar",
                "https://www.gstatic.com/webp/gallery/1.jpg",
                "mamadam",
                mamadSkills));
        Database.setUsers(initialUsers);
    }

    public static HashMap<String, Boolean> getAllSkillsByUser(User user) {
        HashMap<String, Boolean> allSkills = new HashMap<>();
        for (Skill skill : skills){
            for (UserSkill userSkill : user.getSkills()){
                if(skill.getName().equals(userSkill.getName())){
                    allSkills.put(skill.getName(), true);
                    break;
                } else {
                    allSkills.put(skill.getName(), false);
                }
            }
        }
        return allSkills;
    }

    public static ArrayList<Bid> findProjectBids(Project project){
        ArrayList<Bid> projectBids = new ArrayList<>();
        for(Bid bid : bids){
            if(bid.getProject().getId().equals(project.getId())){
                projectBids.add(bid);
            }
        }
        return projectBids;
    }

    public static boolean checkSkillConditions(Project project, User user){

        ArrayList<Skill> projectSkills = project.getSkills();
        boolean find;

        for(Skill projectSkill : projectSkills){
            find = false;
            for(Skill userSkill : user.getSkills()){
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

    public static ArrayList<Project> getQualifiedProjects(User user) {
        ArrayList<Project> qualifiedProjects = new ArrayList<>();
        for(Project project: projects) {
            if(checkSkillConditions(project, user)) {
                qualifiedProjects.add(project);
            }
        }
        return qualifiedProjects;
    }
}

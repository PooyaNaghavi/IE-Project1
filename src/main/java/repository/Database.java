package repository;

import exceptions.BadConditionException;
import exceptions.NotFoundException;
import model.Bid;
import model.Project;
import model.Skill;
import model.User;

import java.util.ArrayList;

public class Database {

    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Project> projects = new ArrayList<>();
    private static ArrayList<Bid> bids = new ArrayList<>();
    private static ArrayList<Skill> skills = new ArrayList<>();

    public static void addAuthenticatedUser(){
        ArrayList<Skill> skills = new ArrayList<>();
        skills.add(new Skill("HTML", 5));
        skills.add(new Skill("Javascript", 4));
        skills.add(new Skill("C++", 2));
        skills.add(new Skill("Java", 3));
        users.add(new User("1", "علی", "شریف‌زاده", "برنامه‌نویس وب", null, "روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلی کارا بکنه ولی پول نداشت", skills));
    }

    public static User findUserById(String id) throws NotFoundException{
        for(User user : users){
            if(user.getId().equals(id)){
                return user;
            }
        }
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
            if(project.getId().equals(id)){
                return project;
            }
        }
        throw new NotFoundException("Project not found!!!");
    }

    public static ArrayList<User> getUsers() {
        return users;
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

    public static void insertUser(String username, ArrayList<Skill> userSkills) {
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
}

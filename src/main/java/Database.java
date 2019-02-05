import Exceptions.BadConditionException;
import Exceptions.NotFoundException;

import java.util.ArrayList;

public class Database {

    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Project> projects = new ArrayList<>();
    private static ArrayList<Bid> bids = new ArrayList<>();

    public static User findUserByName(String name) throws NotFoundException{
        for(User user : users){
            if(user.getName().equals(name)){
                return user;
            }
        }
        throw new NotFoundException("User not found!!!");
    }

    public static Project findProjectByName(String name) throws NotFoundException{
        for(Project project : projects){
            if(project.getName().equals(project.getName())){
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
        Database.getProjects().add(project);
    }

    public static void insertBid(User user, Project project, int bidAmount) throws BadConditionException {
        Bid bid = new Bid(user, project, bidAmount);
        if(!Auction.checkBidCondtions(bid, project))
            throw new BadConditionException("bid conditions not satistfied");
        Database.getBids().add(bid);
    }

    public static void insertUser(String username, ArrayList<Skill> userSkills) {
        User user = new User(username, userSkills);
        Database.getUsers().add(user);
    }

    public static ArrayList<User> findBiddingUserInProject(Project project){
        ArrayList<User> biddingUser = new ArrayList<>();

        for(Bid bid : bids){
            if(bid.getProject().getName().equals(project.getName())){
                biddingUser.add(bid.getUser());
            }
        }
        return biddingUser;
    }

    public static Bid findUserOffer(User biddingUser, Project project) throws NotFoundException {
        for(Bid bid : Database.getBids()){
            if(bid.getUser().getName().equals(biddingUser.getName()) && bid.getProject().getName().equals(project.getName())){
                return bid;
            }
        }
        throw new NotFoundException("Bid not found");
    }
}

import Exceptions.BadConditionException;
import Exceptions.NotFoundException;

import java.util.ArrayList;

public class Database {

    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Project> projects = new ArrayList<>();
    private static ArrayList<Bid> bids = new ArrayList<>();

    public static User findUserByName(String name) throws NotFoundException{
        for(User user : users){
            if(user.getFirstName().equals(name)){
                return user;
            }
        }
        throw new NotFoundException("User not found!!!");
    }

    public static Project findProjectByName(String name) throws NotFoundException{
        for(Project project : projects){
            if(project.getTitle().equals(name)){
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
        if(!Auction.checkBidCondtions(bid, project))
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
}

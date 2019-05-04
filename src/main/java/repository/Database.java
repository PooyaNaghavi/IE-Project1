package repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import dataLayer.dataMappers.*;
import exceptions.BadConditionException;
import exceptions.NotFoundException;
import model.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {

    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Project> projects = new ArrayList<>();
    private static ArrayList<Bid> bids = new ArrayList<>();
    private static ArrayList<Skill> skills = new ArrayList<>();

    private static BidMapper bidMapper;
    private static EndorsementMapper endorsementMapper;
    private static ProjectMapper projectMapper;
    private static ProjectSkillMapper projectSkillMapper;
    private static SkillMapper skillMapper;
    private static UserMapper userMapper;
    private static UserSkillMapper userSkillMapper;

    public static void setMapper() throws SQLException {
        bidMapper = new BidMapper();
        endorsementMapper = new EndorsementMapper();
        projectMapper = new ProjectMapper();
        projectSkillMapper = new ProjectSkillMapper();
        skillMapper = new SkillMapper();
        userMapper = new UserMapper();
        userSkillMapper = new UserSkillMapper();

        bidMapper.setMapper(userMapper, projectMapper);
        endorsementMapper.setMapper(userMapper, skillMapper);
        projectMapper.setMapper(userMapper, projectSkillMapper);
        userMapper.setMapper(userSkillMapper);
        userSkillMapper.setMapper(endorsementMapper);
    }

    //Done
    public static void addAuthenticatedUser() throws SQLException {
        User user = new User("1", "علی", "شریف‌زاده","1","1234" ,"برنامه‌نویس وب", null, "روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلی کارا بکنه ولی پول نداشت");
        userMapper.insertOne(user);
        userSkillMapper.insertOne(new Skill("HTML"), user);
        userSkillMapper.insertOne(new Skill("Javascript"), user);
        userSkillMapper.insertOne(new Skill("C++"), user);
        userSkillMapper.insertOne(new Skill("Java"), user);
        System.out.println("------------------------1");
        ArrayList<User> users = userMapper.findAll();
        System.out.println("------------------------2");
        for(User u : users){
            endorsementMapper.insertOne(new Endorsement(u.getId(), user.getId(), new Skill("HTML")));
            endorsementMapper.insertOne(new Endorsement(u.getId(), user.getId(), new Skill("Javascript")));
            endorsementMapper.insertOne(new Endorsement(u.getId(), user.getId(), new Skill("C++")));
            endorsementMapper.insertOne(new Endorsement(u.getId(), user.getId(), new Skill("Java")));
        }
        System.out.println("saallaammmm");
    }

    // DONE
    public static User findUserById(String id) throws NotFoundException, SQLException {
        return userMapper.findById(id);
//        throw new NotFoundException("User not found!!!");
    }
    // DONE
    public static Project findProjectById(String id) throws NotFoundException, SQLException {
        return projectMapper.findById(id);
//        throw new NotFoundException("Project not found!!!");
    }
    // DONE
    public static ArrayList<User> getUsers() throws SQLException {
        return userMapper.findAll();
    }
    // DONE
    public static ArrayList<Project> getProjects() throws SQLException {
        return projectMapper.findAll();
    }
    // DONE
    public static ArrayList<Bid> getBids() throws SQLException {
        return bidMapper.findAll();
    }
    //DONE
    public static boolean checkBidConditions(Bid bid, Project project) throws SQLException {
        if(bid.getAmount() <= 0 || bid.getAmount() > project.getBudget())
            return false;

        return checkSkillConditions(project, bid.getUser());
    }
    // Done
    public static void insertBid(User user, Project project, int bidAmount) throws BadConditionException, SQLException {
        Bid bid = new Bid(user, project, bidAmount);
        if(!checkBidConditions(bid, project))
            throw new BadConditionException("bid conditions not satistfied");
        bidMapper.insertOne(bid);
    }
    // Done
    public static Bid findBid(User user, Project project) throws SQLException {
        return bidMapper.findBid(user, project);
    }
    // Done
    public static ArrayList<User> findBiddingUserInProject(Project project) throws SQLException {
        return bidMapper.findBiddingUserInProject(project);
    }
    // Done
    public static void insertMultipleUsers(ArrayList<User> users) throws SQLException {
        for (User user : users){
            userMapper.insertOne(user);
        }
    }
    // Done
    public static void insertMultipleProjects(ArrayList<Project> projects) throws SQLException {
        System.out.println(projects.size());
        for (Project project : projects){
            System.out.println(project.getSkills().size());
            projectMapper.insertOne(project);
            System.out.println("-==-=-=-=-=-=-=-=-=-=-=-=-=");
            for(ProjectSkill projectSkill : project.getSkills()) {
                projectSkillMapper.insertOne(projectSkill, project);
            }

        }
    }
    // Done
    public static void insetMultipleBids(ArrayList<Bid> bids) throws SQLException {
        for (Bid bid : bids){
            bidMapper.insertOne(bid);
        }
    }
    // Done
    public static ArrayList<Skill> getSkills() throws SQLException {
        return skillMapper.findAll();
    }
    // Done
    public static void setSkills(ArrayList<Skill> skills) throws SQLException {
        System.out.println(skills.size());
        for (Skill skill : skills){
            skillMapper.insertOne(skill);
        }
    }
    // Done
    public static Skill findSkillByName(String skillName) throws SQLException {
        return skillMapper.findByName(skillName);
//        throw new NotFoundException("Skill not found");
    }
    // Done
    public static void addSomeUsersAndEndorsements() throws SQLException {
        System.out.println("========================1");
        User pooya = new User(
            "2",
            "pooya",
            "naghavi",
            "2",
            "1234",
            "bikar",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRQAfJWoANWz9aJr2R4ke04WLbaZYrlx3dahOzNYtAiTARLy-KGyw",
            "pooooooya hastam");
        System.out.println("========================2");
        userMapper.insertOne(pooya);
        System.out.println("========================3");
        userSkillMapper.insertOne(new Skill("C"), pooya);
        System.out.println("========================4");
        User mamad = new User(
            "3",
            "mohammad",
            "ganji",
            "3",
            "1234",
            "bakar",
            "https://www.gstatic.com/webp/gallery/1.jpg",
            "mamadam");
        System.out.println("========================5");
        userMapper.insertOne(mamad);
        System.out.println("========================6");
        userSkillMapper.insertOne(new Skill("C"), mamad);
        System.out.println("========================7");
        userSkillMapper.insertOne(new Skill("C++"), mamad);
        System.out.println("========================8");
    }
    // Done
    public static ArrayList<Bid> findProjectBids(Project project) throws SQLException {
        return bidMapper.findProjectBids(project);
    }
    // Done
    public static boolean checkSkillConditions(Project project, User user) throws SQLException {

        boolean find;
        ArrayList<ProjectSkill> projectSkills = projectSkillMapper.getProjectSkills(project.getId());
        ArrayList<UserSkill> userSkills = userSkillMapper.getUserSkills(user.getId());

        for(ProjectSkill projectSkill : projectSkills){
            find = false;
            for(UserSkill userSkill : userSkills){
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
    //Done
    public static ArrayList<Project> getQualifiedProjects(User user) throws SQLException {
        ArrayList<Project> qualifiedProjects = new ArrayList<>();
        ArrayList<Project> projects = projectMapper.findAll();
        for(Project project: projects) {
            if(checkSkillConditions(project, user)) {
                qualifiedProjects.add(project);
            }
        }
        return qualifiedProjects;
    }
    // Done
    public static void addSkillToUser(User user, Skill skill) throws SQLException {
        userSkillMapper.insertOne(skill, user);
    }
    // Done
    public static void deleteSkillOfUser(User user, Skill skill) throws SQLException {
        userSkillMapper.deleteSkill(skill, user);
    }
    // Done
    public static void endorseSkillOfUser(User endorserUser, User endorsedUser, Skill skill) throws SQLException {
        endorsementMapper.insertOne(new Endorsement(endorserUser.getId(), endorsedUser.getId(), skill));
    }
    // Done
    public static int getSkillPoint(User user, Skill skill) throws NotFoundException, SQLException {
        return  userSkillMapper.findUserSkill(skill.getName(), user).getPoint();
    }
    // Done
    public static int calcBidValue(User biddingUser, Project project) throws NotFoundException, SQLException {
        int sum = 0;
        for(ProjectSkill skill : project.getSkills()){
            sum += Math.pow((getSkillPoint(biddingUser, skill) - skill.getPoint()), 2) * 10000;
        }
        sum += project.getBudget() - findBid(biddingUser, project).getAmount();
        return sum;

    }
    // Done
    public static User findAuctionWinner(ArrayList<User> biddingUsers, Project project) throws NotFoundException, SQLException {
        if(biddingUsers.size() == 0){
            throw new NotFoundException("No bid for this project");
        }
        int max = calcBidValue(biddingUsers.get(0), project);
        User maxUser = biddingUsers.get(0);
        for(User biddingUser : biddingUsers){
            if(calcBidValue(biddingUser, project) > max){
                max = calcBidValue(biddingUser, project);
                maxUser = biddingUser;
            }
        }
        return maxUser;
    }

    public static boolean AuthenticateUser(User user){
        try {
            userMapper.findByUsernameAndPassword(user);
            // TODO : set Context User
            return true;
        }catch(SQLException e){
            return false;
        }
    }

    public static ArrayList<Project> getProjectsPage(int limit, int nextPageToken) throws SQLException {
        return projectMapper.getProjectsPage(limit, nextPageToken);
    }
}

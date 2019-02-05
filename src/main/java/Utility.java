import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Utility {

    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Project> projects = new ArrayList<>();
    public static ArrayList<Bid> bids = new ArrayList<>();

    public static User findUserByName(String name){
        System.out.println(name);
        for(User user : users){
            System.out.println(user.getName());
            if(user.getName().equals(name)){
                return user;
            }
        }
        return null;
    }

    public static Project findProjectByName(String name){
        for(Project project : projects){
            if(project.getName().equals(project.getName())){
                return project;
            }
        }
        return null;
    }

    public static int getUserOffer(User biddingUser, Project project){
        for(Bid bid : bids){
            if(bid.getUser().getName().equals(biddingUser.getName()) && bid.getProject().getName().equals(project.getName())){
                return bid.getAmount();
            }
        }
        return 0;
    }

    public static int findBidAmount(User biddingUser, Project project){
        int sum = 0;
        for(Skill skill : project.getSkills()){
            sum += Math.pow((biddingUser.getSkillPoint(skill) - skill.getPoint()), 2) * 10000;
        }
        sum += project.getBudget() - getUserOffer(biddingUser, project);
        return sum;
    }

    public static User findBestBid(ArrayList<User> biddingUsers, Project project){
        int max = findBidAmount(biddingUsers.get(0), project);
        User maxUser = biddingUsers.get(0);
        for(User biddingUser : biddingUsers){
            if(findBidAmount(biddingUser, project) > max){
                max = findBidAmount(biddingUser, project);
                maxUser = biddingUser;
            }
        }
        return maxUser;
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

    public static boolean checkBidCondtions(Bid bid, Project project){
        if(bid.getAmount() > project.getBudget())
            return false;

        ArrayList<Skill> projectSkills = project.getSkills();
        ArrayList<Skill> userSkills = bid.getUser().getSkills();
        boolean find = false;

        for(Skill projectSkill : projectSkills){
            find = false;
            for(Skill userSkill : userSkills){
                if(projectSkill.getName().equals(userSkill.getName()))
                {
                    find = true;
                    if(projectSkill.getPoint() > userSkill.getPoint()){
                        return false;
                    }
                }
            }
            if(find == false)
                return false;
        }
        return true;
    }

    public static ArrayList<Skill> getSkills(JSONArray inputJSON) {
        ArrayList<Skill> skills = new ArrayList<>();

        for(int i = 0; i < inputJSON.length(); i++){
            JSONObject skill = inputJSON.getJSONObject(i);
            String skillName = skill.getString("name");
            int skillPoint = skill.getInt("points");

            Skill temp = new Skill(skillName, skillPoint);
            skills.add(temp);

            System.out.println(i + ":");
            System.out.println(skillName);
            System.out.println(skillPoint);
        }
        return skills;
    }

    public static void register(String commandData) {

        JSONObject jsonObject = new JSONObject(commandData);

        String username = jsonObject.getString("username");
        System.out.println(username);

        JSONArray skills = jsonObject.getJSONArray("skills");

        ArrayList<Skill> userSkills = getSkills(skills);

        // TODO : what should do with User
        User user = new User(username, userSkills);
        users.add(user);
    }

    public static void addProject(String commandData) {
        JSONObject jsonObject = new JSONObject(commandData);

        String title = jsonObject.getString("title");
        System.out.println(title);

        JSONArray skills = jsonObject.getJSONArray("skills");

        ArrayList<Skill> projectSkills = getSkills(skills);

        int budget = jsonObject.getInt("budget");
        System.out.println(budget);

        //TODO : what should do with Project
        Project project = new Project(title, projectSkills, budget);
        projects.add(project);

    }

    public static void bid(String commandData) {
        JSONObject jsonObject = new JSONObject(commandData);

        String biddingUser = jsonObject.getString("biddingUser");
        System.out.println(biddingUser);

        String projectTitle = jsonObject.getString("projectTitle");
        System.out.println(projectTitle);

        int bidAmount = jsonObject.getInt("bidAmount");
        System.out.println(bidAmount);

        for (User user : users)
            System.out.println("nowwww " + users.get(1).getName());


        int userIndex = users.indexOf(findUserByName(biddingUser));
        int projectIndex = projects.indexOf(findProjectByName(projectTitle));

        User user = users.get(userIndex);
        Project project = projects.get(projectIndex);

        //TODO : what should do with Bid , check bid conditions
        Bid bid = new Bid(user, project, bidAmount);
        if(checkBidCondtions(bid, project))
            bids.add(bid);
    }

    public static void auction(String commandData) {
        JSONObject jsonObject = new JSONObject(commandData);

        String projectTitle = jsonObject.getString("projectTitle");
        System.out.println(projectTitle);

        int projectIndex = projects.indexOf(findProjectByName(projectTitle));
        Project project = projects.get(projectIndex);

        ArrayList<User> biddingUsers = findBiddingUserInProject(project);
        User winner = findBestBid(biddingUsers, project);
        System.out.println("winner : " + winner.getName());
        //TODO : check auction conditions
    }
}

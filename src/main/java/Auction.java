import Exceptions.NotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Auction {

    private static int calcBidValue(User biddingUser, Project project) throws NotFoundException {
        int sum = 0;
        for(Skill skill : project.getSkills()){
            sum += Math.pow((biddingUser.getSkillPoint(skill) - skill.getPoint()), 2) * 10000;
        }
        sum += project.getBudget() - Database.findUserOffer(biddingUser, project).getAmount();
        return sum;

    }

    private static User findAuctionWinner(ArrayList<User> biddingUsers, Project project) throws NotFoundException{
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

    private static ArrayList<Skill> getSkillsFromJSON(JSONArray inputJSON) {
        ArrayList<Skill> skills = new ArrayList<>();

        for(int i = 0; i < inputJSON.length(); i++){
            JSONObject skill = inputJSON.getJSONObject(i);
            String skillName = skill.getString("name");
            int skillPoint = skill.getInt("points");

            Skill temp = new Skill(skillName, skillPoint);
            skills.add(temp);
        }
        return skills;
    }

    public static void register(String commandData) {

        JSONObject jsonObject = new JSONObject(commandData);

        String username = jsonObject.getString("username");

        JSONArray skills = jsonObject.getJSONArray("skills");

        ArrayList<Skill> userSkills = getSkillsFromJSON(skills);

        Database.insertUser(username, userSkills);

    }

    public static void addProject(String commandData) {
        JSONObject jsonObject = new JSONObject(commandData);

        String title = jsonObject.getString("title");

        JSONArray skills = jsonObject.getJSONArray("skills");

        ArrayList<Skill> projectSkills = getSkillsFromJSON(skills);

        int budget = jsonObject.getInt("budget");

        Database.insertProject(title, projectSkills, budget);
    }

    public static void bid(String commandData) {
        JSONObject jsonObject = new JSONObject(commandData);

        String biddingUser = jsonObject.getString("biddingUser");

        String projectTitle = jsonObject.getString("projectTitle");

        int bidAmount = jsonObject.getInt("bidAmount");

        try {
            User user = Database.findUserByName(biddingUser);
            Project project = Database.findProjectByName(projectTitle);
            Database.insertBid(user, project, bidAmount);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void auction(String commandData) {
        JSONObject jsonObject = new JSONObject(commandData);

        String projectTitle = jsonObject.getString("projectTitle");
        try {
            Project project = Database.findProjectByName(projectTitle);
            ArrayList<User> biddingUsers = Database.findBiddingUserInProject(project);
            User winner = findAuctionWinner(biddingUsers, project);
            System.out.println("winner : " + winner.getName());
        }
        catch(NotFoundException e){
            System.out.println(e.getMessage());
        }
    }
}

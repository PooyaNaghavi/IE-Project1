import Exceptions.NotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Auction {

//    public static boolean checkBidCondtions(Bid bid, Project project){
//        if(bid.getAmount() > project.getBudget())
//            return false;
//
//        ArrayList<Skill> projectSkills = project.getSkills();
//        ArrayList<Skill> userSkills = bid.getUser().getSkills();
//        boolean find = false;
//
//        for(Skill projectSkill : projectSkills){
//            find = false;
//            for(Skill userSkill : userSkills){
//                if(projectSkill.getName().equals(userSkill.getName()))
//                {
//                    find = true;
//                    if(projectSkill.getPoint() > userSkill.getPoint()){
//                        return false;
//                    }
//                }
//            }
//            if(find == false)
//                return false;
//        }
//        return true;
//    }

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

//    public static void register(String commandData) {
//
//        JSONObject jsonObject = new JSONObject(commandData);
//
//        String username = jsonObject.getString("username");
//
//        JSONArray skills = jsonObject.getJSONArray("skills");
//
//        ArrayList<Skill> userSkills = getSkillsFromJSON(skills);
//
//        Database.insertUser(username, userSkills);
//
//    }
//
//    public static void addProject(String commandData) {
//        JSONObject jsonObject = new JSONObject(commandData);
//
//        String title = jsonObject.getString("title");
//
//        JSONArray skills = jsonObject.getJSONArray("skills");
//
//        ArrayList<Skill> projectSkills = getSkillsFromJSON(skills);
//
//        int budget = jsonObject.getInt("budget");
//
//        Database.insertProject(title, projectSkills, budget);
//    }
//
//    public static void bid(String commandData) {
//        JSONObject jsonObject = new JSONObject(commandData);
//
//        String biddingUser = jsonObject.getString("biddingUser");
//
//        String projectTitle = jsonObject.getString("projectTitle");
//
//        int bidAmount = jsonObject.getInt("bidAmount");
//
//        try {
//            User user = Database.findUserByName(biddingUser);
//            Project project = Database.findProjectByTitle(projectTitle);
//            Database.insertBid(user, project, bidAmount);
//        }catch(Exception e){
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public static void auction(String commandData) {
//        JSONObject jsonObject = new JSONObject(commandData);
//
//        String projectTitle = jsonObject.getString("projectTitle");
//        try {
//            Project project = Database.findProjectByTitle(projectTitle);
//            ArrayList<User> biddingUsers = Database.findBiddingUserInProject(project);
//            User winner = project.findAuctionWinner(biddingUsers, project);
//            System.out.println("winner : " + winner.getFirstName());
//        }
//        catch(NotFoundException e){
//            System.out.println(e.getMessage());
//        }
//    }
}

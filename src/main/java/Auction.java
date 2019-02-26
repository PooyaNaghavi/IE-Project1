import model.Bid;
import model.Project;
import model.Skill;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Auction {

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
//        repository.Database.insertUser(username, userSkills);
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
//        repository.Database.insertProject(title, projectSkills, budget);
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
//            User user = repository.Database.findUserByName(biddingUser);
//            Project project = repository.Database.findProjectByTitle(projectTitle);
//            repository.Database.insertBid(user, project, bidAmount);
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
//            Project project = repository.Database.findProjectByTitle(projectTitle);
//            ArrayList<User> biddingUsers = repository.Database.findBiddingUserInProject(project);
//            User winner = project.findAuctionWinner(biddingUsers, project);
//            System.out.println("winner : " + winner.getFirstName());
//        }
//        catch(NotFoundException e){
//            System.out.println(e.getMessage());
//        }
//    }
}

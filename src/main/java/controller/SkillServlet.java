package controller;

import model.Project;
import model.Skill;
import model.User;
import repository.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.StringTokenizer;

@WebServlet("/skill")
public class SkillServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String body = request.getReader().lines()
                .reduce("", (accumulator, actual) -> accumulator + actual);
        StringTokenizer st = new StringTokenizer(body, "&");
        User contextUser = (User) request.getAttribute("contextUser");

        String action = "";
        Skill skill = null;
        User user = null;
        while (st.hasMoreTokens()) {
            String param = st.nextToken();
            StringTokenizer paramSt = new StringTokenizer(param, "=");
            String name = paramSt.nextToken();
            switch (name) {
                case "action":
                    action = paramSt.nextToken();
                    break;
                case "skill":
                    String skillName = paramSt.nextToken();
                    while(skillName.contains("%2B")){
                        skillName = skillName.substring(0, skillName.indexOf("%2B")) + "+" + skillName.substring(skillName.indexOf("%2B"), skillName.lastIndexOf("%2B"));
                    }
                    skill = Database.findSkillByName(skillName);
                    break;
                case "user":
                    user = Database.findUserById(paramSt.nextToken());
                    break;
                default:
                    break;
            }
        }
        switch (action){
            case "add":
                user.addSkill(skill);
                break;
            case "delete":
                user.deleteSkill(skill);
                break;
            case "endorse":
                user.endorseSkill(skill, contextUser);
                break;
            default:
                break;
        }
        request.setAttribute("user", user);
        request.setAttribute("userSkills", user.getSkills());
        request.setAttribute("allSkills", Database.getSkills());
        request.setAttribute("endorseSkills", user.getEndorseSkillsByUser(contextUser));
        request.getRequestDispatcher("/user-profile.jsp").forward(request, response);
    }
}

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
        User contextUser = (User) request.getAttribute("contextUser");
        String action = request.getParameter("action");
        Skill skill = Database.findSkillByName(request.getParameter("skill"));
        User user = Database.findUserById(request.getParameter("user"));
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

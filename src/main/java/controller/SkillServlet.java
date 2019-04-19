package controller;

import model.Project;
import model.Skill;
import model.User;
import model.UserSkill;
import repository.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

//user/skill/html?id=userid

@WebServlet("/user/skill/*")
public class SkillServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    public String getSkillName(HttpServletRequest request){
        String uri = request.getRequestURI().toString();
        StringTokenizer st = new StringTokenizer(uri, "/");
        st.nextToken();
        st.nextToken();
        String skillName = st.nextToken();
        return skillName;
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String skillName = getSkillName(request);
        User contextUser = (User) request.getAttribute("contextUser");
        Skill skill = Database.findSkillByName(skillName);
        UserSkill userSkill = new UserSkill(skill.getName());
        User user = Database.findUserById(request.getParameter("id"));
        user.endorseSkill(userSkill, contextUser);
        Utils.sendJSON("{message: \"endorse skill successful\"}", response, 200);
    }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String skillName = getSkillName(request);
        User contextUser = (User) request.getAttribute("contextUser");
        Skill skill = Database.findSkillByName(skillName);
        UserSkill userSkill = new UserSkill(skill.getName());
//        User user = Database.findUserById(request.getParameter("id"));
        contextUser.deleteSkill(userSkill);
        Utils.sendJSON("{message: \"delete skill successful\"}", response, 200);
    }

}

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

@WebServlet("/skill")
public class SkillServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println(request.getParameter("user"));
//        String str, wholeStr = "";
//        BufferedReader br = request.getReader();
//        while ((str = br.readLine()) != null) {
//            wholeStr += str;
//        }
//
//        System.out.println(wholeStr);
        User contextUser = (User) request.getAttribute("contextUser");
        Skill skill = Database.findSkillByName(request.getParameter("skill"));
        UserSkill userSkill = new UserSkill(skill.getName());
        User user = Database.findUserById(request.getParameter("user"));
        user.addSkill(userSkill);
        Utils.sendJSON("{message: \"add skill successful\"}", response, 200);
    }
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User contextUser = (User) request.getAttribute("contextUser");
        Skill skill = Database.findSkillByName(request.getParameter("skill"));
        UserSkill userSkill = new UserSkill(skill.getName());
        User user = Database.findUserById(request.getParameter("user"));
        user.endorseSkill(userSkill, contextUser);
        Utils.sendJSON("{message: \"endorse skill successful\"}", response, 200);
    }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User contextUser = (User) request.getAttribute("contextUser");
        Skill skill = Database.findSkillByName(request.getParameter("skill"));
        UserSkill userSkill = new UserSkill(skill.getName());
        User user = Database.findUserById(request.getParameter("user"));
        user.deleteSkill(userSkill);
        Utils.sendJSON("{message: \"delete skill successful\"}", response, 200);
    }

}

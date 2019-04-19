package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Skill;
import model.User;
import model.UserSkill;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/skill")
public class AddSkillServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User contextUser = (User) request.getAttribute("contextUser");

        String body = Utils.getBodyOfRequest(request);
        ObjectMapper objectMapper = new ObjectMapper();
        Skill skill = objectMapper.readValue(body, Skill.class);

        UserSkill userSkill = new UserSkill(skill.getName());
//        User user = Database.findUserById(request.getParameter("user"));
        contextUser.addSkill(userSkill);
        Utils.sendJSON("{message: \"add skill successful\"}", response, 200);
    }

}

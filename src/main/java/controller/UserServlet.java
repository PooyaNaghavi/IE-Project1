package controller;

import DTO.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import model.Skill;
import model.User;
import model.UserSkill;
import repository.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        try {
            User foundUser = Database.findUserById(id);
            ArrayList<Skill> allSkills = Database.getSkills();
            UserDTO userDTO = new UserDTO(foundUser, allSkills);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String userJson = ow.writeValueAsString(userDTO);
            Utils.sendJSON(userJson, response, 200);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

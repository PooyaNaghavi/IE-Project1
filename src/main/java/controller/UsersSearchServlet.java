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

@WebServlet("/userssearch")
public class UsersSearchServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchContent = request.getParameter("search");
        try {
            ArrayList<User> searchedUsers = Database.getSearchedUsers(searchContent);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String userJson = ow.writeValueAsString(searchedUsers);
            Utils.sendJSON(userJson, response, 200);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

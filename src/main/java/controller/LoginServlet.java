package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;
import repository.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/login")
public class LoginServlet  extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String body = Utils.getBodyOfRequest(request);
        ArrayList<User> users = null;
        try {
            users = Database.getUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        User loginUser = objectMapper.readValue(body, User.class);

        for(User user : users){
            if(user.getId().equals(loginUser.getUserName()) && user.getPassword().equals(loginUser.getPassword())) {
                response.setStatus(200);
                return;
            }
        }
        response.setStatus(403);
    }
}

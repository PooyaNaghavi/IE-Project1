package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.LoginDTO;
import model.User;
import repository.Database;
import sun.misc.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.StringTokenizer;

@WebServlet("/login")
public class LoginServlet  extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        BufferedReader br = request.getReader();
        String str, body ="";
        while ((str = br.readLine()) != null) {
            body += str;
        }
        ArrayList<User> users = Database.getUsers();
        ObjectMapper objectMapper = new ObjectMapper();
        LoginDTO loginUser = objectMapper.readValue(body, LoginDTO.class);
        for(User user : users){
            if(user.getId().equals(loginUser.getUsername()) && user.getPassword().equals(loginUser.getPassword())) {
                response.setStatus(200);
                return;
            }
        }
        response.setStatus(403);
    }
}

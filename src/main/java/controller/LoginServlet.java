package controller;

import DTO.RegisterDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import model.User;
import repository.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet  extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("safkjsalkfjlkasjflkasjflkjasflkjaslkfjasflkj ----------------------------");
        String body = Utils.getBodyOfRequest(request);
        ObjectMapper objectMapper = new ObjectMapper();
        User loginUser = objectMapper.readValue(body, User.class);
        System.out.println(loginUser.getUserName());
        System.out.println(loginUser.getPassword());
        System.out.println(loginUser.getHashedPassword());
        try {
            response.setStatus(200);
            User foundUser = Database.AuthenticateUser(loginUser);
            String JWTToken = Utils.signJWT(foundUser.getId());
            RegisterDTO registerDTO = new RegisterDTO(JWTToken, foundUser.getId());
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String registerJson = ow.writeValueAsString(registerDTO);
            Utils.sendJSON(registerJson, response, 200);
        } catch (SQLException e) {
            response.setStatus(403);
        }
    }
}

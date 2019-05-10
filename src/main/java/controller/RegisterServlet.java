package controller;
import DTO.RegisterDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import model.User;
import repository.Database;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String body = Utils.getBodyOfRequest(request);
        ObjectMapper objectMapper = new ObjectMapper();
        User registerUser = objectMapper.readValue(body, User.class);
        try {
            Database.insertUser(registerUser);
            String JWTToken = Utils.signJWT(registerUser.getUserName());
            RegisterDTO registerDTO = new RegisterDTO(JWTToken, registerUser.getUserName());
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String registerJson = ow.writeValueAsString(registerDTO);
            Utils.sendJSON(registerJson, response, 200);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(400);
        }
    }
}


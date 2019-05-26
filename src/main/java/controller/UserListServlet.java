package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import exceptions.NotFoundException;
import model.User;
import repository.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/users")
public class UserListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("salamasmfmasmfkasjfkljsaflkjaslkfjlkasfjlkasjlkfjsalkfjlks----------------------");
        int statusCode = 200;
        try {
            ObjectWriter ow = new ObjectMapper().writer();
            String usersJson = ow.writeValueAsString(Database.getUsers());
            Utils.sendJSON(usersJson, response, 200);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}

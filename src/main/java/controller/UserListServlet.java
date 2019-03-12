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

@WebServlet("/user")
public class UserListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int statusCode = 200;
        User authenticatedUser = Database.findUserById("1");

        ObjectWriter ow = new ObjectMapper().writer();
        String usersJson = ow.writeValueAsString(Database.getUsers() );
        Utils.sendJSON(usersJson, response, 200);
//        request.setAttribute("users", Database.getUsers());
//        response.setStatus(statusCode);
//        request.getRequestDispatcher("/user-list.jsp").forward(request, response);
    }
}

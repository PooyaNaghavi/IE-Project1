package controller;

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
        String viewResult;
        int statusCode = 200;
        User authenticatedUser = null;
        try {
            authenticatedUser = Database.findUserById("1");
        } catch (NotFoundException e) {
            statusCode = 404;
            response.setStatus(statusCode);
            //TODO : error JSP
        }
        request.setAttribute("users", Database.getUsers());
        response.setStatus(statusCode);
        request.getRequestDispatcher("/user-list.jsp").forward(request, response);
    }
}

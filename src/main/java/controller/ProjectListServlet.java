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

@WebServlet("/project")
public class ProjectListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User authenticatedUser = (User) request.getAttribute("contextUser");
        request.setAttribute("projects", authenticatedUser.getQualifiedProjects());
        request.getRequestDispatcher("/project-list.jsp").forward(request, response);
    }

}

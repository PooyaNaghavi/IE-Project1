package controller;

import DTO.ProjectDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import model.Project;
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

@WebServlet("/projectssearch")
public class ProjectsSearchServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchContent = request.getParameter("search");
        try {
            ArrayList<Project> searchedProject = Database.getSearchedProjects(searchContent);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String userJson = ow.writeValueAsString(searchedProject);
            Utils.sendJSON(userJson, response, 200);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

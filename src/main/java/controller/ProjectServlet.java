package controller;

import DTO.ProjectDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import exceptions.NotFoundException;
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
import java.util.StringTokenizer;

@WebServlet("/project")
public class ProjectServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");
        User authenticatedUser = (User) request.getAttribute("contextUser");

        Project foundProject = null;
        try {
            foundProject = Database.findProjectById(id);
            ProjectDTO projectDTO = new ProjectDTO(Database.findProjectBids(foundProject), foundProject);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String projectJson = ow.writeValueAsString(projectDTO);
            Utils.sendJSON(projectJson, response, 200);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

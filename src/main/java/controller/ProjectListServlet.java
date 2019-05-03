package controller;

import DTO.ProjectsPaginationDTO;
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
import java.security.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.setOut;

@WebServlet("/projects")
public class ProjectListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User authenticatedUser = (User) request.getAttribute("contextUser");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String limit = "10";
        String nextPageToken = "0";
        limit = request.getParameter("limit");
        nextPageToken = request.getParameter("nextPageToken");
        try {
            ProjectsPaginationDTO projectsPaginationDTO = new ProjectsPaginationDTO(
                    Database.getProjectsPage(Integer.parseInt(limit), Integer.parseInt(nextPageToken)),
                    Integer.parseInt(limit) + Integer.parseInt(nextPageToken));
            String projectsJson = ow.writeValueAsString(projectsPaginationDTO);
            Utils.sendJSON(projectsJson, response, 200);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

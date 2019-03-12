package controller;

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
import java.util.StringTokenizer;

@WebServlet("/project/*")
public class ProjectServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();

        StringTokenizer st = new StringTokenizer(uri, "/");
//        st.nextToken();
        String context = st.nextToken();
        String id = st.nextToken();

        User authenticatedUser = (User) request.getAttribute("contextUser");

        Project foundProject = Database.findProjectById(id);
        if (authenticatedUser.checkSkillCondtions(foundProject)) {
            request.setAttribute("project", foundProject);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String projectJson = ow.writeValueAsString(authenticatedUser.getQualifiedProjects());
            Utils.sendJSON(projectJson, response, 200);
//            try {
//                Database.findBid(authenticatedUser, foundProject);
//                request.setAttribute("userHasBid", true);
//            } catch (NotFoundException err){
//                request.setAttribute("userHasBid", false);
//            }
//            request.getRequestDispatcher("/project.jsp").forward(request, response);
        } else {
            throw new NotFoundException("403 forbidden", 403);
        }
    }
}

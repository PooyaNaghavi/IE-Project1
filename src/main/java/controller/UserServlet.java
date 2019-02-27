package controller;

import exceptions.NotFoundException;
import model.Skill;
import model.User;
import repository.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.StringTokenizer;

@WebServlet("/user/*")
public class UserServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI().toString();
        StringTokenizer st = new StringTokenizer(uri, "/");
        st.nextToken();
        String context = st.nextToken();
        User contextUser = (User) request.getAttribute("contextUser");

        if(st.hasMoreTokens()) {
            String id = st.nextToken();
            User foundUser = Database.findUserById(id);
            request.setAttribute("user", foundUser);
            request.setAttribute("userSkills", foundUser.getSkills());
            System.out.println("id" + foundUser.getId());
            for(Skill s : foundUser.getSkills())
                System.out.println(s.getName());
            request.setAttribute("allSkills", Database.getSkills());
            request.setAttribute("endorseSkills", foundUser.getEndorseSkillsByUser(contextUser));
            request.getRequestDispatcher("/user-profile.jsp").forward(request, response);
        }
    }
}

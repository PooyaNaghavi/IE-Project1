package controller;

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

@WebServlet("/bid")
public class BidServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getAttribute("contextUser");
        Project project = Database.findProjectById(request.getParameter("project"));
        int amount = Integer.valueOf(request.getParameter("bidAmount"));
        Database.insertBid(user, project, amount);
        request.getRequestDispatcher("/bid-result.jsp").forward(request, response);
    }
}

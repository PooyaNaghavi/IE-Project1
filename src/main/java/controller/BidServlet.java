package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Bid;
import model.Project;
import model.User;
import repository.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/project/bid")
public class BidServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getAttribute("contextUser");
        String projectId = request.getParameter("id");

        Project project = Database.findProjectById(projectId);

        String body = Utils.getBodyOfRequest(request);
        ObjectMapper objectMapper = new ObjectMapper();
        Bid bid = objectMapper.readValue(body, Bid.class);
        int amount = bid.getAmount();

        Database.insertBid(user, project, amount);
        Utils.sendJSON("{message: \"bid successful\"}", response, 200);
        //request.getRequestDispatcher("/bid-result.jsp").forward(request, response);
    }
}

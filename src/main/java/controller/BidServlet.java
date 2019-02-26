package controller;

import model.Project;
import model.User;
import org.python.antlr.op.In;
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

    @Override
    public void init() throws ServletException {
        super.init();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String body = request.getReader().lines()
                .reduce("", (accumulator, actual) -> accumulator + actual);
        StringTokenizer st = new StringTokenizer(body, "&");
        User user = (User) request.getAttribute("contextUser");
        String projectId;
        Project project = null;
        int amount = 0;
        while (st.hasMoreTokens()) {
            String param = st.nextToken();
            StringTokenizer paramSt = new StringTokenizer(param, "=");
            String name = paramSt.nextToken();
            switch (name) {
                case "project":
                    projectId = paramSt.nextToken();
                    project = Database.findProjectById(projectId);
                    break;
                case "bidAmount":
                    amount = Integer.valueOf(paramSt.nextToken());
                    break;
                default:
                    break;
            }
        }
        Database.insertBid(user, project, amount);
        request.getRequestDispatcher("/bid-result.jsp").forward(request, response);
    }
}

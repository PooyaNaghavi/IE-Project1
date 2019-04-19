import exceptions.NotFoundException;
import model.Project;
import model.User;
import repository.Database;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.StringTokenizer;

public abstract class Handler implements HttpHandler {
    protected static View view = new View();

    public static void sendOutput(HttpExchange t, int statusCode, String result) throws IOException {
        byte [] response = result.getBytes();
        t.sendResponseHeaders(statusCode, response.length);
        OutputStream os = t.getResponseBody();
        os.write(response);
        os.close();
    }
}

class projectHandler extends Handler{
    public void handle(HttpExchange t) throws IOException {
        String viewResult;
        int statusCode = 200;
        String uri = t.getRequestURI().toString();
        StringTokenizer st = new StringTokenizer(uri, "/");
        String context = st.nextToken();
        String id = st.nextToken();
        User authenticatedUser = null;
        try {
            authenticatedUser = Database.findUserById("1");
        } catch (NotFoundException e) {
            view.showError(e.getMessage());
        }
        try {
            Project foundProject = Database.findProjectById(id);
            if(Database.checkSkillConditions(foundProject, authenticatedUser)){
                viewResult = view.projectView(foundProject);
            } else {
                statusCode = 403;
                viewResult = view.showError("Forbidden!");
            }
        } catch (NotFoundException e) {
            statusCode = 404;
            viewResult = view.showError(e.getMessage());
        }
        sendOutput(t, statusCode, viewResult);
    }
}

class projectListHandler extends Handler{
    public void handle(HttpExchange t) throws IOException {
        String viewResult;
        int statusCode = 200;
        User authenticatedUser = null;
        try {
            authenticatedUser = Database.findUserById("1");
        } catch (NotFoundException e) {
            statusCode = 404;
            view.showError(e.getMessage());
        }
        viewResult = view.projectsListView(Database.getQualifiedProjects(authenticatedUser));
        sendOutput(t, statusCode, viewResult);
    }
}


class userHandler extends Handler {
    public void handle(HttpExchange t) throws IOException {
        String viewResult;
        int statusCode = 200;
        String uri = t.getRequestURI().toString();
        StringTokenizer st = new StringTokenizer(uri, "/");
        String context = st.nextToken();

        if(st.hasMoreTokens())
        {
            String id = st.nextToken();
            try {
                User foundUser = Database.findUserById(id);
                viewResult = view.usersListView(foundUser);
            } catch (NotFoundException e) {
                statusCode = 404;
                viewResult = view.showError(e.getMessage());
            }
        }else{
            statusCode = 404;
            viewResult = view.showError("Page Not Found");
        }
        sendOutput(t, statusCode, viewResult);
    }
}

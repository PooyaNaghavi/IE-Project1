import Exceptions.NotFoundException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.StringTokenizer;

public abstract class Handler implements HttpHandler {
    protected static View view = new View();

    public static void sendOutput(HttpExchange t, String result) throws IOException {
        byte [] response = result.getBytes();
        t.sendResponseHeaders(200, response.length);
        OutputStream os = t.getResponseBody();
        os.write(response);
        os.close();
    }
}

class projectHandler extends Handler{
    public void handle(HttpExchange t) throws IOException {
        String viewResult;
        String uri = t.getRequestURI().toString();
        StringTokenizer st = new StringTokenizer(uri, "/");
        String context = st.nextToken();

        if(st.hasMoreTokens())
        {
            String id = st.nextToken();
            try {
                Project foundProject = Database.findProjectById(id);
                viewResult = view.projectView(foundProject);
            } catch (NotFoundException e) {
                viewResult = view.showError(e.getMessage());
            }

        }
        else {
            viewResult = view.projectsListView(Database.getProjects());
        }
        sendOutput(t, viewResult);
    }
}

class userHandler extends Handler {
    public void handle(HttpExchange t) throws IOException {
        String viewResult;
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
                viewResult = view.showError(e.getMessage());
            }
        }else{
            viewResult = view.showError("404 Not Found");
        }
        sendOutput(t, viewResult);
    }
}

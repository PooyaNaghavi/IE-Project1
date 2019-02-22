import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.*;


public class Main {

    public static APIHelper apiHelper = new APIHelper();

    public static void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/project", new projectListHandler());
        server.createContext("/project/", new projectHandler());
        server.createContext("/user", new userHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public static void main(String[] args) throws IOException {

        Database.addAuthenticatedUser();
        apiHelper.updateProjects();
        apiHelper.updateSkills();
        startServer();
    }

}

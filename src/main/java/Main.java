import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import javafx.util.Pair;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static jdk.nashorn.internal.objects.NativeString.substring;


public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isFinished = false;
    private static ArrayList<Project> myObjects;
    private static ArrayList<User> myUsers = new ArrayList<>();


    public static void sendOutput(HttpExchange t, String result) throws IOException {
        byte [] response = result.getBytes();
        t.sendResponseHeaders(200, response.length);
        OutputStream os = t.getResponseBody();
        os.write(response);
        os.close();
    }

    public static void addAuthenticatedUser(){
        ArrayList<Skill> skills = new ArrayList<>();
        skills.add(new Skill("HTML", 5));
        skills.add(new Skill("Javascript", 4));
        skills.add(new Skill("C++", 2));
        skills.add(new Skill("Java", 3));
        myUsers.add(new User("1", "علی", "شریف‌زاده", "برنامه‌نویس وب", null, "روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلی کارا بکنه ولی پول نداشت", skills));
    }

    public static String getResponse(String urlAddress) throws IOException {
        URL url = new URL(urlAddress);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        String content = "";
        while ((inputLine = in.readLine()) != null) {
            content += inputLine;
        }
        System.out.println(content);
        in.close();
        return content;
    }

    public static void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/project", new MyHandler());
        server.createContext("/user", new MyHandler1());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public static void main(String[] args) throws IOException {

        addAuthenticatedUser();

        String projectContent = getResponse("http://142.93.134.194:8000/joboonja/project");

        ObjectMapper mapper = new ObjectMapper();
        myObjects = mapper.readValue(projectContent, new TypeReference<ArrayList<Project>>(){});
        for(Project p : myObjects)
            System.out.println(p.getBudget());

        String skillContent = getResponse("http://142.93.134.194:8000/joboonja/skill");

        ArrayList<Skill> mySkills = mapper.readValue(skillContent, new TypeReference<ArrayList<Skill>>(){});
        for(Skill s : mySkills)
            System.out.println(s.getName());

        startServer();

    }


    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String uri = t.getRequestURI().toString();
            String res = "";
            if(uri.indexOf("/") != uri.lastIndexOf("/"))
            {
                String id = uri.substring(uri.lastIndexOf("/") + 1);
                System.out.println(id);
                for (Project p : myObjects) {
                    if(p.getId().equals(id)){

                        res += "<!DOCTYPE html> \n <html lang=\"en\"> \n <head> \n <meta charset=\"UTF-8\"> \n <title>Project</title> \n </head> \n <body> \n <ul> \n ";
                        res += "<li>id: " + p.getId() + "</li> \n  <li>title: " +  p.getTitle() + "</li> \n <li>description: " + p.getDescription() + "</li> \n <li> imageUrl: ";
                        res += "<img src= " + p.getImageUrl() + " style=\"width: 50px; height: 50px;\"> </li> \n<li>budget: " + Integer.toString(p.getBudget()) + "</li> \n </ul> \n </body>\n </html>\n";

                    }
                }
            }else {
                res += "<!DOCTYPE html> \n <html lang=\"en\"> \n <head> \n <meta charset=\"UTF-8\"> \n <title>Projects</title> \n" +
                        "<style> \n table { \n text-align: center; \n margin: 0 auto; \n } \n td { \n min-width: 300px; \n margin: 5px 5px 5px 5px; \n" +
                        "text-align: center; \n } \n </style> \n </head> \n <body> \n <ul> \n";
                res += "<body> \n <table> \n <tr> \n <th>id</th> \n <th>title</th> \n <th>budget</th> \n </tr> \n ";
                for (Project p : myObjects) {
                    res += "<tr> \n <td>" + p.getId() + "</td> \n <td>";
                    res += p.getTitle() + "</td> \n <td>" + p.getBudget() + "</tr>\n";
                }
                res += "</table> \n </body> \n </html>";

            }

            sendOutput(t, res);
        }
    }

    static class MyHandler1 implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String uri = t.getRequestURI().toString();
            String res = "";

            if(uri.indexOf("/") != uri.lastIndexOf("/"))
            {
                String id = uri.substring(uri.lastIndexOf("/") + 1);
                System.out.println(id);
                for (User u : myUsers) {
                    if(u.getId().equals(id)){

                        res += "<!DOCTYPE html> \n <html lang=\"en\"> \n <head> \n <meta charset=\"UTF-8\"> \n <title>User</title> \n </head> \n <body> \n <ul> \n ";
                        res += "<li>id: " + u.getId() + "</li> \n  <li>first name: " +  u.getFirstName() + "</li> \n <li>last name: " + u.getLastName() + "</li> \n <li> jobTitle: ";
                        res += u.getJobTitle() + "</li> \n<li>bio: " + u.getBio() + "</li> \n </ul> \n </body>\n </html>\n";
                    }
                }
            }
            sendOutput(t, res);
        }
    }

}

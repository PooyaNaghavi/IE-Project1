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


    public static void addAuthenticatedUser(){
        ArrayList<Skill> skills = new ArrayList<>();
        skills.add(new Skill("HTML", 5));
        skills.add(new Skill("Javascript", 4));
        skills.add(new Skill("C++", 2));
        skills.add(new Skill("Java", 3));
        myUsers.add(new User("1", "علی", "شریف‌زاده", "برنامه‌نویس وب", null, "روی سنگ قبرم بنویسید: خدا بیامرز میخواست خیلیکارا بکنه ولی پول نداشت", skills));
    }
    public static void main(String[] args) throws IOException {

        addAuthenticatedUser();

        URL url = new URL("http://142.93.134.194:8000/joboonja/project");
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

        ObjectMapper mapper = new ObjectMapper();
        myObjects = mapper.readValue(content, new TypeReference<ArrayList<Project>>(){});
        for(Project p : myObjects)
            System.out.println(p.getBudget());


        URL url1 = new URL("http://142.93.134.194:8000/joboonja/skill");
        HttpURLConnection con1 = (HttpURLConnection) url1.openConnection();
        con1.setRequestMethod("GET");

        BufferedReader in1 = new BufferedReader(
                new InputStreamReader(con1.getInputStream()));
        String inputLine1;
        String content1 = "";
        while ((inputLine1 = in1.readLine()) != null) {
            content1 += inputLine1;
        }
        System.out.println(content1);
        in1.close();


        List<Skill> mySkills = mapper.readValue(content1, new TypeReference<List<Skill>>(){});
        for(Skill s : mySkills)
            System.out.println(s.getName());



        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/project", new MyHandler());
        server.createContext("/user", new MyHandler1());
        server.setExecutor(null); // creates a default executor
        server.start();

//        while (!isFinished) {
//            Pair<String, String> commandParts = getCommandParts();
//            String commandName = commandParts.getKey();
//            String commandData = commandParts.getValue();
//
//            switch (commandName) {
//                case "register":
//                    Auction.register(commandData);
//                    break;
//                case "addProject":
//                    Auction.addProject(commandData);
//                    break;
//                case "bid":
//                    Auction.bid(commandData);
//                    break;
//                case "auction":
//                    Auction.auction(commandData);
//                    isFinished = true;
//                    break;
//                default:
//                    System.out.println("unknown command");
//                    break;
//            }
//        }
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
                        res += "id:" + id + "\n";
                        res += "title:" + p.getTitle() + "\n";
                        res += "description:" + p.getDescription() + "\n";
                        res += "Url:" + p.getImageUrl() + "\n";
                        res += "budget:" + Integer.toString(p.getBudget()) + "\n";
                    }
                }
            }else {
                for (Project p : myObjects) {
                    res += p.getId();
                    res += "\t";
                    res += p.getTitle();
                    res += "\t";
                    res += p.getBudget();
                    res += "\n";
                }
            }
            byte [] response = res.getBytes();
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            os.write(response);
            os.close();
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
                        System.out.println("sss");
                        res += "id:" + u.getId() + "\n";
                        res += "firstname:" + u.getFirstName() + "\n";
                        res += "lastname:" + u.getLastName() + "\n";
                        res += "jobTitle:" + u.getJobTitle() + "\n";
                        res += "bio:" + u.getBio() + "\n";
                    }
                }
            }
            byte [] response = res.getBytes();
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            os.write(response);
            os.close();
        }
    }
    private static Pair<String, String> getCommandParts() {
        String command = scanner.nextLine();
        int spaceIndex = command.indexOf(" ");
        return new Pair<>(command.substring(0, spaceIndex), command.substring(spaceIndex));
    }
}

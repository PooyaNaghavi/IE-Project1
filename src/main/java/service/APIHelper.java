package service;

import model.Project;
import model.Skill;
import model.User;
import repository.Database;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class APIHelper {
    private String APIRequest(String urlAddress) throws IOException {
        URL url = new URL(urlAddress);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "UTF-8"));
        String inputLine;
        String content = "";
        while ((inputLine = in.readLine()) != null) {
            content += inputLine;
        }
        in.close();
        return content;
    }
    public void updateProjects() throws IOException, SQLException {
        System.out.println("---------------------------*****_________1");
        String projectContent = APIRequest("http://142.93.134.194:8000/joboonja/project");
        System.out.println("---------------------------*****_________2");
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("---------------------------*****_________3");
        Database.insertMultipleProjects(mapper.readValue(projectContent, new TypeReference<ArrayList<Project>>(){}));
        System.out.println("---------------------------*****_________4");
    }
    public void updateSkills() throws IOException, SQLException {
        System.out.println("=====================================1");
        String skillContent = APIRequest("http://142.93.134.194:8000/joboonja/skill");
        System.out.println("=====================================2");
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("=====================================3");
        Database.setSkills(mapper.readValue(skillContent, new TypeReference<ArrayList<Skill>>(){}));
        System.out.println("=====================================4");
    }
}
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
    public void updateProjects() throws IOException {
        String projectContent = APIRequest("http://142.93.134.194:8000/joboonja/project");
        ObjectMapper mapper = new ObjectMapper();
        Database.setProjects(mapper.readValue(projectContent, new TypeReference<ArrayList<Project>>(){}));
    }
    public void updateSkills() throws IOException {
        String skillContent = APIRequest("http://142.93.134.194:8000/joboonja/skill");
        ObjectMapper mapper = new ObjectMapper();
        Database.setSkills(mapper.readValue(skillContent, new TypeReference<ArrayList<Skill>>(){}));
    }
}
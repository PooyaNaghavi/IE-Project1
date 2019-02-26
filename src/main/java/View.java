import model.Project;
import model.User;

import java.util.ArrayList;

public class View {

    public String projectsListView(ArrayList<Project> projects){
        String result = "";
        result += "<!DOCTYPE html> \n <html lang=\"en\"> \n <head> \n <meta charset=\"UTF-8\"> \n <title>Projects</title> \n" +
                "<style> \n table { \n text-align: center; \n margin: 0 auto; \n } \n td { \n min-width: 300px; \n margin: 5px 5px 5px 5px; \n" +
                "text-align: center; \n } \n </style> \n </head> \n <body> \n <ul> \n";
        result += "<body> \n <table> \n <tr> \n <th>id</th> \n <th>title</th> \n <th>budget</th> \n </tr> \n ";
        for (Project project : projects) {
            result += "<tr> \n <td>" + project.getId() + "</td> \n <td>";
            result += project.getTitle() + "</td> \n <td>" + project.getBudget() + "</tr>\n";
        }
        result += "</table> \n </body> \n </html>";

        return result;
    }
    public String projectView(Project project){
        String result = "";
        result += "<!DOCTYPE html> \n <html lang=\"en\"> \n <head> \n <meta charset=\"UTF-8\"> \n <title>model.Project</title> \n </head> \n <body> \n <ul> \n ";
        result += "<li>id: " + project.getId() + "</li> \n  <li>title: " +  project.getTitle() + "</li> \n <li>description: " + project.getDescription() + "</li> \n <li> imageUrl: ";
        result += "<img src= " + project.getImageUrl() + " style=\"width: 50px; height: 50px;\"> </li> \n<li>budget: " + Integer.toString(project.getBudget()) + "</li> \n </ul> \n </body>\n </html>\n";
        return result;
    }
    public String usersListView(User user){
        String result = "";

        result += "<!DOCTYPE html> \n <html lang=\"en\"> \n <head> \n <meta charset=\"UTF-8\"> \n <title>model.User</title> \n </head> \n <body> \n <ul> \n ";
        result += "<li>id: " + user.getId() + "</li> \n  <li>first name: " +  user.getFirstName() + "</li> \n <li>last name: " + user.getLastName() + "</li> \n <li> jobTitle: ";
        result += user.getJobTitle() + "</li> \n<li>bio: " + user.getBio() + "</li> \n </ul> \n </body>\n </html>\n";

        return result;
    }

    public String showError(String message) {
        String result = "";
        result += "<!DOCTYPE html> \n <html lang=\"en\"> \n <head> \n <meta charset=\"UTF-8\"> \n <title>Error</title> \n </head> \n <body> \n";
        result += "<h2>" + message + "</h2> \n </body>\n </html>\n";
        return result;
    }

}

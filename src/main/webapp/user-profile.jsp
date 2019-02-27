<%@ page import="model.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Skill" %>
<%@ page import="java.util.Collections" %>
<%@ page import="model.SkillComparator" %>
<%@ page import="model.SkillComparator" %><%--
  Created by IntelliJ IDEA.
  User: pooya
  Date: 2019-02-26
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% User user = (User) request.getAttribute("user"); %>
<% User contextUser = (User) request.getAttribute("contextUser"); %>
<% ArrayList<Skill> userSkills = (ArrayList<Skill>) request.getAttribute("userSkills"); %>
<% ArrayList<Skill> allSkills = (ArrayList<Skill>) request.getAttribute("allSkills");%>
<% ArrayList<Skill> endorseSkills = (ArrayList<Skill>) request.getAttribute("endorseSkills");%>
<% boolean selfProfile = user.getId().equals(contextUser.getId()); %>
<%! public boolean objectFoundInArray(Skill skill, ArrayList<Skill> skills){
    for (Skill s : skills){
        if(skill.getName().equals(s.getName())){
            return true;
        }
    }
    return false;
}%>



<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User</title>
</head>
<body>
<ul>
    <li>id: <%= user.getId() %> </li>
    <li>first name: <%= user.getFirstName() %></li>
    <li>last name: <%= user.getLastName() %> </li>
    <li>jobTitle: <%= user.getJobTitle() %> </li>
    <li>bio: <%= user.getBio() %> </li>
    <li>
        skills:
        <ul>
            <% for(Skill skill : userSkills) {%>
                <% if(selfProfile || !objectFoundInArray(skill, endorseSkills)) { %>
                    <li>
                        <%= skill.getName() %> : <%= skill.getPoint() %>
                        <!-- TODO : no form if already endorsed -->
                        <form action="/IE/skill" method="POST">
                            <input type="hidden" name="user" value="<%= user.getId() %>">
                            <input type="hidden" name="skill" value="<%= skill.getName() %>">
                            <% if(!selfProfile) { %>
                                <button type="submit" name="action" value="endorse">Endorse</button>
                            <% } else { %>
                                <button type="submit" name="action" value="delete">Delete</button>
                            <% } %>
                        </form>
                    </li>
                <% } %>
            <% } %>
        </ul>
    </li>
</ul>

<% if(selfProfile) { %>
    Add Skill:
    <form action="/IE/skill" method="POST">
        <select name="skill">
            <% for(Skill skill : allSkills) { %>
                <% if(!objectFoundInArray(skill, userSkills)) { %>
                    <%= skill.getName() %> : <%= skill.getPoint() %>
                        <option name = "skill" value="<%= skill.getName() %>"> <%= skill.getName() %></option>
                <% } %>
            <% } %>
        </select>
        <input type="hidden" name="user" value="<%= user.getId() %>">
        <button type="submit" name="action" value="add">Add</button>
    </form>
<% } %>

</body>
</html>
<%@ page import="model.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Skill" %><%--
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
<% ArrayList<Skill> allSkills = (ArrayList<Skill>) request.getAttribute("allSkills"); %>
<% boolean selfProfile = user.getId().equals(contextUser.getId()); %>
<%--TODO: endorseList...--%>


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
                <li>
                    <%= skill.getName() %> : <%= skill.getPoint() %>
                    <!-- TODO : no form if already endorsed -->
                    <form action="/IE/skill" method="POST">
                        <input type="hidden" name="user" value="<%= user.getId() %>">
                        <% if(!selfProfile) { %>
                            <button type="submit" name="action" value="endorse">Endorse</button>
                        <% } else { %>§
                            <button type="submit" name="action" value="delete">Delete</button>
                        <% } %>
                    </form>
                </li>
            <% } %>
        </ul>
    </li>
</ul>

<% if(selfProfile) {%>
    Add Skill:
    <form action="/IE/skill" method="POST">
        <select name="skill">
            <% for(Skill skill : allSkills) { %>
                <% if(!userSkills.contains(skill)) %>
                    <option value="<%= skill.getName() %>"> <%= skill.getName() %></option>
                <% } %>
            <% } %>
        </select>
        <button type="submit" name="action" value="add">Add</button>
    </form>
<% } %>

</body>
</html>
<%@ page import="model.User" %>
<%@ page import="model.Skill" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: pooya
  Date: 2019-02-26
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% User user = (User) request.getAttribute("user"); %>
<% ArrayList<Skill> userSkills = (ArrayList<Skill>) request.getAttribute("userSkills"); %>
<% ArrayList<Skill> skills = (ArrayList<Skill>) request.getAttribute("skills"); %>

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
                <form action="" method="">
                    <button>Delete</button>
                </form>
            </li>
            <% } %>
        </ul>
    </li>
</ul>
Add Skill:
<form action="" method="">
    <select name="">
        <% for(Skill skill : skills) { %>
        <option value="<%= skill.getName() %>"> <%= skill.getName() %></option>
        <% } %>
    </select>
    <button>Add</button>
</form>
</body>
</html>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.User" %><%--
  Created by IntelliJ IDEA.
  User: pooya
  Date: 2019-02-26
  Time: 16:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% ArrayList<User> users = (ArrayList<User>) request.getAttribute("users"); %>
<% User contextUser = (User) request.getAttribute("contextUser"); %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Users</title>
    <style>
        table {
            text-align: center;
            margin: 0 auto;
        }
        td {
            min-width: 300px;
            margin: 5px 5px 5px 5px;
            text-align: center;
        }
    </style>
</head>
<body>
<table>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>jobTitle</th>
    </tr>
    <% for(User user : users) { %>
        <% if(!user.getId().equals(contextUser.getId())) { %>
            <tr>
                <td> <%= user.getId()%> </td>
                <td> <%= user.getFirstName()%> <%= user.getLastName()%></td>
                <td> <%= user.getJobTitle() %> </td>
            </tr>
        <% } %>
    <% } %>
</table>
</body>
</html>
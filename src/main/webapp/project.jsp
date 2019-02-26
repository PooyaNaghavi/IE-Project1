<%@ page import="model.Project" %>
<--Created by IntelliJ IDEA.
  model.User: pooya
  Date: 2019-02-26
  Time: 10:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Project project = (Project) request.getAttribute("project"); %>
<% boolean userHasBid = (boolean) request.getAttribute("userHasBid"); %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><%= project.getTitle() %></title>
</head>
<body>
<ul>
    <li>id: <%= project.getId() %></li>
    <li>title: <%= project.getTitle() %></li>
    <li>description: <%= project.getDescription() %></li>
    <li>imageUrl: <img src="<%= project.getImageUrl() %>" style="width: 50px; height: 50px;"></li>
    <li>budget: <%= project.getBudget() %> </li>
</ul>
<!-- display form if user has not bidded before -->
<% if(!userHasBid) {%>
<form action="/IE/bid" method="POST">
    <label for="bidAmount">Bid Amount:</label>
    <input type="number" id="bidAmount" name="bidAmount">
    <input type="hidden" id="project" name="project" value="<%=project.getId()%>">
    <button>Submit</button>
</form>
<% } %>
</body>
</html>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.Project" %>
<%@ page import="model.Bid" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><c:out value="${project.getTitle()}" /></title>
</head>
<ul>
    <li>id: <c:out value="${project.getId()}" /></li>
    <li>title: <c:out value="${project.getTitle()}" /></li>
    <li>description: <c:out value="${project.getDescription()}" /></li>
    <li>imageUrl: <img src="<c:out value="${project.getImageUrl()}" />" style="width: 50px; height: 50px;"></li>
    <li>budget: <c:out value="${project.getBudget()}" /> </li>
</ul>
<!-- display form if user has not bidded before -->
<c:if test="${!userHasBid}">
<form action="/bid" method="POST">
    <label for="bidAmount">Bid Amount:</label>
    <input type="number" id="bidAmount" name="bidAmount">
    <input type="hidden" id="project" name="project" value="<c:out value="${project.getId()}" />">
    <button>Submit</button>
</form>
</c:if>
</body>
</html>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Projects</title>
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
        <th>title</th>
        <th>budget</th>
        <th>link</th>
    </tr>
    <c:forEach var="project" items="${projects}" >
        <tr>
            <td><c:out value="${project.getId()}" /></td>
            <td><c:out value="${project.getTitle()}" /></td>
            <td><c:out value="${project.getBudget()}" /></td>
            <td><a href="project/<c:out value="${project.getId()}" />">link</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
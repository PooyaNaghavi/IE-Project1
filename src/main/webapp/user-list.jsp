<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
    <c:forEach var="user" items="${users}">
        <c:if test="${user.getId() ne contextUser.getId()}" >
            <tr>
                <td> <c:out value="${user.getId()}" /> </td>
                <td> <c:out value="${user.getFirstName()}" /> <c:out value="${user.getLastName()}" /></td>
                <td> <c:out value="${user.getJobTitle()}" /> </td>
            </tr>
        </c:if>
    </c:forEach>
</table>
</body>
</html>
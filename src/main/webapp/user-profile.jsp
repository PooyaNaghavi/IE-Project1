<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="selfProfile" value="${user.getId().equals(contextUser.getId())}"></c:set>



<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User</title>
</head>
<body>
<ul>
    <li>id: <c:out value="${user.getId()}" /> </li>
    <li>first name: <c:out value="${user.getFirstName()}" /></li>
    <li>last name: <c:out value="${user.getLastName()}" /> </li>
    <li>jobTitle: <c:out value="${user.getJobTitle()}" /> </li>
    <li>bio: <c:out value="${user.getBio()}" /> </li>
    <li>
        skills:
        <ul>
            <c:forEach var="skill" items="${userSkills}" >
                <li>
                    <c:out value="${skill.getName()}" /> : <c:out value="${skill.getPoint()}" />
                    <form action="/skill" method="POST" >
                        <input type="hidden" name="user" value="<c:out value="${user.getId()}" />">
                        <input type="hidden" name="skill" value="<c:out value="${skill.getName()}" />">
                        <c:if test="${!selfProfile && !endorseSkills.get(skill.getName())}">
                            <button type="submit" name="action" value="endorse">Endorse</button>
                        </c:if>
                        <c:if test="${selfProfile}">
                            <button type="submit" name="action" value="delete">Delete</button>
                        </c:if>
                    </form>
                </li>
            </c:forEach>
        </ul>
    </li>
</ul>


<c:if test="${selfProfile}" >
Add Skill:
<form action="/skill" method="POST">
    <select name="skill">
        <c:forEach var="skill" items="${allSkills}">
            <c:if test="${!contextUserSkills.get(skill.getName())}">
                <option name = "skill" value="<c:out value="${skill.getName()}" />"> <c:out value="${skill.getName()}" /></option>
            </c:if>
        </c:forEach>
    </select>
    <input type="hidden" name="user" value="<c:out value="${user.getId()}" />">
    <button type="submit" name="action" value="add">Add</button>
</form>
</c:if>



</body>
</html>
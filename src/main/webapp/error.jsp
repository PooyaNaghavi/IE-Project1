<%--
  Created by IntelliJ IDEA.
  User: pooya
  Date: 2019-02-26
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error</title>
</head>
<body>
    <h2> <%= request.getAttribute("message") %></h2>
    <a style="text-decoration: none;" href="/"><button>return to home</button></a>
</body>
</html>


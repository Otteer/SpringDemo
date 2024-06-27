<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Grades</title>
</head>
<body>
<h1>Grades</h1>
<table>
    <tr>
        <th>Username</th>
        <th>Score</th>
    </tr>
    <c:forEach items="${userScores}" var="entry">
        <tr>
            <td>${entry.key}</td>
            <td>${entry.value}</td>
        </tr>
    </c:forEach>
</table>
<p><a href="/">Back to Home</a></p>
</body>
</html>
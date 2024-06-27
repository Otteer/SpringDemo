<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
</head>
<body>
<h1>${message}</h1>
<c:choose>
    <c:when test="${not empty sessionScope.username}">
        <p>Welcome, ${sessionScope.username}! (<a href="/logout">Logout</a>)</p>
        <p><a href="/quiz">Take the Quiz!</a></p>
        <p><a href="/grades">See scores!</a></p>
    </c:when>
    <c:otherwise>
        <p><a href="/login">Login</a></p>
    </c:otherwise>
</c:choose>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quiz</title>
</head>
<body>
<h1>Quiz</h1>
<form action="/result" method="post">
    <c:forEach var="question" items="${questions}" varStatus="status">
        <h2>${question[0]}</h2>
        <c:forEach var="choice" items="${question}" begin="1">
            <input type="radio" name="question${status.index}" value="${choice}"> ${choice}<br>
        </c:forEach>
        <br>
    </c:forEach>
    <input type="submit" value="Submit">
    <input type="reset" value="Reset">
</form>
</body>
</html>
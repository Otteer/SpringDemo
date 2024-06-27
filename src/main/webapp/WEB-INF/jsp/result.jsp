<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quiz Result</title>
</head>
<body>
<h1>Quiz Result</h1>
<p>Your score: <c:out value="${score}"/> out of 5</p>
<p><a href="/">Back to Home</a></p>
</body>
</html>
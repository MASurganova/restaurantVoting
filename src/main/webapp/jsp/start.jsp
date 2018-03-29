<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Restaurant Voting</title>
</head>
<body>
<h3>Проект <a href="https://github.com/MASurganova/restaurantVoting" target="_blank">Restaurant Voting</a></h3>
<hr>
<ul>
    <li><a href="voting">Голосование</a></li>
    <c:if test="${userId == 100001}">
        <li><a href="users">Пользоваетли</a></li>
        <li><a href="restaurants">Рестораны</a></li>
    </c:if>
</ul>
</body>
</html>
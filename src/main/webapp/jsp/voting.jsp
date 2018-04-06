<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Voting</title>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <h2>Голосование</h2>
    <section>
        <table border="1" cellpadding="8" cellspacing="0">
            <thead>
            <tr>
                <th>Название</th>
                <th>Список блюд на ланч</th>
                <th>Цена ланча</th>
                <th>Колличество проголосовавших</th>
                <th>Выбрать</th>
            </tr>
            </thead>
            <c:forEach items="${restaurants}" var="restaurant">
                <jsp:useBean id="restaurant" scope="page" type="ru.voting.model.Restaurant"/>
                <tr>
                    <td><c:out value="${restaurant.name}"/></td>
                    <td>
                        <ul>
                            <c:forEach items="${restaurant.lunch}" var="dish">
                                <jsp:useBean id="dish" scope="page" type="ru.voting.model.Dish"/>
                                <li><c:out value="${dish.description}"/></li>
                            </c:forEach>
                        </ul>
                    </td>
                    <td>${restaurant.totalPrice}</td>
                    <td>${restaurant.voters}</td>
                    <td><a href="voting?action=choose&id=${restaurant.id}">Choose</a></td>
                </tr>
            </c:forEach>
        </table>
    </section>
    <c:if test="${userId == 100008}">
        <a href="voting?action=endVoting">Закончить голосование</a>
    </c:if>
</body>
</html>
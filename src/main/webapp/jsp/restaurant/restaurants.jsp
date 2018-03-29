<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Restaurants</title>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <h2>Рестораны</h2>
    <a href="restaurants?action=create">Добавить ресторан</a>
    <section>
        <table border="1" cellpadding="8" cellspacing="0">
            <thead>
            <tr>
                <th>Название</th>
                <th>Список блюд на ланч</th>
                <th>Цена ланча</th>
                <th>Участие в голосовании</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${restaurants}" var="restaurantId">
                <jsp:useBean id="restaurantId" scope="page" type="ru.voting.model.Restaurant"/>
                <tr>
                    <td><c:out value="${restaurantId.name}"/></td>
                    <td>
                        <ul>
                            <c:forEach items="${restaurantId.lunch}" var="dish">
                                <jsp:useBean id="dish" scope="page" type="ru.voting.model.Dish"/>
                                <li><c:out value="${dish.description}"/></li>
                            </c:forEach>
                        </ul>
                    </td>
                    <td>${restaurantId.totalPrice}</td>
                    <td>${restaurantId.enabled}</td>
                    <td><a href="restaurants?action=update&id=${restaurantId.id}">Обновить</a></td>
                    <td><a href="restaurants?action=delete&id=${restaurantId.id}">Удалить</a></td>
                    <td><a href="restaurants?action=enabled&id=${restaurantId.id}">Добавить в голосование</a></td>
                </tr>
            </c:forEach>
        </table>
    </section>
</body>
</html>
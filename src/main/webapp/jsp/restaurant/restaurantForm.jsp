<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Meal</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <h2>${param.action == 'create' ? 'Создать ресторан' : 'Редактировать ресторан'}</h2>
    <jsp:useBean id="restaurantId" type="ru.voting.model.Restaurant" scope="request"/>
    <form method="post" action="restaurants">
        <input type="hidden" name="id" value="${restaurantId.id}">
        <dl>
            <dt>Название:</dt>
            <dd><input type="text" value="${restaurantId.name}" name="name" required></dd>
        </dl>

        <c:if test="${restaurantId.id != null}">
            <dl>
                <dt>Список блюд ланча:</dt>
            </dl>
            <table border="1" cellpadding="8" cellspacing="0">
                <thead>
                <tr>
                    <th>Название</th>
                    <th>Цена</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <c:forEach items="${restaurantId.lunch}" var="dish">
                    <jsp:useBean id="dish" scope="page" type="ru.voting.model.Dish"/>
                    <tr class="normal">
                        <td><c:out value="${dish.description}"/></td>
                        <td>${dish.price}</td>
                        <td><a href="dishes?action=update&id=${dish.id}&restaurantId=${restaurantId.id}">Update</a></td>
                        <td><a href="dishes?action=delete&id=${dish.id}&restaurantId=${restaurantId.id}">Delete</a></td>
                    </tr>
                </c:forEach>
            </table>
            <dl>
                <a href="dishes?action=create&restaurantId=${restaurantId.id}">Добавить блюдо</a>
            </dl>
        </c:if>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>

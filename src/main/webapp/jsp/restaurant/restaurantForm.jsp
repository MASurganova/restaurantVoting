<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Meal</title>
    <style>
        dl {
            background: none repeat scroll 0 0 #FAFAFA;
            margin: 8px 0;
            padding: 0;
        }

        dt {
            display: inline-block;
            width: 170px;
        }

        dd {
            display: inline-block;
            margin-left: 8px;
            vertical-align: top;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="../../index.html">Home</a></h3>
    <h2>${param.action == 'create' ? 'Создать ресторан' : 'Редактировать ресторан'}</h2>
    <hr>
    <jsp:useBean id="restaurant" type="ru.voting.model.Restaurant" scope="request"/>
    <form method="post" action="restaurants">
        <input type="hidden" name="id" value="${restaurant.id}">
        <dl>
            <dt>Название:</dt>
            <dd><input type="text" value="${restaurant.name}" name="name" required></dd>
        </dl>
        <dl>
            <a href="dish?action=create">Добавить блюдо</a>
            <c:forEach items="${restaurant.lunch}" var="dish">
                <jsp:useBean id="dish" scope="page" type="ru.voting.model.Dish"/>
                <tr>
                    <td><c:out value="${dish.description}"/></td>
                    <td>${dish.price}</td>
                    <td>${restaurant.totalPrice}</td>
                    <td><a href="dishes?action=update&id=${dish.id}&restaurant=${restaurant.id}">Update</a></td>
                    <td><a href="dishes?action=delete&id=${dish.id}&restaurant=${restaurant.id}">Delete</a></td>
                </tr>
            </c:forEach>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>

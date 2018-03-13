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
    <h2>${param.action == 'create' ? 'Создать блюдо' : 'Редактировать блюдо'}</h2>
    <hr>
    <jsp:useBean id="dish" type="ru.voting.model.Dish" scope="request"/>
    <form method="post" action="dishes">
        <input type="hidden" name="id" value="${dish.id}">
        <dl>
            <dt>Наименование:</dt>
            <dd><input type="text" value="${dish.description}" name="description" required></dd>
        </dl>
        <dl>
            <dt>Цена:</dt>
            <dd><input type="number" value="${dish.price}" size=40 name="price" required></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()" type="button">Cancel</button>
    </form>
</section>
</body>
</html>

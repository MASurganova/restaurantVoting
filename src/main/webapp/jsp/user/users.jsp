<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Users</title>
</head>
<body>
<section>
    <h3><a href="../../index.html">Home</a></h3>
    <h2>Пользователи</h2>
    <a href="users?action=create">Добавить пользователя</a>
    <section>
        <table border="1" cellpadding="8" cellspacing="0">
            <thead>
            <tr>
                <th>Имя</th>
                <th>Email</th>
                <th>Роли</th>
                <th>Выбор</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${users}" var="user">
                <jsp:useBean id="user" scope="page" type="ru.voting.model.User"/>
                <tr>
                    <td><c:out value="${user.name}"/></td>
                    <td><a href="mailto:${user.email}">${user.email}</a></td>
                    <td>${user.roles}</td>
                    <td>${user.choice == null ? null : user.choice.name}</td>
                    <td><a href="users?action=update&id=${user.id}">Update</a></td>
                    <td><a href="users?action=delete&id=${user.id}">Delete</a></td>
                </tr>
            </c:forEach>
        </table>
    </section>
</body>
</html>
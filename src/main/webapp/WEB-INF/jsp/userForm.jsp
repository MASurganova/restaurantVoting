<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<fmt:setBundle basename="messages.app"/>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <jsp:useBean id="user" type="ru.voting.model.User" scope="request"/>
    <h3><spring:message code="${user.id == null ? 'user.add' : 'user.update'}"/></h3>
    <form method="post" action="userForm">
        <input type="hidden" name="id" value="${user.id}">
        <input type="hidden" name="restaurantId" value="${user.choice == null ? null : user.choice.id}">
        <dl>
            <dt><fmt:message key="user.name"/></dt>
            <dd><input type="text" value="${user.name}" name="name" required></dd>
        </dl>
        <dl>
            <dt><fmt:message key="user.email"/></dt>
            <dd><input type="text" value="${user.email}" size=40 name="email" required></dd>
        </dl>
        <dl>
            <dt><fmt:message key="user.password"/></dt>
            <dd><input type="text" value="${user.password}" name="password" required></dd>
        </dl>
        <button type="submit"><fmt:message key="common.save"/></button>
        <button onclick="window.history.back()" type="button"><fmt:message key="common.cancel"/></button>
    </form>
</section>
</body>
</html>

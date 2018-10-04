<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="messages.app"/>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <jsp:useBean id="dish" type="ru.voting.model.Dish" scope="request"/>
    <h3><spring:message code="${dish.isNew() ? 'dish.add' : 'dish.update'}"/></h3>
    <hr>
    <form method="post" action="dishes">
        <input type="hidden" name="id" value="${dish.id}">
        <input type="hidden" name="restaurantId" value="${param.restaurantId}">
        <dl>
            <dt><fmt:message key="dish.description"/></dt>
            <dd><input type="text" value="${dish.description}" name="description" required></dd>
        </dl>
        <dl>
            <dt><fmt:message key="dish.price"/></dt>
            <dd><input type="number" value="${dish.price}" size=40 name="price" required></dd>
        </dl>
        <button type="submit"><fmt:message key="common.save"/></button>
        <button onclick="window.history.back()" type="button"><fmt:message key="common.cancel"/></button>
    </form>
</section>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="messages.app"/>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <jsp:useBean id="restaurant" type="ru.voting.model.Restaurant" scope="request"/>
    <h3><spring:message code="${restaurant.id == null ? 'restaurant.add' : 'restaurant.update'}"/></h3>
    <form method="post" action="restaurantForm">
        <input type="hidden" name="id" value="${restaurant.id}">
        <dl>
            <dt><fmt:message key="restaurant.name"/></dt>
            <dd><input type="text" value="${restaurant.name}" name="name" required></dd>
        </dl>

        <c:if test="${restaurant.id != null}">
            <dl>
                <dt><fmt:message key="restaurant.lunch"/></dt>
            </dl>
            <table border="1" cellpadding="8" cellspacing="0">
                <thead>
                <tr>
                    <th><fmt:message key="dish.description"/></th>
                    <th><fmt:message key="dish.price"/></th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <c:forEach items="${restaurant.lunch}" var="dish">
                    <jsp:useBean id="dish" scope="page" type="ru.voting.model.Dish"/>
                    <tr class="normal">
                        <td><c:out value="${dish.description}"/></td>
                        <td>${dish.price}</td>
                        <td><a href="dishes/update?id=${dish.id}&restaurantId=${restaurant.id}"><fmt:message key="common.update"/></a></td>
                        <td><a href="dishes/delete?id=${dish.id}&restaurantId=${restaurant.id}"><fmt:message key="common.delete"/></a></td>
                    </tr>
                </c:forEach>
            </table>
            <dl>
                <a href="dishes/create?restaurantId=${restaurant.id}"><fmt:message key="dish.add"/></a>
            </dl>
        </c:if>
        <button type="submit"><fmt:message key="common.save"/></button>
        <button onclick="window.history.back()" type="button"><fmt:message key="common.cancel"/></button>
    </form>
</section>
</body>
</html>

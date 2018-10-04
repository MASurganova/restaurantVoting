<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="messages.app"/>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h2><fmt:message key="restaurant.title"/></h2>
    <a href="restaurants/create"><fmt:message key="restaurant.add"/></a>
    <section>
        <table border="1" cellpadding="8" cellspacing="0">
            <thead>
            <tr>
                <th><fmt:message key="restaurant.name"/></th>
                <th><fmt:message key="restaurant.lunch"/></th>
                <th><fmt:message key="restaurant.totalPrice"/></th>
                <th><fmt:message key="restaurant.enabled"/></th>
                <th></th>
                <th></th>
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
                    <td>${restaurant.getTotalPrice()}</td>
                    <td>${restaurant.enabled}</td>
                    <td><a href="restaurants/update?id=${restaurant.id}"><fmt:message key="common.update"/></a></td>
                    <td><a href="restaurants/delete?id=${restaurant.id}"><fmt:message key="common.delete"/></a></td>
                    <td><a href="restaurants/enabled?id=${restaurant.id}"><fmt:message key="restaurant.enabled"/></a></td>
                </tr>
            </c:forEach>
        </table>
    </section>
</body>
</html>
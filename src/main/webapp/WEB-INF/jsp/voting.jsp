<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>


<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h2><fmt:message key="app.voting"/></h2>
    <section>
        <table border="1" cellpadding="8" cellspacing="0">
            <thead>
            <tr>
                <th><fmt:message key="restaurant.name"/></th>
                <th><fmt:message key="restaurant.lunch"/></th>
                <th><fmt:message key="restaurant.totalPrice"/></th>
                <th><fmt:message key="restaurant.voters"/></th>
                <th><fmt:message key="common.select"/></th>
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
                    <td><a href="voting/choose?id=${restaurant.id}"><fmt:message key="common.select"/></a></td>
                </tr>
            </c:forEach>
        </table>
    </section>
    <c:if test="${userId == 100008}">
        <a href="voting/endVoting"><th><fmt:message key="app.end"/></th></a>
    </c:if>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
<ul>
    <li><a href="voting"><fmt:message key="app.voting"/></a></li>
    <c:if test="${userId == 100008}">
        <li><a href="users"><fmt:message key="user.title"/></a></li>
        <li><a href="restaurants"><fmt:message key="restaurant.title"/></a></li>
    </c:if>
</ul>
</section>
</body>
</html>
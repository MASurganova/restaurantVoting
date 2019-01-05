<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron">
    <div class="container">
        <br/>
        <br/>
        <sec:authorize access="isAuthenticated()">
            <li><a href="voting"><spring:message code="app.voting"/></a></li>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <li><a href="users"><spring:message code="user.title"/></a></li>
                <li><a href="restaurants"><spring:message code="restaurant.title"/></a></li>
                <li><a href="history"><spring:message code="history.title"/></a></li>
            </sec:authorize>
        </sec:authorize>
    </div>
</div>
</body>
<jsp:include page="fragments/footer.jsp"/>
</html>
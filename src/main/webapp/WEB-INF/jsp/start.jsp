<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron">
    <div class="container">
        <p/>
        <p/>
        <p/>

        <p>
            <li><a href="voting"><spring:message code="app.voting"/></a></li>
            <%--<c:if test="${userId == 100008}">--%>
                <li><a href="users"><spring:message code="user.title"/></a></li>
                <li><a href="restaurants"><spring:message code="restaurant.title"/></a></li>
            <%--</c:if>--%>
        </p>
    </div>
</div>
</body>
<jsp:include page="fragments/footer.jsp"/>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron">
    <div class="container">
        <p/>
        <p/>

        <form method="post" action="start">
            <b><spring:message code="app.login"/></b>
            <select name="userId">
                <option value="100007">Пользователь</option>
                <option value="100008">Админ</option>
            </select>
            <button type="submit"><spring:message code="common.select"/></button>
        </form>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
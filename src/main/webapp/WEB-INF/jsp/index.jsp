<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="messages.app"/>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
<form method="post" action="start">
    <b>Войти как &nbsp;</b>
    <select name="userId">
        <option value="100007">Пользователь</option>
        <option value="100008">Админ</option>
    </select>
    <button type="submit"><fmt:message key="common.select"/></button>
</form>
</section>
</body>
</html>
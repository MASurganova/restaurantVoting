<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<<fmt:setBundle basename="messages.app"/>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h2><fmt:message key="user.title"/></h2>
    <a href="users/create"><fmt:message key="user.add"/></a>
    <section>
        <table border="1" cellpadding="8" cellspacing="0">
            <thead>
            <tr>
                <th><fmt:message key="user.name"/></th>
                <th><fmt:message key="user.email"/></th>
                <th><fmt:message key="user.roles"/></th>
                <th><fmt:message key="user.registered"/></th>
                <th><fmt:message key="user.choice"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${users}" var="user">
                <jsp:useBean id="user" scope="page" type="ru.voting.model.User"/>
                <tr>
                    <td><c:out value="${user.name}"/></td>
                    <td><a href="mailto:${user.email}">${user.email}</a></td>
                    <td>${user.roles}</td>
                    <td>${user.registered}</td>
                    <td>${user.choice == null ? null : user.choice.name}</td>
                    <td><a href="users/update?id=${user.id}"><fmt:message key="common.update"/></a></td>
                    <td><a href="users/delete?id=${user.id}"><fmt:message key="common.delete"/></a></td>
                </tr>
            </c:forEach>
        </table>
    </section>
</section>
</body>
</html>
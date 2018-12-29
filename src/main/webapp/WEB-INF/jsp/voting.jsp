<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/votingDatatables.js" defer></script>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/noteUtil.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron">
    <div class="container">
        <h3><spring:message code="app.voting"/></h3>
        <table class="table table-striped display" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="restaurant.name"/></th>
                <th><spring:message code="restaurant.lunch"/></th>
                <th><spring:message code="restaurant.totalPrice"/></th>
                <th><spring:message code="restaurant.voters"/></th>
                <th><spring:message code="common.select"/></th>
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
                    <td><a onclick="chooseRow(${restaurant.id})"><span class="glyphicon glyphicon-heart" aria-hidden="true"></span></a></td>
                </tr>
            </c:forEach>
        </table>
        <br/>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <a class="btn btn-primary" onclick="startVoting()">
                <span class="glyphicon glyphicon-play" aria-hidden="true"></span>
                <spring:message code="app.start"/>
            </a>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <a class="btn btn-primary" onclick="endVoting()">
                <span class="glyphicon glyphicon-stop" aria-hidden="true"></span>
                <spring:message code="app.end"/>
            </a>
        </sec:authorize>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
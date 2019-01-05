<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/votingDatatables.js" defer></script>
<script type="text/javascript" src="resources/js/noteUtil.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron">
    <div class="container">
        <h3><spring:message code="history.title"/></h3>
        <table class="table table-striped display" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="history.restaurantName"/></th>
                <th><spring:message code="history.date"/></th>
                <th></th>
            </tr>
            </thead>
            </thead>
            <c:forEach items="${votingEvents}" var="votingEvent">
                <jsp:useBean id="votingEvent" scope="page" type="ru.voting.model.VotingEvent"/>
                <tr>
                    <td><a href="restaurants/${votingEvent.restaurantName}">${votingEvent.restaurantName}</a></td>
                    <td><${votingEvent.date}></td>
                    <td><a onclick="deleteRow(${votingEvent.date})">
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                    </a></td>
                </tr>
            </c:forEach>
        </table>
        <br/>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/historyDatatables.js" defer></script>
<script type="text/javascript" src="resources/js/noteUtil.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron">
    <div class="container">
        <h3><spring:message code="history.title"/></h3>
        <table class="table table-striped display" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="history.date"/></th>
                <th><spring:message code="history.restaurantName"/></th>
                <th><spring:message code="history.delete"/></th>
            </tr>
            </thead>
        </table>
        <br/>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
<script type="text/javascript">
    var i18n = [];
    <c:forEach var="key" items='<%=new String[]{"common.deleted","common.saved","common.errorStatus"}%>'>
    i18n["${key}"] = "<spring:message code="${key}"/>";
    </c:forEach>
</script>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/restaurantDatatables.js" defer></script>
<script type="text/javascript" src="resources/js/noteUtil.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron">
    <div class="container">
        <h3><spring:message code="restaurant.title"/></h3>
        <br/>
        <a class="btn btn-primary" onclick="add()">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            <spring:message code="common.add"/>
        </a>
        <br/><br/>
        <table class="table table-striped display" id="datatable">
            <thead>
            <tr>
                <th><fmt:message key="restaurant.name"/></th>
                <th><fmt:message key="restaurant.lunch"/></th>
                <th><fmt:message key="restaurant.totalPrice"/></th>
                <th><fmt:message key="restaurant.enabled"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${restaurants}" var="restaurant">
                <jsp:useBean id="restaurant" scope="page" type="ru.voting.model.Restaurant"/>
                <tr class="${restaurant.enabled ? '' : 'disabled'}">
                    <td><c:out value="${restaurant.name}"/></td>
                    <c:choose>
                        <c:when test="${restaurant.lunch.size() != 0}">
                            <td>
                                <ul>
                                    <c:forEach items="${restaurant.lunch}" var="dish">
                                        <jsp:useBean id="dish" scope="page" type="ru.voting.model.Dish"/>
                                        <li><c:out value="${dish.description}"/></li>
                                    </c:forEach>
                                </ul>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td></td>
                        </c:otherwise>
                    </c:choose>
                    <td>${restaurant.totalPrice}</td>
                    <td><input type="checkbox"
                               <c:if test="${restaurant.enabled}">checked</c:if>
                               onclick="enable($(this), ${restaurant.id})"/>
                    </td>
                    <td><a href="restaurants/${restaurant.id}"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a></td>
                    <td><a onclick="deleteRow(${restaurant.id})">
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                    </a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"><spring:message code="restaurant.add"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="detailsForm">

                    <div class="form-group">
                        <label for="name" class="control-label col-xs-3"><spring:message code="restaurant.name"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="name" name="name" placeholder="<spring:message code="user.name"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="button" onclick="save()" class="btn btn-primary">
                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
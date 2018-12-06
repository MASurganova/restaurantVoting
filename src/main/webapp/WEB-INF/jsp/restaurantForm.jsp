<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/dishDatatables.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<<div class="jumbotron">
    <div class="container">
        <jsp:useBean id="restaurant" type="ru.voting.model.Restaurant" scope="request"/>
        <h3><spring:message code="restaurant.update"/></h3>
        <form class="form-horizontal" id="detailsForm">
            <input type="hidden" id="id" name="id">
            <div class="form-group">
                <label for="name" class="control-label col-xs-3"><spring:message code="restaurant.name"/></label>

                <div class="col-xs-9">
                    <input type="text" class="form-control" id="name" name="name" placeholder="<spring:message code="restaurant.name"/>">
                </div>
            </div>

            <c:if test="${restaurant.id != null}">
                <h4><spring:message code="restaurant.lunch"/></h4>
                <br/>
                <a class="btn btn-primary" onclick="add()">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                    <spring:message code="common.add"/>
                </a>
                <br/></br>

                <table class="table table-striped display" id="datatable">
                    <thead>
                    <tr>
                        <th><fmt:message key="dish.description"/></th>
                        <th><fmt:message key="dish.price"/></th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <c:forEach items="${restaurant.lunch}" var="dish">
                        <jsp:useBean id="dish" scope="page" type="ru.voting.model.Dish"/>
                        <tr class="normal">
                            <td><c:out value="${dish.description}"/></td>
                            <td>${dish.price}</td>
                            <td><a><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a></td>
                            <td><a class="delete" id=${dish.id}><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></a></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
            <div class="form-group">
                <div class="col-xs-offset-3 col-xs-9">
                    <button type="submit" class="btn btn-primary">
                        <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>

<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"><spring:message code="dish.add"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="description" class="control-label col-xs-3"><spring:message code="dish.description"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="description" name="description" placeholder="<spring:message code="dish.description"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="submit" class="btn btn-primary">
                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

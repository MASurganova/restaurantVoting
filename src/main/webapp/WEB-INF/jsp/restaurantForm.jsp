<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sprring" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/dishDatatables.js" defer></script>
<script type="text/javascript" src="resources/js/noteUtil.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <jsp:useBean id="restaurant" type="ru.voting.model.Restaurant" scope="request"/>
        <a id="restaurantId" value="${restaurant.id}"></a>
        <h3><spring:message code="restaurant.update"/> ${restaurant.name}</h3>
        <br/>
        <br/>

        <h4><spring:message code="restaurant.lunch"/></h4>
        <a class="btn btn-primary" onclick="add()">
            <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
            <spring:message code="dish.add"/>
        </a>

        <table class="table table-striped display" id="datatable">
            <thead>
            <tr>
                <th><spring:message code="dish.description"/></th>
                <th><spring:message code="dish.price"/></th>
                <th></th>
                <th></th>
            </tr>
            </thead>
        </table>

        <br/>
        <br/>
        <form class="form-horizontal" id="restaurantForm">
            <div class="form-group">
                <label for="name" class="control-label col-lg10"><spring:message code="restaurant.newName"/></label>

                <div class="col-lg10">
                    <input type="text" class="form-control" id="name" name="name" value="${restaurant.name}">
                </div>
            </div>
            <div class="form-group">
                <div class="col-lg-1">
                    <button type="button" onclick="saveRestaurant()" class="btn btn-primary">
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
                <h2 class="modal-title" id = "modalTitle"></h2>
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
                        <label for="price" class="control-label col-xs-3"><spring:message code="dish.price"/></label>

                        <div class="col-xs-9">
                            <input type="number" class="form-control" id="price" name="price" placeholder="<spring:message code="dish.price"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="button" onclick="save(${restaurant.id})" class="btn btn-primary">
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
<script type="text/javascript">
    var i18n = [];
    i18n["addTitle"] = '<spring:message code="dish.add"/>';
    i18n["editTitle"] = '<spring:message code="dish.update"/>';

    <c:forEach var="key" items='<%=new String[]{"common.deleted","common.saved","common.enabled","common.disabled","common.errorStatus"}%>'>
    i18n["${key}"] = "<spring:message code="${key}"/>";
    </c:forEach>
</script>
</html>
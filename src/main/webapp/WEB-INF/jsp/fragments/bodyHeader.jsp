<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <a href="start" class="navbar-brand"><spring:message code="app.title"/></a>

        <div class="collapse navbar-collapse">
            <form class="navbar-form navbar-right">
                <a class="btn btn-primary" href="">
                    <span class="glyphicon glyphicon-log-in" aria-hidden="true"></span>
                </a>
            </form>
        </div>
    </div>
</div>
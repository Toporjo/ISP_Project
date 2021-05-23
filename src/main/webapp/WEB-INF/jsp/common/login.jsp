<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<%--<c:set var="title" value="Login"/>--%>
<fmt:message key="login_jps.title" var="title"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>


<div class="container shadow-lg p-3 mb-5 bg-white rounded" style="margin-top:160px;width: 600px">
    <c:if test="${requestScope.errorMessage ne null}">
        <div class="alert alert-success">
            <strong>Success!</strong> Indicates a successful or positive action.
        </div>
    </c:if>

    <div class="mx-auto" style="width:500px">
        <h2 class="text-center"><fmt:message key="login_jsp.h.login"/></h2>
        <form action="home" method="post">
            <input type="hidden" name="command" value="login"/>
            <div class="form-group">
                <fmt:message key="login_jsp.input.placeholder.agreement_number" var="placeholder_an"/>
                <label for="number"><fmt:message key="login_jsp.label.agreement_number"/></label>
                <input type="text" maxlength="9" class="form-control" id="number" placeholder="${placeholder_an}"
                       name="agreementNumber" pattern="^[0-9]{0,9}$">
            </div>
            <div class="form-group">
                <fmt:message key="login_jsp.input.placeholder.password" var="placeholder_password"/>
                <label for="pwd"><fmt:message key="login_jsp.label.password"/></label>
                <input type="password" class="form-control" id="pwd" placeholder="${placeholder_password}"
                       name="password">
            </div>
            <div class="text-center">
                <button type="submit" class="btn btn-primary"><fmt:message key="login_jps.button.submit"/></button>
            </div>
        </form>
    </div>

</div>

</body>
</html>

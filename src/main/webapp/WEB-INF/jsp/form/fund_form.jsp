<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<c:set var="title" value="Tariff form"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="container shadow-lg p-3 mb-5 bg-white rounded" style="margin-top:160px;width: 600px">
    <form action="home" method="post">
        <input type="hidden" name="command" value="addFunds"/>
        <input type="hidden" name="id" value="${user.agreementNumber}"/>
        <div class="form-group">
            <fmt:message key="register_jsp.placeholder.full_name" var="placeholder_name"/>
            <label for="amount">Кошти</label>
            <input type="text" pattern="^[0-9]{1,4}$" class="form-control" id="amount"
                   placeholder="${placeholder_name}" name="amount">
        </div>
        <div class="text-center">
            <input class="btn btn-success my-2 my-sm-0" type="submit" value="Поповнити"/>
        </div>
    </form>
</div>


</body>
</html>

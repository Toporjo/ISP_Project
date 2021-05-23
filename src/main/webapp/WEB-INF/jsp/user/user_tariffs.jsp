<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/contains.tld" prefix="ct" %>

<html>
<fmt:message key="home_jsp.title" var="title"/>
<c:set var="title" value="Fast Net"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="container">
    <c:forEach var="entry" items="${tariffsDates}">
        Послуга: <fmt:message key="${ct:printService(entry.key.serviceId)}"/>
        Назва: ${entry.key.tariffName}
        Вартість: ${entry.key.price}
        Дійсний до: ${entry.value}
        <br>
    </c:forEach>
</div>
</body>
</html>

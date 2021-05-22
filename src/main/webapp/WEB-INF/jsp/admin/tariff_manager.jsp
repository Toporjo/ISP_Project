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


<div class="container shadow-lg p-3 mh-100" style="margin-top:60px">
    <a href="/home?command=tariffForm&mode=new" class="btn btn-primary mx-auto p-2">Створити тариф</a>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Послуга</th>
            <th>Ціна</th>
            <th>Знижка</th>
            <th>Назва</th>
            <th>Опис</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="tar" items="${tariffs}">
            <tr>
                <td><fmt:message key="${ct:printService(tar.serviceId)}"/></td>
                <td>${tar.price}</td>
                <td>${tar.discount}</td>
                <td>${tar.tariffName}</td>
                <td>${tar.description}</td>
                <td> <a href="/home?command=tariffForm&id=${tar.id}&mode=edit" class="btn btn-secondary text-right">Редагувати</a></td>
                <td> <a href="/home?command=deleteTariff&id=${tar.id}" class="btn btn-danger text-right">&times;</a></td>
            </tr>

        </c:forEach>


        </tbody>
    </table>


</div>


</body>
</html>

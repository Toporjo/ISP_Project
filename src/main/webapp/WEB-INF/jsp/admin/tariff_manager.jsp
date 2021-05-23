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


<div class="container shadow-lg p-3 h-100" style="margin-top:10px">

    <a href="/home?command=tariffForm&mode=new" class="btn btn-primary mx-auto p-2 float-right my-2">
        <fmt:message key="tariff_manager_jsp.a.create_tariff"/></a>
    <table class="table table-striped mh-100">
        <thead>
        <tr>
            <th><fmt:message key="tariff_manager_jsp.th.service"/></th>
            <th><fmt:message key="tariff_manager_jsp.th.price"/></th>
            <th><fmt:message key="tariff_manager_jsp.th.discount"/></th>
            <th><fmt:message key="tariff_manager_jsp.th.name"/></th>
            <th><fmt:message key="tariff_manager_jsp.th.description"/></th>
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
                <td> <a href="/home?command=tariffForm&id=${tar.id}&mode=edit" class="btn btn-secondary text-right"><fmt:message key="tariff_manager_jsp.a.edit"/></a></td>
                <td> <a href="/home?command=deleteTariff&id=${tar.id}" class="btn btn-danger text-right">&times;</a></td>
            </tr>

        </c:forEach>


        </tbody>
    </table>

    <ul class="pagination justify-content-center pb-5 pt-2 " style="margin-top: auto;align-self: flex-end;">
        <li class="page-item"><a class="page-link" href="/home?command=tariffManager&page=1"><fmt:message key="pagination.li.first"/></a></li>
        <li class="page-item"><a class="page-link" href="/home?command=tariffManager&page=${page < 2 ? 1: page-1}"><fmt:message key="pagination.li.previous"/></a></li>
        <c:forEach var="page" begin="${1}" end="${pages}">
            <li class="page-item ${requestScope.page == page? 'active': ''}" ><a class="page-link" href="/home?command=tariffManager&page=${page}">${page}</a></li>
        </c:forEach>
        <li class="page-item"><a class="page-link" href="/home?command=tariffManager&page=${page >= pages ? pages: page+1}"><fmt:message key="pagination.li.next"/></a></li>
        <li class="page-item"><a class="page-link" href="/home?command=tariffManager&page=${pages}"><fmt:message key="pagination.li.last"/></a></li>
    </ul>

</div>


</body>
</html>

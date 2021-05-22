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
    <a href="/home?command=registrationForm" class="btn btn-primary float-right m-2">Зареєструвати користувача</a>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Номер договору</th>
            <th>ПІБ</th>
            <th>Статус</th>
            <th>Баланс</th>
            <th></th>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.agreementNumber}</td>
                <td>${user.firstName} ${user.lastName} ${user.patronymicName}</td>
                <td>
                    <c:choose>
                        <c:when test="${user.blocked}">Заблокований</c:when>
                        <c:otherwise>Розблокований</c:otherwise>
                    </c:choose>
                </td>
                <td>${user.balance}</td>
                <td>

                    <c:choose>
                        <c:when test="${user.blocked}">
                            <a href="/home?command=handleUser&id=${user.agreementNumber}&block=false" class="btn btn-success text-right">Розблокувати</a>
                        </c:when>
                        <c:otherwise>
                            <a href="/home?command=handleUser&id=${user.agreementNumber}&block=true" class="btn btn-danger text-right">Заблокувати</a>
                        </c:otherwise>
                    </c:choose>
                </td>

            </tr>

        </c:forEach>


        </tbody>
    </table>

    <ul class="pagination justify-content-center pb-5 pt-2 " style="margin-top: auto;align-self: flex-end;">
        <li class="page-item"><a class="page-link" href="/home?command=userManager&page=1">First</a></li>
        <li class="page-item"><a class="page-link" href="/home?command=userManager&page=${page < 2 ? 1: page-1}">Previous</a></li>
        <c:forEach var="page" begin="${1}" end="${pages}">
            <li class="page-item ${requestScope.page == page? 'active': ''}" ><a class="page-link" href="/home?command=userManager&page=${page}">${page}</a></li>
        </c:forEach>
        <li class="page-item"><a class="page-link" href="/home?command=userManager&page=${page >= pages ? pages: page+1}">Next</a></li>
        <li class="page-item"><a class="page-link" href="/home?command=userManager&page=${pages}">Last</a></li>
    </ul>
</div>


</body>
</html>

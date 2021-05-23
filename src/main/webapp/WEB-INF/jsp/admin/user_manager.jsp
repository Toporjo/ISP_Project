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
    <a href="/home?command=registrationForm" class="btn btn-primary float-right m-2"><fmt:message key="user_manager_jsp.a.register_user"/></a>
    <table class="table table-striped">
        <thead>
        <tr>
            <th><fmt:message key="user_manager_jsp.th.agreement_number"/></th>
            <th><fmt:message key="user_manager_jsp.th.full_name"/></th>
            <th><fmt:message key="user_manager_jsp.th.status"/></th>
            <th><fmt:message key="user_manager_jsp.th.wallet"/></th>
            <th></th>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.agreementNumber}</td>
                <td>${user.lastName} ${user.firstName} ${user.patronymicName}</td>
                <td>
                    <c:choose>
                        <c:when test="${user.blocked}"><fmt:message key="user_manager_jsp.status.blocked"/></c:when>
                        <c:otherwise><fmt:message key="user_manager_jsp.status.unblocked"/></c:otherwise>
                    </c:choose>
                </td>
                <td>${user.balance}</td>
                <td>

                    <c:choose>
                        <c:when test="${user.blocked}">
                            <a href="/home?command=handleUser&id=${user.agreementNumber}&block=false" class="btn btn-success text-right"><fmt:message key="user_manager_jsp.a.unblock"/></a>
                        </c:when>
                        <c:otherwise>
                            <a href="/home?command=handleUser&id=${user.agreementNumber}&block=true" class="btn btn-danger text-right"><fmt:message key="user_manager_jsp.a.block"/></a>
                        </c:otherwise>
                    </c:choose>
                </td>

            </tr>

        </c:forEach>


        </tbody>
    </table>

    <ul class="pagination justify-content-center pb-5 pt-2 " style="margin-top: auto;align-self: flex-end;">
        <li class="page-item"><a class="page-link" href="/home?command=userManager&page=1"><fmt:message key="pagination.li.first"/></a></li>
        <li class="page-item"><a class="page-link" href="/home?command=userManager&page=${page < 2 ? 1: page-1}"><fmt:message key="pagination.li.previous"/></a></li>
        <c:forEach var="page" begin="${1}" end="${pages}">
            <li class="page-item ${requestScope.page == page? 'active': ''}" ><a class="page-link" href="/home?command=userManager&page=${page}">${page}</a></li>
        </c:forEach>
        <li class="page-item"><a class="page-link" href="/home?command=userManager&page=${page >= pages ? pages: page+1}"><fmt:message key="pagination.li.next"/></a></li>
        <li class="page-item"><a class="page-link" href="/home?command=userManager&page=${pages}"><fmt:message key="pagination.li.last"/></a></li>
    </ul>
</div>


</body>
</html>

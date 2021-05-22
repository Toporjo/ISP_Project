<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>

<html>
<c:set var="title" value="Error"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="container pt-4" style="margin-top:100px">
<%--    <h1 class="display-1"> <p class="text-danger"> <c:out value="${errorMessage}"/></p></h1>--%>
<%--    <br/>--%>
    <h1 class="display-2"> <p class="text-danger">

        <fmt:message key="${errorMessage}"/>
    </p></h1>

</div>
</body>
</html>

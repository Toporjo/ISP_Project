<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/contains.tld" prefix="custom" %>

<html>
<fmt:message key="home_jsp.title" var="title"/>
<c:set var="title" value="Fast Net"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>


<div class="container-fluid mt-2">


    <a href="/home?command=triggerPayment">TRIGGER DAILY PAYMENT</a>
    <p class="text-center">
        <fmt:message
                key="home_jsp.checkbox.ukrainian"/>
    </p>
    <p>${sessionScope.user}</p>


    <%@ include file="/WEB-INF/jspf/success.jspf" %>

</div>

</body>
</html>

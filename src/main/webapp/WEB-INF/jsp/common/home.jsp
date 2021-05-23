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


<div class="jumbotron">
    <h1><fmt:message key="home_jsp.h1.isp_project"/></h1>

<%--    <a href="/home?command=triggerPayment">TRIGGER DAILY PAYMENT</a>--%>


    <%@ include file="/WEB-INF/jspf/success.jspf" %>
</div>
<div class="container">
    <h2><fmt:message key="home_jsp.h2.services_list"/></h2>

    <div class="card bg-light text-center">
        <div class="card-body">
            <h5>
                <a class="text-muted" href="/home?command=service&service=0"><fmt:message
                        key="header_jspf.anchor.telephone"/></a>
            </h5>
        </div>
    </div>
    <br>

    <div class="card bg-light text-center">
        <div class="card-body">
            <h5>
                <a class="text-muted" href="/home?command=service&service=1"><fmt:message
                        key="header_jspf.anchor.internet"/></a>
            </h5>
        </div>
    </div>
    <br>

    <div class="card bg-light text-center">
        <div class="card-body">
            <h5>
                <a class="text-muted" href="/home?command=service&service=2"><fmt:message
                        key="header_jspf.anchor.cable_tv"/></a>
            </h5>
        </div>
    </div>
    <br>

    <div class="card bg-light  text-center">
        <div class="card-body">
            <h5>
                <a class="text-secondary" href="/home?command=service&service=3"><fmt:message
                        key="header_jspf.anchor.ip_tv"/></a>
            </h5>
        </div>
    </div>
    <br>

</div>

</body>
</html>

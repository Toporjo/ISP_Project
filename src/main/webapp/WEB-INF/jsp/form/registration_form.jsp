<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>

<html>
<c:set var="title" value="Login"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>



<div class="container shadow-lg p-3 mb-5 bg-white rounded" style="margin-top:100px;width: 600px">
    <div class="mx-auto" style="width:500px">
        <h2><fmt:message key="register_jsp.h.registration"/></h2>
        <form action="home" method="post">
            <input type="hidden" name="command" value="registerUser"/>
            <div class="form-group">
                <fmt:message key="register_jsp.placeholder.full_name" var="placeholder_name"/>
                <label for="firstName">Ім'я</label>
                <input type="text" class="form-control" id="firstName" placeholder="${placeholder_name}" name="firstName">
            </div>

            <div class="form-group">
                <fmt:message key="register_jsp.placeholder.full_name" var="placeholder_name"/>
                <label for="lastName">Фамілія</label>
                <input type="text" class="form-control" id="lastName" placeholder="${placeholder_name}" name="lastName">
            </div>

            <div class="form-group">
                <fmt:message key="register_jsp.placeholder.full_name" var="placeholder_name"/>
                <label for="patronymicName">По батькові</label>
                <input type="text" class="form-control" id="patronymicName" placeholder="${placeholder_name}" name="patronymicName">
            </div>

            <div class="form-group">
                <fmt:message key="register_jsp.placeholder.full_name" var="placeholder_name"/>
                <label for="password">Пароль</label>
                <input type="password" class="form-control" id="password" placeholder="${placeholder_name}" name="password">
            </div>


            <div class="text-center">
                <div class="container">
                    <p class="text-left" style="font-size: 12px">
                        <fmt:message key="register_jsp.requirements"/>
                    </p>
                </div>
            <button type="submit" class="btn btn-primary"><fmt:message key="register_jsp.button.register"/></button>
            </div>
        </form>
    </div>

</div>



</body>
</html>

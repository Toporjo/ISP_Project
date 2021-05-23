<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/contains.tld" prefix="ct" %>

<html>
<c:set var="title" value="Tariff form"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="container shadow-lg p-3 mb-5 bg-white rounded" style="margin-top:30px;width: 600px">
    <div class="mx-auto" style="width:500px">
        <h2><fmt:message key="tariff_form_jsp.h2.tariff_form"/></h2>
        <c:if test="${tariff ne null}">
            <label for="name"><fmt:message key="${ct:printService(tariff.serviceId)}"/></label>
        </c:if>

        <form action="home" method="post">
            <c:choose>
                <c:when test="${param.mode eq 'edit'}">
                    <input type="hidden" name="command" value="editTariff"/>
                    <input type="hidden" name="id" value="${tariff.id}"/>
                </c:when>
                <c:when test="${param.mode eq 'new'}">
                    <input type="hidden" name="command" value="newTariff"/>
                    <div class="form-group">
                        <label for="sel1"><fmt:message key="tariff_form_jsp.label.service"/>:</label>
                        <select class="form-control" id="sel1" name="serviceId">
                            <option value="0"><fmt:message
                                    key="header_jspf.anchor.telephone"/></option>
                            <option value="1"><fmt:message
                                    key="header_jspf.anchor.internet"/></option>
                            <option value="2"><fmt:message
                                    key="header_jspf.anchor.cable_tv"/></option>
                            <option value="3"><fmt:message
                                    key="header_jspf.anchor.ip_tv"/></option>
                        </select>
                    </div>
                </c:when>
            </c:choose>
            <div class="form-group">
                <fmt:message key="tariff_form_jsp.input.price" var="placeholder_name"/>
                <label for="name"><fmt:message key="tariff_form_jsp.label.price"/></label>
                <input type="text" class="form-control" id="name" placeholder="${placeholder_name}" name="price"
                       <c:if test="${tariff ne null}">value="${tariff.price}"</c:if>/>
            </div>

            <div class="form-group">
                <fmt:message key="tariff_form_jsp.input.discount" var="placeholder_name"/>
                <label for="name"><fmt:message key="tariff_form_jsp.label.discount"/></label>
                <input type="text" class="form-control" id="name" placeholder="${placeholder_name}" name="discount"
                       <c:if test="${tariff ne null}">value="${tariff.discount}"</c:if>/>
            </div>

            <c:forEach items="${languages}" var="lang">
                <input type="hidden" name="language" value="${lang.id}">
                <div class="form-group">
                    <fmt:message key="tariff_form_jsp.input.name" var="placeholder_name"/>
                    <label for="name"><fmt:message key="tariff_form_jsp.label.name"/> (<fmt:message
                            key="${ct:printLanguage(lang.id)}"/>)</label>
                    <input type="text" class="form-control" id="name" placeholder="${placeholder_name}" name="names"
                           <c:if test="${tariff ne null}">value="${tariffInfo[lang].tariffName}"</c:if>/>
                </div>

                <div class="form-group">
                    <fmt:message key="tariff_form_jsp.input.description" var="placeholder_name"/>
                    <label for="name"><fmt:message key="tariff_form_jsp.label.description"/> (<fmt:message
                            key="${ct:printLanguage(lang.id)}"/>)</label>
                    <textarea style="max-height: 200px;min-height: 50px;"
                              class="form-control" id="name" placeholder="${placeholder_name}"
                              name="descriptions"><c:if
                            test="${tariff ne null}">${tariffInfo[lang].description}</c:if></textarea>
                </div>
            </c:forEach>

            <div class="text-center">

                <c:if test="${param.mode eq 'edit'}">
                    <button type="submit" class="btn btn-primary"><fmt:message
                            key="tariff_form_jsp.button.edit"/></button>
                </c:if>
                <c:if test="${param.mode eq 'new'}">
                    <button type="submit" class="btn btn-primary"><fmt:message
                            key="tariff_form_jsp.button.create"/></button>
                </c:if>
            </div>


        </form>

    </div>

</div>


</body>
</html>

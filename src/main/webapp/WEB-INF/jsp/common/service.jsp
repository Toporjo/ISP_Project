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


<div class="container shadow-lg p-3 mb-2" style="margin-top:10px">
    <div class="d-flex flex-row">
        <form>
            <input type="hidden" name="command" value="service">
            <input type="hidden" name="service" value="${param['service']}">
            <div class="p-2 ">
                <div class="d-flex flex-column">
                    <div class="p-2 ">
                        <h6><fmt:message key="service_jsp.h6.sort_order"/></h6>
                    </div>
                    <div class="p-2 ">
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" class="form-check-input" name="order"
                                       checked="checked" ${sessionScope.tariffSortParams.order == 'asc' ? 'checked="checked"' : ''}
                                       value="asc"><fmt:message key="service_jsp.input.ascending"/>
                            </label>
                        </div>
                    </div>
                    <div class="p-2 ">
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" class="form-check-input" name="order"
                                ${sessionScope.tariffSortParams.order == 'desc' ? 'checked="checked"' : ''}
                                       value="desc"><fmt:message key="service_jsp.input.descending"/>
                            </label>
                        </div>
                    </div>
                </div>
            </div>
            <div class="p-2 ">
                <div class="d-flex flex-column">
                    <div class="p-2 ">
                        <h6><fmt:message key="service_jsp.h6.sort_by"/></h6>
                    </div>
                    <div class="p-2 ">
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" class="form-check-input" name="orderBy"
                                ${sessionScope.tariffSortParams.orderBy == 'tariff_name' ? 'checked="checked"' : ''}
                                       value="tariff_name"><fmt:message key="service_jsp.input.name"/></label>
                        </div>
                    </div>
                    <div class="p-2 ">
                        <div class="form-check">
                            <label class="form-check-label">
                                <input type="radio" class="form-check-input" name="orderBy"
                                ${sessionScope.tariffSortParams.orderBy == 'price' ? 'checked="checked"' : ''}
                                       value="price"><fmt:message key="service_jsp.input.price"/></label>
                        </div>
                    </div>
                </div>


            </div>
            <div class="p-2">
                <button type="submit" class="btn btn-outline-primary btn-sm"><fmt:message
                        key="service_jsp.button.sort"/></button>
            </div>
        </form>
        <div class="p-2">
            <form method="get">
                <input type="hidden" name="command" value="downloadTariffs">
                <input type="hidden" name="service" value="${param['service']}">
                <input type="submit" class="btn btn-primary my-2 my-sm-0" value="<fmt:message
                        key="service_jsp.button.download_tariffs_list"/>">
            </form>
        </div>
    </div>


    <%--    <div class="d-flex flex-row bg-secondary">--%>
    <%--        <div class="p-2 ">--%>
    <%--            <a href="/home?command=downloadTariffs&service=${param['service']}">Завантажити тарифи у вигляді тестового--%>
    <%--                файлу</a>--%>
    <%--            <form method="get">--%>
    <%--                <input type="hidden" name="command" value="downloadTariffs">--%>
    <%--                <input type="hidden" name="service" value="${param['service']}">--%>
    <%--                <input type="submit" class="btn btn-primary my-2 my-sm-0" value="Звантажити тарифи">--%>
    <%--            </form>--%>
    <%--        </div>--%>
    <%--        <div class="p-2 ">--%>
    <%--            <form>--%>
    <%--                <div class="btn-group">--%>
    <%--                    <input type="hidden" name="command" value="service">--%>
    <%--                    <input type="hidden" name="service" value="${param['service']}">--%>
    <%--                    <h6>Порядок сортування</h6>--%>
    <%--                    <div class="row">--%>
    <%--                        <div class="col-6">--%>
    <%--                            <div class="form-check">--%>
    <%--                                <label class="form-check-label">--%>
    <%--                                    <input type="radio" class="form-check-input" name="order"--%>
    <%--                                           checked="checked" ${sessionScope.tariffSortParams.order == 'asc' ? 'checked="checked"' : ''}--%>
    <%--                                           value="asc">Зростання (А-Я)--%>
    <%--                                </label>--%>
    <%--                            </div>--%>
    <%--                            <div class="form-check">--%>
    <%--                                <label class="form-check-label">--%>
    <%--                                    <input type="radio" class="form-check-input" name="order"--%>
    <%--                                    ${sessionScope.tariffSortParams.order == 'desc' ? 'checked="checked"' : ''}--%>
    <%--                                           value="desc">Спадання (Я-А)--%>
    <%--                                </label>--%>
    <%--                            </div>--%>
    <%--                        </div>--%>
    <%--                    </div>--%>

    <%--                    <h6>Сортувати за</h6>--%>
    <%--                    <div class="btn-group">--%>
    <%--                        <div class="form-check">--%>
    <%--                            <label class="form-check-label">--%>
    <%--                                <input type="radio" class="form-check-input" name="orderBy"--%>
    <%--                                ${sessionScope.tariffSortParams.orderBy == 'tariff_name' ? 'checked="checked"' : ''}--%>
    <%--                                       value="tariff_name">Назвою</label>--%>
    <%--                        </div>--%>
    <%--                        <div class="form-check">--%>
    <%--                            <label class="form-check-label">--%>
    <%--                                <input type="radio" class="form-check-input" name="orderBy"--%>
    <%--                                ${sessionScope.tariffSortParams.orderBy == 'price' ? 'checked="checked"' : ''}--%>
    <%--                                       value="price">Ціною</label>--%>
    <%--                        </div>--%>
    <%--                    </div>--%>
    <%--                    <div class="col-6 mt-4">--%>
    <%--                        <button type="submit" class="btn btn-outline-primary btn-sm"><fmt:message--%>
    <%--                                key="home_jsp.button.apply"/></button>--%>
    <%--                    </div>--%>
    <%--                </div>--%>
    <%--            </form>--%>
    <%--        </div>--%>
    <%--        <div class="p-2 bg-primary">Flex item 3</div>--%>
    <%--    </div>--%>


    <%--    <c:if test="${empty tariffs}">НІМА ТАРІФІВ</c:if><br/>--%>
    <%--    <form>--%>
    <%--        <div class="btn-group">--%>
    <%--            <input type="hidden" name="command" value="service">--%>
    <%--            <input type="hidden" name="service" value="${param['service']}">--%>
    <%--            <h6>Порядок сортування</h6>--%>
    <%--            <div class="row">--%>
    <%--                <div class="col-6">--%>
    <%--                    <div class="form-check">--%>
    <%--                        <label class="form-check-label">--%>
    <%--                            <input type="radio" class="form-check-input" name="order"--%>
    <%--                                   checked="checked" ${sessionScope.tariffSortParams.order == 'asc' ? 'checked="checked"' : ''}--%>
    <%--                                   value="asc">Зростання (А-Я)--%>
    <%--                        </label>--%>
    <%--                    </div>--%>
    <%--                    <div class="form-check">--%>
    <%--                        <label class="form-check-label">--%>
    <%--                            <input type="radio" class="form-check-input" name="order"--%>
    <%--                            ${sessionScope.tariffSortParams.order == 'desc' ? 'checked="checked"' : ''}--%>
    <%--                                   value="desc">Спадання (Я-А)--%>
    <%--                        </label>--%>
    <%--                    </div>--%>
    <%--                </div>--%>
    <%--            </div>--%>

    <%--            <h6>Сортувати за</h6>--%>
    <%--            <div class="btn-group">--%>
    <%--                <div class="form-check">--%>
    <%--                    <label class="form-check-label">--%>
    <%--                        <input type="radio" class="form-check-input" name="orderBy"--%>
    <%--                        ${sessionScope.tariffSortParams.orderBy == 'tariff_name' ? 'checked="checked"' : ''}--%>
    <%--                               value="tariff_name">Назвою</label>--%>
    <%--                </div>--%>
    <%--                <div class="form-check">--%>
    <%--                    <label class="form-check-label">--%>
    <%--                        <input type="radio" class="form-check-input" name="orderBy"--%>
    <%--                        ${sessionScope.tariffSortParams.orderBy == 'price' ? 'checked="checked"' : ''}--%>
    <%--                               value="price">Ціною</label>--%>
    <%--                </div>--%>
    <%--            </div>--%>
    <%--            <div class="col-6 mt-4">--%>
    <%--                    <button type="submit" class="btn btn-outline-primary btn-sm"><fmt:message--%>
    <%--                            key="home_jsp.button.apply"/></button>--%>
    <%--            </div>--%>
    <%--        </div>--%>
    <%--    </form>--%>

    <div class="d-flex flex-wrap justify-content-start">
        <c:forEach var="tar" items="${tariffs}">
            <div class="card bg-success bg-gradient m-2 rgba-green-light  " style="width:200px;height: 300px;">
                <div class="card-body text-center view overlay hoverable">
                    <p class="card-title">${tar.tariffName}</p>
                    <p class="card-text">${tar.description}</p>
                    <p class="card-text">${tar.discount}</p>
                    <c:choose>
                        <c:when test="${tar.discount ne 0}">
                            <p class="card-text"><fmt:message
                                    key="service_jsp.p.price"/>: <s>${tar.price}</s>
                                <ct:subtractPercentage discount="${tar.discount}" price="${tar.price}"/> ₴</p>
                        </c:when>
                        <c:otherwise>
                            <p class="card-text"><fmt:message
                                    key="service_jsp.p.price"/>:${tar.price} ₴</p>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${sessionScope.role eq 'USER'}">
                        <form class="form-inline my-2 my-lg-0 mx-auto">
                            <input type="hidden" name="command" value="activateTariff"/>
                            <input type="hidden" name="tariffId" value="${tar.id}"/>
                            <input type="hidden" name="serviceId" value="${param['service']}"/>
                            <input class="btn btn-success my-2 my-sm-0" type="submit" value=" <fmt:message key="service_jsp.button.activate_tariff"/>"/>
                        </form>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </div>
    <%@ include file="/WEB-INF/jspf/success.jspf" %>
</div>


</body>
</html>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/error.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html: charset=utf-8">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
        <title>Online shop</title>
        <link href="${pageContext.request.contextPath}/resources/css/index_styles.css" type="text/css" rel="stylesheet">
	</head>
	<body>
	    <!--NAVIGATION BAR-->
	    <nav class="navbar navbar-light bg-light">
	        <a class="navbar-brand">
	            <spring:message code="online_shop_title"/>
	        </a>
            <div class="btn-group" role="group" aria-label="Basic example">

                <!--MAIN PAGE BUTTON-->
                <a href="${pageContext.request.contextPath}/">
                    <button type="button" class="btn btn-outline-warning" data-toggle="modal" data-target="#modalCenter-MainPage">
                        <spring:message code="main_page_title"/>
                    </button>
                </a>

      	        <!--LANGUAGE BUTTONS-->
      	        <div id="localizationFrame">
      	            <span style="float: right">
                        <a href="?lang=en">en</a>
                        |
                        <a href="?lang=ua">ua</a>
                        |
                        <a href="?lang=ru">ru</a>
                    </span>
                </div>

            </div>
        </nav>

        <!--LEFT SIDE BAR-->
	    <div class="left-side-bar">
	        <div class="alert alert-warning" role="alert">

            </div>
	    </div>

	    <!--CENTRAL BAR-->
		<div class="main-bar">
            <div class="alert alert-warning" role="alert">
                <c:choose>
                    <c:when test="${empty orderList}">
                        <div class="input-group mb-3">
                            <h4><spring:message code="empty_order_list"/></h4>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="input-group mb-3">
                            <h4><spring:message code="orders_list"/></h4>
                        </div>
                        <div class="accordion" id="orderListWrapper">
                            <c:forEach var="order" items="${orderList}">
                                <form action="${pageContext.request.contextPath}/order/${order.id}" modelAttribute="orderCreateDto" method="POST">
                                    <div class="card">
                                        <div class="card-header" id="headingOne${order.id}">
                                            <h5 class="mb-0">
                                                <button class="btn btn-link collapsed" type="button" data-toggle="collapse" data-target="#collapseOne${order.id}" aria-expanded="true" aria-controls="collapseOne${order.id}">
                                                    <div class="input-group mb-3">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><spring:message code="order_label"/> # ${order.id}</span>
                                                        </div>
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><spring:message code="date_label"/>:</span>
                                                        </div>
                                                        <input readonly name="date" type="text" value="${order.date}" class="form-control">
                                                    </div>
                                                    <div class="input-group mb-3">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><spring:message code="user_name_label"/>:</span>
                                                        </div>
                                                        <input readonly name="userName" type="text" value="${order.userDto.userName}" class="form-control">
                                                        <input hidden name="userId" type="text" value="${order.userDto.id}">
                                                    </div>
                                                    <div class="input-group mb-3">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><spring:message code="address_label"/>:</span>
                                                        </div>
                                                        <input readonly type="text" name="address" value="${order.address}" class="form-control">
                                                    </div>
                                                    <input hidden name="paymentConditionsIndex" type="text" value="${order.conditions}">
                                                </button>
                                            </h5>
                                        </div>
                                        <div id="collapseOne${order.id}" class="collapse" aria-labelledby="headingOne${order.id}" data-parent="#orderListWrapper">
                                            <div class="card-body">
                                                <c:forEach var="orderPosition" items="${order.orderPositionViewDtoList}">
                                                    <div class="input-group mb-3">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text">${orderPosition.productName}</span>
                                                        </div>
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text">$</span>
                                                        </div>
                                                        <input readonly type="text" value="${orderPosition.unitPrice}" class="form-control">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><spring:message code="product_count"/>:</span>
                                                        </div>
                                                        <input readonly type="text" value="${orderPosition.quantity}" class="form-control">
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="alert alert-success" role="alert">
                                        <div class="input-group mb-3">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><spring:message code="state_label"/>:</span>
                                            </div>
                                            <select name="orderStateIndex" class="custom-select">
                                                <option
                                                    <c:if test="${order.orderState == 0}">
                                                        selected
                                                    </c:if>
                                                value="0"><spring:message code="state_registered"/></option>
                                                <option
                                                    <c:if test="${order.orderState == 1}">
                                                        selected
                                                    </c:if>
                                                value="1"><spring:message code="state_executed"/></option>
                                                <option
                                                    <c:if test="${order.orderState == 2}">
                                                        selected
                                                    </c:if>
                                                value="2"><spring:message code="state_canceled"/></option>
                                            </select>
                                        </div>
                                        <div class="input-group mb-3">
                                            <label>
                                                <input name="payed" <c:if test="${order.payed == true}"> checked </c:if> type="checkbox">
                                                <spring:message code="state_payed"/>
                                            </label>
                                        </div>
                                        <div class="input-group mb-3">
                                            <button type="submit" class="btn btn-primary" data-dismiss="modal">
                                                <spring:message code="save_changes"/>
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	</body>
</html>
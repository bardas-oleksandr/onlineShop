<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/error.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

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
	    <!--Navigation bar-->
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

        <!--Left side bar-->
	    <div class="left-side-bar">
	        <c:choose>
                <c:when test="${sessionScope.user.state == 1}">
	                <div class="alert alert-warning" role="alert">
                        <div class="input-group mb-3">
                            <h4 class="modal-title">
                                <spring:message code="personal_data_label"/>
                            </h5>
                        </div>
                        <div class="input-group mb-3">
                            <h5><spring:message code="user_name_label"/>: ${user.userName}</h5>
                        </div>
                        <div class="input-group mb-3">
                            <h5><spring:message code="email_address_label"/>: ${user.email}</h5>
                        </div>
                    </div>
                </c:when>
            </c:choose>
	    </div>

	    <!--Central bar-->
		<div class="main-bar">
            <div class="modal-body">
                <c:choose>
                    <c:when test="${empty sessionScope.cart or sessionScope.cart.size == 0}">
                        <div class="form-group">
                            <h6><spring:message code="empty_purchase_list"/></h6>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="productId" items="${sessionScope.cart.productCountMap.keySet()}">
                            <c:set var="product" scope="page" value="${sessionScope.cart.productViewDtoMap.get(productId)}"/>
                            <c:set var="count" scope="page" value="${sessionScope.cart.productCountMap.get(productId)}"/>
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">${product.name}</span>
                                </div>
                                <div class="input-group-prepend">
                                    <span class="input-group-text">$</span>
                                </div>
                                <input id="priceFor${productId}" name="priceFor${productId}" readonly type="text" value="${product.price}" class="form-control">
                                <div class="input-group-prepend">
                                    <span class="input-group-text"><spring:message code="product_count"/>:</span>
                                </div>
                                <input id="countFor${productId}" name="countFor${productId}" readonly type="text" value="${count}" class="form-control">
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>


            <c:choose>
                <c:when test="${not empty sessionScope.cart and sessionScope.cart.size > 0}">
                    <div class="modal-body">
                        <form action="${pageContext.request.contextPath}/order" method="post">
                            <!--DELIVERY ADDRESS-->
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="deliveryAddresLabel"><spring:message code="address_label"/></span>
                                </div>
                                <input id="address" name="address" type="text" class="form-control" aria-describedby=="deliveryAddresLabel">
                            </div>
                            <!--PAYMENT CONDITIONS-->
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="paymentConditionsLabel"><spring:message code="payment_conditions"/></span>
                                </div>
    					        <select id="paymentConditions" name="paymentConditions" class="custom-select" aria-describedby=="paymentConditionsLabel">
    						        <option selected value="0">
    						            <spring:message code="cash_payment"/>
    						        </option>
    						        <option value="1">
    						            <spring:message code="card_payment"/>
    						        </option>
    					        </select>
    				        </div>
    				        <!--CONFIRM PURCHASE BUTTON-->
    				        <div class="input-group mb-3">
                                <input type="hidden" name="_method" value="POST">
                                <button type="submit" class="btn btn-primary">
                                    <spring:message code="complete_purchase"/>
                                </button>
                            </div>
                        </form>
                        <form action="${pageContext.request.contextPath}/cart" method="post">
                            <!--FLUSH CART BUTTON-->
                            <div class="input-group mb-3">
                                <input type="hidden" name="_method" value="DELETE">
                                <button type="submit" class="btn btn-secondary" data-dismiss="modal">
                                    <spring:message code="flush_cart"/>
                                </button>
                            </div>
                        </form>
                    </div>
                </c:when>
            </c:choose>
        </div>

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	</body>
</html>
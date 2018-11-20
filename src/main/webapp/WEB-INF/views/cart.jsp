<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/error.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

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
	    <!--TOP SIDE BAR-->
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
	        <security:authorize access="hasRole('ACTIVE')">
	            <div class="alert alert-warning" role="alert">
                    <div class="input-group mb-3">
                        <h4 class="modal-title">
                            <spring:message code="personal_data_label"/>
                        </h4>
                    </div>
                    <div class="input-group mb-3">
                        <h5><spring:message code="user_name_label"/>: ${user.userName}</h5>
                    </div>
                    <div class="input-group mb-3">
                        <h5><spring:message code="email_address_label"/>: ${user.email}</h5>
                    </div>
                </div>
            </security:authorize>
	    </div>

	    <!--CENTRAL BAR-->
		<div class="main-bar">

		    <!--LIST OF PRODUCTS IN THE CART-->
            <div class="modal-body">
                <c:choose>
                    <c:when test="${empty sessionScope.cart or sessionScope.cart.productInCartViewDtoList.size() == 0}">
                        <div class="form-group">
                            <h6><spring:message code="empty_purchase_list"/></h6>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="productInCart" items="${sessionScope.cart.productInCartViewDtoList}">
                            <c:set var="product" scope="page" value="${productInCart.productViewDto}"/>
                            <c:set var="count" scope="page" value="${productInCart.count}"/>
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">${product.name}</span>
                                </div>
                                <div class="input-group-prepend">
                                    <span class="input-group-text">$</span>
                                </div>
                                <input id="priceFor${product.id}" name="priceFor${product.id}" readonly type="text" value="${product.price}" class="form-control">
                                <div class="input-group-prepend">
                                    <span class="input-group-text"><spring:message code="product_count"/>:</span>
                                </div>
                                <input id="countFor${product.id}" name="countFor${product.id}" readonly type="text" value="${count}" class="form-control">
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>

            <c:if test="${not empty sessionScope.cart and sessionScope.cart.productInCartViewDtoList.size() > 0}">
                <div class="modal-body">

                    <!--AVAILABLE FOR ACTIVE STATE USER-->
	                <security:authorize access="hasRole('ACTIVE')">
                        <form action="${pageContext.request.contextPath}/profile/order" modelAttribute="order" method="POST">

                            <!--USER ID-->
                            <input type="hidden" name="userId" value="${user.id}"/>

                            <!--DELIVERY ADDRESS-->
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="deliveryAddressLabel"><spring:message code="address_label"/></span>
                                </div>
                                <input id="address" name="address" type="text" class="form-control" aria-describedby=="deliveryAddressLabel">
                            </div

                            <!--PAYMENT CONDITIONS-->
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="paymentConditionsLabel"><spring:message code="payment_conditions"/></span>
                                </div>
    					        <select id="paymentConditions" name="paymentConditionsIndex" class="custom-select" aria-describedby=="paymentConditionsLabel">
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
                                <button type="submit" class="btn btn-primary">
                                    <spring:message code="complete_purchase"/>
                                </button>
                            </div>

                        </form>
                    </security:authorize>

                    <!--LOGIN BUTTON-->
                    <security:authorize access="!isAuthenticated()">
                        <form action="${pageContext.request.contextPath}/login" method="GET">
                            <div class="input-group mb-3">
                                <button type="submit" class="btn btn-outline-success" data-toggle="modal">
                                    <spring:message code="login"/>
                                </button>
                            </div>
                        </form>
                    </security:authorize>

                    <!--FLUSH CART BUTTON-->
                    <form action="${pageContext.request.contextPath}/cart/flush" method="post">
                        <div class="input-group mb-3">
                            <button type="submit" class="btn btn-secondary" data-dismiss="modal">
                                <spring:message code="flush_cart"/>
                            </button>
                        </div>
                    </form>

                </div>
            </c:if>
        </div>

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	</body>
</html>
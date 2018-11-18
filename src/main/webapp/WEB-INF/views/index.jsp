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

                <!--AVAILABLE FOR NOT AUTHENTICATED USERS AND USERS WITH ACTIVE STATE-->
                <security:authorize access="hasRole('ACTIVE') or !isAuthenticated()">

                    <!--CART BUTTON-->
                    <form action="${pageContext.request.contextPath}/cart" method="GET">
                        <button type="submit" class="btn btn-outline-warning" data-toggle="modal">
                            <spring:message code="cart_label"/>
                        </button>
                    </form>

                </security:authorize>

                <!--NOT AUTHENTICATED USERS BUTTON BAR-->
                <security:authorize access="!isAuthenticated()">

                    <!--LOGIN BUTTON-->
                    <form action="${pageContext.request.contextPath}/login" method="GET">
                        <button type="submit" class="btn btn-outline-success" data-toggle="modal">
                            <spring:message code="login"/>
                        </button>
                    </form>

                    <!--REGISTER BUTTON-->
                    <!-- Button trigger modal -->
                    <button type="button" class="btn btn-outline-danger" data-toggle="modal" data-target="#modalCenter-Register">
                        <spring:message code="register"/>
                    </button>
                    <!-- Modal -->
                    <div class="modal fade" id="modalCenter-Register" tabindex="-1" role="dialog" aria-labelledby="modalCenterTitle" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">
                                        <spring:message code="register_form"/>
                                    </h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <form action="${pageContext.request.contextPath}/user" method="POST">
                                    <div class="modal-body">
                                        <div class="form-group">
                                            <label for="registerUserName">
                                                <spring:message code="user_name_label"/>
                                            </label>
                                            <input type="text" name="user_name" class="form-control" id="registerUserName" placeholder="Enter user name">
                                        </div>
                                        <div class="form-group">
                                            <label for="registerEmail">
                                                <spring:message code="email_address_label"/>
                                            </label>
                                            <input type="text" name="user_email" class="form-control" id="registerEmail" aria-describedby="emailHelp" placeholder="Enter email">
                                            <small id="emailHelp" class="form-text text-muted">
                                                <spring:message code="our_promise"/>
                                            </small>
                                        </div>
                                        <div class="form-group">
                                            <label for="registerPassword">
                                                <spring:message code="password_label"/>
                                            </label>
                                            <input type="password" name="user_password" class="form-control" id="registerPassword" placeholder="Password">
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" class="btn btn-primary">
                                            <spring:message code="register"/>
                                        </button>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                            <spring:message code="close"/>
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                </security:authorize>

                <!--ADMINISTRATOR BUTTON BAR. Available for administrators-->
                <security:authorize access="hasRole('ADMIN')">

                    <!--USERS BUTTON-->
                    <form action="${pageContext.request.contextPath}/user" method="GET">
                        <button type="submit" class="btn btn-outline-primary" data-toggle="modal">
                            <spring:message code="users_label"/>
                        </button>
                    </form>

                    <!--ORDERS BUTTON-->
                    <form action="${pageContext.request.contextPath}/order" method="GET">
                        <button type="submit" class="btn btn-outline-danger" data-toggle="modal">
                            <spring:message code="orders_label"/>
                        </button>
                    </form>

                    <!--ADD NEW PRODUCT BUTTON-->
                    <!-- Button trigger modal -->
                    <button type="button" class="btn btn-outline-warning" data-toggle="modal" data-target="#modalCenter-AddProduct">
                        <spring:message code="add_new_product"/>
                    </button>
                    <!-- Modal -->
                    <div class="modal fade" id="modalCenter-AddProduct" tabindex="-1" role="dialog" aria-labelledby="modalCenterTitle" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">
                                        <spring:message code="add_product_form"/>
                                    </h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <form action="${pageContext.request.contextPath}/product" method="POST">
                                    <div class="modal-body">
                                        <label>
                                            <spring:message code="product_label"/>
                                        </label>
                                        <div class="input-group mb-3">
                                            <input name="productName" type="text" class="form-control">
                                        </div>
                                        <label>
                                            <spring:message code="category_label"/>
                                        </label>
                                        <div class="input-group mb-3">
                                            <select name="parentCategoryId" class="custom-select">
                                                <c:forEach var="parentCategory" items="${categoryList}">
                                                    <option value=${parentCategory.id}>${parentCategory.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label>
                                            <spring:message code="subcategory_label"/>
                                        </label>
                                        <div class="input-group mb-3">
                                            <select name="categoryId" class="custom-select">
                                                <c:forEach var="category" items="${subcategoryList}">
                                                    <option value=${category.id}>${category.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label>
                                            <spring:message code="manufacturer_label"/>
                                        </label>
                                        <div class="input-group mb-3">
                                            <select name="manufacturerId" class="custom-select">
                                                <c:forEach var="manufacturer" items="${manufacturerList}">
                                                    <option value=${manufacturer.id}>${manufacturer.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label>
                                            <spring:message code="price_label"/>
                                        </label>
                                        <div class="input-group mb-3">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text">$</span>
                                            </div>
                                            <input name="price" type="text" class="form-control" aria-label="Amount (to the nearest dollar)">
                                        </div>
                                        <label>
                                            <spring:message code="description_label"/>
                                        </label>
                                        <div class="input-group mb-3">
                                            <input name="productDescription" type="text" class="form-control">
                                        </div>
                                        <div class="input-group mb-3">
                                            <label>
                                                <input name="available" type="checkbox">
                                                <spring:message code="available_label"/>
                                            </label>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" class="btn btn-primary">
                                            <spring:message code="save_label"/>
                                        </button>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                            <spring:message code="close"/>
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <!--ADD NEW CATEGORY BUTTON-->
                    <!-- Button trigger modal -->
                    <button type="button" class="btn btn-outline-warning" data-toggle="modal" data-target="#modalCenter-AddCategory">
                        <spring:message code="add_new_category"/>
                    </button>
                    <!-- Modal -->
                    <div class="modal fade" id="modalCenter-AddCategory" tabindex="-1" role="dialog" aria-labelledby="modalCenterTitle" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">
                                        <spring:message code="add_category_form"/>
                                    </h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <form action="${pageContext.request.contextPath}/category" method="POST">
                                    <div class="modal-body">
                                        <label>
                                            <spring:message code="category_name_label"/>
                                        </label>
                                        <div class="input-group mb-3">
                                            <input name="categoryName" type="text" class="form-control">
                                        </div>
                                        <label>
                                            <spring:message code="parent_category_label"/>
                                        </label>
                                        <div class="input-group mb-3">
                                            <select name="categoryId" class="custom-select">
                                                <option value="0"><spring:message code="no_parent_label"/></option>
                                                <c:forEach var="category" items="${categoryList}">
                                                <option value=${category.id}>${category.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" class="btn btn-primary">
                                            <spring:message code="save_label"/>
                                        </button>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                            <spring:message code="close"/>
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <!--ADD NEW MANUFACTURER BUTTON-->
                    <!-- Button trigger modal -->
                    <button type="button" class="btn btn-outline-warning" data-toggle="modal" data-target="#modalCenter-AddManufacturer">
                        <spring:message code="add_new_manufacturer"/>
                    </button>
                    <!-- Modal -->
                    <div class="modal fade" id="modalCenter-AddManufacturer" tabindex="-1" role="dialog" aria-labelledby="modalCenterTitle" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">
                                        <spring:message code="add_manufacturer_form"/>
                                    </h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <form action="${pageContext.request.contextPath}/manufacturer" method="POST">
                                    <div class="modal-body">
                                        <label>
                                            <spring:message code="manufacturer_name_label"/>
                                        </label>
                                        <div class="input-group mb-3">
                                            <input name="manufacturerName" type="text" class="form-control">
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" class="btn btn-primary">
                                            <spring:message code="save_label"/>
                                        </button>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                            <spring:message code="close"/>
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                </security:authorize>

                <!--AUTHENTICATED USERS BUTTON BAR-->
                <!--Available for authenticated users-->
                <security:authorize access="hasAnyRole('ADMIN', 'ACTIVE', 'BLOCKED')">

                    <!--LOGOUT BUTTON-->
                    <form action="${pageContext.request.contextPath}/logout" method="POST">
                        <button type="submit" class="btn btn-outline-success">
                            <spring:message code="logout"/>
                        </button>
                    </form>

                    <!--PROFILE BUTTON-->
                    <form action="${pageContext.request.contextPath}/profile" method="GET">
                        <button type="submit" class="btn btn-outline-danger">
                            <spring:message code="profile_label"/>
                        </button>
                    </form>

                </security:authorize>

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

        <!--LEFT SIDE BAR. For product searching-->
    	<div class="left-side-bar">
    	    <fieldset>
        	    <legend class="bar-legend">
        	        <spring:message code="search_products"/>
        	    </legend>
        	    <form action="${pageContext.request.contextPath}/product" method="GET">
        	        <label>
        	            <spring:message code="category_label"/>
        	        </label>
        	        <div class="input-group mb-3">
                        <select id="searchCategoryId" name="categoryId" class="custom-select">
                            <option selected
                                <c:if test="${sessionScope.searchParams.categoryId == 0}">
                                    selected
                                </c:if>
                            value="0"><spring:message code="all"/></option>
                            <c:forEach var="category" items="${categoryList}">
                                <option
                                    <c:if test="${sessionScope.searchParams.categoryId == category.id}">
                                        selected
                                    </c:if>
                                value=${category.id}>${category.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <label>
                        <spring:message code="subcategory_label"/>
                    </label>
                    <div class="input-group mb-3">
                        <select id="searchSubcategoryId" name="subcategoryId" class="custom-select">
                            <option
                                <c:if test="${sessionScope.searchParams.subcategoryId == 0}">
                                    selected
                                </c:if>
                            value="0"><spring:message code="all"/></option>
                            <c:forEach var="subcategory" items="${subcategoryList}">
                                <option
                                    <c:if test="${sessionScope.searchParams.subcategoryId == subcategory.id}">
                                        selected
                                    </c:if>
                                value=${subcategory.id}>${subcategory.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <label>
                        <spring:message code="manufacturer_label"/>
                    </label>
                    <div class="input-group mb-3">
                        <select id="searchManufacturerId" name="manufacturerId" class="custom-select">
                            <option
                                <c:if test="${sessionScope.searchParams.product.manufacturerId == 0}">
                                    selected
                                </c:if>
                            value="0"><spring:message code="all"/></option>
                            <c:forEach var="manufacturer" items="${manufacturerList}">
                                <option
                                    <c:if test="${sessionScope.searchParams.product.manufacturerId == manufacturer.id}">
                                        selected
                                    </c:if>
                                value=${manufacturer.id}>${manufacturer.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <label>
                        <spring:message code="sort_by_label"/>
                    </label>
    				<div class="input-group mb-3">
    					<select id="searchSortByParam" name="sortByParam" class="custom-select">
    						<option
                                <c:if test="${sessionScope.searchParams.orderMethod == 'CHEAP_FIRST'}">
                                    selected
                                </c:if>
    						value="0"><spring:message code="cheap_first"/></option>
    						<option
                                <c:if test="${sessionScope.searchParams.orderMethod == 'CHEAP_LAST'}">
                                    selected
                                </c:if>
    						value="1"><spring:message code="expensive_first"/></option>
    						<option
                                <c:if test="${sessionScope.searchParams.orderMethod == 'PRODUCT_NAME'}">
                                    selected
                                </c:if>
    						value="2"><spring:message code="alphabetical_order"/></option>
    					</select>
    				</div>
    				<label>
    				    <spring:message code="price_range_label"/>
                    </label>
    				<div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text">$</span>
                        </div>
                        <input id="searchMinPrice" name="minPrice" type="text" value="${sessionScope.searchParams.minPrice}" class="form-control" aria-label="Amount (to the nearest dollar)" placeholder="min price">
                        <div class="input-group-prepend">
                            <span class="input-group-text">$</span>
                        </div>
                        <input id="searchMaxPrice" name="maxPrice" type="text" value="${sessionScope.searchParams.maxPrice}" class="form-control" aria-label="Amount (to the nearest dollar)" placeholder="max price">
                    </div>
    				<div class="input-group mb-3">
    					<label>
    						<input
    						    <c:if test="${sessionScope.searchParams.product.available == true}">
    						        checked
    						    </c:if>
    						 name="available" type="checkbox">
    						<spring:message code="only_available"/>
    					</label>
    				</div>
    				<div id="applyFilters" class="input-group mb-3">
    				    <button type="submit" id="buttonApplyFilters" name="applyFilters" class="btn btn-outline-primary btn-block">
    				        <spring:message code="apply_filters"/>
    				    </button>
    				</div>
    			</form>
                <form action="${pageContext.request.contextPath}/product/default" method="GET">
                    <div class="input-group mb-3">
                        <button type="submit" id="buttonResetFilters" name="resetFilters" class="btn btn-outline-secondary btn-block">
                            <spring:message code="reset_filters"/>
                        </button>
                    </div>
                </form>
    		</fieldset>
    	</div>

        <!--MAIN BAR-->
        <div class="main-bar">
            <div class="accordion" id="productListWrapper">
                <c:forEach var="product" items="${productList}">

                    <!--AVAILABLE FOR NOT AUTHENTICATED USERS AND USERS WITH ACTIVE STATE-->
                    <security:authorize access="!isAuthenticated() or hasRole('ACTIVE')">
                        <form action="${pageContext.request.contextPath}/cart/${product.id}" method="POST">
                            <div class="card">
                                <div class="card-header" id="headingOne${product.id}">
                                    <h5 class="mb-0">
                                        <button class="btn btn-link collapsed" type="button" data-toggle="collapse" data-target="#collapseOne${product.id}" aria-expanded="true" aria-controls="collapseOne${product.id}">
                                            <div align="left">
                                                <h5>${product.name}</h5>
                                                <h5><spring:message code="price_label"/>: ${product.price}</h5>
                                                <h5><spring:message code="available_label"/>:
                                                    <c:choose>
                                                        <c:when test="${product.available}">
                                                            <spring:message code="yes_label"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <spring:message code="no_label"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </h5>
                                            </div>
                                            <div align="right">
                                                <button type="submit" class="btn btn-primary">
                                                    <spring:message code="buy_label"/>
                                                </button>
                                            </div>
                                        </button>
                                    </h5>
                                </div>
                                <div id="collapseOne${product.id}" class="collapse" aria-labelledby="headingOne${product.id}" data-parent="#productListWrapper">
                                    <div class="card-body">
                                        <h6><b><spring:message code="description_label"/>:</b> ${product.description}</h6>
                                        <h6><b><spring:message code="category_label"/>:</b> ${product.categoryName}</h6>
                                        <h6><b><spring:message code="manufacturer_label"/>:</b> ${product.manufacturerName}</h6>
                                        <div class="form-group">
                                            <label for="exampleFormControlSelect1">
                                                <spring:message code="product_count"/>
                                            </label>
                                            <select class="form-control" name="count">
                                                <option value="1">1</option>
                                                <option value="2">2</option>
                                                <option value="3">3</option>
                                                <option value="4">4</option>
                                                <option value="5">5</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            <!--</div>-->
                        </form>
                    </security:authorize>

                    <!--AVAILABLE FOR USERS WITH STATE ADMIN-->
                    <security:authorize access="hasRole('ADMIN')">
                        <div class="alert alert-primary" role="alert">
                            <form action="${pageContext.request.contextPath}/product/${product.id}" method="POST">
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text"><spring:message code="product_label"/></span>
                                    </div>
                                    <input id="editProductName" name="productName" type="text" value="${product.name}" class="form-control">
                                </div>
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text"><spring:message code="category_label"/></span>
                                    </div>
                                    <select id="editCategoryId" name="categoryId" class="custom-select">
                                        <c:forEach var="category" items="${subcategoryList}">
                                            <option
                                                <c:if test="${product.categoryId == category.id}">
                                                    selected
                                                </c:if>
                                            value=${category.id}>${category.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text"><spring:message code="manufacturer_label"/></span>
                                    </div>
                                    <select id="editManufacturerId" name="manufacturerId" class="custom-select">
                                        <c:forEach var="manufacturer" items="${manufacturerList}">
                                            <option
                                                <c:if test="${product.manufacturerId == manufacturer.id}">
                                                    selected
                                                </c:if>
                                            value=${manufacturer.id}>${manufacturer.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text"><spring:message code="price_label"/></span>
                                    </div>
                                    <div class="input-group-prepend">
                                        <span class="input-group-text">$</span>
                                    </div>
                                    <input id="editPrice" name="price" type="text" value="${product.price}" class="form-control" aria-label="Amount (to the nearest dollar)" placeholder="price">
                                </div>
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text"><spring:message code="description_label"/></span>
                                    </div>
                                    <input id="editProductDescription" name="productDescription" type="text" value="${product.description}" class="form-control">
                                </div>
                                <div class="input-group mb-3">
                                    <label>
                                        <c:choose>
                                            <c:when test="${product.available}">
                                                <input checked name="available" type="checkbox">
                                            </c:when>
                                            <c:otherwise>
                                                <input name="available" type="checkbox">
                                            </c:otherwise>
                                        </c:choose>
                                        <spring:message code="available_label"/>
                                    </label>
                                </div>
                                <div id="editProduct" class="input-group mb-3">
                                    <button type="submit" id="buttonEditProduct" name="editProduct" class="btn btn-outline-primary btn-block">
                                        <spring:message code="save_changes"/>
                                    </button>
                                </div>
                            </form>
                            <form action="${pageContext.request.contextPath}/product/delete/${product.id}" method="POST">
                                <div class="input-group mb-3">
                                    <button type="submit" id="buttonDeleteProduct" name="deleteProduct" class="btn btn-outline-secondary btn-block">
                                        <spring:message code="delete_label"/>
                                    </button>
                                </div>
                            </form>
                        </div>
                    </security:authorize>

                </c:forEach>
            </div>
        </div>

	    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	</body>
</html>
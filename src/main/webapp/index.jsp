<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/error.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/functions.tld" prefix="fn"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html: charset=utf-8">
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
		<title>Online shop</title>
		<link href="resources/css/index_styles.css" type="text/css" rel="stylesheet">
	</head>
	<body>
	    <!--CONFIGURING RESOURCEBUNDLE-->
    	<c:choose>
            <c:when test="${empty sessionScope.locale or sessionScope.locale.language == 'en'}">
                <c:set var="lang" scope="session"
                        value="${fn:getBundleByLanguage('en')}"/>
            </c:when>
            <c:otherwise>
                <c:set var="lang" scope="session"
                        value="${fn:getBundleByLanguage('ru')}"/>
            </c:otherwise>
        </c:choose>

        <!--TOP SIDE BAR-->
	    <nav class="navbar navbar-light bg-light">
	        <a class="navbar-brand">
	            <c:out value="${sessionScope.lang.getString('online_shop_title')}"/>
	        </a>
            <div class="btn-group" role="group" aria-label="Basic example">
                <c:choose>
                    <c:when test="${empty sessionScope.user or sessionScope.user.state == 1}">
                        <!--CART BUTTON. Available only for unsigned users or for signed up users with ACTIVE state-->
                        <form action="${pageContext.request.contextPath}/cart" method="GET">
                            <button type="submit" class="btn btn-outline-warning" data-toggle="modal">
                                <c:out value="${sessionScope.lang.getString('cart_label')}"/>
                            </button>
                        </form>
                    </c:when>
                    <c:when test="${sessionScope.user.state == 0}">
                         <!--USERS BUTTON. Available only for administrator-->
                        <form action="${pageContext.request.contextPath}/user" method="GET">
                            <button type="submit" class="btn btn-outline-primary" data-toggle="modal">
                                <c:out value="${sessionScope.lang.getString('users_label')}"/>
                            </button>
                        </form>
                         <!--ORDERS BUTTON. Available only for administrator-->
                        <form action="${pageContext.request.contextPath}/order" method="GET">
                            <button type="submit" class="btn btn-outline-danger" data-toggle="modal">
                                <input type="hidden" name="_method" value="GET_ALL">
                                <c:out value="${sessionScope.lang.getString('orders_label')}"/>
                            </button>
                        </form>
                        <!--ADD NEW PRODUCT BUTTON. Available only for administrator-->
                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-outline-warning" data-toggle="modal" data-target="#modalCenter-AddProduct">
                            <c:out value="${sessionScope.lang.getString('add_new_product')}"/>
                        </button>
                        <!-- Modal -->
                        <div class="modal fade" id="modalCenter-AddProduct" tabindex="-1" role="dialog" aria-labelledby="modalCenterTitle" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">
                                            <c:out value="${sessionScope.lang.getString('add_product_form')}"/>
                                        </h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <form action="${pageContext.request.contextPath}/product" method="POST">
                                        <div class="modal-body">
                        	                <label>
                        	                    <c:out value="${sessionScope.lang.getString('product_label')}"/>
                        	                </label>
                    				        <div class="input-group mb-3">
                                                <input name="productName" type="text" class="form-control">
                                            </div>
                        	                <label>
                        	                    <c:out value="${sessionScope.lang.getString('category_label')}"/>
                        	                </label>
                        	                <div class="input-group mb-3">
                                                <select name="parentCategoryId" class="custom-select">
                                                    <c:forEach var="parentCategory" items="${categoryList}">
                                                        <option value=${parentCategory.id}>${parentCategory.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                        	                <label>
                        	                    <c:out value="${sessionScope.lang.getString('subcategory_label')}"/>
                        	                </label>
                        	                <div class="input-group mb-3">
                                                <select name="categoryId" class="custom-select">
                                                    <c:forEach var="category" items="${subcategoryList}">
                                                        <option value=${category.id}>${category.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <label>
                                                <c:out value="${sessionScope.lang.getString('manufacturer_label')}"/>
                                            </label>
                                            <div class="input-group mb-3">
                                                <select name="manufacturerId" class="custom-select">
                                                    <c:forEach var="manufacturer" items="${manufacturerList}">
                                                        <option value=${manufacturer.id}>${manufacturer.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                    				        <label>
                                                <c:out value="${sessionScope.lang.getString('price_label')}"/>
                                            </label>
                    				        <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text">$</span>
                                                </div>
                                                <input name="price" type="text" class="form-control" aria-label="Amount (to the nearest dollar)">
                                            </div>
                        	                <label>
                        	                    <c:out value="${sessionScope.lang.getString('description_label')}"/>
                        	                </label>
                    				        <div class="input-group mb-3">
                                                <input name="productDescription" type="text" class="form-control">
                                            </div>
                    				        <div class="input-group mb-3">
                    					        <label>
                    						        <input name="available" type="checkbox">
                    						        <c:out value="${sessionScope.lang.getString('available_label')}"/>
                    					        </label>
                    				        </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="submit" class="btn btn-primary">
                                                <input type="hidden" name="_method" value="POST">
                                                <c:out value="${sessionScope.lang.getString('save_label')}"/>
                                            </button>
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                                <c:out value="${sessionScope.lang.getString('close')}"/>
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <!--ADD NEW CATEGORY BUTTON. Available only for administrator-->
                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-outline-warning" data-toggle="modal" data-target="#modalCenter-AddCategory">
                            <c:out value="${sessionScope.lang.getString('add_new_category')}"/>
                        </button>
                        <!-- Modal -->
                        <div class="modal fade" id="modalCenter-AddCategory" tabindex="-1" role="dialog" aria-labelledby="modalCenterTitle" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">
                                            <c:out value="${sessionScope.lang.getString('add_category_form')}"/>
                                        </h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <form action="${pageContext.request.contextPath}/category" method="POST">
                                        <div class="modal-body">
                        	                <label>
                        	                    <c:out value="${sessionScope.lang.getString('category_name_label')}"/>
                        	                </label>
                    				        <div class="input-group mb-3">
                                                <input name="categoryName" type="text" class="form-control">
                                            </div>
                        	                <label>
                        	                    <c:out value="${sessionScope.lang.getString('parent_category_label')}"/>
                        	                </label>
                        	                <div class="input-group mb-3">
                                                <select name="categoryId" class="custom-select">
                                                    <option value="0"><c:out value="${sessionScope.lang.getString('no_parent_label')}"/></option>
                                                    <c:forEach var="category" items="${categoryList}">
                                                        <option value=${category.id}>${category.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="submit" class="btn btn-primary">
                                                <input type="hidden" name="_method" value="POST">
                                                <c:out value="${sessionScope.lang.getString('save_label')}"/>
                                            </button>
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                                <c:out value="${sessionScope.lang.getString('close')}"/>
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <!--ADD NEW MANUFACTURER BUTTON. Available only for administrator-->
                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-outline-warning" data-toggle="modal" data-target="#modalCenter-AddManufacturer">
                            <c:out value="${sessionScope.lang.getString('add_new_manufacturer')}"/>
                        </button>
                        <!-- Modal -->
                        <div class="modal fade" id="modalCenter-AddManufacturer" tabindex="-1" role="dialog" aria-labelledby="modalCenterTitle" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">
                                            <c:out value="${sessionScope.lang.getString('add_manufacturer_form')}"/>
                                        </h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <form action="${pageContext.request.contextPath}/manufacturer" method="POST">
                                        <div class="modal-body">
                        	                <label>
                        	                    <c:out value="${sessionScope.lang.getString('manufacturer_name_label')}"/>
                        	                </label>
                    				        <div class="input-group mb-3">
                                                <input name="manufacturerName" type="text" class="form-control">
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="submit" class="btn btn-primary">
                                                <input type="hidden" name="_method" value="POST">
                                                <c:out value="${sessionScope.lang.getString('save_label')}"/>
                                            </button>
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                                <c:out value="${sessionScope.lang.getString('close')}"/>
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:when>
                </c:choose>
                <c:choose>
                    <c:when test="${empty sessionScope.user}">
                        <!--SIGN IN BUTTON. Available only for unsigned users-->
                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-outline-success" data-toggle="modal" data-target="#modalCenter-SignIn">
                            <c:out value="${sessionScope.lang.getString('sign_in')}"/>
                        </button>
                        <!-- Modal -->
                        <div class="modal fade" id="modalCenter-SignIn" tabindex="-1" role="dialog" aria-labelledby="modalCenterTitle" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">
                                            <c:out value="${sessionScope.lang.getString('sign_in_form')}"/>
                                        </h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <form action="${pageContext.request.contextPath}/login" method="POST">
                                        <div class="modal-body">
                                            <div class="form-group">
                                                <label for="loginEmail">
                                                    <c:out value="${sessionScope.lang.getString('email_address_label')}"/>
                                                </label>
                                                <input type="text" name="user_email" class="form-control" id="loginEmail" aria-describedby="emailHelp" placeholder="Enter email">
                                                <small id="emailHelp" class="form-text text-muted">
                                                    <c:out value="${sessionScope.lang.getString('our_promise')}"/>
                                                </small>
                                            </div>
                                            <div class="form-group">
                                                <label for="loginPassword">
                                                    <c:out value="${sessionScope.lang.getString('password_label')}"/>
                                                </label>
                                                <input type="password" name="user_password" class="form-control" id="loginPassword" placeholder="Password">
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="submit" class="btn btn-primary">
                                                <c:out value="${sessionScope.lang.getString('sign_in')}"/>
                                            </button>
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                                <c:out value="${sessionScope.lang.getString('close')}"/>
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <!--REGISTER BUTTON. Available only for unsigned users-->
                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-outline-danger" data-toggle="modal" data-target="#modalCenter-Register">
                            <c:out value="${sessionScope.lang.getString('register')}"/>
                        </button>
                        <!-- Modal -->
                        <div class="modal fade" id="modalCenter-Register" tabindex="-1" role="dialog" aria-labelledby="modalCenterTitle" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">
                                            <c:out value="${sessionScope.lang.getString('register_form')}"/>
                                        </h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <form action="${pageContext.request.contextPath}/register" method="POST">
                                        <div class="modal-body">
                                            <div class="form-group">
                                                <label for="registerUserName">
                                                    <c:out value="${sessionScope.lang.getString('user_name_label')}"/>
                                                </label>
                                                <input type="text" name="user_name" class="form-control" id="registerUserName" placeholder="Enter user name">
                                            </div>
                                            <div class="form-group">
                                                <label for="registerEmail">
                                                    <c:out value="${sessionScope.lang.getString('email_address_label')}"/>
                                                </label>
                                                <input type="text" name="user_email" class="form-control" id="registerEmail" aria-describedby="emailHelp" placeholder="Enter email">
                                                <small id="emailHelp" class="form-text text-muted">
                                                    <c:out value="${sessionScope.lang.getString('our_promise')}"/>
                                                </small>
                                            </div>
                                            <div class="form-group">
                                                <label for="registerPassword">
                                                    <c:out value="${sessionScope.lang.getString('password_label')}"/>
                                                </label>
                                                <input type="password" name="user_password" class="form-control" id="registerPassword" placeholder="Password">
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="submit" class="btn btn-primary">
                                                <c:out value="${sessionScope.lang.getString('register')}"/>
                                            </button>
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">
                                                <c:out value="${sessionScope.lang.getString('close')}"/>
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <!--SIGN OUT BUTTON. Available for signed up users-->
                        <form action="${pageContext.request.contextPath}/signOut" method="POST">
                            <button type="submit" class="btn btn-outline-success">
                                <c:out value="${sessionScope.lang.getString('sign_out')}"/>
                            </button>
                        </form>
                        <!--PROFILE BUTTON. Available for signed up users-->
                        <form action="${pageContext.request.contextPath}/profile" method="get">
                            <button type="submit" class="btn btn-outline-danger">
                                <c:out value="${sessionScope.lang.getString('profile_label')}"/>
                            </button>
                        </form>
                    </c:otherwise>
                </c:choose>
                <!--LANGUAGE BUTTON-->
                <div class="btn-group-vertical">
                    <form action="${pageContext.request.contextPath}/setlanguage" method="post">
                        <c:choose>
                            <c:when test="${not empty sessionScope.locale and sessionScope.locale.language == 'en'}">
                                <button type="submit" href="${pageContext.request.contextPath}/setlanguage" name="rusButton" class="btn btn-danger">Rus</button>
                            </c:when>
                            <c:otherwise>
                                <button type="submit" href="${pageContext.request.contextPath}/setlanguage" name="engButton" class="btn btn-primary">Eng</button>
                            </c:otherwise>
                        </c:choose>
                    </form>
                </div>
            </div>
        </nav>

        <!--LEFT SIDE BAR. For product searching-->
    	<div class="left-side-bar">
    	    <fieldset>
        	    <legend class="bar-legend">
        	        <c:out value="${sessionScope.lang.getString('search_products')}"/>
        	    </legend>
        	    <form action="${pageContext.request.contextPath}/product" method="get">
        	        <label>
        	            <c:out value="${sessionScope.lang.getString('category_label')}"/>
        	        </label>
        	        <div class="input-group mb-3">
                        <select id="searchCategoryId" name="categoryId" class="custom-select">
                            <option selected
                                <c:if test="${sessionScope.searchParams.categoryId == 0}">
                                    selected
                                </c:if>
                            value="0"><c:out value="${sessionScope.lang.getString('all')}"/></option>
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
                        <c:out value="${sessionScope.lang.getString('subcategory_label')}"/>
                    </label>
                    <div class="input-group mb-3">
                        <select id="searchSubcategoryId" name="subcategoryId" class="custom-select">
                            <option
                                <c:if test="${sessionScope.searchParams.subcategoryId == 0}">
                                    selected
                                </c:if>
                            value="0"><c:out value="${sessionScope.lang.getString('all')}"/></option>
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
                        <c:out value="${sessionScope.lang.getString('manufacturer_label')}"/>
                    </label>
                    <div class="input-group mb-3">
                        <select id="searchManufacturerId" name="manufacturerId" class="custom-select">
                            <option
                                <c:if test="${sessionScope.searchParams.product.manufacturerId == 0}">
                                    selected
                                </c:if>
                            value="0"><c:out value="${sessionScope.lang.getString('all')}"/></option>
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
                        <c:out value="${sessionScope.lang.getString('sort_by_label')}"/>
                    </label>
    				<div class="input-group mb-3">
    					<select id="searchSortByParam" name="sortByParam" class="custom-select">
    						<option
                                <c:if test="${sessionScope.searchParams.orderMethod == 'CHEAP_FIRST'}">
                                    selected
                                </c:if>
    						value="0"><c:out value="${sessionScope.lang.getString('cheap_first')}"/></option>
    						<option
                                <c:if test="${sessionScope.searchParams.orderMethod == 'CHEAP_LAST'}">
                                    selected
                                </c:if>
    						value="1"><c:out value="${sessionScope.lang.getString('expensive_first')}"/></option>
    						<option
                                <c:if test="${sessionScope.searchParams.orderMethod == 'PRODUCT_NAME'}">
                                    selected
                                </c:if>
    						value="2"><c:out value="${sessionScope.lang.getString('alphabetical_order')}"/></option>
    					</select>
    				</div>
    				<label>
                        <c:out value="${sessionScope.lang.getString('price_range_label')}"/>
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
    						<c:out value="${sessionScope.lang.getString('only_available')}"/>
    					</label>
    				</div>
    				<div id="applyFilters" class="input-group mb-3">
    				    <button type="submit" id="buttonApplyFilters" name="applyFilters" class="btn btn-outline-primary btn-block">
    				        <c:out value="${sessionScope.lang.getString('apply_filters')}"/>
    				    </button>
    				</div>
    			</form>
                <form action="${pageContext.request.contextPath}/product" method="get">
                    <div class="input-group mb-3">
                        <input type="hidden" name="_method" value="RESET">
                        <button type="submit" id="buttonResetFilters" name="resetFilters" class="btn btn-outline-secondary btn-block">
                            <c:out value="${sessionScope.lang.getString('reset_filters')}"/>
                        </button>
                    </div>
                </form>
    		</fieldset>
    	</div>

        <!--MAIN BAR-->
        <div class="main-bar">
            <div class="accordion" id="productListWrapper">
                <c:forEach var="product" items="${productList}">
                    <c:choose>
                        <c:when test="${empty sessionScope.user or sessionScope.user.state == 1}">
                            <form action="${pageContext.request.contextPath}/cart" method="post">
                                <div class="card">
                                    <div class="card-header" id="headingOne${product.id}">
                                        <h5 class="mb-0">
                                            <button class="btn btn-link collapsed" type="button" data-toggle="collapse" data-target="#collapseOne${product.id}" aria-expanded="true" aria-controls="collapseOne${product.id}">
                                                <div align="left">
                                                    <h5>${product.name}</h5>
                                                    <h5><c:out value="${sessionScope.lang.getString('price_label')}"/>: ${product.price}</h5>
                                                    <h5><c:out value="${sessionScope.lang.getString('available_label')}"/>:
                                                        <c:choose>
                                                            <c:when test="${product.available}">
                                                                <c:out value="${sessionScope.lang.getString('yes_label')}"/>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:out value="${sessionScope.lang.getString('no_label')}"/>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </h5>
                                                </div>
                                                <div align="right">
                                                    <input type="hidden" name="productId" value="${product.id}">
                                                    <input type="hidden" name="_method" value="PUT">
                                                    <button type="submit" class="btn btn-primary">
                                                        <c:out value="${sessionScope.lang.getString('buy_label')}"/>
                                                    </button>
                                                </div>
                                            </button>
                                        </h5>
                                    </div>
                                    <div id="collapseOne${product.id}" class="collapse" aria-labelledby="headingOne${product.id}" data-parent="#productListWrapper">
                                        <div class="card-body">
                                            <h6><b><c:out value="${sessionScope.lang.getString('description_label')}"/>:</b> ${product.description}</h6>
                                            <h6><b><c:out value="${sessionScope.lang.getString('category_label')}"/>:</b> ${product.categoryName}</h6>
                                            <h6><b><c:out value="${sessionScope.lang.getString('manufacturer_label')}"/>:</b> ${product.manufacturerName}</h6>
                                            <div class="form-group">
                                                <label for="exampleFormControlSelect1">
                                                    <c:out value="${sessionScope.lang.getString('product_count')}"/>
                                                </label>
                                                <select class="form-control" name="productCount">
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
                        </c:when>
                        <c:when test="${not empty sessionScope.user and sessionScope.user.state == 0}">
                            <div class="alert alert-primary" role="alert">
                        	    <form action="${pageContext.request.contextPath}/product/${product.id}" method="post">
                    				<div class="input-group mb-3">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><c:out value="${sessionScope.lang.getString('product_label')}"/></span>
                                        </div>
                                        <input id="editProductName" name="productName" type="text" value="${product.name}" class="form-control">
                                    </div>
                        	        <div class="input-group mb-3">
                        	            <div class="input-group-prepend">
                                            <span class="input-group-text"><c:out value="${sessionScope.lang.getString('category_label')}"/></span>
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
                                            <span class="input-group-text"><c:out value="${sessionScope.lang.getString('manufacturer_label')}"/></span>
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
                                            <span class="input-group-text"><c:out value="${sessionScope.lang.getString('price_label')}"/></span>
                                        </div>
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">$</span>
                                        </div>
                                        <input id="editPrice" name="price" type="text" value="${product.price}" class="form-control" aria-label="Amount (to the nearest dollar)" placeholder="price">
                                    </div>
                    				<div class="input-group mb-3">
                        	            <div class="input-group-prepend">
                                            <span class="input-group-text"><c:out value="${sessionScope.lang.getString('description_label')}"/></span>
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
                    						<c:out value="${sessionScope.lang.getString('available_label')}"/>
                    					</label>
                    				</div>
                    				<div id="editProduct" class="input-group mb-3">
                    				    <input type="hidden" name="_method" value="PUT">
                    				    <button type="submit" id="buttonEditProduct" name="editProduct" class="btn btn-outline-primary btn-block">
                    				        <c:out value="${sessionScope.lang.getString('save_changes')}"/>
                    				    </button>
                    				</div>
                    			</form>
                                <form action="${pageContext.request.contextPath}/product/${product.id}" method="post">
                                    <div class="input-group mb-3">
                                        <input type="hidden" name="_method" value="DELETE">
                                        <button type="submit" id="buttonDeleteProduct" name="deleteProduct" class="btn btn-outline-secondary btn-block">
                                            <c:out value="${sessionScope.lang.getString('delete_label')}"/>
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </c:when>
                    </c:choose>
                </c:forEach>
            </div>
        </div>

	    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	</body>
</html>
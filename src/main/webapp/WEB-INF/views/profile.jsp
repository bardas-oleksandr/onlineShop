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
	    <!--NAVIGATION BAR-->
	    <nav class="navbar navbar-light bg-light">
	        <a class="navbar-brand">
	            <spring:message code="online_shop_title"/>
	        </a>
	        <c:choose>
                <c:when test="${sessionScope.user.state == 2}">
                    <div class="btn-group" role="group" aria-label="Basic example">
                        <!--SIGN OUT BUTTON-->
                        <form action="${pageContext.request.contextPath}/signOut" method="POST">
                            <button type="submit" class="btn btn-outline-success" data-toggle="modal" data-target="#modalCenter-MainPage">
                                <spring:message code="sign_out"/>
                            </button>
                        </form>
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
                </c:when>
                <c:otherwise>
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
                </c:otherwise>
            </c:choose>
        </nav>

        <!--Left side bar-->
	    <div class="left-side-bar">
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
	    </div>

	    <!--Central bar-->
		<div class="main-bar">
            <c:choose>
                <c:when test="${sessionScope.user.state == 2}">
                    <div class="message-style">
                        <div class="alert alert-danger" role="alert">
                            <spring:message code="blocked_message"/>
                        </div>
                    </div>
                </c:when>
                <c:when test="${sessionScope.user.state == 1}">
                    <form action="${pageContext.request.contextPath}/order" method="get">
                        <!--SHOW ORDERS BUTTON-->
                        <div class="input-group mb-3">
                            <input type="hidden" name="_method" value="GET_FOR_USER">
                            <button type="submit" class="btn btn-primary" data-dismiss="modal">
                                <spring:message code="watch_orders_list"/>
                            </button>
                        </div>
                    </form>
                </c:when>
                <c:otherwise>
                    <div class="message-style">
                        <div class="alert alert-success" role="alert">
                            <spring:message code="admin_welcome"/>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	</body>
</html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/error.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
	    <!--NAVIGATION BAR-->
	    <nav class="navbar navbar-light bg-light">
	        <a class="navbar-brand">
	            <c:out value="${sessionScope.lang.getString('online_shop_title')}"/>
	        </a>
	        <c:choose>
                <c:when test="${sessionScope.user.state == 2}">
                    <div class="btn-group" role="group" aria-label="Basic example">
                        <!--SIGN OUT BUTTON-->
                        <form action="${pageContext.request.contextPath}/signOut" method="POST">
                            <button type="submit" class="btn btn-outline-success" data-toggle="modal" data-target="#modalCenter-MainPage">
                                <c:out value="${sessionScope.lang.getString('sign_out')}"/>
                            </button>
                        </form>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="btn-group" role="group" aria-label="Basic example">
                        <!--MAIN PAGE BUTTON-->
                        <a href="index.jsp">
                            <button type="button" class="btn btn-outline-warning" data-toggle="modal" data-target="#modalCenter-MainPage">
                                <c:out value="${sessionScope.lang.getString('main_page_title')}"/>
                            </button>
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
        </nav>

        <!--Left side bar-->
	    <div class="left-side-bar">
	        <div class="alert alert-warning" role="alert">
                <div class="input-group mb-3">
                    <h4 class="modal-title">
                        <c:out value="${sessionScope.lang.getString('personal_data_label')}"/>
                    </h5>
                </div>
                <div class="input-group mb-3">
                    <h5><c:out value="${sessionScope.lang.getString('user_name_label')}"/>: ${user.userName}</h5>
                </div>
                <div class="input-group mb-3">
                    <h5><c:out value="${sessionScope.lang.getString('email_address_label')}"/>: ${user.email}</h5>
                </div>
            </div>
	    </div>

	    <!--Central bar-->
		<div class="main-bar">
            <c:choose>
                <c:when test="${sessionScope.user.state == 2}">
                    <div class="message-style">
                        <div class="alert alert-danger" role="alert">
                            <c:out value="${sessionScope.lang.getString('blocked_message')}"/>
                        </div>
                    </div>
                </c:when>
                <c:when test="${sessionScope.user.state == 1}">
                    <form action="${pageContext.request.contextPath}/order" method="get">
                        <!--SHOW ORDERS BUTTON-->
                        <div class="input-group mb-3">
                            <input type="hidden" name="_method" value="GET_FOR_USER">
                            <button type="submit" class="btn btn-primary" data-dismiss="modal">
                                <c:out value="${sessionScope.lang.getString('watch_orders_list')}"/>
                            </button>
                        </div>
                    </form>
                </c:when>
                <c:otherwise>
                    <div class="message-style">
                        <div class="alert alert-success" role="alert">
                            <c:out value="${sessionScope.lang.getString('admin_welcome')}"/>
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
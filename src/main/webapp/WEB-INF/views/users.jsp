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
                    <c:when test="${empty userList}">
                        <div class="input-group mb-3">
                            <h4><spring:message code="empty_user_list"/></h4>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="input-group mb-3">
                            <h4><spring:message code="users_list"/></h4>
                        </div>
                        <div class="accordion" id="orderListWrapper">
                            <c:forEach var="user" items="${userList}">
                                <form action="${pageContext.request.contextPath}/user/${user.id}" modelAttribute="userCreateDto" method="POST">
                                    <div class="alert alert-success" role="alert">
                                        <div class="card">
                                            <div class="card-header">
                                                <h5 class="mb-0">
                                                    <div class="input-group mb-3">
                                                        <span class="input-group-text"><spring:message code="user_name_label"/>: ${user.userName}</span>
                                                        <input type="hidden" name="userName" type="text" value="${user.userName}">
                                                    </div>
                                                    <div class="input-group mb-3">
                                                        <span class="input-group-text"><spring:message code="email_address_label"/>: ${user.email}</span>
                                                        <input type="hidden" name="email" type="text" value="${user.email}">
                                                    </div>
                                                </h5>
                                                <div class="input-group mb-3">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><spring:message code="user_state_label"/>:</span>
                                                    </div>
                                                    <select name="userStateIndex" class="custom-select">
                                                        <option
                                                            <c:if test="${user.state == 0}">
                                                                selected
                                                            </c:if>
                                                        value="0"><spring:message code="state_admin"/></option>
                                                        <option
                                                            <c:if test="${user.state == 1}">
                                                                selected
                                                            </c:if>
                                                        value="1"><spring:message code="state_active"/></option>
                                                        <option
                                                            <c:if test="${user.state == 2}">
                                                                selected
                                                            </c:if>
                                                        value="2"><spring:message code="state_blocked"/></option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="card-body">
                                            <button type="submit" class="btn btn-primary">
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
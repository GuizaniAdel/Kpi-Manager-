<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="stag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml"
  xmlns:th="http://www.thymeleaf.org"> 
<head>
	<title>Reset password</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->	
	<link rel="icon" type="image/png" href="images/icons/favicon.ico"/>
<!--===============================================================================================-->
     <link href="<c:url value="/resources/vendors/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet">
<!--===============================================================================================-->
    <link href="<c:url value="/resources/fonts/font-awesome-4.7.0/css/font-awesome.min.css"/>" rel="stylesheet">
<!--===============================================================================================-->
    <link href="<c:url value="/resources/fonts/Linearicons-Free-v1.0.0/icon-font.min.css"/>" rel="stylesheet">

<!--===============================================================================================-->
    <link href="<c:url value="/resources/vendor/animate/animate.css"/>" rel="stylesheet">

<!--===============================================================================================-->	
 <link href="<c:url value="/resources/vendor/css-hamburgers/hamburgers.min.css"/>" rel="stylesheet">
<!--===============================================================================================-->
 <link href="<c:url value="/resources/vendor/animsition/css/animsition.min.css"/>" rel="stylesheet">

<!--===============================================================================================-->
 <link href="<c:url value="/resources/vendor/select2/select2.min.css"/>" rel="stylesheet">


<!--===============================================================================================-->
 <link href="<c:url value="/resources/css/util.css"/>" rel="stylesheet">
 <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">

<!--===============================================================================================-->
</head>
<body>
	
	<div class="limiter">
		<div class="container-login100" style="background-image: url('resources/images/a.jpg');">
			<div class="wrap-login100 p-t-30 p-b-50">
				<span class="login100-form-title p-b-41">
					Reset Password
				</span>
				<form:form class="login100-form validate-form p-b-33 p-t-5" action="/reset-password" modelAttribute="user" method ="post" >

					<spring:bind path="email">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:input type="email" path="email" class="form-control"
										placeholder="email"></form:input>
							<form:errors path="email"></form:errors>
						</div>
					</spring:bind>
					<spring:bind path="password">
						<div class="form-group ${status.error ? 'has-error' : ''}">
							<form:input type="password" path="password" class="form-control" placeholder="Password"></form:input>
							<form:errors path="password"></form:errors>
						</div>
					</spring:bind>

					<div class="container-login100-form-btn m-t-32">
						<button class="login100-form-btn" type="submit">
							Confirm
						</button>
					</div>
           

				</form:form>
				 
               
                      
               

			</div>
		</div>
	</div>
	

	<div id="dropDownSelect1"></div>
	
<!--===============================================================================================-->
     <script src="<c:url value="/resources/vendor/jquery/jquery-3.2.1.min.js"/>"></script>

<!--===============================================================================================-->
     <script src="<c:url value="/resources/vendor/animsition/js/animsition.min.js"/>"></script>

<!--===============================================================================================-->
     <script src="<c:url value="/resources/vendor/bootstrap/js/popper.js"/>"></script>
     <script src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.min.js"/>"></script>

<!--===============================================================================================-->
     <script src="<c:url value="/resources/vendor/select2/select2.min.js"/>"></script>


<!--===============================================================================================-->
     <script src="<c:url value="/resources/js/main.js"/>"></script>



</body>
</html>
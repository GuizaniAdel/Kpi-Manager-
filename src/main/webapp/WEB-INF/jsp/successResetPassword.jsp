<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="stag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml"
  xmlns:th="http://www.thymeleaf.org"> 
<head>
	<title>Login V16</title>
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
			Password successfully reset. You can now log in with the new credentials.
			</span>

    
                 <a href="/login">Login</a>
        
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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>


<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Login </title>
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
    <!-- PNotify -->
    <link href="<c:url value="/resources/vendors/pnotify/dist/pnotify.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/vendors/pnotify/dist/pnotify.buttons.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/vendors/pnotify/dist/pnotify.nonblock.css"/>" rel="stylesheet">



    <!--===============================================================================================-->
    <link href="<c:url value="/resources/css/util.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">
    <!-- Custom Theme Style -->
    <link href="<c:url value="/resources/build/css/custom.min.css"/>" rel="stylesheet">
    <!--===============================================================================================-->
</head>


<body>

<div class="limiter">
    <div class="container-login100" style="background-image: url('/resources/images/a.jpg');">
        <div class="wrap-login100 p-t-30 p-b-50">
				<span class="login100-form-title p-b-41">
					Account Login
				</span>

    <form method="POST" class="login100-form validate-form p-b-33 p-t-5"  action="${contextPath}/login" class="form-signin">
 <% if (request.getAttribute("error")!=null)
 {
%>
        <div class="alert alert-info alert-dismissible " role="alert">

            <strong>${error}${message}</strong>
        </div>
        <% } %>
        <div class="form-group ${error != null ? 'has-error' : ''}">


            <div class="wrap-input100 validate-input" data-validate = "Enter username">
            <input class="input100" name="username" type="text" class="input100"  placeholder="Username"
                   autofocus="true"/>
                <span class="focus-input100" data-placeholder="&#xe82a;"></span>
            </div>

            <div class="wrap-input100 validate-input" data-validate="Enter password">
            <input name="password" type="password" class="input100"  placeholder="Password"/>
                <span class="focus-input100" data-placeholder="&#xe80f;"></span>
            </div>

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="container-login100-form-btn m-t-32">
                <button class="login100-form-btn" type="submit">
                    Login
                </button>
            </div>
            <h4 class="text-center"><a class="txt2" href="${contextPath}/forgotPassword">Forget Password?</a></h4>
            <h4 class="text-center"><a class="txt2" href="${contextPath}/registration">Register</a></h4>
        </div>

    </form>
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
<!-- Custom Theme Scripts -->
<script src="<c:url value="/resources/build/js/custom.min.js"/>"></script>
<!-- PNotify -->
<script src="<c:url value="/resources/vendors/pnotify/dist/pnotify.js"/>"></script>
<script src="<c:url value="/resources/vendors/pnotify/dist/pnotify.nonblock.js"/>"></script>
<script src="<c:url value="/resources/vendors/pnotify/dist/pnotify.buttons.js"/>"></script>


</body>
</html>
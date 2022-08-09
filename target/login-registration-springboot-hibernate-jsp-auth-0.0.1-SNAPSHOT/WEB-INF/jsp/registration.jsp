<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Register</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--===============================================================================================-->
    <link rel="icon" type="image/png" href="/resources/images/icons/favicon.ico"/>
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
    <div class="container-login100" style="background-image: url('/resources/images/a.jpg');">
        <div class="wrap-login100 p-t-30 p-b-50">
				<span class="login100-form-title p-b-41">
					Create your account
				</span>

    <form:form method="POST" class="login100-form validate-form p-b-33 p-t-5" modelAttribute="userForm" >

        <spring:bind path="username">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <div class="wrap-input100 validate-input" data-validate = "Enter username">
                <form:input type="text" path="username" class="input100" placeholder="Username"
                            autofocus="true"></form:input>
                <form:errors path="username"></form:errors>
                    <span class="focus-input100" data-placeholder="&#xe82a;"></span>
                </div>
            </div>
        </spring:bind>

        <spring:bind path="password">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <div class="wrap-input100 validate-input" data-validate="Enter password">
                <form:input type="password" path="password" class="input100" placeholder="Password"></form:input>
                <form:errors path="password"></form:errors>
                    <span class="focus-input100" data-placeholder="&#xe80f;"></span>
                </div>
            </div>
        </spring:bind>

        <spring:bind path="passwordConfirm">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <div class="wrap-input100 validate-input" data-validate="Enter confirm password">
                <form:input type="password" path="passwordConfirm" class="input100"
                            placeholder="Confirm your password"></form:input>
                <form:errors path="passwordConfirm"></form:errors>
                    <span class="focus-input100" data-placeholder="&#xe80f;"></span>

                </div>
            </div>
        </spring:bind>
        <spring:bind path="email">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <div class="wrap-input100 validate-input" data-validate = "Enter mail">
                <form:input type="email" path="email" class="input100"
                            placeholder="email"></form:input>
                <form:errors path="email"></form:errors>
                    <span class="focus-input100" data-placeholder="&#xe818;"></span>
                </div>
            </div>
        </spring:bind>

        <div class="container-login100-form-btn m-t-32">
            <button class="login100-form-btn" type="submit">
                Register
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
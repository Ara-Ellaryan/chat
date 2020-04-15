<%@ page import="am.home.chat.utils.DataValidator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" type="text/css" href="/css/header.css">
    <link rel="stylesheet" type="text/css" href="/css/login-register.css">
    <link rel="stylesheet" type="text/css" href="/css/footer.css">
    <script type="text/javascript" src="/js/jQuery.js"></script>
    <script type="text/javascript" src="/js/login-register.js"></script>
</head>
<body>
<div class="container">
    <header class="header">
        <div class="header-wrapper">
            <div class="logo">
                <span>Logo</span>
            </div>
        </div>
    </header>
    <main class="main">
        <div class="main-login-reg">
            <c:if test="${requestScope.globalError != null}">
                <p class="global-error">Server can't process your request, Please try again!</p>
            </c:if>
            <form id="register-form" class="form" method="post" action="/register" enctype="multipart/form-data">
                <label for="input-name" class="label">FirstName:</label>
                <span id="input-name-span" class="error">
                    <c:out value="${requestScope.errorName ? '' : requestScope.errorName}"/>
                </span><br>
                <input id="input-name" class="input" type="text" name="name" placeholder="for ex. Jhon"
                       value="<c:out value="${DataValidator.isNullOrBlank(requestScope.name) ? '' : requestScope.name}" />"><br>

                <label for="input-surname" class="label">LastName:</label>
                <span id="input-surname-span" class="error">
                    <c:out value="${requestScope.errorSurname == null ? '' : requestScope.errorSurname}"/>
                </span><br>
                <input id="input-surname" class="input" type="text" name="surname" placeholder="for ex. Smith"
                       value="<c:out value="${DataValidator.isNullOrBlank(requestScope.surname) ? '' : requestScope.surname}"/>"><br>

                <label for="input-email" class="label">Email:</label>
                <span id="input-email-span" class="error">
                    <c:out value="${requestScope.errorEmail == null ? '' : requestScope.errorEmail}"/>
                </span><br>
                <input id="input-email" class="input" type="text" name="email"  placeholder="for ex. JhonSmith@email.com"
                       value="<c:out value="${DataValidator.isNullOrBlank(requestScope.email) ? '' : requestScope.email}"/>"><br>

                <label for="input-password" class="label">Password:</label>
                <span id="input-password-span" class="error">
                    <c:out value="${requestScope.errorPassword == null ? '' : requestScope.errorPassword}"/>
                </span><br>
                <input id="input-password" class="input" type="password" name="password" placeholder="min 8 character"><br>

                <label for="input-confirmPassword" class="label">Confirm password:</label>
                <span id="input-confirmPassword-span" class="error">
                    <c:out value="${requestScope.errorConfirmPassword == null ? '' : requestScope.errorConfirmPassword}"/>
                </span><br>
                <input id="input-confirmPassword" class="input" type="password" name="confirmPassword"><br>

                <label for="input-file" class="input-file-label">Choose profile picture</label>
                <input id="input-file" type="file" name="file" accept="image/*"><br>

                <button id="form-button" onclick="doRegister()">Creat</button>
                <span class="error">
                    <c:out value="${requestScope.userExist == null ? '' : requestScope.userExist}"/>
                </span>
                <a class="navto" href="/login">-LOG IN-</a>
            </form>
        </div>
    </main>
    <footer class="footer">
        <span>&copyAra Ellaryan</span>
    </footer>
</div>
</body>
</html>

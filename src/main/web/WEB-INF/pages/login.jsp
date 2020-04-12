<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="/css/header.css">
    <link rel="stylesheet" type="text/css" href="/css/login-register.css">
    <link rel="stylesheet" type="text/css" href="/css/footer.css">
    <script type="text/javascript" src="/js/jQuery.js"></script>
    <script type="text/javascript" src="/js/login-register.js"></script>
</head>
<body>
<div class="container">
    <header class="header">
        <div class="headerWrapper">
            <div class="logo">
                <span>Logo</span>
            </div>
        </div>
    </header>
    <main class="main">
        <div class="main-login-reg">
            <form id="login-form" class="form login" method="post" action="/login">
                <span class="error"><c:out value="${requestScope.wrongEmailPassword == null ? '' : requestScope.wrongEmailPassword}"/></span>
                <label for="input-email" class="label">Email:</label>
                <span id="input-email-span" class="error"><c:out value="${requestScope.errorEmail == null ? '' : requestScope.errorEmail}"/></span><br>
                <input id="input-email" class="input" type="text" name="email" value="" ><br>

                <label for="input-password" class="label">Password:</label>
                <span id="input-password-span" class="error"><c:out value="${requestScope.errorPassword == null ? '' : requestScope.errorPassword}"/></span><br>
                <input id="input-password" class="input" type="password" name="password"><br>

                <button id="submit" onclick="doLogin()">Login</button>
                <a class="navto" href="/register">-Create an account-</a>
            </form>
        </div>
    </main>
    <footer class="footer">
        <span>&copyAra Ellaryan</span>
    </footer>
</div>
</body>
</html>

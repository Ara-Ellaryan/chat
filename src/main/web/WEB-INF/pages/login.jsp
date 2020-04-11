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
            <form class="form" method="post" action="/registration">
                <label>Email:</label><br>
                <span class="error"></span><br>
                <input type="text" name="email" value=""><br>

                <label>Password:</label><br>
                <span class="error"></span><br>
                <input type="password" name="password"><br>

                <button id="submit" onclick="">Registration</button>
                <a class="navto" href="/login">Login</a>
            </form>
        </div>
    </main>
    <footer class="footer">
        <span>&copyAra Ellaryan</span>
    </footer>
</div>
</body>
</html>

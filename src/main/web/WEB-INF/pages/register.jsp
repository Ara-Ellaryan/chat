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
        <div class="headerWrapper">
            <div class="logo">
                <span>Logo</span>
            </div>
        </div>
    </header>
    <main class="main">
        <div class="main-login-reg">
            <form method="post" action="/registration" class="form">
                <label>FirstName:</label><br>
                <span class="error"></span><br>
                <input class="input" type="text" name="firstName" value="" placeholder="for ex. Jhon" required><br>

                <label>LastName:</label><br>
                <span class="error"></span><br>
                <input class="input" type="text" name="lastName" value="" placeholder="for ex. Smith" required><br>

                <label>Email:</label><br>
                <span class="error"></span><br>
                <input class="input" type="text" name="email" value="" placeholder="for ex. JhonSmith@email.com" required><br>

                <label>Password:</label><br>
                <span class="error"></span><br>
                <input class="input" type="password" name="password" placeholder="min 8 character" required><br>

                <label>Confirm password:</label><br>
                <span class="error"></span><br>
                <input class="input" type="password" name="confirmPassword" placeholder="confirm" required><br>

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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" type="text/css" href="/css/header.css">
    <link rel="stylesheet" type="text/css" href="/css/chat.css">
    <link rel="stylesheet" type="text/css" href="/css/footer.css">
    <script type="text/javascript" src="/js/jQuery.js"></script>
    <script type="text/javascript" src="/js/chat.js"></script>
</head>
<body>
<div class="container">
    <header class="header">
        <div class="header-wrapper">
            <div class="logo">
                <span>Logo</span>
            </div>
            <div class="nav">
                <span id="current-user"><c:out value="${sessionScope.user.name.concat(' ').concat(sessionScope.user.surname)}"/></span>
                <a id="logout" href="/logout">logout</a>
            </div>
        </div>
    </header>
    <main class="main">girw
        <div class="main-wrapper">
            <aside class="aside">
                <div id = "search-form">
                    <input id="user-search" type="text" name="email"  placeholder="ex. jhon@mail.ru">
                    <button id="search-button" onclick="userSearch()">search</button>
                    <span id="search-error"></span>
                </div>
                <div id="find-user">
                    <span class="friend"></span>
                </div>
                <ul class="user-list">
                    <li id="">
                        <span class="friend " onclick=""></span>
                    </li>
                </ul>
            </aside>
            <section class="section">
                <div class="chat-box">
                    <div class="message-area">
                        <div class="">
                            <span class="message-text"></span>
                            <br>
                            <span class="time"></span>
                        </div>
                    </div>
                    <div class="message-box">
                        <textarea name="message" id="message-box"></textarea>
                        <button onclick="sendMessage()" id="button">Send</button>
                    </div>
                </div>
            </section>
        </div>
    </main>
    <footer class="footer">
        <span>&copyAra Ellaryan</span>
    </footer>
</div>
</body>
</html>

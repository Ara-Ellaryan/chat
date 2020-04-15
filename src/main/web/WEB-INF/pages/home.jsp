<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="/css/header.css">
    <link rel="stylesheet" type="text/css" href="/css/chat.css">
    <link rel="stylesheet" type="text/css" href="/css/footer.css">
    <script type="text/javascript" src="/js/jQuery.js"></script>
    <script type="text/javascript" src="/js/chat.js"></script>
</head>
<body>
<div id="current-user-id" user-id="<c:out value="${sessionScope.user.id}"/>" hidden="hidden"></div>
<div class="container">
    <header class="header">
        <div class="header-wrapper">
            <div class="logo">
                <span>Logo</span>
            </div>
            <nav class="nav">
                <div id = "search-form">
                    <div class="user-search-input-wrapper">
                        <input id="user-search" type="text" name="email"  placeholder="ex. jhon@mail.ru">
                    </div>
                    <div>
                        <img id="search-button" src="/images/icon/icon_search.png" onclick="userSearch()"/>
                    </div>
                </div>
                <div ></div>
                <div class="current-user-bar-wrapper">
                    <span id="current-user"><c:out value="${sessionScope.user.name.concat(' ').concat(sessionScope.user.surname)}"/></span>
                    <img id="user-menu-list" src="/images/icon/menu_icon.png" onclick="openMenuList()">
                </div>
                <div><a id="logout" href="/logout">logout</a></div>
            </nav>
        </div>
    </header>
    <main class="main">
        <div class="main-wrapper">
            <aside class="aside">

                <div class="current-user-block">
                    <img src="">
                    <span class=""></span>
                    <span></span>
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

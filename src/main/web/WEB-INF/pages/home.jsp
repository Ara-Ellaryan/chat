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
<c:set var="currentUserFullName" value="${sessionScope.user.name.concat(' ').concat(sessionScope.user.surname)}"/>
<span id="current-user-id" user-id="<c:out value="${sessionScope.user.id}"/>" hidden="hidden"></span>
<span id="current-user-name" user-name="<c:out value="${currentUserFullName}"/>" hidden="hidden"></span>
<div class="container">
    <header class="header">
        <div class="header-wrapper">
            <div class="logo">
                <span>Logo</span>
            </div>
            <nav class="nav">
                <div id="search-form">
                    <div class="user-search-input-wrapper">
                        <input id="user-search" type="text" name="email"
                               placeholder="Search contact... 'search by email'">
                    </div>
                    <div>
                        <img id="search-button" src="/images/icon/icon_search.png" onclick="userSearch()"/>
                    </div>
                </div>
                <div></div>
                <div class="current-user-bar-wrapper">
                    <span id="current-user"><c:out value="${currentUserFullName}"/></span>
                    <img id="user-menu-list" src="/images/icon/menu_icon.png" onclick="openMenuList()">
                </div>
                <div><a id="logout" href="/logout">logout</a></div>
            </nav>
        </div>
    </header>
    <main class="main">
        <div class="main-wrapper">
            <aside class="aside">
                <div class="current-user-block" onclick="loadMessages()">
                    <div class="current-user-image">
                        <img class="profile-image" src="<c:out value="${sessionScope.user.imageUrl}"/>"
                             alt="<c:out value="${currentUserFullName}"/>">
                        <span class="user-activity current-place active"></span>
                    </div>
                    <span class="current-user-name"><c:out value="${currentUserFullName}"/></span>
                </div>
                <div class="contacts">
                    <c:set var="activeUsers" value="${applicationScope.users_activity}"/>
                    <ul class="user-list">
                        <c:forEach items="${requestScope.users}" var="user">
                            <c:if test="${user.id != sessionScope.user.id}">
                                <c:set var="userFullName" value="${user.name.concat(' ').concat(user.surname)}"/>
                                <li class="contact" onclick="loadMessages()">
                                    <div class="user-image">
                                        <img class="profile-image" src="<c:out value="${user.imageUrl}"/>"
                                             alt="<c:out value="${userFullName}"/>">
                                        <span class="user-activity contact-place <c:out value="${activeUsers[user.id] != null
                                    && (System.currentTimeMillis() - activeUsers[user.id].time) < 15 * 60 * 1000
                                    ? 'active' : 'passive'}"/>"></span>
                                    </div>
                                    <span class="user-name"><c:out value="${userFullName}"/></span>
                                </li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </div>
            </aside>
            <section class="section">
                <div class="chat-contact">
                    <div class="chat-user-image">
                        <img class="profile-image" src="<c:out value="${sessionScope.user.imageUrl}"/>"
                             alt="<c:out value="${currentUserFullName}"/>">
                    </div>
                    <span class="chat-user-name"><c:out value="${currentUserFullName}"/></span>
                </div>
                <div class="chat-box">
                    <div class="message-area">
                        <c:forEach var="message" items="${requestScope.messages}">
                            <c:set var="date" value="${message.createdAt}"/>
                            <div class="message-text-wrapper <c:out value="${message.senderId == sessionScope.user.id ? 'sender' : 'receiver'}"/>">
                                <span class="message-text"><c:out value="${message.message}"/></span>
                                <br>
                                <span class="time">
                                    <c:out value="${Integer.toString(date.hours).concat(':').concat(Integer.toString(date.minutes)).
                                    concat(' ').concat(Integer.toString(date.date)).concat('-').concat(Integer.toString(date.month))}"/>
                                </span>
                            </div>
                        </c:forEach>
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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.epam.jwd.library.model.Role" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello! Welcome to JSP!!!</title>
    <style><%@include file="/WEB-INF/jsp/style/main.css"%></style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</head>
<body>
    <div class="container-main container">
        <div class="container-context container">
            <header class="container">
                <nav class="navbar navbar-expand navbar-light" style="background-color: #56abe7;">
                    <div class="container nav-context">
                        <div class="logo"><a href="#">MyLibrary</a></div>
                        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                            <li class="nav-item">
                                <a class="nav-link" href="/controller?command=main_page">Main</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/controller?command=author_page">Author</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/controller?command=catalog_page">Catalog</a>
                            </li>
                        </ul>
                    </div>
                </nav>
                <div class="header-rightside container">
                    <div class="header-rightside-inner container">
                        <div class="lang-switcher">
                            <a href="#">en</a>
                            <a href="#">ru</a>
                        </div>
                        <div class="sing-container">
                            <c:choose>
                                <c:when test="${not empty sessionScope.account}">
                                    <a class="btn btn-primary" href="/controller?command=logout" role="button">Logout</a>
                                </c:when>
                                <c:otherwise>
                                    <a class="btn btn-primary" href="/controller?command=login_page" role="button">Sing in</a>
                                    <a class="btn btn-primary" href="/controller?command=registration_page" role="button">Register</a>
                                </c:otherwise>
                            </c:choose>

                        </div>
                    </div>
                </div>
            </header>
            <main class="container-body">
                <div class="container">
                    <div class="container-img">
                        <img src="https://mipt.ru/upload/medialibrary/907/111.jpg">
                        <span class="some-text">
                  <div class="btn-group-vertical">
                    <c:if test="${not empty sessionScope.account and (sessionScope.account.role eq Role.LIBRARIAN)}">
                        <a class="btn btn-primary" href="/controller?command=librarian_book_order_page" role="button">check orders</a>
                    </c:if>
                    <c:if test="${not empty sessionScope.account and (sessionScope.account.role eq Role.READER)}">
                        <a class="btn btn-primary" href="/controller?command=reader_book_order_page" role="button">order</a>
                        <a class="btn btn-primary" href="/controller?command=account_page&id=${sessionScope.account.id}" role="button">personal data</a>
                    </c:if>
                    <c:if test="${not empty sessionScope.account && sessionScope.account.role eq Role.ADMIN}">
                        <a class="btn btn-primary" href="/controller?command=accounts_page" role="button">accounts</a>
                    </c:if>
                </div>
                </span>
                    </div>
                </div>
            </main>
            <footer>
                <div class="container">
                    <i>Сегодня: <%= new Date() %></i>
                </div>
            </footer>
        </div>
    </div>
</body>
</html>

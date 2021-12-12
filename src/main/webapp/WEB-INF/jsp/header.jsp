<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<fmt:setLocale value="en_US"/>--%>
<%--&lt;%&ndash;<fmt:setBundle basename="l10n.page.main" var="loc" />&ndash;%&gt;--%>

<%--<fmt:bundle basename="l10.page.main">--%>
<%--    <fmt:message key="label.main" var="locMain"/>--%>
<%--&lt;%&ndash;    <fmt:message key="label.button.order" var="locOrder"/>&ndash;%&gt;--%>
<%--</fmt:bundle>--%>

<html>
<head>
    <title>Header</title>
    <style><%@include file="/WEB-INF/jsp/style/header.css"%></style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</head>
<body>
<header class="container">
    <nav class="navbar navbar-expand navbar-light" style="background-color: #56abe7;">
        <div class="container">
            <div class="logo"><a href="#">MyLibrary</a></div>
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
<%--                    <a class="nav-link" href="/controller?command=main_page"><fmt:message bundle="loc" key="label.main"/> </a>--%>
<%--                        <a class="nav-link" href="/controller?command=main_page">${locMain}</a>--%>
    <a class="nav-link" href="/controller?command=main_page">Main</a>

                </li>
                <li class="nav-item">
<%--                    <a class="nav-link" href="/controller?command=author_page"><fmt:message bundle="loc" key="label.author"/> </a>--%>
    <a class="nav-link" href="/controller?command=author_page">Authors</a>
                </li>
                <li class="nav-item">
<%--                    <a class="nav-link" href="/controller?command=catalog_page"><fmt:message bundle="loc" key="label.catalog"/> </a>--%>
    <a class="nav-link" href="/controller?command=catalog_page">Catalog</a>
                </li>
            </ul>
        </div>
    </nav>
    <div class="header-rightside container">
        <div class="header-rightside-inner container">
            <div class="container-lang">
                <div class="lang-switcher">
                    <a href="#">en</a>
                    <a href="#">ru</a>
                </div>
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
</body>
</html>

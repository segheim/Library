<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello! Welcome to JSP!!!</title>
</head>
<body>
<h1>Welcome to page Main.jsp</h1>
<i>Сегодня: <%= new Date() %></i>
</b>
<c:choose>
    <c:when test="${not empty sessionScope.account}">
        <h1> Hello, dear ${sessionScope.account.login}!</h1>
        <p>
            <a href="/controller?command=author_page">authors</a>
        </p>
        <p>
            <a href="/controller?command=catalog_page">catalog</a>
        </p>
        <p>
            <a href="/controller?command=logout">logout</a>
        </p>
    </c:when>
    <c:otherwise>
        <h1> Hello!</h1>
        <p>
            <a href="/controller?command=author_page">authors</a>
        </p>
        <p>
            <a href="/controller?command=catalog_page">catalog</a>
        </p>
        <p>
            <a href="/controller?command=login_page">login</a>
        </p>
    </c:otherwise>
</c:choose>
</body>
</html>

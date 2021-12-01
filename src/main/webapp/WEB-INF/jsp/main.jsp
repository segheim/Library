<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.epam.jwd.library.model.Role" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello! Welcome to JSP!!!</title>
</head>
<body>
<i>Сегодня: <%= new Date() %></i>
</b>
<c:if test="${not empty sessionScope.account}">
    <h1> Hello, dear ${sessionScope.account.details.firstName}!</h1>
</c:if>
<p>
    <a href="/controller?command=author_page">authors</a>
</p>
<p>
    <a href="/controller?command=catalog_page">catalog</a>
</p>
<c:if test="${not empty sessionScope.account and ((sessionScope.account.role eq Role.ADMIN) or (sessionScope.account.role eq Role.LIBRARIAN))}">
    <p>
        <a href="/controller?command=librarian_book_order_page">check orders</a>
    </p>
</c:if>
<c:if test="${not empty sessionScope.account and (sessionScope.account.role eq Role.READER)}">
    <p>
        <a href="/controller?command=reader_book_order_page">order</a>
    </p>
</c:if>
<c:if test="${not empty sessionScope.account && sessionScope.account.role eq Role.ADMIN}">
    <p>
        <a href="/controller?command=account_page">accounts</a>
    </p>
</c:if>
<c:choose>
    <c:when test="${not empty sessionScope.account}">
        <p>
            <a href="/controller?command=logout">logout</a>
        </p>
    </c:when>
    <c:otherwise>
        <p>
            <a href="/controller?command=login_page">login</a>
        </p>
        <p>
            <a href="/controller?command=registration_page">registration</a>
        </p>
    </c:otherwise>
</c:choose>
</body>
</html>

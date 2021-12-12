<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.epam.jwd.library.model.Role" %>
<html>
<head>
    <title>Book</title>
    <style><%@include file="/WEB-INF/jsp/style/book.css"%></style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</head>
<body>
    <div class="container-main">
        <div class="container-context">
            <jsp:include page="header.jsp"></jsp:include>
            <div class="container">
                <h1>${requestScope.book.title}</h1>
            </div>
            <div class="container">
                <h3>
                    Author:
                    <c:forEach var="author" items="${requestScope.book.authors}">
                        <br>${author.firstName}
                            ${author.lastName}
                    </c:forEach>
                </h3>
            </div>
            <div class="container">
                <h3>
                    Date of published: ${requestScope.book.datePublished}
                </h3>
            </div>
            <div class="container">
                <h3>
                    Quantity of items: ${requestScope.book.amountOfLeft}
                </h3>
            </div>
            <div class="container">
                <c:choose>
                    <c:when test="${not empty sessionScope.account and (sessionScope.account.role eq Role.ADMIN)}">
                        <a class="btn btn-primary" href="/controller?command=update_book_page&id=${requestScope.book.id}" role="button">Update book</a>
                    </c:when>
                    <c:when test="${not empty sessionScope.account and (sessionScope.account.role eq Role.LIBRARIAN)}">
                        <a class="btn btn-primary disabled" href="/controller?command=create_book_order_page&id=${requestScope.book.id}" role="button" aria-disabled="true">Add to order</a>
                    </c:when>
                    <c:otherwise>
                        <a class="btn btn-primary" href="/controller?command=create_book_order_page&id=${requestScope.book.id}" role="button">Add to order</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</body>
</html>

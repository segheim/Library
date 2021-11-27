<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book</title>
</head>
<body>
<h1>
    ${requestScope.book.title}
</h1>
<p>
    Author: <c:forEach var="author" items="${requestScope.book.authors}">
            <br>${author.firstName}
            ${author.lastName}
    </c:forEach>
</p>
<p>
    Date of published: ${requestScope.book.datePublished}
</p>
<p>
    Quantity of items: ${requestScope.book.amountOfLeft}
</p>
<p>
    <a href="/controller?command=update_book_page&id=${book.id}">Update book</a>
</p>
<p>
    <a href="/controller?command=book_order_page&id=${book.id}">Add to order</a>
</p>
</body>
</html>

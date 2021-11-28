<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book order for reader</title>
</head>
<body>
<h1>
    Order:
</h1>
<p>
    <c:forEach var="bookOrder" items="${requestScope.bookOrders}">
        <br>${bookOrder.details.firstName}
            ${bookOrder.details.lastName}
        <br>&#34;${bookOrder.book.title}&#34;
            <c:forEach var="author" items="${requestScope.book.authors}">
                <br>${author.firstName}
                <br>${author.lastName}
            </c:forEach>
        <br>${bookOrder.type.name()}
        <br>${bookOrder.dateCreate}
        <br>${bookOrder.dateIssue}
        <br>${bookOrder.dateReturn}
        <br>${bookOrder.status.name()}
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
    <a href="/controller?command=create_book_order_page&id=${book.id}">Add to order</a>
</p>
</body>
</html>

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
    <c:forEach var="bookOrder" items="${bookOrders}">
        <br>${bookOrder.details.firstName}
        ${bookOrder.details.lastName}
        <br>Book: &#34;${bookOrder.book.title}&#34;
        <c:forEach var="author" items="${requestScope.bookOrder.book.authors}">
            <br>${author.firstName}
            <br>${author.lastName}
        </c:forEach>
        <br>Type of order: ${bookOrder.type.name()}
        <br>Date create order: ${bookOrder.dateCreate}
        <br>Date issue a book: ${bookOrder.dateIssue}
        <br>Date return a book: ${bookOrder.dateReturn}
        <br>Order status: ${bookOrder.status.name()}
        <p>
            <a href="/controller?command=update_book_order_page&id=${book.id}">Update order</a>
        </p>
        <br>
    </c:forEach>
</p>
</body>
</html>

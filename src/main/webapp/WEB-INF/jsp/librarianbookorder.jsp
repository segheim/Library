<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book orders for librarian</title>
</head>
<body>
<h1>
    Orders:
</h1>
<table>
    <tr>
        <th>Id</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Book title</th>
        <th>Amount of left books</th>
        <th>Date create order</th>
        <th>Date issue order</th>
        <th>Date return order</th>
        <th>Order status</th>
        <th>Action</th>
    </tr>
    <c:forEach var="bookOrder" items="${requestScope.bookOrders}">
        <tr>
            <td>${bookOrder.id}</td>
            <td>${bookOrder.details.firstName}</td>
            <td>${bookOrder.details.lastName}</td>
            <td>${bookOrder.book.title}</td>
            <td>${bookOrder.book.amountOfLeft}</td>
            <td>${bookOrder.dateCreate}</td>
            <td>${bookOrder.dateIssue}</td>
            <td>${bookOrder.dateReturn}</td>
            <td>${bookOrder.status}</td>
            <td>
                <p>
                    <a href="/controller?command=issue_book&id=${bookOrder.id}">issued</a>
                    <a href="/controller?command=end_book_order&id=${bookOrder.id}">ended</a>
                    <a href="/controller?command=delete_book_order&id=${bookOrder.id}">delete order</a>
                </p>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book orders for librarian</title>
    <style><%@include file="/WEB-INF/jsp/style/librarianBookOrder.css"%></style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</head>
<body>
    <table>
        <div class="container-main">
            <div class="container-context">
                <div class="container">
                    <table class="table table-striped table-hover">
                        <thead class="table-primary">
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
                        </thead>
                        <tbody>
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
                                    <a href="/controller?command=issue_book&id=${bookOrder.id}">issued</a>
                                    <a href="/controller?command=end_book_order&id=${bookOrder.id}">ended</a>
                                    <a href="/controller?command=delete_book_order&id=${bookOrder.id}">delete order</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </table>
</body>
</html>


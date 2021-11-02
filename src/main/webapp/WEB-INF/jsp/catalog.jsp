<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>Catalog</h3>
<p>
    <form action="student.jsp" method="POST">
        Search: <input type="text" name="Name">
        <input type ="submit" value="search">
    </form>
</p>
<table>
    <tr>
        <th>Id</th>
        <th>Title</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Date published</th>
        <th>Quantity</th>
        <th>Order</th>
    </tr>
    <c:forEach var="book" items="${requestScope.books}">
        <tr>
            <td>${book.id}</td>
            <td>${book.title}</td>
            <td>${book.author.first_name}</td>
            <td>${book.author.last_name}</td>
            <td>${book.date_published}</td>
            <td>${book.amount_of_left}</td>
            <c:if test="${book.amount_of_left>0}">
                <td><label><input type="checkbox"/></label></td>
            </c:if>
        </tr>
    </c:forEach>
</table>
</body>
</html>

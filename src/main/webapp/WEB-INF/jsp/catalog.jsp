<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>Authors</h3>
<table>
    <tr>
        <th>Id</th>
        <th>Title</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Date published</th>
        <th>Quantity</th>
    </tr>
    <c:forEach var="book" items="${requestScope.books}">
        <tr>
            <td>${book.id}</td>
            <td>${book.title}</td>
            <td>${book.author.first_name}</td>
            <td>${book.author.last_name}</td>
            <td>${book.date_published}</td>
            <td>${book.mount_of_left}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
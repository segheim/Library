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
        <th>First name</th>
        <th>Last name</th>
        <th>Book</th>
    </tr>
    <c:forEach var="author" items="${requestScope.authors}">
        <tr>
            <td>${author.id}</td>
            <td>${author.first_name}</td>
            <td>${author.last_name}</td>
            <td>
        <c:forEach var="book" items="${requestScope.books}">
            <tr>${author.book.title} ${author.book.date_published}</tr>
        </c:forEach>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

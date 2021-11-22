<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>Catalog</h3>
<p>
    <a href="/controller?command=create_book_page">create book</a>
</p>
<table>
    <tr>
        <th>Add</th>
        <th>Title</th>
        <th>Author</th>
        <th>Date published</th>
        <th>Quantity</th>
        <th>Action</th>
    </tr>
    <c:forEach var="book" items="${requestScope.books}">
        <tr>
            <c:if test="${book.amount_of_left > 0}">
                <td><label><input type="checkbox"/></label></td>
            </c:if>
            <td>
                <a href="/controller?command=book_page&id=${book.id}">${book.title}</a>
            </td>
            <td>
                <c:forEach var="author" items="${book.authors}">
                    <br>${author.first_name}
                    ${author.last_name}
                </c:forEach>
            </td>
            <td>${book.date_published}</td>
            <td>${book.amount_of_left}</td>
            <td>
                <p>
                    <a href="/controller?command=book_page">update</a>
                    <a href="/controller?command=delete_book&id=${book.id}">delete</a>
                </p>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

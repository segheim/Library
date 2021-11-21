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
        <th>Add</th>
        <th>Title</th>
        <th>Author</th>
        <th>Date published</th>
        <th>Quantity</th>
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
        </tr>
    </c:forEach>
</table>
</body>
</html>

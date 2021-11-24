<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Authors</title>
</head>
<body>
<h3>Authors</h3>
<table>
    <tr>
        <th>Id</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Action</th>
    </tr>
    <c:forEach var="author" items="${requestScope.authors}">
        <tr>
            <td>${author.id}</td>
            <td>${author.firstName}</td>
            <td>${author.lastName}</td>
            <td>
                <p>
                    <a href="/controller?command=update_author_page&id=${author.id}">update</a>
                    <a href="/controller?command=delete_author&id=${author.id}">delete</a>
                </p>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
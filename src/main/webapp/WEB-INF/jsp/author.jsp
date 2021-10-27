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
        <th>First name</th>
        <th>Last name</th>
        <%--        <th>Owner</th>--%>
    </tr>
    <c:forEach var="authir" items="${requestScope.authors}">
        <tr>
            <td><a href="/controller?command=show_bike&id=${author.id}">${bike.first_name}</a></td>
            <td>${bike.last_name}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

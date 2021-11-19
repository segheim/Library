<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>Accounts</h3>
<table>
    <tr>
        <th>Id</th>
        <th>Login</th>
        <th>Role</th>
    </tr>
    <c:forEach var="account" items="${requestScope.accounts}">
        <tr>
        <td>${account.id}</td>
        <td>${account.login}</td>
        <td>${account.role}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
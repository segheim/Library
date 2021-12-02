<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account</title>
</head>
<body>
<h1>
    Your personal data, dear ${requestScope.account.role.name()}!
</h1>
<h3>
    Personal number: ${requestScope.account.id}
</h3>
<h3>
    Login: ${requestScope.account.login}
</h3>
<h3>
    First Name: ${requestScope.account.details.firstName}
</h3>
<h3>
    Last Name: ${requestScope.account.details.lastName}
</h3>
</body>
</html>

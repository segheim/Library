<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update author</title>
</head>
<body>
<h1>${author.firstName} ${author.lastName}</h1>
<h3>Please enter changes into Author:</h3>
<form name = "update_author_form" method="post" action="/controller?command=update_author">
    <input type="hidden" name="id" value="${author.id}">
    First name:<br/>
    <input type="text" name="first_name" value=""/>
    <br/>Last name:<br/>
    <input type="text" name="last_name" value=""/>
    <br/>
    <c:if test="${not empty requestScope.errorUpdateAuthorPassMessage}">
        <b style="color: red">${requestScope.errorUpdateAuthorPassMessage}</b>
        <br>
    </c:if>
    <input type="submit" value="update"/>
</form>
</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CreateAuthor</title>
</head>
<body>
<h3>Please enter data for creating author:</h3>
<form name = "create_author_form" method="post" action="/controller?command=create_author">
    <br/>First name:<br/>
    <input type="text" name="first_name" value=""/>
    <br/>Last name:<br/>
    <input type="text" name="last_name" value=""/>
    <br/>
    <c:if test="${not empty requestScope.errorLoginPassMessage}">
        <b style="color: red">${requestScope.errorLoginPassMessage}</b>
        <br>
    </c:if>
    <input type="submit" value="create"/>
</form>
</body>
</html>

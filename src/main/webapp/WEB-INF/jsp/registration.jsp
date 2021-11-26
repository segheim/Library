<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h3>Please enter registration data:</h3>
<form name = "registration_form" method="post" action="/controller?command=registration">
    Login:<br/>
    <input type="text" name="login" value=""/>
    <br/>Password:<br/>
    <input type="password" name="password" value=""/>
    <br/>First name:<br/>
    <input type="text" name="first_name" value=""/>
    <br/>Last name:<br/>
    <input type="text" name="last_name" value=""/>
    <br/>
    <c:if test="${not empty requestScope.errorRegistrationMessage}">
        <b style="color: red">${requestScope.errorRegistrationMessage}</b>
        <br>
    </c:if>
    <input type="submit" value="register"/>
</form>
</body>
</html>
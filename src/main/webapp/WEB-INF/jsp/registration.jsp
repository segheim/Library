<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
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
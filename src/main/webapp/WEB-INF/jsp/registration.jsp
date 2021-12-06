<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <style><%@include file="/WEB-INF/jsp/style/registration.css" %></style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</head>
<body>
    <div class="container-login">
        <div class="col-sm-4">
            <h1 class="header-register">Register</h1>
            <form name = "registration_form" method="post" action="/controller?command=registration">
                <div class="mb-3">
                    <label for="exampleInputLogin" class="form-label">Login</label>
                    <input type="text" name="login" class="form-control" id="exampleInputLogin">
                </div>
                <div class="mb-3">
                    <label for="exampleInputPassword" class="form-label">Password</label>
                    <input type="password" name="password" class="form-control" id="exampleInputPassword">
                </div>
                <div class="mb-3">
                    <label for="exampleInputFirstName" class="form-label">First name</label>
                    <input type="text" name="first_name" class="form-control" id="exampleInputFirstName">
                </div>
                <div class="mb-3">
                    <label for="exampleInputLastName" class="form-label">Last name</label>
                    <input type="text" name="last_name" class="form-control" id="exampleInputLastName">
                </div>
                <c:if test="${not empty requestScope.errorRegistrationMessage}">
                    <b style="color: red">${requestScope.errorRegistrationMessage}</b>
                    <br>
                </c:if>
                <button type="submit" class="btn btn-primary">Register</button>
            </form>
        </div>
    </div>
</body>
</html>
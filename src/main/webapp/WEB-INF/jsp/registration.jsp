<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <style><%@include file="/WEB-INF/jsp/style/registration.css" %></style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.3.0/lodash.js"></script>
</head>
<body>
    <div class="container-login">
        <div class="col-sm-4">
            <h1 class="header-register">Register</h1>
            <form  class="needs-validation" name = "registration_form" method="post" action="/controller?command=registration" novalidate>
                <div class="mb-3">
                    <label for="exampleInputLogin" class="form-label">Login</label>
                    <input type="text" name="login" pattern=".{3,30}" class="form-control" id="exampleInputLogin" required>
                    <div class="valid-feedback">
                        Looks good!
                    </div>
                    <div class="invalid-feedback">
                        Please enter login(3-30 symbols)
                    </div>
                </div>
                <div class="mb-3">
                    <label for="exampleInputPassword" class="form-label">Password</label>
                    <input type="password" name="password" pattern=".{3,100}" class="form-control" id="exampleInputPassword" required>
                    <div class="valid-feedback">
                        Looks good!
                    </div>
                    <div class="invalid-feedback">
                        Please enter password(3-100 symbols)
                    </div>
                </div>
                <div class="mb-3">
                    <label for="exampleInputFirstName" class="form-label">First name</label>
                    <input type="text" name="first_name" pattern="[A-Za-z]{1,20}" class="form-control" id="exampleInputFirstName" required>
                    <div class="valid-feedback">
                        Looks good!
                    </div>
                    <div class="invalid-feedback">
                        Please enter first name(1-20 letters)
                    </div>
                </div>
                <div class="mb-3">
                    <label for="exampleInputLastName" class="form-label">Last name</label>
                    <input type="text" name="last_name" pattern="[A-Za-z]{1,20}" class="form-control" id="exampleInputLastName" required>
                    <div class="valid-feedback">
                        Looks good!
                    </div>
                    <div class="invalid-feedback">
                        Please enter last name(1-20 letters)
                    </div>
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
<script>
    (function () {
        'use strict'
        var forms = document.querySelectorAll('.needs-validation')
        Array.prototype.slice.call(forms)
            .forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }
                    form.classList.add('was-validated')
                }, false)
            })
    })()
</script>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update author</title>
    <style><%@include file="/WEB-INF/jsp/style/updateAuthor.css"%></style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.3.0/lodash.js"></script>
</head>
<body>
    <div class="container-context">
        <jsp:include page="header.jsp"></jsp:include>
        <div class="container-login">
            <div class="col-sm-4">
                <h1 class="header-update">Update author</h1>
                <h2 class="header-form">${author.firstName} ${author.lastName}</h2>
                <form class="needs-validation" name = "update_author_form" method="post"
                      action="/controller?command=update_author" novalidate>
                    <input type="hidden" name="id" value="${author.id}">
                    <div class="mb-3">
                        <label for="exampleInputFirstName" class="form-label">First name</label>
                        <input type="text" pattern="[A-Za-z]{1,20}" name="first_name" class="form-control"
                               id="exampleInputFirstName" required>
                        <div class="valid-feedback">
                            Looks good!
                        </div>
                        <div class="invalid-feedback">
                            Please enter first name(1-20 letters)
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="exampleInputLastName" class="form-label">Last name</label>
                        <input type="text" pattern="[A-Za-z]{1,20}" name="last_name" class="form-control"
                               id="exampleInputLastName" required>
                        <div class="valid-feedback">
                            Looks good!
                        </div>
                        <div class="invalid-feedback">
                            Please enter last name(1-20 letters)
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary">Update</button>
                </form>
            </div>
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

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
</head>
<body>
    <div class="container-context">
        <div class="container-login">
            <div class="col-sm-4">
                <h1 class="header-update">Update author</h1>
                <h2 class="header-form">${author.firstName} ${author.lastName}</h2>
                <form name = "update_author_form" method="post" action="/controller?command=update_author">
                    <input type="hidden" name="id" value="${author.id}">
                    <div class="mb-3">
                        <label for="exampleInputFirstName" class="form-label">First name</label>
                        <input type="text" name="first_name" class="form-control" id="exampleInputFirstName">
                    </div>
                    <div class="mb-3">
                        <label for="exampleInputLastName" class="form-label">Last name</label>
                        <input type="text" name="last_name" class="form-control" id="exampleInputLastName">
                    </div>
                    <button type="submit" class="btn btn-primary">Update</button>
                </form>
            </div>
        </div>
    </div>
</form>
</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CreateBook</title>
    <style><%@include file="/WEB-INF/jsp/style/createBook.css"%></style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</head>
<body>
    <div class="container-context">
        <div class="container-login">
            <div class="col-sm-4">
                <h1 class="header-create">Create book form</h1>
                <form name = "create_book_form" method="post" action="/controller?command=create_book">
                    <div class="mb-3">
                        <label for="exampleInputTitle" class="form-label">Title</label>
                        <input type="text" name="title" class="form-control" id="exampleInputTitle">
                    </div>
                    <div class="mb-3">
                        <label for="exampleInputDatePublished" class="form-label">Published date</label>
                        <input type="date" name="date_published" class="form-control" id="exampleInputDatePublished">
                    </div>
                    <div class="mb-3">
                        <label for="exampleInputAmountOfLeft" class="form-label">Quantity of items</label>
                        <input type="number" name="amount_of_left" class="form-control" id="exampleInputAmountOfLeft">
                    </div>
                    <div class="mb-3">
                        <label for="exampleInputFirstName" class="form-label">First name</label>
                        <input type="text" name="first_name" class="form-control" id="exampleInputFirstName">
                    </div>
                    <div class="mb-3">
                        <label for="exampleInputLastName" class="form-label">First name</label>
                        <input type="text" name="last_name" class="form-control" id="exampleInputLastName">
                    </div>
                    <c:if test="${not empty requestScope.errorCreatBookMessage}">
                        <b style="color: red">${requestScope.errorCreatBookMessage}</b>
                        <br>
                    </c:if>
                    <button type="submit" class="btn btn-primary">Create</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update book</title>
    <style><%@include file="/WEB-INF/jsp/style/updateBook.css"%></style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</head>
<body>
    <div class="container-context">
        <div class="container-login">
            <div class="col-sm-4">
                <h1 class="header-update">Update Book</h1>
                <h2 class="header-form">Title:${book.title} Date:${book.datePublished} Quantity:${book.amountOfLeft}</h2>
                <form name = "update_book_form" method="post" action="/controller?command=update_book">
                    <input type="hidden" name="id" value="${book.id}">
                    <div class="mb-3">
                        <label for="exampleInputTitle" class="form-label">Title</label>
                        <input type="text" name="title" class="form-control" id="exampleInputTitle">
                    </div>
                    <div class="mb-3">
                        <label for="exampleInputDate" class="form-label">Date of published</label>
                        <input type="date" name="date-published" class="form-control" id="exampleInputDate">
                    </div>
                    <div class="mb-3">
                        <label for="exampleInputQuantity" class="form-label">Quantity of items</label>
                        <input type="number" name="amount_of_left" class="form-control" id="exampleInputQuantity">
                    </div>
                    <c:if test="${not empty requestScope.errorUpdateBookPassMessage}">
                        <b style="color: red">${requestScope.errorUpdateBookPassMessage}</b>
                        <br>
                    </c:if>
                    <button type="submit" class="btn btn-primary">Update</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>

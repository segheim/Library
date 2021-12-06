<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update book</title>
    <style><%@include file="/WEB-INF/jsp/style/main.css"%></style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</head>
<body>
<h3>Please enter changes into Book:</h3>
<form name = "update_book_form" method="post" action="/controller?command=update_book">
    <input type="hidden" name="id" value="${book.id}">
    Title:<br/>
    <input type="text" name="title" value=""/>
    <br/>Published Date:<br/>
    <input type="date" name="date_published" value=""/>
    <br/>Quantity of items:<br/>
    <input type="text" name="amount_of_left" value=""/>
    <br/>
    <c:if test="${not empty requestScope.errorUpdateBookPassMessage}">
        <b style="color: red">${requestScope.errorUpdateBookPassMessage}</b>
        <br>
    </c:if>
    <input type="submit" value="update"/>
</form>
</body>
</html>

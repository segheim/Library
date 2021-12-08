<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.epam.jwd.library.model.OrderType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Book order</title>
  <style><%@include file="/WEB-INF/jsp/style/main.css"%></style>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</head>
<body>
<h3>Your order, dear ${sessionScope.account.details.firstName} ${sessionScope.account.details.lastName}:</h3>
<br/>Selecting book:
<h3>${requestScope.book.title}</h3>
<c:forEach var="author" items="${requestScope.book.authors}">
  ${author.firstName}
  ${author.lastName}
  <br/>
</c:forEach>
<br/>
<br/>Select the method to lending book:<br/>
<form name = "create_book_order_form" method="post" action="/controller?command=create_book_order">
  <input type="hidden" name="book_id" value="${book.id}">
  <input type = "checkbox" name = "order_type" value="READINGROOM"/> Reading room
  <input type = "checkbox" name = "order_type" value="LIBRARYCARD"/> Library Card
  <br/>
  <c:if test="${not empty requestScope.errorCreateBookOrderPassMessage}">
    <b style="color: red">${requestScope.errorCreateBookOrderPassMessage}</b>
    <br>
  </c:if>
  <input type="submit" value="create order"/>
</form>
</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.epam.jwd.library.model.OrderType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Book order</title>
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

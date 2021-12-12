<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Book order</title>
  <style><%@include file="/WEB-INF/jsp/style/createBookOrder.css"%></style>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</head>
<body>
  <div class="container">
    <div class="container-context">
      <jsp:include page="header.jsp"></jsp:include>
      <div class="container">
        <div class="header-name">
          <b>Your order, dear ${sessionScope.account.details.firstName} ${sessionScope.account.details.lastName}</b>
        </div>
      </div>
      <div class="container">Selected book: ${requestScope.book.title}</div>
      <div class="container">
        Author:
        <c:forEach var="author" items="${requestScope.book.authors}">
          ${author.firstName}
          ${author.lastName}
        </c:forEach>
      </div>
      <div class="container">
        <header-name>Select the method to lending book:</header-name>
      </div>
      <div class="container">
        <form name="create_book_order_form" method="post" action="/controller?command=create_book_order">
          <input type="hidden" name="book_id" value="${book.id}">
          <div class="form-check">
                <input class="form-check-input" type="radio" name="order_type" value="READINGROOM"
                       id="checkReadingRoom">
            <label class="form-check-label" for="checkReadingRoom">
              Reading Room
            </label>
          </div>
          <div class="form-check">
                <input class="form-check-input" type="radio" name="order_type" value="LIBRARYCARD"
                       id="checkLibraryCard" checked>
            <label class="form-check-label" for="checkLibraryCard">
              Library Card
            </label>
          </div>
          <c:if test="${not empty requestScope.errorCreateBookOrderMassage}">
            <b style="color: red">${requestScope.errorCreateBookOrderMassage}</b>
            <br>
          </c:if>
          <button type="submit" class="btn btn-primary">Create order</button>
        </form>
      </div>
    </div>
  </div>
</body>
</html>

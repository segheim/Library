<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.epam.jwd.library.model.Role" %>
<html>
<head>
    <title>Catalog</title>
    <style><%@include file="/WEB-INF/jsp/style/catalog.css"%></style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</head>
<body>
    <div class="container-main">
        <div class="container-context">
            <jsp:include page="header.jsp"></jsp:include>
            <div class="container col-ms-6">
                <form class="d-flex" name="search" role="search" method="post" action="/controller?command=search_book">
                    <input name="title" class="form-control me-4" type="search" placeholder="Search by book title" aria-label="Search">
                    <button class="btn btn-primary" type="submit">Search</button>
                </form>
            </div>
            <c:if test="${not empty sessionScope.account and (sessionScope.account.role eq Role.ADMIN)}">
                <div class="d-grid gap-2">
                    <a class="btn btn-primary" href="/controller?command=create_book_page" role="button">create book</a>
                </div>
            </c:if>
            <div class="container">
                <table class="table table-striped table-hover">
                    <thead class="table-primary">
                        <tr>
                            <th>Title</th>
                            <th>Author</th>
                            <th>Date published</th>
                            <th>Quantity</th>
                            <c:if test="${not empty sessionScope.account and (sessionScope.account.role eq Role.ADMIN)}">
                                <th>Action</th>
                            </c:if>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="book" items="${requestScope.books}">
                            <tr>
                                <c:if test="${book.amountOfLeft > 0}">
                                    <td><a href="/controller?command=book_page&id=${book.id}">${book.title}</a></td>
                                    <td>
                                        <c:forEach var="author" items="${book.authors}">
                                            ${author.firstName} ${author.lastName}<br>
                                        </c:forEach>
                                    </td>
                                    <td>${book.datePublished}</td>
                                    <td>${book.amountOfLeft}</td>
                                    <c:if test="${not empty sessionScope.account and (sessionScope.account.role eq Role.ADMIN)}">
                                        <td>
                                            <a href="/controller?command=update_book_page&id=${book.id}">update</a>
                                            <a href="/controller?command=delete_book&id=${book.id}">delete</a>
                                        </td>
                                    </c:if>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>

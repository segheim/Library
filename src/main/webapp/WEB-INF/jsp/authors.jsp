<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Authors</title>
    <style><%@include file="/WEB-INF/jsp/style/authors.css"%></style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</head>
<body>
    <div class="container-main">
        <div class="container-context">
            <c:if test="${not empty sessionScope.account and (sessionScope.account.role eq Role.ADMIN)}">
                <div class="d-grid gap-2">
                    <a class="btn btn-primary" href="/controller?command=create_author_page" role="button">create author</a>
                </div>
            </c:if>
            <div class="container">
                <table class="table table-striped table-hover">
                    <thead class="table-primary">
                        <tr>
                            <th>Id</th>
                            <th>First name</th>
                            <th>Last name</th>
                            <c:if test="${not empty sessionScope.account and (sessionScope.account.role eq Role.ADMIN)}">
                                <th>Action</th>
                            </c:if>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="author" items="${requestScope.authors}">
                            <tr>
                                <td>${author.id}</td>
                                <td>${author.firstName}</td>
                                <td>${author.lastName}</td>
                                <c:if test="${not empty sessionScope.account and (sessionScope.account.role eq Role.ADMIN)}">
                                    <td>
                                        <a href="/controller?command=update_author_page&id=${author.id}">update</a>
                                        <a href="/controller?command=delete_author&id=${author.id}">delete</a>
                                    </td>
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
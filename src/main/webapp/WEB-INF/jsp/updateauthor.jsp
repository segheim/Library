<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update author</title>
    <style><%@include file="/WEB-INF/jsp/style/main.css"%></style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</head>
<body>
<h1>${author.firstName} ${author.lastName}</h1>
<h3>Please enter changes into Author:</h3>
<form name = "update_author_form" method="post" action="/controller?command=update_author">
    <input type="hidden" name="id" value="${author.id}">
    First name:<br/>
    <input type="text" name="first_name" value=""/>
    <br/>Last name:<br/>
    <input type="text" name="last_name" value=""/>
    <br/>
    <c:if test="${not empty requestScope.errorUpdateAuthorPassMessage}">
        <b style="color: red">${requestScope.errorUpdateAuthorPassMessage}</b>
        <br>
    </c:if>
    <input type="submit" value="update"/>
</form>
</body>
</html>

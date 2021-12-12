<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.epam.jwd.library.model.Role" %>
<html>
<head>
    <title>Account</title>
    <style><%@include file="/WEB-INF/jsp/style/account.css"%></style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</head>
<body>
    <div class="container-main">
        <div class="container-context">
            <jsp:include page="header.jsp"></jsp:include>
            <div class="container">
                <h2>Personal data</h2>
            </div>
            <div class="container">
                <h3>Personal number: ${requestScope.account.id}</h3>
            </div>
            <div class="container">
                <h3>Login: ${requestScope.account.login}</h3>
            </div>
            <div class="container">
                <h3>First Name: ${requestScope.account.details.firstName}</h3>
            </div>
            <div class="container">
               <h3>Last Name: ${requestScope.account.details.lastName}</h3>
            </div>
            <div class="container">
                <h3>Role: ${requestScope.account.role.name()}</h3>
            </div>
            <div class="container">
                <c:if test="${not empty sessionScope.account and (sessionScope.account.role eq Role.ADMIN)}">
                    <form name="change_role_form" method="post" action="/controller?command=change_account_role">
                        <input type="hidden" name="id" value="${requestScope.account.id}">
                        <div class="container">
                            <div class="mb-3">
                                <select name="role" class="form-select mb-3" aria-label=".form-select-lg example">
                                    <option selected>select role</option>
                                    <option value="ADMIN">ADMIN</option>
                                    <option value="LIBRARIAN">LIBRARIAN</option>
                                    <option value="READER">READER</option>
                                    <option value="GUEST">GUEST</option>
                                </select>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary">Change role</button>
                    </form>
                </c:if>
            </div>
        </div>
    </div>
</body>
</html>

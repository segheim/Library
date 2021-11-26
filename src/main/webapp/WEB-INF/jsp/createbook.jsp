<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CreateBook</title>
</head>
<body>
<h3>Please enter data for creating book:</h3>
<form name = "create_book_form" method="post" action="/controller?command=create_book">
    Title:<br/>
    <input type="text" name="title" value=""/>
    <br/>Published date:<br/>
    <input type="date" name="date_published" value=""/>
    <br/>Quantity of items:<br/>
    <input type="text" name="amount_of_left" value=""/>
    <br/>Author first name:<br/>
    <input type="text" name="author_first_name" value=""/>
    <br/>Author last name:<br/>
    <input type="text" name="author_last_name" value=""/>
    <br/>
    <c:if test="${not empty requestScope.errorLoginPassMessage}">
        <b style="color: red">${requestScope.errorLoginPassMessage}</b>
        <br>
    </c:if>
    <input type="submit" value="create"/>
</form>
</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book</title>
</head>
<body>
<h1>
    ${requestScope.book.title}
</h1>
<p>
    Author: <c:forEach var="author" items="${requestScope.book.authors}">
            <br>${author.first_name}
            ${author.last_name}
    </c:forEach>
</p>
<p>
    Date of published: ${requestScope.book.date_published}
</p>
<p>
    Quantity of items: ${requestScope.book.amount_of_left}
</p>

</body>
</html>

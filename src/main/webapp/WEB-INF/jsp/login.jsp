<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log in</title>
</head>
<body>
<h3>Please log in:</h3>
<form name = "login_form" method="post" action="/controller?command=login">
  Login:<br/>
  <input type="text" name="login" value=""/>
  <br/>Password:<br/>
  <input type="password" name="password" value=""/>
  <br/>
  <c:if test="${not empty requestScope.errorLoginPassMessage}">
    <b style="color: red">${requestScope.errorLoginPassMessage}</b>
<%--    <b>${requestScope.errorLoginPassMessage}</b>--%>
  <br>
  </c:if>
  <input type="submit" value="log in"/>
</form>
</body>
</html>
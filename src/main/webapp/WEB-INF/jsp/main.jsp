<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello! Welcome to JSP!!!</title>
</head>
<body>
<h1>Welcome to page Main.jsp</h1>
<i>Сегодня: <%= new Date() %></i>
<p>
    <a href="/controller?command=author_page">authors</a>
</p>
<p>
    <a href="/controller?command=catalog">catalog</a>
</p>
</body>
</html>

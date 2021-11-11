<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log in</title>
</head>
<body>
<h3>Please log in:</h3>
<form name = "login_form" method="POST" action="controller">
  Login:<br/>
  <input type="text" name="login" value=""/>
  <br/>Password:<br/>
  <input type="password" name="password" value=""/>
  <br/>
  <input type="submit" value="log in"/>
</form>
<hr/>
</body>
</html>

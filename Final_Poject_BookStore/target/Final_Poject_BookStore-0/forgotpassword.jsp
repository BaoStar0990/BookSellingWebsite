<%--
  Created by IntelliJ IDEA.
  User: hadan
  Date: 11/3/2024
  Time: 8:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="forgotpasword" method="post">
      <input type="text" value="Enter your email: " name="email"></input>
      <input type="hidden" value="RequestCode" name="action"></input>
      <input type="submit" value="Submit"></input>
    </form>
</body>
</html>

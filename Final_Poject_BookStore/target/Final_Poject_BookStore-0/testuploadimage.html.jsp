<%--
  Created by IntelliJ IDEA.
  User: hadan
  Date: 11/2/2024
  Time: 7:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Upload Image</title>
</head>
<body>
<h2>Upload Image</h2>
<form action="uploadServlet" method="post" enctype="multipart/form-data">
    <input type="file" name="imageFile" accept="image/*" required>
    <input type="text" name="nameOfFileOnCloud" placeholder="Enter name of file on cloud" required>
    <button type="submit">Upload</button>
</form>
</body>
</html>


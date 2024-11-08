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
<h2>Upload Book Image and Details</h2>
<form action="addbookcontroller" method="post" enctype="multipart/form-data">
    <input type="file" name="imageFile" accept="image/*" >
    <input type="text" name="nameOfFileOnCloud" placeholder="Enter name of file on cloud" ><br><br>

    <!--<label>Book ID:</label>
    <input type="number" name="bookID" ><br><br>-->

    <label>Title:</label>
    <input type="text" name="title" ><br><br>

    <label>Description:</label>
    <textarea name="description" placeholder="Enter book description" ></textarea><br><br>

    <label>ISBN:</label>
    <input type="text" name="isbn" ><br><br>

    <label>Cost Price:</label>
    <input type="number" step="0.01" name="costPrice" ><br><br>

    <label>Selling Price:</label>
    <input type="number" step="0.01" name="sellingPrice" ><br><br>

    <label>Stocks:</label>
    <input type="number" name="stock" ><br><br>

    <label>Publish Year:</label>
    <input type="number" step="1" name="publishYear" ><br><br>

    <label>Language:</label>
    <input type="text" name="language" ><br><br>

    <input type="submit" value="Upload"/>
</form>
</body>
</html>

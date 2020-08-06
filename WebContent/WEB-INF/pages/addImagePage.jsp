<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.utils.*" %>
<!DOCTYPE html>
<html>
<head>
    <link href="style.css" rel="stylesheet" type="text/css">
    <link rel='icon' type='image/x-icon' href='images/AFC.ico'/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <meta charset="UTF-8">
    <meta name="keywords" content="afc,A.F.C.,AFC,aldermaston,football">
    <meta name="description" content="AFC Aldermaston Football Club website">
    <meta name="abstract" content="AFC Aldermaston">
    <meta http-equiv="Content-Language" content="EN-GB">
    <meta name="author" content="Liam Burnand">
    <meta name="distribution" content="Global">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="copyright"
          content="© Copyright 2020 - <%=MyUtils.getYear()%>, AFC Aldermaston, website provided by Liam Burnand">
    <title>Official website of AFC Aldermaston - Add image</title>
</head>
<body>
<jsp:include page="_topPageConfirmation.jsp"/>
<main class="main" style="text-align: center;">
    <div style="background-color: white; margin: 10px 0 0 0; padding: 5px 0 0 0;">
        <h2 style="text-decoration: underline; text-decoration-color: red; width: 100vx; text-align: center;">Add
            image</h2>
        <p style="color: red; padding: 0; margin: 0;" id="error">${error}</p><br>
        <%request.getSession().setAttribute("error", null);%>
        <form id="add" method="POST" action="addimage" enctype="multipart/form-data">
            <div>
                <label for="image">Image: </label>
                <input type="file" id="image" name="image" accept="image/*" style="max-width: 300px;">
            </div>
            <div>
                <label for="caption">Caption: </label>
                <input type="text" id="caption" name="caption">
            </div>
            <p><a onclick="document.getElementById('add').submit();" href="javascript:{}">Add</a></p>
            <br>
            <p><a href="users">Cancel</a></p>
        </form>
    </div>
</main>
</body>
<footer>
    <small>&copy; Copyright 2020 - <%=MyUtils.getYear()%>, AFC Aldermaston, website provided by Liam Burnand</small>
</footer>
</html>
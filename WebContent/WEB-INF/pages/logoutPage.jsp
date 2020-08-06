<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.utils.*" %>
<!DOCTYPE html>
<html>
<head>
    <link href="styleHome.css" rel="stylesheet" type="text/css">
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
    <title>Official website of AFC Aldermaston - Logout</title>
    <style>
        form {
            margin: auto;
            width: 500px;
            padding: 1em;
            border: none;
            border-radius: 1em;
        }

        input {
            display: inline-flex;
            height: 40px;
            width: 250px;
            border: 2px solid #BFC0C0;
            margin: 20px 20px 20px 20px;
            text-decoration: none;
            letter-spacing: 2px;
            align-items: center;
            justify-content: center;
            overflow: hidden;
        }

        label {
            display: inline-block;
            width: 90px;
            text-align: right;
            position: relative;
            vertical-align: middle;
            margin: 20px 0 20px 0;
        }
    </style>
</head>
<body>
<jsp:include page="_topPage.jsp"/>
<main class="main" style="text-align: center;">
    <div style="background-color: white; margin: 10px 0 0 0; padding: 5px 0 0 0;">
        <h2 style="text-decoration: underline; text-decoration-color: red; width: 100vx; text-align: center;">
            Logout</h2>
        <p style="padding: 0; margin: 0; height: auto;">Are you sure you want to logout?</p>
        <p style="color: red; padding: 0; margin: 0;" id="error">${error}</p><br>
        <form id="logout" method="POST" action="logout">
            <div class="button" id="container">
                <div id="translate"></div>
                <a onclick="document.getElementById('logout').submit();" href="javascript:{}">Logout</a>
            </div>
            <div class="button" id="container">
                <div id="translate"></div>
                <a href="home">Cancel</a>
            </div>
        </form>
    </div>
    <p style="z-index: -1; opacity: 0; float: left; width: 96%;">AFC</p>
</main>
</body>
<footer>
    <small>&copy; Copyright 2020 - <%=MyUtils.getYear()%>, AFC Aldermaston, website provided by Liam Burnand</small>
</footer>
</html>
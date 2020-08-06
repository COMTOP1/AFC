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
    <title>Official website of AFC Aldermaston - Documents</title>
</head>
<body>
<jsp:include page="_topPage.jsp"/>
<main class="main" style="padding: 0 0 0 10px">
    <h2>Registration</h2>
    <a href="download?file=0" style="text-decoration: underline;">Download</a>
    <h2>Code of Conduct for players</h2>
    <a href="download?file=1" style="text-decoration: underline;">Download</a>
    <h2>Code of Conduct for spectators</h2>
    <a href="download?file=2" style="text-decoration: underline;">Download</a>
    <h2>Respect</h2>
    <a href="download?file=3" style="text-decoration: underline;">Download</a>
    <h2>GDPR</h2>
    <a href="download?file=4" style="text-decoration: underline;">Download</a>
    <h2>Equality Policy</h2>
    <a href="download?file=5" style="text-decoration: underline;">Download</a>
    <h2>Safeguarding Policy</h2>
    <a href="download?file=6" style="text-decoration: underline;">Download</a>
    <p style="z-index: -1; opacity: 0; float: left; width: 96%; height: 50px;">AFC</p>
</main>
<div id="socialBar">
    <a href="https://www.facebook.com/AFC-Aldermaston-114651238068/" class="fa fa-facebook"></a>
    <a href="https://twitter.com/afcaldermaston?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor"
       class="fa fa-twitter"></a>
</div>
</body>
<footer>
    <small>&copy; Copyright 2020 - <%=MyUtils.getYear()%>, AFC Aldermaston, website provided by Liam Burnand</small>
</footer>
</html>
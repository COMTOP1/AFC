<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.utils.*, com.bswdi.beans.Users, java.sql.Connection" %>
<!DOCTYPE html>
<html>
<head>
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
</head>
<body>
<div id="topPage">
    <img id="mainImage" class="faImage" style="float: right; height: 125px; width: 150px;" src="images/facs.jpeg">
    <img id="mainImage" src="images/AFC.png" alt="images/AFC.png">
    <header><h1>AFC ALDERMASTON</h1></header>
</div>
<div id="menuBarAlert" style="z-index: 1;">This Website is still under construction, please report any bugs to
    webmaster@afcaldermaston.co.uk
</div>
<%
    String email = MyUtils.getEmailInCookie(request);
    Connection con = MyUtils.getStoredConnection(request);
    Users user = null;
    try {
        user = DBUtils.findUser(con, email);
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
<div id="menuBar" style="z-index: 1">
    <div id="mobileMenu">
        <a href="javascript:{}" id="dropdown">&bull;&bull;&bull;</a>
        <div id="dropdown-content">
            <a href="home">Home</a>
            <a href="teams">Teams</a>
            <a href="news">News</a>
            <a href="whatson">What's on</a>
            <a href="gallery">Gallery</a>
            <a href="documents">Documents</a>
            <a href="sponsors">Sponsors</a>
            <a href="info">Info</a>
            <a href="contact">Contact</a>
            <%if (email == null || email.equals("")) {%>
            <a href="login">Login</a>
            <%
            } else {
                assert user != null;
                if (user.getRole() > 4 || user.getRole() == 1) {
            %>
            <a href="users">Users</a>
            <%}%>
            <a href="players">Players</a>
            <a href="account">Account</a>
            <a href="logout">Logout</a>
            <%}%>
        </div>
    </div>
    <div id="leftSide" style="color: white;">
        <div class="button">
            <a href="home" style="color: white;">Home</a>
        </div>
        |
        <div class="button">
            <a href="teams" style="color: white;">Teams</a>
        </div>
        |
        <div class="button">
            <a href="news" style="color: white;">News</a>
        </div>
        |
        <div class="button">
            <a href="whatson" style="color: white;">What's on</a>
        </div>
        |
        <div class="button">
            <a href="gallery" style="color: white;">Gallery</a>
        </div>
        |
        <div class="button">
            <a href="documents" style="color: white;">Documents</a>
        </div>
        |
        <div class="button">
            <a href="sponsors" style="color: white;">Sponsors</a>
        </div>
        |
        <div class="button">
            <a href="info" style="color: white;">Info</a>
        </div>
        |
        <div class="button">
            <a href="contact" style="color: white;">Contact</a>
        </div>
    </div>
    <div id="rightSide"
         style="color: white;<%if (email != null && !email.equals("")) {%> width: <%if (user.getRole() > 4 || user.getRole() == 1) {%>290px;<%} else {%>150px;<%}}%>">
        <%if (email == null || email.equals("")) {%>
        <div class="button" align="right">
            <a href="login" style="color: white;">Login</a>
        </div>
        <%
        } else {
            if (user.getRole() > 4 || user.getRole() == 1) {
        %>
        <div class="button" align="right">
            <a href="users" style="color: white;">Users</a>
        </div>
        |
        <%}%>
        <div class="button" align="right">
            <a href="players" style="color: white;">Players</a>
        </div>
        |
        <div class="button" align="right">
            <a href="account" style="color: white;">Account</a>
        </div>
        |
        <div class="button" align="right">
            <a href="logout" style="color: white;">Logout</a>
        </div>
        <%}%>

    </div>
</div>
</body>
</html>
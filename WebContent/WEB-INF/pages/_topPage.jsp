<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.utils.*, com.bswdi.beans.Users, java.sql.Connection, com.bswdi.beans.Role" %>
<!DOCTYPE html>
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.6.0.slim.min.js"
            integrity="sha256-u7e5khyithlIdTpu22PHhENmPcRdFiHRjhAuHcs05RI=" crossorigin="anonymous"></script>
</head>
<body>
<div id="topPage">
    <img id="mainImage" class="faImage" style="float: right; height: 125px; width: 150px;" src="images/facs.jpeg" alt="images/default.png">
    <img id="mainImage" src="images/AFC.png" alt="images/AFC.png">
    <header><h1>AFC ALDERMASTON</h1></header>
</div>
<!--<div id="menuBarAlert" style="z-index: 1;">This Website is still under construction, please report any bugs to
    webmaster@afcaldermaston.co.uk
</div>-->
<%
    Connection con = MyUtils.getStoredConnection(request);
    Users user = MyUtils.getUser(request, response, con);
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
            <a href="programmes">Programmes</a>
            <a href="sponsors">Sponsors</a>
            <a href="info">Info</a>
            <a href="contact">Contact</a>
            <%if (user == null) {%>
            <a href="login">Login</a>
            <%
            } else {
                assert user != null;
                if (user.getRole() == Role.CLUB_SECRETARY || user.getRole() == Role.CHAIRPERSON || user.getRole() == Role.WEBMASTER) {
            %>
            <a href="users">Users</a>
            <%}%>
            <a href="players">Players</a>
            <a href="account">Account</a>
            <a href="logout">Log out</a>
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
            <a href="programmes" style="color: white;">Programmes</a>
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
         style="color: white;<%if (user != null) {%> width: <%if (user.getRole() == Role.CLUB_SECRETARY || user.getRole() == Role.CHAIRPERSON || user.getRole() == Role.WEBMASTER) {%>281px;<%} else {%>150px;<%}}%>">
        <%if (user == null) {%>
        <div class="button" align="right">
            <a href="login" style="color: white;">Login</a>
        </div>
        <%
        } else {
            if (user.getRole() == Role.CLUB_SECRETARY || user.getRole() == Role.CHAIRPERSON || user.getRole() == Role.WEBMASTER) {
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
            <a href="logout" style="color: white;">Log out</a>
        </div>
        <%}%>

    </div>
</div>
</body>
</html>
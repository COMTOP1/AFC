<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.beans.*, java.sql.Connection,com.bswdi.utils.*, java.util.List" %>
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
    <title>Official website of AFC Aldermaston - Teams</title>
</head>
<body>
<jsp:include page="_topPage.jsp"/>
<main class="main">
    <%
        Connection con = MyUtils.getStoredConnection(request);
        String email = MyUtils.getEmailInCookie(request);
        Users user = null;
        try {
            user = DBUtils.findUser(con, email);
        } catch (Exception ignored) {

        }
        List<Teams> list = null;
        if (user != null && user.getRole() > 0) {
            try {
                list = DBUtils.queryTeams(con);
            } catch (Exception ignored) {

            }
        } else {
            try {
                list = DBUtils.queryTeamsActive(con);
            } catch (Exception ignored) {

            }
        }
        assert list != null;
        for (Teams team : list) {
    %>
    <div id="listItem" style="text-align: center; width: auto; height: auto;">
        <div>
            <a href="team?id=<%=team.getID()%>">
                <%
                    if (user != null && user.getRole() > 0) {
                        String active = (team.getActive()) ? "Active" : "Inactive";
                %>
                <%=active%><br><br><br>
                <%}%>
                <%=team.getName()%>
            </a></div>
        <br>
        <%if (user != null && user.getRole() > 0) {%>
        <p><a href="editteam?id=<%=team.getID()%>">Edit team</a></p><br>
        <p><a href="deleteteam?id=<%=team.getID()%>">Delete team</a></p>
        <%}%>
    </div>
    <%}%>
    <p style="z-index: -1; opacity: 0; float: left; width: 96%;">AFC</p>
</main>
<%if (user != null && user.getRole() > 0) {%>
<p style="width: 96%;">
    <a href="addteam">Add team</a>
</p>
<%}%>
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
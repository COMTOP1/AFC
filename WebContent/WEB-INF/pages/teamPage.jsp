<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.beans.*, java.sql.Connection, com.bswdi.utils.*, java.util.List" %>
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
    <%Teams team = (Teams) request.getAttribute("team");%>
    <title>Official website of AFC Aldermaston - <%=team.getName()%>
    </title>
</head>
<body>
<jsp:include page="_topPage.jsp"/>
<main class="main" style="padding: 0 10px;">
    <div style="width: 100%; overflow: auto;">
        <div style="float:left; width: 50%">
            <%
                Connection con = MyUtils.getStoredConnection(request);
                String email = MyUtils.getEmailInCookie(request);
                Users user = null;
                try {
                    user = DBUtils.findUser(con, email);
                } catch (Exception ignored) {

                }
            %>
            <h2><%=team.getName()%>
            </h2>
            <h3>Team Manager(s)</h3>
            <%
                List<Users> list = null;
                try {
                    list = DBUtils.queryUsersManagersTeam(con, team.getID());
                } catch (Exception ignored) {

                }
                assert list != null;
                for (Users manager : list) {
            %>
            <%=manager.getName()%>
            <%}%><br><br>
            <h3>Training Location</h3>
            AWE Recreational Society<br><br>
            <%if (team.getAges() >= 13) {%>
            <h3>League</h3>
            <%=team.getLeague()%><br><br>
            <h3>Division</h3>
            <%=team.getDivision()%><br><br>
            <h3>League Table</h3>
            <a href="<%=team.getLeagueTable()%>" target="_blank" style="text-decoration: underline;">League
                Table</a><br><br>
            <h3>Fixtures</h3>
            <a href="<%=team.getFixtures()%>" target="_blank"
               style="text-decoration: underline;">Fixtures</a><br><br><br>
            <%}%>
        </div>
        <div style="float:right; text-align: right; width: 50%;">
            <h2>Team photo</h2>
            <img src="data:image/jpg;base64,<%=team.getTeamPhoto()%>" alt=""
                 onerror="this.onerror=null;this.src='images/default.png';"
                 style="padding: 5px; max-height: 1000px; max-width: 700px; width: 100%;">
        </div>
    </div>
    <p>
            <%List<Sponsors> list1 = null;
            try {
            	list1 = DBUtils.querySponsorsTeam(con, String.valueOf(team.getID()), team.getYouth());
            } catch (Exception ignored) {

            }%>
    <div display="inline-block" style="width: 100%;<%if (list1.size() > 0) {%> height: 300px;<%}%>">
        <%for (Sponsors sponsor : list1) {%>
        <div id="listItem">
            <div>
                <a href="<%=sponsor.getWebsite()%>" target="_blank">
                    <img src="data:image/jpg;base64,<%=sponsor.getImage()%>" alt=""
                         onerror="this.onerror=null;this.src='images/default.png';" style="padding: 5px;">
                    <%=sponsor.getName()%><br></br><br>
                    <%=sponsor.getPurpose()%>
                </a></div>
        </div>
        <%}%>
    </div>
    <br><br><br>
    <%
        if (!team.getYouth()) {
            List<Players> list2 = null;
            try {
                list2 = DBUtils.queryPlayersTeam(con, team.getID());
            } catch (Exception ignored) {

            }
    %>
    <h3>Players</h3><br>
    <div display="inline-block" style="width: 100%;<%if (list2.size() > 0) {%> height: 300px;<%}%>">
        <%for (Players player : list2) {%>
        <div id="listItem" style="height: 200px;">
            <div>
                <img src="data:image/jpg;base64,<%=player.getImage()%>" alt=""
                     onerror="this.onerror=null;this.src='images/default.png';" style="padding: 5px;">
                <%=player.getName()%><br>
                <%=player.getPosition()%><br>
                <%=(player.getCaptain()) ? "Captain" : ""%>
            </div>
        </div>
        <%}%>
    </div>
    <%}%>
    </p>
    <p style="z-index: -1; opacity: 0; float: left; width: 98%;">AFC</p>
</main>
<%if (user != null && user.getRole() > 0) {%>
<p style="width: 96%;">
    <a href="editteam?id=<%=team.getID()%>">Edit team</a>
</p>
<p style="width: 96%;">
    <a href="deleteteam?id=<%=team.getID()%>">Delete team</a>
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
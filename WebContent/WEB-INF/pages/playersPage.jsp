<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.beans.*, java.sql.Connection, com.bswdi.utils.*, java.util.List, java.time.LocalDate" %>
<!DOCTYPE html>
<html>
<head>
   <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - Players</title>
    <%
        Connection con = MyUtils.getStoredConnection(request);
        List<Players> list = null;
        try {
            list = DBUtils.queryPlayers(con);
        } catch (Exception ignored) {

        }
    %>
</head>
<body>
<jsp:include page="_topPage.jsp"/>
<main class="main" style="text-align: center;">
    <p style="color: green; padding: 0; margin: 0;" id="success">${success}</p><br>
    <p style="color: red; padding: 0; margin: 0;" id="error">${error}</p><br>
    <%
        request.getSession().setAttribute("success", null);
        request.getSession().setAttribute("error", null);
    %>
    <table style="align-items: center;">
        <tr>
            <th>Name</th>
            <th>Image</th>
            <th>Date of birth</th>
            <th>Captain</th>
            <th>Team</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        <%
            assert list != null;
            for (Players player : list) {%>
        <tr>
            <th><%=player.getName()%>
            </th>
            <%if (player.getAge() >= 18) {%>
            <th><img src="data:image/jpg;base64,<%=player.getImage()%>" alt=""
                     onerror="this.onerror=null;this.src='images/default.png';"
                     style="max-width: 150px; max-height: 150px;"/></th>
            <%
                }
                LocalDate date = LocalDate.ofEpochDay(player.getDateOfBirth());
                String dateString = null;
                try {
                    dateString = date.getDayOfMonth() + " "
                            + String.valueOf(date.getMonth()).charAt(0) + String.valueOf(date.getMonth()).substring(1).toLowerCase() + " "
                            + date.getYear();
                } catch (Exception ignored) {

                }
            %>
            <th><%=dateString%>
            </th>
            <%
                String captain = "No";
                if (player.getCaptain()) captain = "Captain";
            %>
            <th><%=captain%>
            </th>
            <%
                String team;
                Teams team1 = null;
                try {
                    team1 = DBUtils.findTeam(con, player.getTeamID());
                } catch (Exception ignored) {

                }
                assert team1 != null;
                team = team1.getName();
            %>
            <th><%=team%>
            </th>
            <th>
                <div class="button" id="container">
                    <div id="translate"></div>
                    <a href="editplayer?id=<%=player.getID()%>">Edit</a>
                </div>
            </th>
            <th>
                <div class="button" id="container">
                    <div id="translate"></div>
                    <a href="deleteplayer?id=<%=player.getID()%>">Delete</a>
                </div>
            </th>
        </tr>
        <%}%>
    </table>
    <br>
    <p style="z-index: -1; opacity: 0; float: left; width: 96%;">AFC</p>
</main>
<p style="width: 96%">
    <a href="addplayer">Add player</a>
</p>
<div id="socialBar">
    <a href="https://www.facebook.com/AFC-Aldermaston-114651238068/" target="_blank" class="fa fa-facebook"></a>
    <a href="https://twitter.com/afcaldermaston?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor" target="_blank"
       class="fa fa-twitter"></a>
</div>
</body>
<jsp:include page="_footerPage.jsp"/>
</html>
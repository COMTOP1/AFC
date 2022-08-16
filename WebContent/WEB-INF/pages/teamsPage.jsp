<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.beans.*, java.sql.Connection,com.bswdi.utils.*, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - Teams</title>
</head>
<body>
<jsp:include page="_topPage.jsp"/>
<main class="main">
    <%
        Connection con = MyUtils.getStoredConnection(request);
        Users user = MyUtils.getUser(request, con);
        List<Teams> list = null;
        if (user != null && user.getRole() != Role.MANAGER) {
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
    <div id="listItem" style="text-align: center; width: auto; height: auto;" style="display: block; cursor: pointer;" onclick="location.href='team?id=<%=team.getID()%>';">
                <%
                    if (user != null && user.getRole() != Role.MANAGER) {
                        String active = (team.getActive()) ? "Active" : "Inactive";
                %>
                <%=active%><br><br><br>
                <%}%>
                <%=team.getName()%>
        <br>
        <%if (user != null && user.getRole() != Role.MANAGER) {%>
        <p><a href="editteam?id=<%=team.getID()%>">Edit team</a></p><br>
        <p><a href="deleteteam?id=<%=team.getID()%>">Delete team</a></p>
        <%}%>
    </div>
    <%}%>
    <p style="z-index: -1; opacity: 0; float: left; width: 96%;">AFC</p>
</main>
<%if (user != null && user.getRole() != Role.MANAGER) {%>
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
<jsp:include page="_footerPage.jsp"/>
</html>
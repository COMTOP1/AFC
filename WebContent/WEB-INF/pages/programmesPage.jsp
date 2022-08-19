<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page
        import="com.bswdi.beans.*, java.sql.Connection, com.bswdi.utils.*, java.util.List, java.time.LocalDate, java.util.Date" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - Programmes</title>
</head>
<body>
<jsp:include page="_topPage.jsp"/>
<main class="main" style="text-align: center;"><p style="color: red; padding: 0; margin: 0;" id="error">${error}</p><br>
    <%
        request.getSession().setAttribute("error", null);
        Connection con = MyUtils.getStoredConnection(request);
        Users user = MyUtils.getUser(request, response, con);
        List<Programmes> list = null;
        try {
            list = DBUtils.queryProgrammes(con);
        } catch (Exception ignored) {

        }
        if (list != null && list.size() != 0) {
            assert list != null;
            boolean first = true;
            for (Programmes programme : list) {
                if (first) {
                    first = false;%>
    <h2>Latest programme - <%=programme.getName()%>
    </h2><%
        Date date = new Date(programme.getDateOfProgramme());
        String[] dateArray = date.toString().split(" ");
        String dateString = String.format("%s %s %s %s", dateArray[0], dateArray[2], dateArray[1], dateArray[5]);
        if (programme.getProgrammeSeasonID() != -1) {
            ProgrammeSeasons programmeSeason = DBUtils.findProgrammeSeason(con, programme.getProgrammeSeasonID());
            if (programmeSeason != null)
    %>
    <p>Season - <%=programmeSeason.getSeason()%>
    </p>
    <%}%>
    <p>Date of programme - <%=dateString%>
    </p>
    <iframe src="FileStore/<%=programme.getFileName()%>" width="75%" height="500px"></iframe>
    <br><br><br>
    <%if (user != null && user.getRole() != Role.MANAGER) {%>
    <div class="button">
        <a href="deleteprogramme?id=<%=programme.getID()%>">Delete</a>
    </div>
    <%}%>
    <br><br><br>
    <%
    } else {
    %>

    <div id="listItem" style="display: block; cursor: pointer;"
         onclick="location.href('download?id=<%=programme.getID()%>&s=p');">
        <span style="margin: 0.83em 0 0.83em 0; display: block; font-size: 1.5em; font-weight: bold;"><%=programme.getName()%></span><%
        Date date = new Date(programme.getDateOfProgramme());
        String[] dateArray = date.toString().split(" ");
        String dateString = String.format("%s %s %s %s", dateArray[0], dateArray[2], dateArray[1], dateArray[5]);
        if (programme.getProgrammeSeasonID() != -1) {
            ProgrammeSeasons programmeSeason = DBUtils.findProgrammeSeason(con, programme.getProgrammeSeasonID());%>
        <p>Season - <%=programmeSeason.getSeason()%>
        </p>
        <%}%>
        <p>Date of programme - <%=dateString%>
        </p>
        <p>Click to download</p>
        <%if (user != null && user.getRole() != Role.MANAGER) {%>
        <div class="button">
            <a href="deleteprogramme?id=<%=programme.getID()%>">Delete</a>
        </div>
        <%}%>
    </div>
    <%
            }
        }
    } else {%>
    <p style="color: red; padding: 0; margin: 0;">There are no programmes to display</p>
    <%
        }
    %>
    <p style="z-index: -1; opacity: 0; float: left; width: 96%;">AFC</p>
</main>
<%if (user != null && user.getRole() != Role.MANAGER) {%>
<p style="width: 96%;">
    <a href="addprogramme">Add programme</a><br>
    <a href="addprogrammeseason">Add programme season</a>
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
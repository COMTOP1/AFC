<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page
        import="com.bswdi.beans.*, java.sql.Connection, com.bswdi.utils.*, java.util.List, java.util.Date, java.time.LocalDate" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - What's On</title>
</head>
<body>
<jsp:include page="_topPage.jsp"/>
<main class="main" style="text-align: center;">
    <%
        Connection con = MyUtils.getStoredConnection(request);
        List<WhatsOn> list = null;
        try {
            list = DBUtils.queryWhatsOnEventDate(con);
        } catch (Exception ignored) {

        }
        Users user = MyUtils.getUser(request, con);
        assert list != null;
        for (WhatsOn whatsOn : list) {
    %>
    <a href="whatson?id=<%=whatsOn.getID()%>">
        <div id="listItem" style="display: block; cursor: pointer;"
             onclick="location.href='whatson?id=<%=whatsOn.getID()%>';">
            <img src="data:image/jpg;base64,<%=whatsOn.getImage()%>" alt=""
                 onerror="this.onerror=null;this.src='images/default.png';" style="padding: 5px;">
            <span style="margin: 0.83em 0 0.83em 0; display: block; font-size: 1.5em; font-weight: bold;"><%=whatsOn.getTitle()%></span>
            <%
                Date date1 = new Date(whatsOn.getDate());
                String dateOfEvent = "";
                try {
                    LocalDate date = LocalDate.ofEpochDay(whatsOn.getDateOfEvent());
                    dateOfEvent = String.valueOf(date.getDayOfWeek()).charAt(0) + String.valueOf(date.getDayOfWeek()).substring(1).toLowerCase() + " "
                            + date.getDayOfMonth() + " "
                            + String.valueOf(date.getMonth()).charAt(0) + String.valueOf(date.getMonth()).substring(1).toLowerCase() + " "
                            + date.getYear();
                } catch (Exception ignored) {

                }
            %>
            <p style="text-align: left; padding: 10px 10px 10px 0;"><%=date1.toString()%><br>
                Date of Event - <%=dateOfEvent%>
            </p>
            <%if (user != null && user.getRole() != Role.MANAGER) {%>
            <div class="button" id="container">
                <div id="translate"></div>
                <a href="editwhatson?id=<%=whatsOn.getID()%>">Edit</a>
            </div>
            <div class="button" id="container">
                <div id="translate"></div>
                <a href="deletewhatson?id=<%=whatsOn.getID()%>">Delete</a>
            </div>
            <%}%>
        </div>
            <%}%>
        <p style="z-index: -1; opacity: 0; float: left; width: 96%;">AFC</p>
</main>
<%if (user != null && user.getRole() != Role.MANAGER) {%>
<p style="width: 96%;">
    <a href="addwhatson">Add what's on</a>
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
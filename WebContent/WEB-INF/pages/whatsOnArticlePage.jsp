<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.beans.*, java.sql.Connection, com.bswdi.utils.*, java.util.Date, java.time.LocalDate" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <%
        Connection con = MyUtils.getStoredConnection(request);
        WhatsOn whatsOn = (WhatsOn) request.getAttribute("whatsOn");
    %>
    <title>Official website of AFC Aldermaston - <%=whatsOn.getTitle()%>
    </title>
</head>
<body>
<jsp:include page="_topPage.jsp"/>
<main class="main" style="text-align: center; overflow: inherit;">
    <div style="background-color: white; margin: 10px 10px 0 10px; padding: 5px 5px 10px 5px; display: table">
        <div class="button" id="container">
            <div id="translate"></div>
            <a href="whatson">Return</a>
        </div>
        <h2><%=whatsOn.getTitle()%>
        </h2>
        <%
            Date date = new Date(whatsOn.getDate());
            String[] dateArray = date.toString().split(" ");
            String email = MyUtils.getEmailInCookie(request);
            Users user = null;
            try {
                user = DBUtils.findUser(con, email);
            } catch (Exception ignored) {

            }
            String dateString = String.format("%s %s %s %s", dateArray[0], dateArray[2], dateArray[1], dateArray[5]);
            String dateOfEvent = "";
            try {
                LocalDate date1 = LocalDate.ofEpochDay(whatsOn.getDateOfEvent());
                dateOfEvent = String.valueOf(date1.getDayOfWeek()).charAt(0) + String.valueOf(date1.getDayOfWeek()).substring(1).toLowerCase() + " "
                        + date1.getDayOfMonth() + " "
                        + String.valueOf(date1.getMonth()).charAt(0) + String.valueOf(date1.getMonth()).substring(1).toLowerCase() + " "
                        + date1.getYear();
            } catch (Exception ignored) {

            }
        %>
        <h3><%=dateString%><br><br>
            Date of event - <%=dateOfEvent%>
            <br></h3>
        <div id="col-1"><p><%=whatsOn.getContent()%>
        </p></div>
        <div id="col-2"><img src="data:image/jpg;base64,<%=whatsOn.getImage()%>" alt=""
                             onerror="this.onerror=null;this.src='images/default.png';"
                             style="padding: 5px; width: 50%; max-height: 500px; max-width: 700px;"></div>
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
    <p style="z-index: -1; opacity: 0; float: left; width: 96%;">AFC</p>
</main>
<div id="socialBar">
    <a href="https://www.facebook.com/AFC-Aldermaston-114651238068/" class="fa fa-facebook"></a>
    <a href="https://twitter.com/afcaldermaston?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor"
       class="fa fa-twitter"></a>
</div>
</body>
<jsp:include page="_footerPage.jsp"/>
</html>
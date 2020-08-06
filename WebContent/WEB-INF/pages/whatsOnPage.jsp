<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page
        import="com.bswdi.beans.*, java.sql.Connection, com.bswdi.utils.*, java.util.List, java.util.Date, java.time.LocalDate" %>
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
        String email = MyUtils.getEmailInCookie(request);
        Users user = null;
        try {
            user = DBUtils.findUser(con, email);
        } catch (Exception ignored) {

        }
        assert list != null;
        for (WhatsOn whatsOn : list) {
    %>
    <div id="listItem">
        <div>
            <a href="whatson?id=<%=whatsOn.getID()%>">
                <img src="data:image/jpg;base64,<%=whatsOn.getImage()%>" alt=""
                     onerror="this.onerror=null;this.src='images/default.png';" style="padding: 5px;">
                <h2 style="margin: 5px 0 5px 0;"><%=whatsOn.getTitle()%>
                </h2>
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
                <%=date1.toString()%><br>
                Date of Event - <%=dateOfEvent%><br>
                <%if (user != null && user.getRole() > 0) {%>
                <div class="button" id="container">
                    <div id="translate"></div>
                    <a href="editwhatson?id=<%=whatsOn.getID()%>">Edit</a>
                </div>
                <div class="button" id="container">
                    <div id="translate"></div>
                    <a href="deletewhatson?id=<%=whatsOn.getID()%>">Delete</a>
                </div>
                <%}%>
            </a></div>
    </div>
    <%}%>
    <p style="z-index: -1; opacity: 0; float: left; width: 96%;">AFC</p>
</main>
<%if (user != null && user.getRole() > 0) {%>
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
<footer>
    <small>&copy; Copyright 2020 - <%=MyUtils.getYear()%>, AFC Aldermaston, website provided by Liam Burnand</small>
</footer>
</html>
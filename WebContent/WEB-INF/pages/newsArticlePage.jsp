<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.beans.*, java.sql.Connection, com.bswdi.utils.*, java.util.Date" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <%
        Connection con = MyUtils.getStoredConnection(request);
        News news = (News) request.getAttribute("news");
    %>
    <title>Official website of AFC Aldermaston - <%=news.getTitle()%>
    </title>
</head>
<body>
<jsp:include page="_topPage.jsp"/>
<main class="main" style="text-align: center; overflow: inherit;">
    <div style="background-color: white; margin: 10px 10px 0 10px; padding: 5px 0 0 0;">
        <div class="button" id="container">
            <div id="translate"></div>
            <a href="news">Return</a>
        </div>
        <h2><%=news.getTitle()%>
        </h2>
        <%
            Date date = new Date(news.getDate());
            String[] dateArray = date.toString().split(" ");
            String email = MyUtils.getEmailInCookie(request);
            Users user = null;
            try {
                user = DBUtils.findUser(con, email);
            } catch (Exception ignored) {

            }
            String dateString = String.format("%s %s %s %s", dateArray[0], dateArray[2], dateArray[1], dateArray[5]);
        %>
        <h3><%=dateString%><br></h3>
        <img src="data:image/jpg;base64,<%=news.getImage()%>" alt=""
             onerror="this.onerror=null;this.src='images/default.png';"
             style="padding: 5px; width: 50%; max-height: 500px; max-width: 700px;"><br>
        <p><%=news.getContent()%>
        </p>
        <%if (user != null && user.getRole() > 0) {%>
        <div class="button" id="container">
            <div id="translate"></div>
            <a href="editnews?id=<%=news.getID()%>">Edit</a>
        </div>
        <div class="button" id="container">
            <div id="translate"></div>
            <a href="deletenews?id=<%=news.getID()%>">Delete</a>
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
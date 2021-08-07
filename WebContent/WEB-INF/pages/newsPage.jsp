<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.beans.*, java.sql.Connection, com.bswdi.utils.*, java.util.List, java.util.Date" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - News</title>
</head>
<body>
<jsp:include page="_topPage.jsp"/>
<main class="main" style="text-align: center;">
    <%
        Connection con = MyUtils.getStoredConnection(request);
        String email = MyUtils.getEmailInCookie(request);
        Users user = null;
        try {
            user = DBUtils.findUser(con, email);
        } catch (Exception ignored) {

        }
        List<News> list = null;
        try {
            list = DBUtils.queryNews(con);
        } catch (Exception ignored) {

        }
        assert list != null;
        for (News news : list) {
    %>
    <div id="listItem">
        <div>
            <a href="news?id=<%=news.getID()%>">
                <img src="data:image/jpg;base64,<%=news.getImage()%>" alt=""
                     onerror="this.onerror=null;this.src='images/default.png';" style="padding: 5px; width: auto;">
                <span style="margin: 0.83em 0 0.83em 0; display: block; font-size: 1.5em; font-weight: bold;"><%=news.getTitle()%></span>
                <%Date date = new Date(news.getDate());%>
                <p style="text-align: left; padding: 10px 10px 10px 0;"><%=date.toString()%></p>
                </a>
                <%if (user != null && user.getRole() != Role.MANAGER) {%>
                <div class="button">
            		<a href="editnews?id=<%=news.getID()%>">Edit</a>
        		</div>
        		<div class="button">
            		<a href="deletenews?id=<%=news.getID()%>">Delete</a>
        		</div>
                <%}%>
            </div>
    </div>
    <%}%>
    <p style="z-index: -1; opacity: 0; float: left; width: 96%;">AFC</p>
</main>
<%if (user != null && user.getRole() != Role.MANAGER) {%>
<p style="width: 96%;">
    <a href="addnews">Add news</a>
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
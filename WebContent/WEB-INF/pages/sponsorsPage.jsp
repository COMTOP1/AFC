<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.beans.*, java.sql.Connection,com.bswdi.utils.*, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - Sponsors</title>
</head>
<body>
<jsp:include page="_topPage.jsp"/>
<main class="main" style="text-align: center;">
    <div style="width: 98%; height: 325px;">
        <%
            Connection con = MyUtils.getStoredConnection(request);
            String email = MyUtils.getEmailInCookie(request);
            Users user = null;
            try {
                user = DBUtils.findUser(con, email);
            } catch (Exception ignored) {

            }
            List<Sponsors> list = null;
            try {
                list = DBUtils.querySponsors(con);
            } catch (Exception ignored) {

            }
            assert list != null;
            for (Sponsors sponsor : list) {
        %>
        <div id="listItem">
            <div>
                <a href="<%=sponsor.getWebsite()%>" target="_blank">
                    <img src="data:image/jpg;base64,<%=sponsor.getImage()%>" alt=""
                         onerror="this.onerror=null;this.src='images/default.png';" style="padding: 5px;">
                    <%=sponsor.getName()%><br><br><br>
                    <%=sponsor.getPurpose()%><br><br>
                    <%if (user != null && user.getRole() > 0) {%>
                    <div class="button" id="container">
                        <div id="translate"></div>
                        <a href="editsponsor?id=<%=sponsor.getID()%>">Edit</a>
                    </div>
                    <div class="button" id="container">
                        <div id="translate"></div>
                        <a href="deletesponsor?id=<%=sponsor.getID()%>">Delete</a>
                    </div>
                    <%}%>
                </a></div>
        </div>
        <%}%>
    </div>
    <br>
    <p style="z-index: -1; opacity: 0; float: left; width: 96%;">AFC</p>
</main>
<%if (user != null && user.getRole() > 0) {%>
<p style="width: 96%">
    <a href="addsponsor">Add sponsor</a>
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
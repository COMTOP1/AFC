<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.beans.*, java.sql.Connection, com.bswdi.utils.*, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - Contact</title>
</head>
<body>
<jsp:include page="_topPage.jsp"/>
<main class="main" style="padding: 10px; text-align: center;">
    <br>
    <%
        Connection con = MyUtils.getStoredConnection(request);
        List<Users> list = null;
        try {
            list = DBUtils.queryUsersContacts(con);
        } catch (Exception ignored) {

        }
        assert list != null;
        for (Users user : list) {
    %>
    <div id="listItemContact" style="display: inline-block; float: none;">
        <div>
            <div style="height: 200px;">
                <img src="data:image/jpg;base64,<%=user.getImage()%>" alt=""
                     onerror="this.onerror=null;this.src='images/default.png';"
                     style="padding: 5px; max-height: 200px;">
            </div>
            <br><%=user.getName()%><br><br>
            <%=UserRole.getRole(user.getRole())%><br>
            <%=user.getEmail()%>
        </div>
    </div>
    <%}%>
    <p style="z-index: -1; opacity: 0; float: left; width: 98%; height: 25px; margin: 0;">AFC</p>
    <div style="width: 100%; background-color: white; text-align: center; padding: 10px 0 10px 0; margin: 10px 0 10px 0; display: block; float: left;">
        <b>IF YOU ARE USING A SATNAV THEN PUT IN THE POSTCODE: <span style="text-decoration: underline;">RG26 4QP</span><br>THE
            POSTCODE LISTED TAKES YOU SOME
            DISTANCE AWAY</b></div>
    <br><br><br>
    <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d8939.983502968527!2d-1.1655729266615455!3d51.361551652298395!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x4876a0226ede6355%3A0xefc85c4cd2bbf09b!2sAldermaston%20Recreational%20Society!5e1!3m2!1sen!2suk!4v1587086773128!5m2!1sen!2suk"
            width="100%" height="500" style="border:0;" aria-hidden="false" tabindex="0"></iframe>
    <p style="z-index: -1; opacity: 0; float: left; width: 98%; height: 50px;">AFC</p>
</main>
<div id="socialBar" style="z-index: 0;">
    <a href="https://www.facebook.com/AFC-Aldermaston-114651238068/" target="_blank" class="fa fa-facebook"></a>
    <a href="https://twitter.com/afcaldermaston?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor" target="_blank"
       class="fa fa-twitter"></a>
</div>
</body>
<jsp:include page="_footerPage.jsp"/>
</html>
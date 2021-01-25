<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.beans.*, java.sql.Connection, com.bswdi.utils.*" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - Account</title>
    <%
        Connection con = MyUtils.getStoredConnection(request);
        String email = MyUtils.getEmailInCookie(request);
        Users user = null;
        try {
            user = DBUtils.findUser(con, email);
        } catch (Exception ignored) {

        }
    %>
</head>
<body>
<jsp:include page="_topPage.jsp"/>
<main class="main" style="text-align: center;">
    <h1 style="margin: 20px 0;">Your account details</h1>
    <p style="color: green; padding: 0; margin: 0;">${passwordchanged}</p>
    <%request.getSession().setAttribute("passwordchanged", null);%>
    <p style="color: red; padding: 0; margin: 0;">${error}</p>
    <%request.getSession().setAttribute("error", null);%>
    <h2>Name</h2><%assert user != null;%>
    <%=user.getName()%>
    <h2>Email</h2>
    <%=user.getEmail()%>
    <h2>Role</h2>
    <%=user.getRole()%>
    <h2>Change password</h2>
    <%request.getSession().setAttribute("allowedOwnUserModification", true);%>
    <div class="button" id="container">
        <div id="translate"></div>
        <a href="changepassword?email=<%=user.getEmail()%>" style="text-decoration: underline;">Change password</a>
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
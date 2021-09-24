<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.utils.*, com.bswdi.beans.*, java.util.List, java.sql.Connection" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - Documents</title>
</head>
<body>
<jsp:include page="_topPage.jsp"/>
<%
    Connection con = MyUtils.getStoredConnection(request);
    String email = MyUtils.getEmailInCookie(request);
    Users user = null;
    try {
        user = DBUtils.findUser(con, email);
    } catch (Exception ignored) {

    }
%>
<main class="main" style="padding: 0 0 0 10px">
	<p style="color: red; padding: 0; margin: 0;" id="error">${error}</p><br>
    <%
    	request.getSession().setAttribute("error", null);
        List<Documents> list = null;
        try {
            list = DBUtils.queryDocuments(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert list != null;
        for (Documents document : list) {
    %>
    <h2><%=document.getName()%>
    </h2>
    <a href="download?id=<%=document.getID()%>&s=d" style="text-decoration: underline;">Download</a><%if (user != null && user.getRole() != Role.MANAGER) {
    	for (int i = 0; i < 21; i++) {%>&emsp;<%}%><a href="deletedocument?id=<%=document.getID()%>">Delete</a>
    <%
            }
        }
    %>
    <p style="z-index: -1; opacity: 0; float: left; width: 96%; height: 50px;">AFC</p>
</main>
<%if (user != null && user.getRole() != Role.MANAGER) {%>
<p style="width: 96%;">
    <a href="adddocument">Add document</a>
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
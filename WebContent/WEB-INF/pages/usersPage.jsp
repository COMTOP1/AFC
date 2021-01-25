<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.beans.*, java.sql.Connection, com.bswdi.utils.*, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - Users</title>
    <%
        Connection con = MyUtils.getStoredConnection(request);
        String email = MyUtils.getEmailInCookie(request);
        Users user = null;
        try {
            user = DBUtils.findUser(con, email);
        } catch (Exception ignored) {

        }
        List<Users> list = null;
        try {
            list = DBUtils.queryUsers(con);
        } catch (Exception ignored) {

        }
    %>
</head>
<body>
<jsp:include page="_topPage.jsp"/>
<main class="main" style="text-align: center;">
    <p style="color: green; padding: 0; margin: 0;" id="error">${success}</p><br>
    <%request.getSession().setAttribute("success", null);%>
    <table style="align-items: center;">
        <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Team</th>
            <th>Role</th>
            <th>Image</th>
            <%
                assert user != null;
                if (user.getRole() == Role.CLUB_SECRETARY || user.getRole() == Role.CHAIRPERSON || user.getRole() == Role.WEBMASTER) {%>
            <th>Edit</th>
            <th>Delete</th>
            <th>Reset password</th>
            <%}%>
        </tr>
        <%
            assert list != null;
            for (Users user1 : list) {%>
        <tr>
            <th><%=user1.getName()%>
            </th>
            <th><%=user1.getEmail()%>
            </th>
            <th><%=user1.getPhone()%>
            </th>
            <%
                String team;
                if (user1.getTeam() == 0) team = "Not related to team";
                else {
                    Teams team1 = null;
                    try {
                        team1 = DBUtils.findTeam(con, user1.getTeam());
                    } catch (Exception ignored) {

                    }
                    assert team1 != null;
                    team = team1.getName();
                }%>
            <th><%=team%>
            </th>
            <th><%=user1.getRole()%>
            </th>
            <th><img src="data:image/jpg;base64,<%=user1.getImage()%>" alt=""
                     onerror="this.onerror=null;this.src='images/default.png';"
                     style="max-width: 150px; max-height: 150px;"/></th>
            <%if ((user.getRole() == Role.CLUB_SECRETARY || user.getRole() == Role.CHAIRPERSON || user.getRole() == Role.WEBMASTER) && user1.getEmail() != "liamb1216@gmail.com") {%>
            <th>
                <div class="button" id="container">
                    <div id="translate"></div>
                    <a href="edituser?email=<%=user1.getEmail()%>">Edit</a>
                </div>
            </th>
            <th>
                <div class="button" id="container">
                    <div id="translate"></div>
                    <a href="deleteuser?email=<%=user1.getEmail()%>">Delete</a>
                </div>
            </th>
            <th>
                <div class="button" id="container">
                    <div id="translate"></div>
                    <a href="resetpassword?email=<%=user1.getEmail()%>">Reset password</a>
                </div>
            </th>
            <%}%>
        </tr>
        <%}%>
    </table>
    <br>
    <p style="z-index: -1; opacity: 0; float: left; width: 96%;">AFC</p>
</main>
<%if (user.getRole() == Role.CLUB_SECRETARY || user.getRole() == Role.CHAIRPERSON || user.getRole() == Role.WEBMASTER) {%>
<p style="width: 96%">
    <a href="adduser">Add user</a>
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
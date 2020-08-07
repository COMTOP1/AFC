<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.beans.*, java.sql.Connection, com.bswdi.utils.*, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - Add user</title>
</head>
<body>
<jsp:include page="_topPageConfirmation.jsp"/>
<main class="main" style="text-align: center;">
    <div style="background-color: white; margin: 10px 0 0 0; padding: 5px 0 0 0;">
        <h2 style="text-decoration: underline; text-decoration-color: red; width: 100vx; text-align: center;">Add
            user</h2>
        <p style="color: red; padding: 0; margin: 0;" id="error">${error}</p>
        <%
            request.getSession().setAttribute("error", null);
            String email = MyUtils.getEmailInCookie(request);
            Connection con = MyUtils.getStoredConnection(request);
            Users user = null;
            try {
                user = DBUtils.findUser(con, email);
            } catch (Exception ignored) {

            }
        %>
        <form id="add" method="POST" action="adduser" enctype="multipart/form-data">
            <div>
                <label for="name">Name: </label>
                <input type="text" id="name" name="name">
            </div>
            <div>
                <label for="email">Email: </label>
                <input type="text" id="email" name="email">
            </div>
            <div>
                <label for="phone">Phone: </label>
                <input type="text" id="phone" name="phone">
            </div>
            <div>
                <label type="role" for="role">Role: </label>
                <select form="add" name="role" id="role" onClick="team()" onmouseup="team()" onchange="team()">
                    <option id="role" name="role" value="<%=UserRole.MANAGER%>" onClick="team()">Manager</option>
                    <option id="role" name="role" value="<%=UserRole.SECRETARY%>" onClick="team()">Secretary</option>
                    <option id="role" name="role" value="<%=UserRole.TREASURER%>" onClick="team()">Treasurer</option>
                    <option id="role" name="role" value="<%=UserRole.SAFEGUARDING_OFFICER%>" onClick="team()">
                        Safeguarding officer
                    </option>
                    <option id="role" name="role" value="<%=UserRole.PROGRAMME_EDITOR%>" onClick="team()">Programme
                        editor
                    </option>
                    <option id="role" name="role" value="<%=UserRole.CHAIRPERSON%>" onClick="team()">Chairperson
                    </option>
                    <%
                        assert user != null;
                        if (user.getRole() >= 5) {%>
                    <option id="role" name="role" value="<%=UserRole.WEBMASTER%>">Webmaster</option>
                    <%}%>
                </select>
            </div>
            <div id="team" style="display: block;">
                <label for="teamID">Team: </label>
                <select form="add" name="teamID" id="teamID">
                    <option id="teamID" name="teamID" value="0">Not associated with a team</option>
                    <%
                        List<Teams> list = null;
                        try {
                            list = DBUtils.queryTeams(con);
                        } catch (Exception ignored) {

                        }
                        assert list != null;
                        for (Teams team : list) {
                    %>
                    <option id="teamID" name="teamID" value="<%=team.getID()%>"><%=team.getName()%>
                    </option>
                    <%}%>
                </select>
            </div>
            <div>
                <label for="image">Image: </label>
                <input type="file" id="image" name="image" accept="image/*" style="max-width: 300px;">
            </div>
            <p><a onclick="validateFunction()" href="javascript:{}">Add</a></p>
            <br>
            <p><a href="users">Cancel</a></p>
            <script>
                function team() {
                    const role = document.getElementById('role');
                    const team = document.getElementById('team');
                    if (role.value === "0") {
                        team.style.display = "block";
                    } else {
                        team.style.display = "none";
                    }
                }

                function validateFunction() {
                    const name = document.getElementById('name').value.length;
                    const email = document.getElementById('email').value.length;
                    const phone = document.getElementById('phone').value.length;
                    const role = document.getElementById('role').value.length;
                    if (name > 0 && email > 0 && phone > 0 && role > 0) {
                        add.submit();
                    } else {
                        document.getElementById('error').innerHTML = "Name, email, phone and role are required!";
                    }
                }
            </script>
        </form>
    </div>
</main>
</body>
<jsp:include page="_footerPage.jsp"/>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.beans.*, java.sql.Connection, com.bswdi.utils.*, java.util.List" %>
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
    <title>Official website of AFC Aldermaston - Edit user</title>
</head>
<body>
<jsp:include page="_topPageConfirmation.jsp"/>
<main class="main" style="text-align: center;">
    <div style="background-color: white; margin: 10px 0 0 0; padding: 5px 0 0 0;">
        <h2 style="text-decoration: underline; text-decoration-color: red; width: 100vx; text-align: center;">Edit
            user</h2>
        <p style="color: red; padding: 0; margin: 0;" id="error">${error}</p>
        <%
            request.getSession().setAttribute("error", null);
            Users user = (Users) request.getSession().getAttribute("user");
        %>
        <form id="edit" method="POST" action="edituser" enctype="multipart/form-data">
            <div>
                <label for="name">Name: </label>
                <input type="text" id="name" name="name" value="<%=user.getName()%>">
            </div>
            <div>
                <label for="email">Email: </label>
                <input type="text" id="email" name="email" value="<%=user.getEmail()%>">
            </div>
            <div>
                <label for="phone">Phone: </label>
                <input type="text" id="phone" name="phone" value="<%=user.getPhone()%>">
            </div>
            <div>
                <label for="role">Role: </label>
                <select form="edit" name="role" id="role">
                    <option id="role" name="role" value="<%=UserRole.MANAGER%>" onClick="team()"
                            <%if (user.getRole() == UserRole.MANAGER) {%>selected<%}%>>Manager
                    </option>
                    <option id="role" name="role" value="<%=UserRole.SECRETARY%>" onClick="team()"
                            <%if (user.getRole() == UserRole.SECRETARY) {%>selected<%}%>>Secretary
                    </option>
                    <option id="role" name="role" value="<%=UserRole.TREASURER%>" onClick="team()"
                            <%if (user.getRole() == UserRole.TREASURER) {%>selected<%}%>>Treasurer
                    </option>
                    <option id="role" name="role" value="<%=UserRole.SAFEGUARDING_OFFICER%>" onClick="team()"
                            <%if (user.getRole() == UserRole.SAFEGUARDING_OFFICER) {%>selected<%}%>>Safeguarding officer
                    </option>
                    <option id="role" name="role" value="<%=UserRole.PROGRAMME_EDITOR%>" onClick="team()"
                            <%if (user.getRole() == UserRole.PROGRAMME_EDITOR) {%>selected<%}%>>Programme editor
                    </option>
                    <option id="role" name="role" value="<%=UserRole.CHAIRPERSON%>" onClick="team()"
                            <%if (user.getRole() == UserRole.CHAIRPERSON) {%>selected<%}%>>Chairperson
                    </option>
                    <%if (user.getRole() >= 5) {%>
                    <option id="role" name="role" value="<%=UserRole.WEBMASTER%>"
                            <%if (user.getRole() == UserRole.WEBMASTER) {%>selected<%}%>>Webmaster
                    </option>
                    <%}%>
                </select>
            </div>
            <div id="team" style="display: block;">
                <label for="teamID">Team: </label>
                <select form="edit" name="teamID" id="teamID">
                    <option id="teamID" name="teamID" value="0" <%if (user.getTeam() == 0) {%>selected<%}%>>Not
                        associated with a team
                    </option>
                    <%
                        List<Teams> list = null;
                        Connection con = MyUtils.getStoredConnection(request);
                        try {
                            list = DBUtils.queryTeams(con);
                        } catch (Exception ignored) {

                        }
                        assert list != null;
                        for (Teams team : list) {
                    %>
                    <option id="teamID" name="teamID" value="<%=team.getID()%>"
                            <%if (user.getTeam() == team.getID()) {%>selected<%}%>><%=team.getName()%>
                    </option>
                    <%}%>
                </select>
            </div>
            <div>
                <label for="image">Image: </label>
                <input type="file" id="image" name="image" accept="image/*" style="max-width: 300px;">
                <%if (user.getImage() != null) {%><img src="data:image/jpg;base64,<%=user.getImage()%>"
                                                       style="max-width: 300px; max-height: 300px;"/><%}%>
            </div>
            <p><a onclick="validateFunction()" href="javascript:{}">Confirm</a></p>
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
                        edit.submit();
                    } else {
                        document.getElementById('error').innerHTML = "Name, email, phone and role are required!";
                    }
                }
            </script>
        </form>
    </div>
</main>
</body>
<footer>
    <small>&copy; Copyright 2020 - <%=MyUtils.getYear()%>, AFC Aldermaston, website provided by Liam Burnand</small>
</footer>
</html>
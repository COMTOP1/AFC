<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.beans.*, java.sql.Connection, com.bswdi.utils.*, java.util.List, java.time.LocalDate" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - Edit player</title>
</head>
<body>
<jsp:include page="_topPageConfirmation.jsp"/>
<main class="main" style="text-align: center;">
    <div style="background-color: white; margin: 10px 0 0 0; padding: 5px 0 0 0;">
        <h2 style="text-decoration: underline; text-decoration-color: red; width: 100vx; text-align: center;">Edit
            player</h2>
        <p style="color: red; padding: 0; margin: 0;" id="error">${error}</p>
        <%
            request.getSession().setAttribute("error", null);
            Connection con = MyUtils.getStoredConnection(request);
            Players player = (Players) request.getSession().getAttribute("player");
        %>
        <form id="edit" method="POST" action="editplayer" enctype="multipart/form-data">
            <div>
                <label for="name">Name: </label>
                <input type="text" id="name" name="name" value="<%=player.getName()%>">
            </div>
            <div>
                <label for="image">Image: </label>
                <input type="file" id="image" name="image" accept="image/*" style="max-width: 300px;">
                <%if (player.getImage() != null) {%><img src="data:image/jpg;base64,<%=player.getImage()%>"
                                                         style="max-width: 300px; max-height: 300px;"/><%}%>
            </div>
            <div>
                <label for="dateOfBirth">Date of birth: </label>
                <%LocalDate date = LocalDate.now(), date1 = LocalDate.ofEpochDay(player.getDateOfBirth());%>
                <input type="date" id="dateOfBirth" name="dateOfBirth" max="<%=date%>" value="<%=date1%>">
            </div>
            <div>
                <label for="position">Position: </label>
                <input type="text" id="position" name="position" value="<%=player.getPosition()%>">
            </div>
            <div>
                <label for="captain">Captain: </label>
                <input type="checkbox" id="captain" name="captain" value="Y" <%if (player.getCaptain()) {%>checked<%}%>>
            </div>
            <div>
                <label for="teamID">Team: </label>
                <select form="edit" name="teamID" id="teamID">
                    <%
                        List<Teams> list = null;
                        try {
                            list = DBUtils.queryTeams(con);
                        } catch (Exception ignored) {

                        }
                        assert list != null;
                        for (Teams team : list) {
                    %>
                    <option id="teamID" name="teamID" value="<%=team.getID()%>"
                            <%if (player.getTeamID() == team.getID()) {%>selected<%}%>><%=team.getName()%>
                    </option>
                    <%}%>
                </select>
            </div>
            <p><a onclick="validateFunction()" href="javascript:{}">Confirm</a></p>
            <br>
            <p><a href="players">Cancel</a></p>
            <script>
                function validateFunction() {
                    const name = document.getElementById('name').value.length;
                    const position = document.getElementById('position').value.length;
                    const teamID = document.getElementById('teamID').value.length;
                    if (name > 0 && position > 0 && teamID > 0) {
                        edit.submit();
                    } else {
                        document.getElementById('error').innerHTML = "Name, position and team are required!";
                    }
                }
            </script>
        </form>
    </div>
</main>
</body>
<jsp:include page="_footerPage.jsp"/>
</html>
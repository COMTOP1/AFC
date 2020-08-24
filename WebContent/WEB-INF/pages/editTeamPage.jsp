<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.beans.*" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - Edit team</title>
</head>
<body>
<jsp:include page="_topPageConfirmation.jsp"/>
<main class="main" style="text-align: center;">
    <div style="background-color: white; margin: 10px 0 0 0; padding: 5px 0 0 0;">
        <h2 style="text-decoration: underline; text-decoration-color: red; width: 100vx; text-align: center;">Edit
            team</h2>
        <p style="color: red; padding: 0; margin: 0;" id="error">${error}</p>
        <%
            request.getSession().setAttribute("error", null);
            Teams team = (Teams) request.getSession().getAttribute("team");
        %>
        <form id="edit" method="POST" action="editteam" enctype="multipart/form-data">
            <div>
                <label for="name">Name: </label>
                <input type="text" id="name" name="name" value="<%=team.getName()%>">
            </div>
            <div>
                <label for="league">League: </label>
                <input type="text" id="league" name="league" value="<%=team.getLeague()%>">
            </div>
            <div>
                <label for="division">Division: </label>
                <input type="text" id="division" name="division" value="<%=team.getDivision()%>">
            </div>
            <div>
                <label for="leagueTable">League table (url, leave blank if unknown): </label>
                <input type="url" id="leagueTable" name="leagueTable" value="<%=team.getLeagueTable()%>">
            </div>
            <div>
                <label for="fixtures">Fixtures (url, leave blank if unknown): </label>
                <input type="url" id="fixtures" name="fixtures" value="<%=team.getFixtures()%>">
            </div>
            <div>
                <label for="coach">Coach: </label>
                <input type="text" id="coach" name="coach" value="<%=team.getCoach()%>">
            </div>
            <div>
                <label>Manager assigned via Users</label>
            </div>
            <div>
                <label for="teamPhoto">Team photo: </label>
                <input type="file" id="teamPhoto" name="teamPhoto" accept="image/*" style="max-width: 300px;">
                <%if (team.getTeamPhoto() != null) {%><img src="data:image/jpg;base64,<%=team.getTeamPhoto()%>"
                                                           style="max-width: 300px; max-height: 300px;"/><%}%>
            </div>
            <div>
                <label for="active">Active: </label>
                <input type="checkbox" id="active" name="active" value="Y" <%if (team.getActive()) {%>checked<%}%>>
            </div>
            <div>
                <label for="youth">Youth: </label>
                <input type="checkbox" id="youth" name="youth" value="Y" <%if (team.getYouth()) {%>checked<%}%>>
            </div>
            <div>
                <label for="ages">Ages: </label>
                <select form="edit" id="ages">
                    <option id="ages" value="6" <%if (team.getAges() == 6) {%>selected<%}%>>&lt;6</option>
                    <option id="ages" value="7" <%if (team.getAges() == 7) {%>selected<%}%>>&lt;7</option>
                    <option id="ages" value="8" <%if (team.getAges() == 8) {%>selected<%}%>>&lt;8</option>
                    <option id="ages" value="9" <%if (team.getAges() == 9) {%>selected<%}%>>&lt;9</option>
                    <option id="ages" value="10" <%if (team.getAges() == 10) {%>selected<%}%>>&lt;10
                    </option>
                    <option id="ages" value="11" <%if (team.getAges() == 11) {%>selected<%}%>>&lt;11
                    </option>
                    <option id="ages" value="12" <%if (team.getAges() == 12) {%>selected<%}%>>&lt;12
                    </option>
                    <option id="ages" value="13" <%if (team.getAges() == 13) {%>selected<%}%>>&lt;13
                    </option>
                    <option id="ages" value="14" <%if (team.getAges() == 14) {%>selected<%}%>>&lt;14
                    </option>
                    <option id="ages" value="15" <%if (team.getAges() == 15) {%>selected<%}%>>&lt;15
                    </option>
                    <option id="ages" value="16" <%if (team.getAges() == 16) {%>selected<%}%>>&lt;16
                    </option>
                    <option id="ages" value="17" <%if (team.getAges() == 17) {%>selected<%}%>>&lt;17
                    </option>
                    <option id="ages" value="18" <%if (team.getAges() == 18) {%>selected<%}%>>&lt;18
                    </option>
                    <option id="ages" value="19" <%if (team.getAges() == 19) {%>selected<%}%>>&gt;18
                    </option>
                </select>
            </div>
            <p><a onclick="validateFunction()" href="javascript:{}">Confirm</a></p>
            <br>
            <p><a href="teams">Cancel</a></p>
            <script>
                function validateFunction() {
                    const name = document.getElementById('name').value.length;
                    const coach = document.getElementById('coach').value.length;
                    if (name > 0 && coach > 0) {
                        edit.submit();
                    } else {
                        document.getElementById('error').innerHTML = "Name and coach are required!";
                    }
                }
            </script>
        </form>
    </div>
</main>
</body>
<jsp:include page="_footerPage.jsp"/>
</html>
<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.beans.*, java.sql.Connection, com.bswdi.utils.*, java.util.List, java.time.LocalDate" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - Add player</title>
</head>
<body>
<jsp:include page="_topPageConfirmation.jsp"/>
<main class="main" style="text-align: center;">
    <div style="background-color: white; margin: 10px 0 0 0; padding: 5px 0 0 0;">
        <h2 style="text-decoration: underline; text-decoration-color: red; width: 100%; text-align: center;">Add
            player</h2>
        <p style="color: red; padding: 0; margin: 0;" id="error">${error}</p>
        <%
            request.getSession().setAttribute("error", null);
            Connection con = MyUtils.getStoredConnection(request);
            Users user = MyUtils.getUser(request, response, con);
        %>
        <form id="add" method="POST" action="addplayer" enctype="multipart/form-data">
            <div>
                <label for="name">Name: </label>
                <input type="text" id="name" name="name">
            </div>
            <div>
                <label for="image">Image: </label>
                <input type="file" id="image" name="image" accept="image/*" style="max-width: 300px;">
            </div>
            <div>
                <label for="dateOfBirth">Date of birth: </label>
                <%LocalDate date = LocalDate.now();%>
                <input type="date" id="dateOfBirth" name="dateOfBirth" max="<%=date%>">
            </div>
            <div>
                <label for="position">Position: </label>
                <input type="text" id="position" name="position">
            </div>
            <div>
                <label for="captain">Captain: </label>
                <input type="checkbox" id="captain" name="captain" value="Y">
            </div>
            <div>
                <label for="teamID">Team: </label>
                <select form="add" name="teamID" id="teamID">
                    <%
                        List<Teams> list = null;
                        try {
                            list = DBUtils.queryTeams(con);
                        } catch (Exception ignored) {

                        }
                        assert list != null;
                        for (Teams team : list) {
                    %>
                    <option id="teamID" value="<%=team.getID()%>"><%=team.getName()%>
                    </option>
                    <%}%>
                </select>
            </div>
            <p><a onclick="validateFunction()" href="javascript:{}">Add</a></p>
            <br>
            <p><a href="players">Cancel</a></p>
            <script>
                function validateFunction() {
                    const name = document.getElementById('name').value.length;
                    const position = document.getElementById('position').value.length;
                    const teamID = document.getElementById('teamID').value.length;
                    if (name > 0 && position > 0 && teamID > 0) {
                        add.submit();
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
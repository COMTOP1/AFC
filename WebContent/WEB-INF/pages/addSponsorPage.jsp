<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.beans.*, java.sql.Connection,com.bswdi.utils.*, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - Add sponsor</title>
</head>
<body>
<jsp:include page="_topPageConfirmation.jsp"/>
<main class="main" style="text-align: center;">
    <div style="background-color: white; margin: 10px 0 0 0; padding: 5px 0 0 0;">
        <h2 style="text-decoration: underline; text-decoration-color: red; width: 100%; text-align: center;">Add
            sponsor</h2>
        <p style="color: red; padding: 0; margin: 0;" id="error">${error}</p><br>
        <form id="add" method="POST" action="addsponsor" enctype="multipart/form-data">
            <div>
                <label for="name">Name: </label>
                <input type="text" id="name" name="name">
            </div>
            <div>
                <label for="website">Website (leave blank if there isn't one): </label>
                <input type="text" id="website" name="website">
            </div>
            <div>
                <label for="image">Image: </label>
                <input type="file" id="image" name="image" accept="image/*" style="max-width: 300px;">
            </div>
            <div>
                <label for="purpose">Purpose: </label>
                <input type="text" id="purpose" name="purpose">
            </div>
            <div>
                <label for="team">Team: </label>
                <select form="add" name="team" id="team">
                    <option id="team" value="A">All</option>
                    <option id="team" value="O">Adult</option>
                    <option id="team" value="Y">Youth</option>
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
                    <option id="team" value="<%=team.getID()%>"><%=team.getName()%>
                    </option>
                    <%}%>
                </select>
            </div>
            <p><a onclick="validateFunction()" href="javascript:{}">Add</a></p>
            <br>
            <p><a href="sponsors">Cancel</a></p>
            <script>
                function validateFunction() {
                    const name = document.getElementById('name').value.length;
                    const purpose = document.getElementById('purpose').value.length;
                    const team = document.getElementById('team').value.length;
                    if (name > 0 && purpose > 0 && team > 0) {
                        add.submit();
                    } else {
                        document.getElementById("error").innerHTML = "Name, purpose and team are required!";
                    }
                }
            </script>
        </form>
    </div>
</main>
</body>
<jsp:include page="_footerPage.jsp"/>
</html>
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
    <title>Official website of AFC Aldermaston - Edit sponsor</title>
</head>
<body>
<jsp:include page="_topPageConfirmation.jsp"/>
<main class="main" style="text-align: center;">
    <div style="background-color: white; margin: 10px 0 0 0; padding: 5px 0 0 0;">
        <h2 style="text-decoration: underline; text-decoration-color: red; width: 100vx; text-align: center;">Edit
            sponsor</h2>
        <p style="color: red; padding: 0; margin: 0;" id="error">${error}</p><br>
        <%Sponsors sponsor = (Sponsors) request.getSession().getAttribute("sponsor");%>
        <form id="edit" method="POST" action="editsponsor" enctype="multipart/form-data">
            <div>
                <label for="name">Name: </label>
                <input type="text" id="name" name="name" value="<%=sponsor.getName()%>">
            </div>
            <div>
                <label for="website">Website (leave blank if there isn't one): </label>
                <input type="url" id="website" name="website" value="<%=sponsor.getWebsite()%>">
            </div>
            <div>
                <label for="image">Image: </label>
                <input type="file" id="image" name="image" accept="image/*" style="max-width: 300px;">
                <%if (sponsor.getImage() != null) {%><img src="data:image/jpg;base64,<%=sponsor.getImage()%>"
                                                          style="max-width: 300px; max-height: 300px;"/><%}%>

            </div>
            <div>
                <label for="purpose">Purpose: </label>
                <input type="text" id="purpose" name="purpose" value="<%=sponsor.getPurpose()%>">
            </div>
            <div>
                <label for="teamID">Team: </label>
                <select form="edit" name="companyID" id="companyID">
                    <option id="teamID" name="teamID" value="A" <%if (sponsor.getTeamID().equals("A")) {%>selected<%}%>>
                        All
                    </option>
                    <option id="teamID" name="teamID" value="O" <%if (sponsor.getTeamID().equals("O")) {%>selected<%}%>>
                        Adult
                    </option>
                    <option id="teamID" name="teamID" value="Y" <%if (sponsor.getTeamID().equals("Y")) {%>selected<%}%>>
                        Youth
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
                            <%if (sponsor.getTeamID().equals("" + team.getID())) {%>selected<%}%>><%=team.getName()%>
                    </option>
                    <%}%>
                </select>
            </div>
            <p><a onclick="validateFunction()" href="javascript:{}">Confirm</a></p>
            <br>
            <p><a href="sponsors">Cancel</a></p>
            <script>
                function validateFunction() {
                    const name = document.getElementById('name').value.length;
                    const purpose = document.getElementById('purpose').value.length;
                    if (name > 0 && purpose > 0) {
                        edit.submit();
                    } else {
                        document.getElementById("error").innerHTML = "Name, purpose and team are required!";
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
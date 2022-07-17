<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.time.LocalDate, com.bswdi.beans.ProgrammeSeasons, java.util.List, com.bswdi.utils.*, java.sql.Connection" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - Add programme</title>
</head>
<body>
<jsp:include page="_topPageConfirmation.jsp"/>
<main class="main" style="text-align: center;">
    <div style="background-color: white; margin: 10px 0 0 0; padding: 5px 0 0 0;">
        <h2 style="text-decoration: underline; text-decoration-color: red; width: 100%; text-align: center;">Add
            programme</h2>
        <p style="color: red; padding: 0; margin: 0;" id="error">${error}</p><br><%
        request.getSession().setAttribute("error", null);
    %>
        <form id="add" method="POST" action="addprogramme">
            <div>
                <label for="name">Name: </label>
                <input type="text" id="name" name="name">
            </div>
            <div>
                <label for="dateOfProgramme">Date of programme: </label>
                <%LocalDate date = LocalDate.now();%>
                <input type="date" id="dateOfProgramme" name="dateOfProgramme" value="<%=date.now()%>">
            </div>
            <div>
                <label for="programmeSeason"></label>
                <select form="add" name="programmeSeason" id="programmeSeason">
                    <option id="programmeSeason" value="-1">No season</option>
                    <%List<ProgrammeSeasons> list = null;
                        request.getSession().setAttribute("error", null);
                        Connection con = MyUtils.getStoredConnection(request);
                    try {
                        list = DBUtils.queryProgrammeSeasons(con);
                    } catch (Exception ignored) {

                    }
                    if (list != null) {
                    for (ProgrammeSeasons programmeSeason : list) {%>
                    <option id="programmeSeason" value="<%=programmeSeason.getID()%>"><%=programmeSeason.getSeason()%>
                    </option>
                    <%}
                    }%>
                </select>
            </div>
            <p><a onclick="validateFunction()" href="javascript:{}">Next</a></p>
            <br>
            <p><a href="programmes">Cancel</a></p>
            <script>
                function validateFunction() {
                    const name = document.getElementById('name').value.length;
                    const programmeSeason = document.getElementById('programmeSeason').value.length;
                    if (name > 0 && programmeSeason > 0) {
                        add.submit();
                    } else {
                        document.getElementById("error").innerHTML = "Name and programme season are required!";
                    }
                }
            </script>
        </form>
    </div>
</main>
</body>
<jsp:include page="_footerPage.jsp"/>
</html>

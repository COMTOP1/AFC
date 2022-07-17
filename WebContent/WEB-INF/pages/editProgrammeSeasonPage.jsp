<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.beans.*" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - Edit programme season</title>
</head>
<body>
<jsp:include page="_topPageConfirmation.jsp"/>
<main class="main" style="text-align: center;">
    <div style="background-color: white; margin: 10px 0 0 0; padding: 5px 0 0 0;">
        <h2 style="text-decoration: underline; text-decoration-color: red; width: 100%; text-align: center;">Edit
            programme season</h2>
        <p style="color: red; padding: 0; margin: 0;" id="error">${error}</p><br>
        <%
            request.getSession().setAttribute("error", null);
            ProgrammeSeasons programmeSeason = (ProgrammeSeasons) request.getSession().getAttribute("programmeSeason");
        %>
        <form id="edit" method="POST" action="editprogrammeseason">
            <div>
                <label for="season">Season: </label>
                <input type="text" id="season" name="season" value="<%=programmeSeason.getSeason()%>">
            </div>
            <p><a onclick="validateFunction()" href="javascript:{}">Confirm</a></p>
            <br>
            <p><a href="programmes">Cancel</a></p>
            <script>
                function validateFunction() {
                    const season = document.getElementById('season').value.length;
                    if (season > 0) {
                        edit.submit();
                    } else {
                        document.getElementById("error").innerHTML = "Season is required!";
                    }
                }
            </script>
        </form>
    </div>
</main>
</body>
<jsp:include page="_footerPage.jsp"/>
</html>
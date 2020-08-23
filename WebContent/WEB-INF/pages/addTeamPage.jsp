<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - Add team</title>
</head>
<body>
<jsp:include page="_topPageConfirmation.jsp"/>
<main class="main" style="text-align: center;">
    <div style="background-color: white; margin: 10px 0 0 0; padding: 5px 0 0 0;">
        <h2 style="text-decoration: underline; text-decoration-color: red; width: 100vx; text-align: center;">Add
            team</h2>
        <p style="color: red; padding: 0; margin: 0;" id="error">${error}</p>
        <%request.getSession().setAttribute("error", null);%>
        <form id="add" method="POST" action="addteam" enctype="multipart/form-data">
            <div>
                <label for="name">Name: </label>
                <input type="text" id="name" name="name">
            </div>
            <div>
                <label for="league">League: </label>
                <input type="text" id="league" name="league">
            </div>
            <div>
                <label for="division">Division: </label>
                <input type="text" id="division" name="division">
            </div>
            <div>
                <label for="leagueTable">League table (url, leave blank if unknown): </label>
                <input type="url" id="leagueTable" name="leagueTable">
            </div>
            <div>
                <label for="fixtures">Fixtures (url, leave blank if unknown): </label>
                <input type="url" id="fixtures" name="fixtures">
            </div>
            <div>
                <label for="coach">Coach: </label>
                <input type="text" id="coach" name="coach">
            </div>
            <div>
                <label>Manager assigned via Users</label>
            </div>
            <div>
                <label for="teamPhoto">Team photo: </label>
                <input type="file" id="teamPhoto" name="teamPhoto" accept="image/*" style="max-width: 300px;">
            </div>
            <div>
                <label for="active">Active: </label>
                <input type="checkbox" id="active" name="active" value="Y">
            </div>
            <div>
                <label for="youth">Youth: </label>
                <input type="checkbox" id="youth" name="youth" value="Y">
            </div>
            <div>
                <label for="ages">Ages: </label>
                <select form="add" name="ages" id="ages">
                    <option id="ages" name="ages" value="6">&lt;6</option>
                    <option id="ages" name="ages" value="7">&lt;7</option>
                    <option id="ages" name="ages" value="8">&lt;8</option>
                    <option id="ages" name="ages" value="9">&lt;9</option>
                    <option id="ages" name="ages" value="10">&lt;10</option>
                    <option id="ages" name="ages" value="11">&lt;11</option>
                    <option id="ages" name="ages" value="12">&lt;12</option>
                    <option id="ages" name="ages" value="13">&lt;13</option>
                    <option id="ages" name="ages" value="14">&lt;14</option>
                    <option id="ages" name="ages" value="15">&lt;15</option>
                    <option id="ages" name="ages" value="16">&lt;16</option>
                    <option id="ages" name="ages" value="17">&lt;17</option>
                    <option id="ages" name="ages" value="18">&lt;18</option>
                    <option id="ages" name="ages" value="19">&gt;18</option>
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
                        add.submit();
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
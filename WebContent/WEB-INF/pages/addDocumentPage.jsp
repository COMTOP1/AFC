<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - Add document</title>
</head>
<body>
<jsp:include page="_topPageConfirmation.jsp"/>
<main class="main" style="text-align: center;">
    <div style="background-color: white; margin: 10px 0 0 0; padding: 5px 0 0 0;">
        <h2 style="text-decoration: underline; text-decoration-color: red; width: 100%; text-align: center;">Add
            document</h2>
        <p style="color: red; padding: 0; margin: 0;" id="error">${error}</p><br><%
        request.getSession().setAttribute("error", null);
        %>
        <form id="add" method="POST" action="adddocument" enctype="multipart/form-data">
            <div>
                <label for="name">Name: </label>
                <input type="text" id="name" name="name">
            </div>
            <div>
                <label for="file">File: </label>
                <input type="file" id="file" name="file" accept="application/pdf" style="max-width: 300px;">
            </div>
            <p><a onclick="validateFunction()" href="javascript:{}">Next</a></p>
            <br>
            <p><a href="users">Cancel</a></p>
            <script>
                function validateFunction() {
                    const name = document.getElementById('name').value.length;
                    const file = document.getElementById('file').value.length;
                    if (name > 0 && file > 0) {
                        add.submit();
                    } else {
                        document.getElementById("error").innerHTML = "Name and file are required!";
                    }
                }
            </script>
        </form>
    </div>
</main>
</body>
<jsp:include page="_footerPage.jsp"/>
</html>

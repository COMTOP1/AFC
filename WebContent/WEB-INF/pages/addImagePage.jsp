<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - Add image</title>
</head>
<body>
<jsp:include page="_topPageConfirmation.jsp"/>
<main class="main" style="text-align: center;">
    <div style="background-color: white; margin: 10px 0 0 0; padding: 5px 0 0 0;">
        <h2 style="text-decoration: underline; text-decoration-color: red; width: 100%; text-align: center;">Add
            image</h2>
        <p style="color: red; padding: 0; margin: 0;" id="error">${error}</p><br>
        <%request.getSession().setAttribute("error", null);%>
        <form id="add" method="POST" action="addimage" enctype="multipart/form-data">
            <div>
                <label for="image">Image: </label>
                <input type="file" id="image" name="image" accept="image/*" style="max-width: 300px;">
            </div>
            <div>
                <label for="caption">Caption: </label>
                <input type="text" id="caption" name="caption">
            </div>
            <p><a onclick="document.getElementById('add').submit();" href="javascript:{}">Add</a></p>
            <br>
            <p><a href="users">Cancel</a></p>
        </form>
    </div>
</main>
</body>
<jsp:include page="_footerPage.jsp"/>
</html>
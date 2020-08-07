<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.utils.*" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - Add news</title>
</head>
<body>
<jsp:include page="_topPageConfirmation.jsp"/>
<main class="main" style="text-align: center;">
    <div style="background-color: white; margin: 10px 0 0 0; padding: 5px 0 0 0;">
        <h2 style="text-decoration: underline; text-decoration-color: red; width: 100vx; text-align: center;">Add
            news</h2>
        <p style="color: red; padding: 0; margin: 0;" id="error">${error}</p><br>
        <form id="add" method="POST" action="addnews" enctype="multipart/form-data">
            <div>
                <label for="title">Title: </label>
                <input type="text" id="title" name="title">
            </div>
            <div>
                <label for="image">Image: </label>
                <input type="file" id="image" name="image" accept="image/*" style="max-width: 300px;">
            </div>
            <div>
                <label for="content">Content: </label>
                <textarea form="add" id="content" name="content" rows="10" cols="30"></textarea>
            </div>
            <p><a onclick="validateFunction()" href="javascript:{}">Add</a></p>
            <br>
            <p><a href="users">Cancel</a></p>
            <script>
                function validateFunction() {
                    const title = document.getElementById('title').value.length;
                    const content = document.getElementById('content').value.length;
                    if (title > 0 && content > 0) {
                        add.submit();
                    } else {
                        document.getElementById("error").innerHTML = "Title and content are required!";
                    }
                }
            </script>
        </form>
    </div>
</main>
</body>
<jsp:include page="_footerPage.jsp"/>
</html>
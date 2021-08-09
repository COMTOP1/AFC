<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.beans.*" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - Edit news</title>
</head>
<body>
<jsp:include page="_topPageConfirmation.jsp"/>
<main class="main" style="text-align: center;">
    <div style="background-color: white; margin: 10px 0 0 0; padding: 5px 0 0 0;">
        <h2 style="text-decoration: underline; text-decoration-color: red; width: 100%; text-align: center;">Edit
            news</h2>
        <p style="color: red; padding: 0; margin: 0;" id="error">${error}</p><br>
        <%
            request.getSession().setAttribute("error", null);
            News news = (News) request.getSession().getAttribute("news");
        %>
        <form id="edit" method="POST" action="editnews" enctype="multipart/form-data">
            <div>
                <label for="title">Title: </label>
                <input type="text" id="title" name="title" value="<%=news.getTitle()%>">
            </div>
            <div>
                <label for="image">Image: </label>
                <input type="file" id="image" name="image" accept="image/*" style="max-width: 300px;">
                <%if (news.getImage() != null) {%><img src="data:image/jpg;base64,<%=news.getImage()%>"
                                                       style="max-width: 300px; max-height: 300px;"/><%}%>
            </div>
            <div>
                <label for="content">Content: </label>
                <textarea form="edit" id="content" name="content" rows="10" cols="30"><%=news.getContent()%></textarea>
            </div>
            <p><a onclick="validateFunction()" href="javascript:{}">Confirm</a></p>
            <br>
            <p><a href="news">Cancel</a></p>
            <script>
                function validateFunction() {
                    const title = document.getElementById('title').value.length;
                    const content = document.getElementById('content').value.length;
                    if (title > 0 && content > 0) {
                        edit.submit();
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
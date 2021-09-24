<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - Delete user confirmation</title>
</head>
<body>
<jsp:include page="_topPageConfirmation.jsp"/>
<%String email = (String) request.getAttribute("email");%>
<main class="main" style="text-align: center;">
    <div style="background-color: white; margin: 10px 0 0 0; padding: 5px 0 0 0;">
        <h2 style="text-decoration: underline; text-decoration-color: red; width: 100%; text-align: center;">Delete
            user</h2>
        <p style="padding: 0; margin: 0; height: auto;">Are you sure you want to delete this user?</p>
        <p style="color: red; padding: 0; margin: 0;" id="error">${error}</p><br>
        <form id="delete" method="POST" action="deleteuser?email=<%=email%>">
            <p><a onclick="document.getElementById('delete').submit();" href="javascript:{}">Delete</a></p>
            <br>
            <p><a href="users">Cancel</a></p>
        </form>
    </div>
</main>
</body>
<jsp:include page="_footerPage.jsp"/>
</html>
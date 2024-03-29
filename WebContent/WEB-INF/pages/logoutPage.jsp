<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - Log out</title>
    <style>
        form {
            margin: auto;
            width: 500px;
            padding: 1em;
            border: none;
            border-radius: 1em;
        }

        input {
            display: inline-flex;
            height: 40px;
            width: 250px;
            border: 2px solid #BFC0C0;
            margin: 20px 20px 20px 20px;
            text-decoration: none;
            letter-spacing: 2px;
            align-items: center;
            justify-content: center;
            overflow: hidden;
        }

        label {
            display: inline-block;
            width: 90px;
            text-align: right;
            position: relative;
            vertical-align: middle;
            margin: 20px 0 20px 0;
        }
    </style>
</head>
<body>
<jsp:include page="_topPage.jsp"/>
<main class="main" style="text-align: center;">
    <div style="background-color: white; margin: 10px 0 0 0; padding: 5px 0 0 0;">
        <h2 style="text-decoration: underline; text-decoration-color: red; width: 100%; text-align: center;">
            Log out</h2>
        <p style="padding: 0; margin: 0; height: auto;">Are you sure you want to log out?</p>
        <p style="color: red; padding: 0; margin: 0;" id="error">${error}</p><br>
        <form id="logout" method="POST" action="logout">
            <div class="button" id="container">
                <div id="translate"></div>
                <a onclick="document.getElementById('logout').submit();" href="javascript:{}">Log out</a>
            </div>
            <div class="button" id="container">
                <div id="translate"></div>
                <a href="home">Cancel</a>
            </div>
        </form>
    </div>
    <p style="z-index: -1; opacity: 0; float: left; width: 96%;">AFC</p>
</main>
</body>
<jsp:include page="_footerPage.jsp"/>
</html>
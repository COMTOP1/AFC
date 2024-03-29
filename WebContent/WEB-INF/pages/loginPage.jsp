<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - Login</title>
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
        <h2 style="text-decoration: underline; text-decoration-color: red; width: 100%; text-align: center;">Login</h2>
        <p style="padding: 0; margin: 0; height: auto;">Please enter your email and password to login<br>
            This login is for officials only</p>
        <p style="color: red; padding: 0; margin: 0;" id="error">${error}</p><br>
        <form method="POST" id="login" action="login">
            <div>
                <label for="email">Email: </label>
                <input type="text" id="email" name="email" onkeyup="keyPress()">
            </div>
            <div>
                <label for="password">Password: </label>
                <input type="password" id="password" name="password" onkeyup="keyPress()">
            </div>
            <div class="button" id="container">
                <div id="translate"></div>
                <a id="loginButton" onclick="loginFunction()" href="javascript:{}">Login</a>
            </div>
            <div class="button" id="container">
                <div id="translate"></div>
                <a href="home">Cancel</a>
            </div>
        </form>
        <script type="text/javascript">
            let validate = false;

            function keyPress() {
                const email = document.getElementById('email').value.length;
                const password = document.getElementById('password').value.length;
                if (email > 0 && password > 0) {
                    validate = true;
                    document.getElementById("error").innerHTML = "";
                } else {
                    validate = false;
                }
            }

            function loginFunction() {
                if (validate) {
                    login.submit();
                } else {
                    document.getElementById("error").innerHTML = "An email and password are required!";
                }
            }

            const input1 = document.getElementById("email");
            const input2 = document.getElementById("password");

            input1.addEventListener("keyup", function (event) {
                if (event.keyCode === 13 && validate) {
                    event.preventDefault();
                    loginFunction();
                }
            });

            input2.addEventListener("keyup", function (event) {
                if (event.keyCode === 13 && validate) {
                    event.preventDefault();
                    loginFunction();
                }
            });
        </script>
    </div>
    <p style="z-index: -1; opacity: 0; float: left; width: 96%;">AFC</p>
</main>
<div id="socialBar">
    <a href="https://www.facebook.com/AFC-Aldermaston-114651238068/" class="fa fa-facebook"></a>
    <a href="https://twitter.com/afcaldermaston?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor"
       class="fa fa-twitter"></a>
</div>
</body>
<jsp:include page="_footerPage.jsp"/>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.utils.*" %>
<!DOCTYPE html>
<html>
<head>
    <link href="styleHome.css" rel="stylesheet" type="text/css">
    <link rel='icon' type='image/x-icon' href='images/AFC.ico'/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <meta charset="UTF-8">
    <meta name="keywords" content="afc,A.F.C.,AFC,aldermaston,football">
    <meta name="description" content="AFC Aldermaston Football Club website">
    <meta name="abstract" content="AFC Aldermaston">
    <meta http-equiv="Content-Language" content="EN-GB">
    <meta name="author" content="Liam Burnand">
    <meta name="distribution" content="Global">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="copyright"
          content="© Copyright 2020 - <%=MyUtils.getYear()%>, AFC Aldermaston, website provided by Liam Burnand">
    <title>Official website of AFC Aldermaston - Change password</title>
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
        <h2 style="text-decoration: underline; text-decoration-color: red; width: 100vx; text-align: center;">Change
            password</h2>
        <p style="padding: 0; margin: 0; height: auto;">Please enter your email and password to login<br>
            Enter your old password and new password then confirm it<br>
            Changing your password</p>
        <p style="color: red; padding: 0; margin: 0;" id="error">${error}</p>
        <%request.getSession().setAttribute("error", null);%>
        <form id="edit" method="POST" action="changepassword">
            <div>
                <label for="passwordold">Old password: </label>
                <input type="password" id="passwordold" name="passwordold">
            </div>
            <div>
                <label for="passwordnew1">New password: </label>
                <input type="password" id="passwordnew1" name="passwordnew1">
            </div>
            <div>
                <label for="passwordnew2">Confirm password: </label>
                <input type="password" id="passwordnew2" name="passwordnew2">
            </div>
            <div class="button" id="container">
                <div id="translate"></div>
                <a onclick="validateFunction()" href="javascript:{}">Submit</a>
            </div>
            <div class="button" id="container">
                <div id="translate"></div>
                <a onclick="document.getElementById('edit').reset();" href="javascript:{}">Reset</a>
            </div>
            <div class="button" id="container">
                <div id="translate"></div>
                <a href="account">Cancel</a>
            </div>
            <script>
                let validate = false;

                function keyPress() {
                    const passwordold = document.getElementById('passwordold').value.length;
                    const passwordnew1 = document.getElementById('passwordnew1').value.length;
                    const passwordnew2 = document.getElementById('passwordnew2').value.length;
                    if (passwordold > 0 && passwordnew1 > 0 && passwordnew2 > 0) {
                        validate = true;
                        document.getElementById("error").innerHTML = "";
                    } else {
                        validate = false;
                    }
                }

                function validateFunction() {
                    keyPress();
                    if (validate) {
                        edit.submit();
                    } else {
                        document.getElementById("error").innerHTML = "All fields are required and passwords must match!";
                    }
                }

                const passwordold = document.getElementById('passwordold');
                const passwordnew1 = document.getElementById('passwordnew1');
                const passwordnew2 = document.getElementById('passwordnew2');

                passwordold.addEventListener("keyup", function (event) {
                    if (event.keyCode === 13 && validate) {
                        event.preventDefault();
                        validateFunction();
                    }
                });

                passwordnew1.addEventListener("keyup", function (event) {
                    if (event.keyCode === 13 && validate) {
                        event.preventDefault();
                        validateFunction();
                    }
                });

                passwordnew2.addEventListener("keyup", function (event) {
                    if (event.keyCode === 13 && validate) {
                        event.preventDefault();
                        validateFunction();
                    }
                });
            </script>
        </form>
    </div>
    <p style="z-index: -1; opacity: 0; float: left; width: 96%;">AFC</p>
</main>
<div id="socialBar">
    <a href="https://www.facebook.com/AFC-Aldermaston-114651238068/" class="fa fa-facebook"></a>
    <a href="https://twitter.com/afcaldermaston?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor"
       class="fa fa-twitter"></a>
</div>
</body>
<footer>
    <small>&copy; Copyright 2020 - <%=MyUtils.getYear()%>, AFC Aldermaston, website provided by Liam Burnand</small>
</footer>
</html>
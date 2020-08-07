<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.bswdi.utils.*, java.util.Date, java.util.Calendar, java.util.GregorianCalendar" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="_headerPage.jsp"/>
    <title>Official website of AFC Aldermaston - Add what's on</title>
</head>
<body>
<jsp:include page="_topPageConfirmation.jsp"/>
<main class="main" style="text-align: center;">
    <div style="background-color: white; margin: 10px 0 0 0; padding: 5px 0 0 0;">
        <h2 style="text-decoration: underline; text-decoration-color: red; width: 100vx; text-align: center;">Add what's
            on</h2>
        <p style="color: red; padding: 0; margin: 0;" id="error">${error}</p><br>
        <%request.getSession().setAttribute("error", null);%>
        <form id="add" method="POST" action="addwhatson" enctype="multipart/form-data">
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
            <div>
                <label for="dateOfEvent">Date of event: </label>
                <%
                    Date date = new Date();
                    Calendar cal = new GregorianCalendar();
                    cal.setTime(date);
                    String dateString = cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH);
                %>
                <input type="date" id="dateOfEvent" name="dateOfEvent" min="<%=dateString%>">
            </div>
            <p><a onclick="document.getElementById('add').submit();" href="javascript:{}">Add</a></p>
            <br>
            <p><a href="whatson">Cancel</a></p>
            <script>
                function validateFunction() {
                    const title = document.getElementById('title').value.length;
                    const content = document.getElementById('content').value.length;
                    const dateOfEvent = document.getElementById('dateOfEvent').value.length();
                    if (title > 0 && content > 0 && dateOfEvent > 0) {
                        add.submit();
                    } else {
                        document.getElementById("error").innerHTML = "Title, content and date of event are required!";
                    }
                }
            </script>
        </form>
    </div>
</main>
</body>
<jsp:include page="_footerPage.jsp"/>
</html>
<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page
        import="com.bswdi.beans.*, java.util.Date, java.util.Calendar, java.util.GregorianCalendar, java.time.LocalDate" %>
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
        <%
            request.getSession().setAttribute("error", null);
            WhatsOn whatsOn = (WhatsOn) request.getSession().getAttribute("whatsOn");
        %>
        <form id="edit" method="POST" action="editwhatson" enctype="multipart/form-data">
            <div>
                <label for="title">Title: </label>
                <input type="title" id="title" name="title" value="<%=whatsOn.getTitle()%>">
            </div>
            <div>
                <label for="image">Image: </label>
                <input type="file" id="image" name="image" accept="image/*" style="max-width: 300px;">
                <%if (whatsOn.getImage() != null) {%><img src="data:image/jpg;base64,<%=whatsOn.getImage()%>"
                                                          style="max-width: 300px; max-height: 300px;"/><%}%>
            </div>
            <div>
                <label for="content">Content: </label>
                <textarea form="edit" id="content" name="content" rows="10"
                          cols="30"><%=whatsOn.getContent()%></textarea>
            </div>
            <div>
                <label for="dateOfEvent">Date of event: </label>
                <%
                    Date date = new Date();
                    Calendar cal = new GregorianCalendar();
                    cal.setTime(date);
                    String dateString = cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH);
                    LocalDate localDate = LocalDate.ofEpochDay(whatsOn.getDateOfEvent());
                %>
                <input type="date" name="dateOfEvent" id="dateOfEvent" min="<%=dateString%>" value="<%=localDate%>">
            </div>
            <p><a onclick="validateFunction()" href="javascript:{}">Confirm</a></p>
            <br>
            <p><a href="whatson">Cancel</a></p>
            <script>
                function validateFunction() {
                    const title = document.getElementById('title').value.length;
                    const content = document.getElementById('content').value.length;
                    const dateOfEvent = document.getElementById('dateOfEvent').value.length;
                    if (title > 0 && content > 0 && dateOfEvent > 0) {
                        edit.submit();
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
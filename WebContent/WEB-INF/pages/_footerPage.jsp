<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bswdi.utils.*, java.sql.Connection, com.bswdi.beans.*" %>
<!DOCTYPE html>
<html>
<footer>
	<%
		Connection con = MyUtils.getStoredConnection(request);
        Users user = MyUtils.getUser(request, con);
	%>
    <img src="https://hitwebcounter.com/counter/counter.php?page=7737765&style=0024&nbdigits=5&type=ip&initCount=0" title="Free Counter" Alt="web counter" border="0" <%if (user == null) {%>style="visibility: hidden;"<%}%> /><br><small>&copy; Copyright 2020 - <%=MyUtils.getYear()%>, AFC Aldermaston, website provided by <a href="https://bswdi.co.uk" style="text-decoration: underline; font-weight: 900;" target="_blank">BSWDI</a></small>
</footer>
</html>
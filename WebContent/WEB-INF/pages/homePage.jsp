<%--suppress ALL --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page
        import="com.bswdi.beans.*, java.sql.Connection, java.util.List, com.bswdi.utils.*, java.util.Date, java.time.LocalDate" %>
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
    <meta name="author" content="BSWDI">
    <meta name="distribution" content="Global">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="copyright"
          content="© Copyright 2020 - <%=MyUtils.getYear()%>, AFC Aldermaston, website provided by BSWDI">
    <title>Official website of AFC Aldermaston - Home</title>
    <%
        Connection con = MyUtils.getStoredConnection(request);
        String email = MyUtils.getEmailInCookie(request);
        Users user = null;
        try {
            user = DBUtils.findUser(con, email);
        } catch (Exception ignored) {

        }
        List<Sponsors> listSponsors = null;
        List<Affiliations> listAffiliations = null;
        try {
            listSponsors = DBUtils.querySponsors(con);
        } catch (Exception ignored) {

        }
        try {
            listAffiliations = DBUtils.queryAffiliations(con);
        } catch (Exception ignored) {

        }
    %>
    <style>
        #listItemAffiliation {
            padding: 10px;
            height: <%if (user != null && user.getRole() != Role.MANAGER) {%>150<%} else {%>100<%}%>px;
            width: 150px;
            display: inline-flex;
            flex-wrap: wrap;
        }

        #container {
            width: 100%;
            height: 270px;
            overflow: hidden;
            margin: 50px auto;
            background: white;
        }

        /*photobanner*/

        .photobanner {
            height: 250px;
            width: <%=(listSponsors.size() + 4) * 354%>px;
            padding: 20px 0 20px 0;
        }

        .photobanner img {
            -webkit-transition: all 0.5s ease;
            -moz-transition: all 0.5s ease;
            -o-transition: all 0.5s ease;
            -ms-transition: all 0.5s ease;
            transition: all 0.5s ease;
        }

        .photobanner img:hover {
            -webkit-transform: scale(1.1);
            -moz-transform: scale(1.1);
            -o-transform: scale(1.1);
            -ms-transform: scale(1.1);
            transform: scale(1.1);
            cursor: pointer;
            -webkit-box-shadow: 0 3px 5px rgba(0, 0, 0, 0.2);
            -moz-box-shadow: 0 3px 5px rgba(0, 0, 0, 0.2);
            box-shadow: 0 3px 5px rgba(0, 0, 0, 0.2);
        }


        /*keyframe animations*/

        .first {
            -webkit-animation: bannermove <%=listSponsors.size() * 15 / 7%>s linear infinite;
            -moz-animation: bannermove <%=listSponsors.size() * 15 / 7%>s linear infinite;
            -ms-animation: bannermove <%=listSponsors.size() * 15 / 7%>s linear infinite;
            animation: bannermove <%=listSponsors.size() * 15 / 7%>s linear infinite;
        }

        @keyframes bannermove {
            0% {
                margin-left: 0;
            }
            100% {
                margin-left: <%=listSponsors.size() * -355%>px;
            }

        }

        @-moz-keyframes bannermove {
            0% {
                margin-left: 0;
            }
            100% {
                margin-left: <%=listSponsors.size() * -355%>px;
            }

        }

        @-webkit-keyframes bannermove {
            0% {
                margin-left: 0;
            }
            100% {
                margin-left: <%=listSponsors.size() * -355%>px;
            }

        }

        @-ms-keyframes bannermove {
            0% {
                margin-left: 0;
            }
            100% {
                margin-left: <%=listSponsors.size() * -355%>px;
            }

        }

        @-o-keyframes bannermove {
            0% {
                margin-left: 0;
            }
            100% {
                margin-left: <%=listSponsors.size() * -355%>px;
            }

        }

    </style>
</head>
<body>
<jsp:include page="_topPage.jsp"/>
<!--<div style="width: 100%; background-color: yellow; text-align: center; padding: 10px 0 10px 0; margin: 10px 0 10px 0; display: block; float: left;"><b>ATTENTION!<BR>THIS WEBSITE IS CURRENTLY UNDER CONSTRUCTION<br>IF THERE ARE ANY ISSUES BUGS WITH THE WEBSITE, PLEASE EMAIL 'webmaster@afcaldermaston.com'</b></div><br></br><br></br><br><br>-->
<div class="welcomeBackground">
    <h2 class="welcomeHeader">Welcome</h2>
    <p class="welcome">AFC Aldermaston is a Chartered Standard club based in West Berkshire which is run by a group of
        volunteers. We have over 20 teams with age groups registered from U6’s to U18’s and onto Adult
        Football.<br><br><br></p>
    <%
        News news = null;
        try {
            news = DBUtils.findNewsLatest(con);
        } catch (Exception ignored) {

        }
        if (news != null) {%>
    <div id="listItem" class="latest"
         style="position: absolute; bottom: 0; background-color: white; left: 0; text-align: center; cursor: pointer;"
         onclick="location.href='news?id=<%=news.getID()%>';">
        Latest from news
        <img src="data:image/jpg;base64,<%=news.getImage()%>" alt=""
             onerror="this.onerror=null;this.src='images/default.png';"
             style="padding: 5px; max-height: 150px; max-width: 150px;">
        <span style="margin: 0.83em 0 0.83em 0; display: block; font-size: 1.5em; font-weight: bold;"><%=news.getTitle()%></span>
        <%Date date = new Date(news.getDate());%>
        <p style="text-align: left; padding: 10px 10px 10px 0;"><%=date.toString()%>
        </p>
    </div>
    <%
        }
        WhatsOn whatsOn = null;
        try {
            whatsOn = DBUtils.findWhatsOnLatest(con);
        } catch (Exception ignored) {

        }
        if (whatsOn != null) {
    %>
    <div id="listItem" class="latest"
         style="position: absolute; bottom: 0; background-color: white; left: 190px; text-align: center; cursor: pointer;" onclick="location.href='whatson?id=<%=whatsOn.getID()%>';">
        Latest from what's on
        <img src="data:image/jpg;base64,<%=whatsOn.getImage()%>" alt=""
             onerror="this.onerror=null;this.src='images/default.png';"
             style="padding: 5px; max-height: 150px; max-width: 150px;">
        <span style="margin: 0.83em 0 0.83em 0; display: block; font-size: 1.5em; font-weight: bold;"><%=whatsOn.getTitle()%></span>
        <%
            Date date1 = new Date(whatsOn.getDate());
            String dateOfEvent = "";
            try {
                LocalDate date = LocalDate.ofEpochDay(whatsOn.getDateOfEvent());
                dateOfEvent = String.valueOf(date.getDayOfWeek()).charAt(0) + String.valueOf(date.getDayOfWeek()).substring(1).toLowerCase() + " "
                        + date.getDayOfMonth() + " "
                        + String.valueOf(date.getMonth()).charAt(0) + String.valueOf(date.getMonth()).substring(1).toLowerCase() + " "
                        + date.getYear();
            } catch (Exception ignored) {

            }
        %>
        <p style="text-align: left; padding: 10px 10px 10px 0;"><%=date1.toString()%><br>
            Date of Event - <%=dateOfEvent%>
        </p>
    </div>
    <%}%>
</div>
<main class="main" style="text-align: center;">
    <div id="container" style="text-align: left;">
        <div class="photobanner">
            <%
                int i = 0;
                for (Sponsors sponsor : listSponsors) {
            %>
            <a href="<%=sponsor.getWebsite()%>" target="_blank" style="width: 350px; height: 250px;">
                <img <%if (i == 0) {%>class="first"<%}%> style="width: 350px; max-height: 250px;"
                     src="data:image/jpg;base64,<%=sponsor.getImage()%>" alt=""
                     onerror="this.onerror=null;this.src='images/default.png';">
            </a>
            <%
                    i++;
                }
                for (Sponsors sponsor : listSponsors) {
            %>
            <a href="<%=sponsor.getWebsite()%>" target="_blank" style="width: 350px; height: 250px;">
                <img style="width: 350px; max-height: 250px;" src="data:image/jpg;base64,<%=sponsor.getImage()%>" alt=""
                     onerror="this.onerror=null;this.src='images/default.png';">
            </a>
            <%}%>
        </div>
    </div>
    <div style="background-color: white; margin: 10px 0 0 0; padding: 5px 0 0 0;">
        <h2 style="text-decoration: underline; text-decoration-color: red; width: 100%; text-align: center;">
            AFFILIATIONS</h2>
        <div id="affiliationContainer">
            <%
                assert listAffiliations != null;
                for (Affiliations affiliation : listAffiliations) {%>
            <div id="listItemAffiliation">
                <div class="imgContainer">
                    <%if (affiliation.getWebsite() != null) {%>
                    <a href="<%=affiliation.getWebsite()%>" target="_blank"><%}%>
                        <img id="affiliationImage" src="data:image/jpg;base64,<%=affiliation.getImage()%>" alt=""
                             onerror="this.onerror=null;this.src='images/default.png';" style="padding: 5px;">
                    <%if (affiliation.getWebsite() != null) {%></a><%}%>
                    <%if (user != null && user.getRole() != Role.MANAGER) {%>
                    <p style="padding: 0; margin: 0;">
                        <a href="deleteaffiliation?id=<%=affiliation.getID()%>">Delete</a>
                    </p>
                    <%}%>
                </div>
            </div>
            <%}%>
        </div>
        <%if (user != null && user.getRole() != Role.MANAGER) {%>
        <p style="text-align: center;">
            <a href="addaffiliation">Add affiliation</a>
        </p>
        <%}%>
    </div>
    <p style="z-index: -1; opacity: 0; float: left; width: 96%;">AFC</p>
</main>
<div id="socialBar">
    <a href="https://www.facebook.com/AFC-Aldermaston-114651238068/" target="_blank" class="fa fa-facebook"></a>
    <a href="https://twitter.com/afcaldermaston?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor" target="_blank"
       class="fa fa-twitter"></a>
</div>
</body>
<jsp:include page="_footerPage.jsp"/>
</html>
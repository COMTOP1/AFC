package com.bswdi.servlets;

import com.bswdi.beans.*;
import com.bswdi.utils.*;

import java.io.*;
import java.sql.Connection;
import java.util.Base64;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Download servlet
 *
 * @author BSWDI
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/download"})
public class DownloadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public DownloadServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Connection con = MyUtils.getStoredConnection(request);
        int id = -1;
        String source = null, email = null;
        try {
            source = request.getParameter("s");
            if (request.getParameter("id") != null)
                id = Integer.parseInt(request.getParameter("id"));
            if (request.getParameter("email") != null)
                email = request.getParameter("email");
            if (id == -1 && email == null)
                throw new Exception("No id or email were entered");
        } catch (Exception e) {
            e.printStackTrace();
            if (source == null)
                response.sendRedirect("home");
            switch (Objects.requireNonNull(source)) {
                case "d":
                    response.sendRedirect("documents");
                case "p":
                    response.sendRedirect("programmes");
                case "s":
                    response.sendRedirect("sponsors");
                case "g":
                    response.sendRedirect("gallery");
                case "n":
                    response.sendRedirect("news");
                case "w":
                    response.sendRedirect("whatson");
                case "u":
                    response.sendRedirect("contacts");
                default:
                    response.sendRedirect("home");
            }
        }
        switch (Objects.requireNonNull(source)) {
            case "d":
                try {
                    Documents document = DBUtils.findDocument(con, id);
                    assert document != null;
                    method(response, out, (String) request.getServletContext().getAttribute("FILES_DIR"), document.getFileName());
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("documents");
                }
                break;
            case "p":
                try {
                    Programmes programme = DBUtils.findProgramme(con, id);
                    assert programme != null;
                    method(response, out, (String) request.getServletContext().getAttribute("FILES_DIR"), programme.getFileName());
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("programmes");
                }
                break;
            case "s":
                try {
                    Sponsors sponsor = DBUtils.findSponsor(con, id);
                    assert sponsor != null;
                    dbImage(response, out, sponsor.getImage(), 's', String.valueOf(sponsor.getID()), "sponsors");
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("sponsors");
                }
                break;
            case "g":
                try {
                    Images image = DBUtils.findImage(con, id);
                    assert image != null;
                    dbImage(response, out, image.getImage(), 'g', String.valueOf(image.getID()), "gallery");
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("gallery");
                }
                break;
            case "a":
                try {
                    Affiliations affiliation = DBUtils.findAffiliation(con, id);
                    assert affiliation != null;
                    dbImage(response, out, affiliation.getImage(), 'a', String.valueOf(affiliation.getID()), "home");
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("home");
                }
                break;
            case "n":
                try {
                    News news = DBUtils.findNews(con, id);
                    assert news != null;
                    dbImage(response, out, news.getImage(), 'n', String.valueOf(news.getID()), "news");
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("news");
                }
                break;
            case "w":
                try {
                    WhatsOn whatsOn = DBUtils.findWhatsOn(con, id);
                    assert whatsOn != null;
                    dbImage(response, out, whatsOn.getImage(), 'w', String.valueOf(whatsOn.getID()), "whatson");
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("whatson");
                }
                break;
            case "u":
                try {
                    Users user = DBUtils.findUser(con, email);
                    assert user != null;
                    dbImage(response, out, user.getImage(), 'u', user.getEmail(), "contact");
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("contact");
                }
                break;
            default:
                break;
        }
    }

    private void method(HttpServletResponse response, PrintWriter out, String filepath, String fileName) throws IOException {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        FileInputStream fileInputStream = new FileInputStream(filepath + fileName);
        int i;
        while ((i = fileInputStream.read()) != -1) out.write(i);
        fileInputStream.close();
        out.close();
    }

    private void dbImage(HttpServletResponse response, PrintWriter out, String image, char fileStarter, String unique, String redirect) throws IOException {
        try {
            byte[] imageDecoded = Base64.getDecoder().decode(image);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileStarter + "." + unique + ".png\"");
            ByteArrayInputStream stream = new ByteArrayInputStream(imageDecoded);
            int i;
            while ((i = stream.read()) != -1) out.write(i);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(redirect);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("documents");
    }
}

package com.bswdi.servlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

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
        int file = -1;
        try {
            file = Integer.parseInt(request.getParameter("file"));
        } catch (Exception e) {
            response.sendRedirect("documents");
        }
        String filename = null;
        switch (file) {
            case 0:
                filename = "Membership form 2020 - 21.pdf";
                break;
            case 1:
                filename = "Code of Conduct for Players 2020-21.pdf";
                break;
            case 2:
                filename = "Code of Conduct for Parents and Spectators 2020 - 21.pdf";
                break;
            case 3:
                filename = "Club Anti Discrimination Policy 2020 - 21.pdf";
                break;
            case 4:
                filename = "GDPR and Image Consent Form 2020-21.pdf";
                break;
            case 5:
                filename = "afcaldermaston equality policy.pdf";
                break;
            case 6:
                filename = "afcaldermaston safeguarding policy.pdf";
                break;
            default:
                response.sendRedirect("documents");
                break;
        }
        try {
            String filepath = "/Users/liam/Desktop/Code/Java/AFC/";
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            FileInputStream fileInputStream = new FileInputStream(filepath + filename);
            int i;
            while ((i = fileInputStream.read()) != -1) out.write(i);
            fileInputStream.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("documents");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("documents");
    }
}

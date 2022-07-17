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
@WebServlet(urlPatterns = {"/FileStore/*"})
public class FileStoreServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public FileStoreServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String pathInfo;
//        String filepath = "C:\\Users\\Administrator.LIAM-ACER-SERVE\\Desktop\\Code\\Java\\AFC\\FileStore\\";
//        String filepath = System.getProperty("catalina.home") + "/FileStore";
        String filepath = "/Users/liam/Desktop/Desktop - Liam’s MacBook Pro/Code/Java/AFC/WebContent/FileStore/";
//        String filepath = "/FileStore/";
        try {
            pathInfo = request.getPathInfo();
//            response.setHeader("Content-Disposition", "attachment; filename=\"" + pathInfo + "\"");
//            FileInputStream fileInputStream = new FileInputStream(filepath + pathInfo.replaceAll("%20", " "));
            FileInputStream fileInputStream = new FileInputStream(filepath + pathInfo.replaceAll("%20", "\\ "));
            int i;
            while ((i = fileInputStream.read()) != -1) out.write(i);
            fileInputStream.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/home");
    }
}

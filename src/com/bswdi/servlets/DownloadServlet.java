package com.bswdi.servlets;

import com.bswdi.beans.*;
import com.bswdi.utils.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
        String source = null;
//        String filepath = "C:\\Users\\Administrator.LIAM-ACER-SERVE\\Desktop\\Code\\Java\\AFC\\FileStore\\";
//        String filepath = System.getProperty("catalina.home") + "/FileStore";
//        String filepath = "/Users/liam/Desktop/Code/Java/AFC/WebContent/FileStore/";
        String filepath = "/FileStore/";
        try {
            id = Integer.parseInt(request.getParameter("id"));
            source = request.getParameter("s");
        } catch (Exception e) {
            response.sendRedirect("documents");
        }
        switch (Objects.requireNonNull(source)) {
            case "d":
                Documents document = null;
                try {
                    document = DBUtils.findDocument(con, id);
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("documents");
                }
                try {
                    assert document != null;
                    method(response, out, filepath, document.getFileName());
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("documents");
                }
                break;
            case "p":
                Programmes programme = null;
                try {
                    programme = DBUtils.findProgramme(con, id);
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("programmes");
                }
                try {
                    assert programme != null;
                    method(response, out, filepath, programme.getFileName());
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("programmes");
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

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("documents");
	}  
}

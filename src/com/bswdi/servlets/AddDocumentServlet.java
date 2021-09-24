package com.bswdi.servlets;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bswdi.beans.*;
import com.bswdi.utils.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Add document servlet
 *
 * @author BSWDI
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/adddocument"})
public class AddDocumentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ServletFileUpload uploader = null;

    /**
     * Constructor
     */
    public AddDocumentServlet() {
        super();
    }

    @Override
    public void init() {
        DiskFileItemFactory fileFactory = new DiskFileItemFactory();
        File filesDir = (File) getServletContext().getAttribute("FILES_DIR_FILE");
        fileFactory.setRepository(filesDir);
        this.uploader = new ServletFileUpload(fileFactory);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = MyUtils.getEmailInCookie(request);
        try {
            Connection con = MyUtils.getStoredConnection(request);
            Users user = DBUtils.findUser(con, email);
            assert user != null;
            if (user.getRole() != Role.MANAGER) {
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/addDocumentPage.jsp");
                dispatcher.forward(request, response);
            } else response.sendRedirect("documents");
        } catch (Exception e) {
            response.sendRedirect("documents");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletFileUpload.isMultipartContent(request))
            throw new ServletException("Content type is not multipart/form-data");
        Connection con = MyUtils.getStoredConnection(request);
        String name = request.getParameter("name"), fileName = null;
        if (name == null || name.equals("")) {
            request.getSession().setAttribute("error", "Name must not be empty");
            response.sendRedirect("adddocument");
        } else {
            try {
                List<FileItem> fileItemsList = uploader.parseRequest(request);
                for (FileItem fileItem : fileItemsList) {
                    fileName = fileItem.getName();
                    File file = new File(request.getServletContext().getAttribute("FILES_DIR") + File.separator + fileName);
                    fileItem.write(file);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (fileName == null || fileName.equals("")) {
                request.getSession().setAttribute("error", "Name and file must not be empty");
                System.out.println(name + " ~ " + fileName);
                response.sendRedirect("adddocument");
            } else
                try {
                    Documents document = new Documents(0, name, fileName);
                    DBUtils.insertDocument(con, document);
                    response.sendRedirect("documents");
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("documents");
                }
        }
    }
}

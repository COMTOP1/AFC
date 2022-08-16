package com.bswdi.servlets;

import com.bswdi.beans.Programmes;
import com.bswdi.beans.Role;
import com.bswdi.beans.Users;
import com.bswdi.utils.DBUtils;
import com.bswdi.utils.MyUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 * Add programme servlet
 *
 * @author BSWDI
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/addprogrammesecond"})
@MultipartConfig(maxFileSize = 67108864)
public class AddProgrammeSecondServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ServletFileUpload uploader = null;

    /**
     * Constructor
     */
    public AddProgrammeSecondServlet() {
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
        try {
            Connection con = MyUtils.getStoredConnection(request);
            Users user = MyUtils.getUser(request, con);
            assert user != null;
            if (user.getRole() != Role.MANAGER) {
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/addProgrammeSecondPage.jsp");
                dispatcher.forward(request, response);
            } else response.sendRedirect("programmes");
        } catch (Exception e) {
            response.sendRedirect("programmes");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ServletFileUpload.isMultipartContent(request))
            throw new ServletException("Content type is not multipart/form-data");
        Connection con = MyUtils.getStoredConnection(request);
        String name = (String) request.getSession().getAttribute("programmeName"), fileName = null;
        int programmeSeasonID = (int) request.getSession().getAttribute("programmeSeasonID");
        long dateOfProgramme = Long.parseLong(request.getSession().getAttribute("programmeDate").toString());
        if (name == null || name.equals("")) {
            request.getSession().setAttribute("error", "Name must not be empty");
            response.sendRedirect("addprogramme");
        } else {
            try {
                List<FileItem> fileItemsList = uploader.parseRequest(request);
                for (FileItem fileItem : fileItemsList) {
                    fileName = fileItem.getName();
                    File file = new File(request.getServletContext().getAttribute("FILES_DIR") + fileName);
                    fileItem.write(file);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (fileName == null || fileName.equals("")) {
                request.getSession().setAttribute("error", "Name, programme season and file must not be empty");
                response.sendRedirect("addprogramme");
            } else
                try {
                    Programmes programme = new Programmes(0, name, fileName, dateOfProgramme, programmeSeasonID);
                    DBUtils.insertProgramme(con, programme);
                    response.sendRedirect("programmes");
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("programmes");
                }
        }
    }
}

package com.bswdi.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Base64;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.bswdi.beans.*;
import com.bswdi.utils.*;

/**
 * Edit news servlet
 *
 * @author BSWDI
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/editnews"})
@MultipartConfig(maxFileSize = 16177215)
public class EditNewsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public EditNewsServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = MyUtils.getEmailInCookie(request);
        try {
            Connection con = MyUtils.getStoredConnection(request);
            Users user = DBUtils.findUser(con, email);
            assert user != null;
            if (user.getRole() != Role.MANAGER) {
                int id = Integer.parseInt(request.getParameter("id"));
                News news = DBUtils.findNews(con, id);
                request.getSession().setAttribute("news", news);
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/editNewsPage.jsp");
                dispatcher.forward(request, response);
            } else response.sendRedirect("news");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("news");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con = MyUtils.getStoredConnection(request);
        String title, image, content;
        News newsOld = null;
        try {
            newsOld = (News) request.getSession().getAttribute("news");
            request.getSession().setAttribute("news", null);
        } catch (Exception e) {
            request.getSession().setAttribute("news", null);
        }
        title = request.getParameter("title");
        InputStream inputStream;
        Part filePart;
        try {
            filePart = request.getPart("image");
        } catch (Exception e) {
            e.printStackTrace();
            filePart = null;
        }
        if (filePart != null) {
            inputStream = filePart.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) outputStream.write(buffer, 0, bytesRead);
            byte[] imageBytes = outputStream.toByteArray();
            image = Base64.getEncoder().encodeToString(imageBytes);
            if (image.equals("")) image = null;
            inputStream.close();
            outputStream.close();
        } else {
            assert newsOld != null;
            image = newsOld.getImage();
        }
        content = request.getParameter("content");
        try {
            assert newsOld != null;
            News news = new News(newsOld.getID(), title, image, content, MyUtils.getTime());
            DBUtils.updateNews(con, news);
            response.sendRedirect("news");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("news");
        }
    }
}

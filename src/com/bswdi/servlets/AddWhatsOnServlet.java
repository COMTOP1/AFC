package com.bswdi.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

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
 * Add what's on servlet
 *
 * @author BSWDI
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/addwhatson"})
@MultipartConfig(maxFileSize = 16177215)
public class AddWhatsOnServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public AddWhatsOnServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Connection con = MyUtils.getStoredConnection(request);
            Users user = MyUtils.getUser(request, response, con);
            assert user != null;
            if (user.getRole() != Role.MANAGER) {
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/addWhatsOnPage.jsp");
                dispatcher.forward(request, response);
            } else response.sendRedirect("whatson");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("whatson");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con = MyUtils.getStoredConnection(request);
        String title, image = null, content;
        long dateOfEvent = 0L;
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
        }
        content = request.getParameter("content");
        try {
            String dateString = request.getParameter("dateOfEvent");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(dateString);
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            dateOfEvent = localDate.toEpochDay();
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Please enter a date");
            response.sendRedirect("addwhatson");
        }
        try {
            WhatsOn whatsOn = new WhatsOn(0, title, image, content, MyUtils.getTime(), dateOfEvent);
            DBUtils.insertWhatsOn(con, whatsOn);
            response.sendRedirect("whatson");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("whatson");
        }
    }
}

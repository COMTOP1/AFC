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
 * Edit what's on servlet
 *
 * @author BSWDI
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/editwhatson"})
@MultipartConfig(maxFileSize = 16177215)
public class EditWhatsOnServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public EditWhatsOnServlet() {
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
                WhatsOn whatsOn = DBUtils.findWhatsOn(con, id);
                request.getSession().setAttribute("whatsOn", whatsOn);
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/editWhatsOnPage.jsp");
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
        String title, image, content;
        long dateOfEvent = 0L;
        WhatsOn whatsOnOld = null;
        try {
            whatsOnOld = (WhatsOn) request.getSession().getAttribute("whatsOn");
            request.getSession().setAttribute("whatsOn", null);
        } catch (Exception e) {
            request.getSession().setAttribute("whatsOn", null);
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
            assert whatsOnOld != null;
            image = whatsOnOld.getImage();
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
            assert whatsOnOld != null;
            WhatsOn whatsOn = new WhatsOn(whatsOnOld.getID(), title, image, content, MyUtils.getTime(), dateOfEvent);
            DBUtils.updateWhatsOn(con, whatsOn);
            response.sendRedirect("whatson");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("whatson");
        }
    }
}

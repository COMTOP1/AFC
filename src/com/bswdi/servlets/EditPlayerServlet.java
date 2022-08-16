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
import java.util.List;

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
 * Edit player servlet
 *
 * @author BSWDI
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/editplayer"})
@MultipartConfig(maxFileSize = 16177215)
public class EditPlayerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public EditPlayerServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Connection con = MyUtils.getStoredConnection(request);
            Users user = MyUtils.getUser(request, con);
            assert user != null;
            if (user.getRole() != Role.MANAGER) {
                Players player = DBUtils.findPlayer(con, id);
                request.getSession().setAttribute("player", player);
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/pages/editPlayerPage.jsp");
                dispatcher.forward(request, response);
            } else response.sendRedirect("players");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("players");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con = MyUtils.getStoredConnection(request);
        String name, image, position;
        long dateOfBirth = 0L;
        boolean captain;
        int teamID = 0;
        Players playerOld = null;
        try {
            playerOld = (Players) request.getSession().getAttribute("player");
            request.getSession().setAttribute("player", null);
        } catch (Exception e) {
            request.getSession().setAttribute("player", null);
        }
        name = request.getParameter("name");
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
            assert playerOld != null;
            image = playerOld.getImage();
        }
        try {
            String dateString = request.getParameter("dateOfBirth");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(dateString);
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            dateOfBirth = localDate.toEpochDay();
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Please enter a date");
            assert playerOld != null;
            response.sendRedirect("editplayer?id=" + playerOld.getID());
        }
        position = request.getParameter("position");
        captain = "Y".equals(request.getParameter("captain"));
        try {
            List<Players> list = DBUtils.queryPlayersTeam(con, teamID);
            boolean captainPresent = false;
            for (Players player : list)
                if (player.getCaptain()) {
                    captainPresent = true;
                    break;
                }
            if (captainPresent) {
                request.getSession().setAttribute("error", "A captain is already present for this team");
                captain = false;
            }
        } catch (Exception ignored) {

        }
        try {
            teamID = Integer.parseInt(request.getParameter("teamID"));
            if (teamID < 0) teamID = 0;
        } catch (Exception e) {
            teamID = 0;
        }
        try {
            assert playerOld != null;
            Players player = new Players(playerOld.getID(), name, image, dateOfBirth, position, captain, teamID);
            DBUtils.updatePlayer(con, player);
            response.sendRedirect("players");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("players");
        }
    }
}

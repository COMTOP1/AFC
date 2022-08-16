package com.bswdi.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bswdi.beans.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Utilities for database and queries
 *
 * @author BSWDI
 * @version 1.0
 */
@SuppressWarnings("unused")
public class DBUtils {

    /**
     * Sets time zone
     *
     * @param con connection
     * @throws SQLException SQL exception
     */
    public static void SQLTimeZone(Connection con) throws SQLException {
        String sql1 = "SET GLOBAL time_zone = '+00:00';", sql2 = "SET @@global.time_zone = '+00:00';";
        PreparedStatement pstm1 = con.prepareStatement(sql1), pstm2 = con.prepareStatement(sql2);
        pstm1.executeQuery();
        pstm2.executeQuery();
    }

    /**
     * Returns affiliations
     *
     * @param con connection
     * @return List<Affiliations> list
     * @throws SQLException SQL exception
     */
    public static List<Affiliations> queryAffiliations(Connection con) throws SQLException {
        String sql = "SELECT * FROM affiliations";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        return getAffiliationsMethod(rs);
    }

    /**
     * Get affiliations method
     *
     * @param rs result set
     * @return List<Affiliations> list
     * @throws SQLException SQL exception
     */
    private static List<Affiliations> getAffiliationsMethod(ResultSet rs) throws SQLException {
        List<Affiliations> list = new ArrayList<>();
        while (rs.next()) {
            Affiliations affiliation = getAffiliationMethod(rs);
            list.add(affiliation);
        }
        return list;
    }

    /**
     * Returns affiliation
     *
     * @param con connection
     * @param id  id
     * @return Affiliations affiliation
     * @throws SQLException SQL exception
     */
    public static Affiliations findAffiliation(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM affiliations WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) return getAffiliationMethod(rs);
        else return null;
    }

    /**
     * Get affiliation method
     *
     * @param rs result set
     * @return Affiliations affiliation
     * @throws SQLException SQL exception
     */
    private static Affiliations getAffiliationMethod(ResultSet rs) throws SQLException {
        int id;
        String name, website, image;
        id = rs.getInt("id");
        name = rs.getString("name");
        website = rs.getString("website");
        image = rs.getString("image");
        return new Affiliations(id, name, website, image);
    }

    /**
     * Update affiliation
     *
     * @param con         connection
     * @param affiliation affiliation
     * @throws SQLException SQL exception
     */
    public static void updateAffiliation(Connection con, Affiliations affiliation) throws SQLException {
        backupAffiliation(con, affiliation.getID(), "UPDATE");
        String sql = "UPDATE affiliations SET name = ?, website = ?, image = ? WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, affiliation.getName());
        pstm.setString(2, affiliation.getWebsite());
        pstm.setString(3, affiliation.getWebsite());
        pstm.setInt(4, affiliation.getID());
        pstm.executeUpdate();
    }

    /**
     * Insert affiliation
     *
     * @param con         connection
     * @param affiliation affiliation
     * @throws SQLException SQL exception
     */
    public static void insertAffiliation(Connection con, Affiliations affiliation) throws SQLException {
        String sql = "INSERT INTO affiliations (name, website, image) VALUES (?, ?, ?)";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, affiliation.getName());
        pstm.setString(2, affiliation.getWebsite());
        pstm.setString(3, affiliation.getImage());
        pstm.executeUpdate();
    }

    /**
     * Delete affiliation
     *
     * @param con connection
     * @param id  id
     * @throws SQLException SQL exception
     */
    public static void deleteAffiliation(Connection con, int id) throws SQLException {
        backupAffiliation(con, id, "DELETE");
        String sql = "DELETE FROM affiliations WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    /**
     * Backup affiliation
     *
     * @param con    connection
     * @param id     id
     * @param action action
     * @throws SQLException SQL exception
     */
    private static void backupAffiliation(Connection con, int id, String action) throws SQLException {
        String sql = "INSERT INTO affiliations_backup (id, name, website, image, action) SELECT id, name, website, image, ? FROM affiliations WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, action);
        pstm.setInt(2, id);
        pstm.executeUpdate();
    }

    /**
     * Returns documents
     *
     * @param con connection
     * @return List<Documents> list
     * @throws SQLException SQL exception
     */
    public static List<Documents> queryDocuments(Connection con) throws SQLException {
        String sql = "SELECT * FROM documents";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        return getDocumentsMethod(rs);
    }

    /**
     * Get documents method
     *
     * @param rs result set
     * @return List<Documents> list
     * @throws SQLException SQL exception
     */
    private static List<Documents> getDocumentsMethod(ResultSet rs) throws SQLException {
        List<Documents> list = new ArrayList<>();
        while (rs.next()) {
            Documents document = getDocumentMethod(rs);
            list.add(document);
        }
        return list;
    }

    /**
     * Returns document
     *
     * @param con connection
     * @param id  id
     * @return Documents document
     * @throws SQLException SQL exception
     */
    public static Documents findDocument(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM documents WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) return getDocumentMethod(rs);
        else return null;
    }

    /**
     * Get document method
     *
     * @param rs result set
     * @return Documents document
     * @throws SQLException SQL exception
     */
    private static Documents getDocumentMethod(ResultSet rs) throws SQLException {
        int id;
        String name, fileName;
        id = rs.getInt("id");
        name = rs.getString("name");
        fileName = rs.getString("file_name");
        return new Documents(id, name, fileName);
    }

    /**
     * Update document
     *
     * @param con      connection
     * @param document document
     * @throws SQLException SQL exception
     */
    public static void updateDocument(Connection con, Documents document) throws SQLException {
        backupDocument(con, document.getID(), "UPDATE");
        String sql = "UPDATE documents SET name = ?, file_name = ? WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, document.getName());
        pstm.setString(2, document.getFileName());
        pstm.setInt(3, document.getID());
        pstm.executeUpdate();
    }

    /**
     * Insert document
     *
     * @param con      connection
     * @param document document
     * @throws SQLException SQL exception
     */
    public static void insertDocument(Connection con, Documents document) throws SQLException {
        String sql = "INSERT INTO documents (name, file_name) VALUES (?, ?)";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, document.getName());
        pstm.setString(2, document.getFileName());
        pstm.executeUpdate();
    }

    /**
     * Delete document
     *
     * @param con connection
     * @param id  id
     * @throws SQLException SQL exception
     */
    public static void deleteDocument(Connection con, int id) throws SQLException {
        backupDocument(con, id, "DELETE");
        String sql = "DELETE FROM documents WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    /**
     * Backup document
     *
     * @param con    connection
     * @param id     id
     * @param action action
     * @throws SQLException SQL exception
     */
    private static void backupDocument(Connection con, int id, String action) throws SQLException {
        String sql = "INSERT INTO documents_backup (id, name, file_name, action) SELECT id, name, file_name, ? FROM documents WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, action);
        pstm.setInt(2, id);
        pstm.executeUpdate();
    }

    /**
     * Returns images
     *
     * @param con connection
     * @return List<Images> list
     * @throws SQLException SQL exception
     */
    public static List<Images> queryImages(Connection con) throws SQLException {
        String sql = "SELECT * FROM images";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        List<Images> list = new ArrayList<>();
        while (rs.next()) {
            int id;
            String image1, caption;
            id = rs.getInt("id");
            image1 = rs.getString("image");
            caption = rs.getString("caption");
            Images image = new Images(id, image1, caption);
            list.add(image);
        }
        return list;
    }

    /**
     * Returns image
     *
     * @param con connection
     * @param id  id
     * @return Images image
     * @throws SQLException SQL exception
     */
    public static Images findImage(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM images WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            String image1, caption;
            image1 = rs.getString("image");
            caption = rs.getString("caption");
            return new Images(id, image1, caption);
        } else return null;
    }

    /**
     * Update image
     *
     * @param con   connection
     * @param image image
     * @throws SQLException SQL exception
     */
    public static void updateImage(Connection con, Images image) throws SQLException {
        backupImage(con, image.getID(), "UPDATE");
        String sql = "UPDATE images SET image = ?, caption = ? WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, image.getImage());
        pstm.setString(2, image.getCaption());
        pstm.setInt(3, image.getID());
        pstm.executeUpdate();
    }

    /**
     * Insert image
     *
     * @param con   connection
     * @param image image
     * @throws SQLException SQL exception
     */
    public static void insertImage(Connection con, Images image) throws SQLException {
        String sql = "INSERT INTO images (image, caption) VALUES (?, ?)";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, image.getImage());
        pstm.setString(2, image.getCaption());
        pstm.executeUpdate();
    }

    /**
     * Delete image
     *
     * @param con connection
     * @param id  id
     * @throws SQLException SQL exception
     */
    public static void deleteImage(Connection con, int id) throws SQLException {
        backupImage(con, id, "DELETE");
        String sql = "DELETE FROM images WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    /**
     * Backup image
     *
     * @param con    connection
     * @param id     id
     * @param action action
     * @throws SQLException SQL exception
     */
    private static void backupImage(Connection con, int id, String action) throws SQLException {
        String sql = "INSERT INTO images_BACKUP (id, image, caption, action) SELECT id, image, caption, ? FROM images WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, action);
        pstm.setInt(2, id);
        pstm.executeUpdate();
    }

    public static List<JWTToken> queryJWToken(Connection con) throws SQLException {
        String sql = "SELECT * FROM jwt_tokens";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        return getJWTTokenMethodMultiple(rs);
    }

    private static List<JWTToken> getJWTTokenMethodMultiple(ResultSet rs) throws SQLException {
        List<JWTToken> list = new ArrayList<>();
        while (rs.next()) {
            JWTToken jwtToken = getJWTTokenMethodSingle(rs);
            list.add(jwtToken);
        }
        return list;
    }

    public static JWTToken findJWTToken(Connection con, long id) throws SQLException {
        String sql = "SELECT * FROM jwt_tokens WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setLong(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) return getJWTTokenMethodSingle(rs);
        return null;
    }

    private static JWTToken getJWTTokenMethodSingle(ResultSet rs) throws SQLException {
        long id, iat, exp;
        String email, userAgent;
        id = rs.getLong("id");
        email = rs.getString("email");
        iat = rs.getLong("iat");
        exp = rs.getLong("exp");
        userAgent = rs.getString("user_agent");
        return new JWTToken(id, email, iat, exp, userAgent);
    }

    public static long insertJWTToken(Connection con, JWTToken jwtToken) throws SQLException {
        String sql1 = "INSERT INTO jwt_tokens (email, iat, exp, user_agent) VALUES (?, ?, ?, ?)", sql2 = "SELECT id FROM jwt_tokens WHERE email = ? AND iat = ? AND exp = ? AND user_agent = ?";
        PreparedStatement pstm1 = con.prepareStatement(sql1), pstm2 = con.prepareStatement(sql2);
        pstm1.setString(1, jwtToken.getEmail());
        pstm1.setLong(2, jwtToken.getIat());
        pstm1.setLong(3, jwtToken.getExp());
        pstm1.setString(4, jwtToken.getUserAgent());
        pstm1.executeUpdate();
        pstm2.setString(1, jwtToken.getEmail());
        pstm2.setLong(2, jwtToken.getIat());
        pstm2.setLong(3, jwtToken.getExp());
        pstm2.setString(4, jwtToken.getUserAgent());
        ResultSet rs = pstm2.executeQuery();
        rs.next();
        return rs.getLong("id");
    }

    /**
     * Delete jwt token
     *
     * @param con connection
     * @param id  id
     * @throws SQLException SQL exception
     */
    public static void deleteJWTToken(Connection con, long id) throws SQLException {
        backupJWTToken(con, id, "DELETE");
        String sql = "DELETE FROM jwt_tokens WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setLong(1, id);
        pstm.executeUpdate();
    }

    public static void deleteJWTTokenExpired(Connection con, long exp) throws SQLException {
        String sql = "DELETE FROM jwt_tokens WHERE exp <= ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setLong(1, exp);
        pstm.executeUpdate();
    }

    /**
     * Backup jwt token
     *
     * @param con    connection
     * @param id     id
     * @param action action
     * @throws SQLException SQL exception
     */
    private static void backupJWTToken(Connection con, long id, String action) throws SQLException {
        String sql = "INSERT INTO jwt_tokens_backup (id, email, iat, exp, user_agent, action) SELECT id, email, iat, exp, user_agent, ? FROM jwt_tokens WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, action);
        pstm.setLong(2, id);
        pstm.executeUpdate();
    }

    /**
     * Returns all news articles
     *
     * @param con connection
     * @return List<News> list
     * @throws SQLException SQL exception
     */
    public static List<News> queryNews(Connection con) throws SQLException {
        String sql = "SELECT * FROM news ORDER BY date DESC";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        return getNewsMethodMultiple(rs);
    }

    /**
     * Get news method multiple
     *
     * @param rs result set
     * @return List<News> list
     * @throws SQLException SQL exception
     */
    private static List<News> getNewsMethodMultiple(ResultSet rs) throws SQLException {
        List<News> list = new ArrayList<>();
        while (rs.next()) {
            News news = getNewsMethodSingle(rs);
            list.add(news);
        }
        return list;
    }

    /**
     * Returns one news article
     *
     * @param con connection
     * @param id  id
     * @return Images image
     * @throws SQLException SQL exception
     */
    public static News findNews(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM news WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) return getNewsMethodSingle(rs);
        else return null;
    }

    /**
     * Returns news latest
     *
     * @param con connection
     * @return News news
     * @throws SQLException SQL exception
     */
    public static News findNewsLatest(Connection con) throws SQLException {
        String sql = "SELECT * FROM news ORDER BY id DESC";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) return getNewsMethodSingle(rs);
        else return null;
    }

    /**
     * Get news method single
     *
     * @param rs result set
     * @return News news
     * @throws SQLException SQL exception
     */
    private static News getNewsMethodSingle(ResultSet rs) throws SQLException {
        int id;
        String title, image, content;
        long date;
        id = rs.getInt("id");
        title = rs.getString("title");
        image = rs.getString("image");
        content = rs.getString("content");
        date = rs.getLong("date");
        return new News(id, title, image, content, date);
    }

    /**
     * Update news
     *
     * @param con  connection
     * @param news news
     * @throws SQLException SQL exception
     */
    public static void updateNews(Connection con, News news) throws SQLException {
        backupNews(con, news.getID(), "UPDATE");
        String sql = "UPDATE news SET title = ?, image = ?, content = ?, date = ? WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql), pstm1;
        pstm1 = setNewsMethod(pstm, news);
        pstm1.setInt(5, news.getID());
        pstm1.executeUpdate();
    }

    /**
     * Insert news
     *
     * @param con  connection
     * @param news news
     * @throws SQLException SQL exception
     */
    public static void insertNews(Connection con, News news) throws SQLException {
        String sql = "INSERT INTO news (title, image, content, date) VALUES (?, ?, ?, ?)";
        PreparedStatement pstm = con.prepareStatement(sql), pstm1;
        pstm1 = setNewsMethod(pstm, news);
        pstm1.executeUpdate();
    }

    /**
     * Set news method
     *
     * @param pstm prepared statement
     * @param news news
     * @return PreparedStatement pstm
     * @throws SQLException SQL exception
     */
    private static PreparedStatement setNewsMethod(PreparedStatement pstm, News news) throws SQLException {
        pstm.setString(1, news.getTitle());
        pstm.setString(2, news.getImage());
        pstm.setString(3, news.getContent());
        pstm.setLong(4, news.getDate());
        return pstm;
    }

    /**
     * Delete news
     *
     * @param con connection
     * @param id  id
     * @throws SQLException SQL exception
     */
    public static void deleteNews(Connection con, int id) throws SQLException {
        backupNews(con, id, "DELETE");
        String sql = "DELETE FROM news WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    /**
     * Backup news
     *
     * @param con    connection
     * @param id     id
     * @param action action
     * @throws SQLException SQL exception
     */
    private static void backupNews(Connection con, int id, String action) throws SQLException {
        String sql = "INSERT INTO news_backup (id, title, image, content, date, action) SELECT id, title, image, content, date, ? FROM news WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, action);
        pstm.setInt(2, id);
        pstm.executeUpdate();
    }

    /**
     * Returns players
     *
     * @param con connection
     * @return List<Players> list
     * @throws SQLException SQL exception
     */
    public static List<Players> queryPlayers(Connection con) throws SQLException {
        String sql = "SELECT * FROM players";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        return getPlayersMethod(rs);
    }

    /**
     * Returns players by team
     *
     * @param con    connection
     * @param teamID team id
     * @return List<Players> list
     * @throws SQLException SQL exception
     */
    public static List<Players> queryPlayersTeam(Connection con, int teamID) throws SQLException {
        String sql = "SELECT * FROM players WHERE team_id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, teamID);
        ResultSet rs = pstm.executeQuery();
        return getPlayersMethod(rs);
    }

    /**
     * Get players method
     *
     * @param rs result set
     * @return List<Players> list
     * @throws SQLException SQL exception
     */
    private static List<Players> getPlayersMethod(ResultSet rs) throws SQLException {
        List<Players> list = new ArrayList<>();
        while (rs.next()) {
            Players player = getPlayerMethod(rs);
            list.add(player);
        }
        return list;
    }

    /**
     * Returns player
     *
     * @param con connection
     * @param id  id
     * @return Players player
     * @throws SQLException SQL exception
     */
    public static Players findPlayer(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM players WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) return getPlayerMethod(rs);
        else return null;
    }

    /**
     * Get player method
     *
     * @param rs result set
     * @return Players player
     * @throws SQLException SQL exception
     */
    private static Players getPlayerMethod(ResultSet rs) throws SQLException {
        int id, teamID;
        String name, image, position;
        long dateOfBirth;
        boolean captain;
        id = rs.getInt("id");
        name = rs.getString("name");
        image = rs.getString("image");
        dateOfBirth = Long.parseLong(rs.getString("date_of_birth"));
        position = rs.getString("position");
        captain = rs.getBoolean("captain");
        teamID = rs.getInt("team_id");
        return new Players(id, name, image, dateOfBirth, position, captain, teamID);
    }

    /**
     * Update player
     *
     * @param con    connection
     * @param player player
     * @throws SQLException SQL exception
     */
    public static void updatePlayer(Connection con, Players player) throws SQLException {
        backupPlayer(con, player.getID(), "UPDATE");
        String sql = "UPDATE players SET name = ?, image = ?, date_of_birth = ?, position = ?, captain = ?, team_id = ? WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql), pstm1;
        pstm1 = setPlayerMethod(pstm, player);
        pstm1.setInt(7, player.getID());
        pstm1.executeUpdate();
    }

    /**
     * Insert player
     *
     * @param con    connection
     * @param player player
     * @throws SQLException SQL exception
     */
    public static void insertPlayer(Connection con, Players player) throws SQLException {
        String sql = "INSERT INTO players (name, image, date_of_birth, position, captain, team_id) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = con.prepareStatement(sql), pstm1;
        pstm1 = setPlayerMethod(pstm, player);
        pstm1.executeUpdate();
    }

    /**
     * Set player method
     *
     * @param pstm   prepared statement
     * @param player player
     * @return PreparedStatement pstm
     * @throws SQLException SQL exception
     */
    private static PreparedStatement setPlayerMethod(PreparedStatement pstm, Players player) throws SQLException {
        pstm.setString(1, player.getName());
        pstm.setString(2, player.getImage());
        pstm.setLong(3, player.getDateOfBirth());
        pstm.setString(4, player.getPosition());
        pstm.setBoolean(5, player.getCaptain());
        pstm.setInt(6, player.getTeamID());
        return pstm;
    }

    /**
     * Delete player
     *
     * @param con connection
     * @param id  id
     * @throws SQLException SQL exception
     */
    public static void deletePlayer(Connection con, int id) throws SQLException {
        backupPlayer(con, id, "DELETE");
        String sql = "DELETE FROM players WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    /**
     * Backup player
     *
     * @param con    connection
     * @param id     id
     * @param action action
     * @throws SQLException SQL exception
     */
    private static void backupPlayer(Connection con, int id, String action) throws SQLException {
        String sql = "INSERT INTO players_backup (id, name, image, date_of_birth, position, captain, team_id, action) SELECT id, name, image, date_of_birth, position, captain, team_id, ? FROM players WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, action);
        pstm.setInt(2, id);
        pstm.executeUpdate();
    }

    /**
     * Returns programmes
     *
     * @param con connection
     * @return List<Programmes> list
     * @throws SQLException SQL exception
     */
    public static List<Programmes> queryProgrammes(Connection con) throws SQLException {
        String sql = "SELECT * FROM programmes ORDER BY date_of_programme DESC";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        return getProgrammesMethod(rs);
    }

    /**
     * Get programmes method
     *
     * @param rs result set
     * @return List<Programmes> list
     * @throws SQLException SQL exception
     */
    private static List<Programmes> getProgrammesMethod(ResultSet rs) throws SQLException {
        List<Programmes> list = new ArrayList<>();
        while (rs.next()) {
            Programmes programme = getProgrammeMethod(rs);
            list.add(programme);
        }
        return list;
    }

    /**
     * Returns programme
     *
     * @param con connection
     * @param id  id
     * @return Programme programme
     * @throws SQLException SQL exception
     */
    public static Programmes findProgramme(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM programmes WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) return getProgrammeMethod(rs);
        else return null;
    }

    /**
     * Get programme method
     *
     * @param rs result set
     * @return Programmes programme
     * @throws SQLException SQL exception
     */
    private static Programmes getProgrammeMethod(ResultSet rs) throws SQLException {
        int id, programmeSeasonID;
        String name, fileName;
        long dateOfProgramme;
        id = rs.getInt("id");
        name = rs.getString("name");
        fileName = rs.getString("file_name");
        dateOfProgramme = rs.getLong("date_of_programme");
        programmeSeasonID = rs.getInt("programme_season_id");
        return new Programmes(id, name, fileName, dateOfProgramme, programmeSeasonID);
    }

    /**
     * Insert programme
     *
     * @param con       connection
     * @param programme programme
     * @throws SQLException SQL exception
     */
    public static void insertProgramme(Connection con, Programmes programme) throws SQLException {
        String sql = "INSERT INTO programmes (name, file_name, date_of_programme) VALUES (?, ?, ?)";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, programme.getName());
        pstm.setString(2, programme.getFileName());
        pstm.setLong(3, programme.getDateOfProgramme());
        pstm.executeUpdate();
    }

    /**
     * Delete programme
     *
     * @param con connection
     * @param id  id
     * @throws SQLException SQL exception
     */
    public static void deleteProgramme(Connection con, int id) throws SQLException {
        backupProgramme(con, id);
        String sql = "DELETE FROM programmes WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    /**
     * Backup programme
     *
     * @param con connection
     * @param id  id
     * @throws SQLException SQL exception
     */
    private static void backupProgramme(Connection con, int id) throws SQLException {
        String sql = "INSERT INTO programmes_backup (id, name, file_name, date_of_programme, action) SELECT id, name, file_name, date_of_programme, 'DELETE' FROM programmes WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    /**
     * Return programme seasons
     *
     * @param con connection
     * @return List<ProgrammeSeasons> list
     * @throws SQLException SQL exception
     */
    public static List<ProgrammeSeasons> queryProgrammeSeasons(Connection con) throws SQLException {
        String sql = "SELECT * FROM programme_seasons";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        return getProgrammeSeasonsMethod(rs);
    }

    /**
     * Get programme seasons method
     *
     * @param rs result set
     * @return List<ProgrammeSeasons> list
     * @throws SQLException SQL exception
     */
    private static List<ProgrammeSeasons> getProgrammeSeasonsMethod(ResultSet rs) throws SQLException {
        List<ProgrammeSeasons> list = new ArrayList<>();
        while (rs.next()) {
            ProgrammeSeasons programmeSeason = getProgrammeSeasonMethod(rs);
            list.add(programmeSeason);
        }
        return list;
    }

    /**
     * Returns programme season
     *
     * @param con connection
     * @param id  id
     * @return ProgrammeSeasons programme season
     * @throws SQLException SQL exception
     */
    public static ProgrammeSeasons findProgrammeSeason(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM programme_seasons WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) return getProgrammeSeasonMethod(rs);
        else return null;
    }

    /**
     * Get programme season method
     *
     * @param rs result set
     * @return ProgrammeSeasons programmeSeason
     * @throws SQLException SQL exception
     */
    private static ProgrammeSeasons getProgrammeSeasonMethod(ResultSet rs) throws SQLException {
        int id;
        String season;
        id = rs.getInt("id");
        season = rs.getString("season");
        return new ProgrammeSeasons(id, season);
    }

    /**
     * Update programme season
     *
     * @param con  connection
     * @param programmeSeason programme season
     * @throws SQLException SQL exception
     */
    public static void updateProgrammeSeason(Connection con, ProgrammeSeasons programmeSeason) throws SQLException {
        backupProgrammeSeason(con, programmeSeason.getID(), "UPDATE");
        String sql = "UPDATE programme_seasons SET season = ? WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql), pstm1;
        pstm1 = setProgrammeSeasonMethod(pstm, programmeSeason);
        pstm1.setInt(2, programmeSeason.getID());
        pstm1.executeUpdate();
    }

    /**
     * Insert programme season
     *
     * @param con  connection
     * @param programmeSeason programme season
     * @throws SQLException SQL exception
     */
    public static void insertProgrammeSeason(Connection con, ProgrammeSeasons programmeSeason) throws SQLException {
        String sql = "INSERT INTO programme_seasons (season) VALUES (?)";
        PreparedStatement pstm = con.prepareStatement(sql), pstm1;
        pstm1 = setProgrammeSeasonMethod(pstm, programmeSeason);
        pstm1.executeUpdate();
    }

    /**
     * Set programme season method
     *
     * @param pstm prepared statement
     * @param programmeSeason programme season
     * @return PreparedStatement pstm
     * @throws SQLException SQL exception
     */
    private static PreparedStatement setProgrammeSeasonMethod(PreparedStatement pstm, ProgrammeSeasons programmeSeason) throws SQLException {
        pstm.setString(1, programmeSeason.getSeason());
        return pstm;
    }

    /**
     * Delete programme season
     *
     * @param con connection
     * @param id  id
     * @throws SQLException SQL exception
     */
    public static void deleteProgrammeSeason(Connection con, int id) throws SQLException {
        backupProgrammeSeason(con, id, "DELETE");
        String sql = "DELETE FROM programme_seasons WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    /**
     * Backup programme season
     *
     * @param con    connection
     * @param id     id
     * @param action action
     * @throws SQLException SQL exception
     */
    private static void backupProgrammeSeason(Connection con, int id, String action) throws SQLException {
        String sql = "INSERT INTO programme_seasons_backup (id, season, action) SELECT id, season, ? FROM programme_seasons WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, action);
        pstm.setInt(2, id);
        pstm.executeUpdate();
    }

    /**
     * Returns teams
     *
     * @param con connection
     * @return List<Teams> list
     * @throws SQLException SQL exception
     */
    public static List<Teams> queryTeams(Connection con) throws SQLException {
        String sql = "SELECT * FROM teams ORDER BY ages DESC";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        return getTeamsMethod(rs);
    }

    /**
     * Returns active teams
     *
     * @param con connection
     * @return List<Teams> list
     * @throws SQLException SQL exception
     */
    public static List<Teams> queryTeamsActive(Connection con) throws SQLException {
        String sql = "SELECT * FROM teams WHERE active = TRUE ORDER BY ages DESC";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        return getTeamsMethod(rs);
    }

    /**
     * Get teams method
     *
     * @param rs result set
     * @return List<Teams> list
     * @throws SQLException SQL exception
     */
    private static List<Teams> getTeamsMethod(ResultSet rs) throws SQLException {
        List<Teams> list = new ArrayList<>();
        while (rs.next()) {
            Teams team = getTeamMethod(rs);
            list.add(team);
        }
        return list;
    }

    /**
     * Returns team
     *
     * @param con connection
     * @param id  id
     * @return Teams team
     * @throws SQLException SQL exception
     */
    public static Teams findTeam(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM teams WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) return getTeamMethod(rs);
        else return null;
    }

    /**
     * Get team method
     *
     * @param rs result set
     * @return Teams team
     * @throws SQLException SQL exception
     */
    private static Teams getTeamMethod(ResultSet rs) throws SQLException {
        int id, ages;
        String name, league, division, leagueTable, fixtures, coach, physio, teamPhoto;
        boolean active, youth;
        id = rs.getInt("id");
        name = rs.getString("name");
        league = rs.getString("league");
        division = rs.getString("division");
        leagueTable = rs.getString("league_table");
        fixtures = rs.getString("fixtures");
        coach = rs.getString("coach");
        physio = rs.getString("physio");
        teamPhoto = rs.getString("team_photo");
        active = rs.getBoolean("active");
        youth = rs.getBoolean("youth");
        ages = rs.getInt("ages");
        return new Teams(id, name, league, division, leagueTable, fixtures, coach, physio, teamPhoto, active, youth, ages);
    }

    /**
     * Update team
     *
     * @param con  connection
     * @param team team
     * @throws SQLException SQL exception
     */
    public static void updateTeam(Connection con, Teams team) throws SQLException {
        backupTeam(con, team.getID(), "UPDATE");
        String sql = "UPDATE teams SET name = ?, league = ?, division = ?, league_table = ?, fixtures = ?, coach = ?, physio = ?, team_photo = ?, active = ?, youth = ?, ages = ? WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql), pstm1;
        pstm1 = setTeamMethod(pstm, team);
        pstm1.setInt(12, team.getID());
        pstm1.executeUpdate();
    }

    /**
     * Insert team
     *
     * @param con  connection
     * @param team team
     * @throws SQLException SQL exception
     */
    public static void insertTeam(Connection con, Teams team) throws SQLException {
        String sql = "INSERT INTO teams (name, league, division, league_table, fixtures, coach, physio, team_photo, active, youth, ages) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = con.prepareStatement(sql), pstm1;
        pstm1 = setTeamMethod(pstm, team);
        pstm1.executeUpdate();
    }

    /**
     * Set team method
     *
     * @param pstm prepared statement
     * @param team team
     * @return PreparedStatement pstm
     * @throws SQLException SQL exception
     */
    private static PreparedStatement setTeamMethod(PreparedStatement pstm, Teams team) throws SQLException {
        pstm.setString(1, team.getName());
        pstm.setString(2, team.getLeague());
        pstm.setString(3, team.getDivision());
        pstm.setString(4, team.getLeagueTable());
        pstm.setString(5, team.getFixtures());
        pstm.setString(6, team.getCoach());
        pstm.setString(7, team.getPhysio());
        pstm.setString(8, team.getTeamPhoto());
        pstm.setBoolean(9, team.getActive());
        pstm.setBoolean(10, team.getYouth());
        pstm.setInt(11, team.getAges());
        return pstm;
    }

    /**
     * Delete team
     *
     * @param con connection
     * @param id  id
     * @throws SQLException SQL exception
     */
    public static void deleteTeam(Connection con, int id) throws SQLException {
        backupTeam(con, id, "DELETE");
        String sql = "DELETE FROM teams WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    /**
     * Backup team
     *
     * @param con    connection
     * @param id     id
     * @param action action
     * @throws SQLException SQL exception
     */
    private static void backupTeam(Connection con, int id, String action) throws SQLException {
        String sql = "INSERT INTO teams_backup (id, name, league, division, league_table, fixtures, coach, physio, team_photo, active, youth, ages, action) SELECT id, name, league, division, league_table, fixtures, coach, physio, team_photo, active, youth, ages, ? FROM teams WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, action);
        pstm.setInt(2, id);
        pstm.executeUpdate();
    }

    /**
     * Returns sponsors
     *
     * @param con connection
     * @return List<Sponsors> list
     * @throws SQLException SQL exception
     */
    public static List<Sponsors> querySponsors(Connection con) throws SQLException {
        String sql = "SELECT * FROM sponsors ORDER BY team_id, name";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        return getSponsorsMethod(rs);
    }

    /**
     * Returns sponsors for team
     *
     * @param con    connection
     * @param teamID team id
     * @param youth  youth
     * @return List<Sponsors> list
     * @throws SQLException SQL exception
     */
    public static List<Sponsors> querySponsorsTeam(Connection con, String teamID, boolean youth) throws SQLException {
        String sql = "SELECT * FROM sponsors WHERE team_id = ? OR team_id = ? OR team_id = 'A'";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, teamID);
        if (youth) pstm.setString(2, "U");
        else pstm.setString(2, "O");
        ResultSet rs = pstm.executeQuery();
        return getSponsorsMethod(rs);
    }

    /**
     * Get sponsors method
     *
     * @param rs result set
     * @return List<Sponsors> list
     * @throws SQLException SQL exception
     */
    private static List<Sponsors> getSponsorsMethod(ResultSet rs) throws SQLException {
        List<Sponsors> list = new ArrayList<>();
        while (rs.next()) {
            Sponsors sponsor = getSponsorMethod(rs);
            list.add(sponsor);
        }
        return list;
    }

    /**
     * Returns sponsor
     *
     * @param con connection
     * @param id  id
     * @return Sponsors sponsor
     * @throws SQLException SQL exception
     */
    public static Sponsors findSponsor(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM sponsors WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) return getSponsorMethod(rs);
        else return null;
    }

    /**
     * Get sponsor method
     *
     * @param rs result set
     * @return Sponsors sponsor
     * @throws SQLException SQL exception
     */
    private static Sponsors getSponsorMethod(ResultSet rs) throws SQLException {
        int id;
        String name, website, image, purpose, teamID;
        id = rs.getInt("id");
        name = rs.getString("name");
        website = rs.getString("website");
        image = rs.getString("image");
        purpose = rs.getString("purpose");
        teamID = rs.getString("team_id");
        return new Sponsors(id, name, website, image, purpose, teamID);
    }

    /**
     * Update sponsor
     *
     * @param con     connection
     * @param sponsor sponsor
     * @throws SQLException SQL exception
     */
    public static void updateSponsor(Connection con, Sponsors sponsor) throws SQLException {
        backupSponsor(con, sponsor.getID(), "UPDATE");
        String sql = "UPDATE sponsors SET name = ?, website = ?, image = ?, purpose = ?, team_id = ? WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql), pstm1;
        pstm1 = setSponsorMethod(pstm, sponsor);
        pstm1.setInt(6, sponsor.getID());
        pstm1.executeUpdate();
    }

    /**
     * Insert sponsor
     *
     * @param con     connection
     * @param sponsor sponsor
     * @throws SQLException SQL exception
     */
    public static void insertSponsor(Connection con, Sponsors sponsor) throws SQLException {
        String sql = "INSERT INTO sponsors (name, website, image, purpose, team_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstm = con.prepareStatement(sql), pstm1;
        pstm1 = setSponsorMethod(pstm, sponsor);
        pstm1.executeUpdate();
    }

    /**
     * Set sponsor method
     *
     * @param pstm    prepared statement
     * @param sponsor sponsor
     * @return PreparedStatement pstm
     * @throws SQLException SQL exception
     */
    private static PreparedStatement setSponsorMethod(PreparedStatement pstm, Sponsors sponsor) throws SQLException {
        pstm.setString(1, sponsor.getName());
        pstm.setString(2, sponsor.getWebsite());
        pstm.setString(3, sponsor.getImage());
        pstm.setString(4, sponsor.getPurpose());
        pstm.setString(5, sponsor.getTeamID());
        return pstm;
    }

    /**
     * Delete sponsor
     *
     * @param con connection
     * @param id  id
     * @throws SQLException SQL exception
     */
    public static void deleteSponsor(Connection con, int id) throws SQLException {
        backupSponsor(con, id, "DELETE");
        String sql = "DELETE FROM sponsors WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    /**
     * Backup sponsor
     *
     * @param con    connection
     * @param id     id
     * @param action action
     * @throws SQLException SQL exception
     */
    private static void backupSponsor(Connection con, int id, String action) throws SQLException {
        String sql = "INSERT INTO sponsors_backup (id, name, website, image, purpose, team_id, action) SELECT id, name, website, image, purpose, team_id, ? FROM sponsors WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, action);
        pstm.setInt(2, id);
        pstm.executeUpdate();
    }

    /**
     * Returns users
     *
     * @param con connection
     * @return List<Users> list
     * @throws SQLException throws SQLException
     */
    public static List<Users> queryUsers(Connection con) throws SQLException {
        String sql = "SELECT * FROM users ORDER BY role";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        return getUsersMethod(rs);
    }

    /**
     * Returns users for contact
     *
     * @param con connection
     * @return List<Users> list
     * @throws SQLException throws SQLException
     */
    public static List<Users> queryUsersContacts(Connection con) throws SQLException {
        String sql = "SELECT * FROM users WHERE role IN ('PROGRAMME_EDITOR', 'LEAGUE_SECRETARY', 'TREASURER', 'SAFEGUARDING_OFFICER', 'CLUB_SECRETARY', 'CHAIRPERSON') ORDER BY FIELD(role, 'PROGRAMME_EDITOR', 'LEAGUE_SECRETARY', 'TREASURER', 'SAFEGUARDING_OFFICER', 'CLUB_SECRETARY', 'CHAIRPERSON') DESC";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        return getUsersMethod(rs);
    }

    /**
     * Returns users who are managers for team
     *
     * @param con    connection
     * @param teamID team id
     * @return List<Users> list
     * @throws SQLException SQL exception
     */
    public static List<Users> queryUsersManagersTeam(Connection con, int teamID) throws SQLException {
        String sql = "SELECT * FROM users WHERE role = 'MANAGER' AND team_id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, teamID);
        ResultSet rs = pstm.executeQuery();
        return getUsersMethod(rs);
    }

    /**
     * Get users method
     *
     * @param rs result set
     * @return List<Users> list
     * @throws SQLException SQL exception
     */
    private static List<Users> getUsersMethod(ResultSet rs) throws SQLException {
        List<Users> list = new ArrayList<>();
        while (rs.next()) {
            Users user = getUserMethod(rs);
            list.add(user);
        }
        return list;
    }

    /**
     * Returns user
     *
     * @param con   connection
     * @param email email
     * @return Users user
     * @throws SQLException throws SQLException
     */
    public static Users findUser(Connection con, String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, email);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) return getUserMethod(rs);
        else return null;
    }

    /**
     * Returns user
     *
     * @param con      connection
     * @param email    email
     * @param password password
     * @param request  request
     * @return Users user
     * @throws Exception throws SQLException
     */
    public static Users login(Connection con, String email, String password, HttpServletRequest request) throws Exception {
        String sql = "SELECT * FROM users WHERE email = ? AND role != 'MANAGER'";
        String sql1 = "UPDATE users SET temp = SHA2(?, 512) WHERE email = ? AND role != 'MANAGER'", sql2 = "SELECT * FROM users WHERE email = ? AND role != 'MANAGER'", sql3 = "UPDATE users SET temp = NULL WHERE email = ? AND role != 'MANAGER'";
        PreparedStatement pstm = con.prepareStatement(sql), pstm1 = con.prepareStatement(sql1), pstm2 = con.prepareStatement(sql2), pstm3 = con.prepareStatement(sql3);
        if (email == null) return null;
        pstm.setString(1, email);
        ResultSet rs = pstm.executeQuery();
        rs.next();
        if (rs.getString("password") == null) {
            byte[] salt = rs.getBytes("salt");
            byte[] hash = MyUtils.hash(password.toCharArray(), salt);
            if (MyUtils.verifyPassword(password.toCharArray(), salt, rs.getBytes("hash")))
                return getUserMethod(rs);
            else
                return null;
        } else {
            pstm1.setString(1, password);
            pstm1.setString(2, email);
            try {
                pstm1.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            pstm2.setString(1, email);
            pstm3.setString(1, email);
            ResultSet rs1 = pstm2.executeQuery();
            rs1.next();
            String password1 = null, temp = null;
            try {
                password1 = rs1.getString("password");
                temp = rs1.getString("temp");
            } catch (Exception e) {
                e.printStackTrace();
            }
            assert password1 != null;
            if (password1.equals(temp)) {
                pstm3.execute();
                request.getSession().setAttribute("UPDATE PASSWORD", true);
                request.getSession().setAttribute("UPDATE PASSWORD NOTIFICATION", "We are updating our password encryption algorithms. For security reasons, we ask that you change your password.");
                return getUserMethod(rs1);
            }
            return null;
        }
    }

    /**
     * Get user method
     *
     * @param rs result set
     * @return Users user
     * @throws SQLException SQL exception
     */
    private static Users getUserMethod(ResultSet rs) throws SQLException {
        String name, email, phone, image;
        int teamID;
        Role role;
        name = rs.getString("name");
        email = rs.getString("email");
        phone = rs.getString("phone");
        teamID = rs.getInt("team_id");
        role = Role.valueOf(rs.getString("role"));
        image = rs.getString("image");
        return new Users(name, email, phone, teamID, role, image);
    }

    /**
     * Change password
     *
     * @param con   connection
     * @param email email
     * @param hash  hash
     * @param salt  salt
     * @throws SQLException throws SQLException
     */
    public static void changePassword(Connection con, String email, byte[] hash, byte[] salt) throws SQLException {
        backupUser(con, email, "CHANGE_PASSWORD");
        String sql = "UPDATE users SET password = NULL, temp = NULL, hash = ?, salt = ? WHERE email = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setBytes(1, hash);
        pstm.setBytes(2, salt);
        pstm.setString(3, email);
        pstm.executeUpdate();
    }

    /**
     * Check password
     *
     * @param con      connection
     * @param email    email
     * @param password password
     * @return boolean (password correct)
     * @throws SQLException throws SQLException
     */
    public static boolean checkPassword(Connection con, String email, String password) throws SQLException {
        if (email == null) return false;
        String sql = "SELECT * FROM users WHERE email = ?";
        String sql1 = "UPDATE users SET temp = SHA2(?, 512) WHERE email = ?", sql2 = "SELECT * FROM users WHERE email = ?", sql3 = "UPDATE users SET temp = NULL WHERE email = ?";
        PreparedStatement pstm = con.prepareStatement(sql), pstm1 = con.prepareStatement(sql1), pstm2 = con.prepareStatement(sql2), pstm3 = con.prepareStatement(sql3);
        pstm.setString(1, email);
        ResultSet rs = pstm.executeQuery();
        rs.next();
        if (rs.getString("password") == null) {
            byte[] hash = rs.getBytes("hash"), salt = rs.getBytes("salt");
            return MyUtils.verifyPassword(password.toCharArray(), salt, hash);
        } else {
            pstm1.setString(1, password);
            pstm1.setString(2, email);
            pstm1.execute();
            pstm2.setString(1, email);
            pstm3.setString(1, email);
            ResultSet rs1 = pstm2.executeQuery();
            rs1.next();
            if (rs1.getString("password").equals(rs1.getString("temp"))) {
                pstm3.execute();
                return true;
            }
        }
        return false;
    }

    /**
     * Reset password
     *
     * @param con   connection
     * @param email email
     * @throws SQLException throws SQLException
     */
    public static void resetPassword(Connection con, String email) throws SQLException {
        backupUser(con, email, "RESET_PASSWORD");
        byte[] salt = MyUtils.getNextSalt(), hash = MyUtils.hash("AFCPa£$word".toCharArray(), salt);
        String sql = "UPDATE users SET password = null, hash = ?, salt = ?, temp = null WHERE email = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setBytes(1, hash);
        pstm.setBytes(2, salt);
        pstm.setString(3, email);
        pstm.executeUpdate();
    }

    /**
     * Update user
     *
     * @param con      connection
     * @param user     user
     * @param emailOld email old
     * @throws SQLException throws SQLException
     */
    public static void updateUser(Connection con, Users user, String emailOld) throws SQLException {
        backupUser(con, emailOld, "UPDATE");
        String sql = "UPDATE users SET name = ?, email = ?, phone = ?, team_id = ?, role = ?, image = ? WHERE email = ?";
        PreparedStatement pstm = con.prepareStatement(sql), pstm1;
        pstm1 = setUserMethod(pstm, user);
        pstm1.setString(7, emailOld);
        pstm1.executeUpdate();
    }

    /**
     * Insert user
     *
     * @param con  connection
     * @param user user
     * @param hash hash
     * @param salt salt
     * @throws SQLException throws SQLException
     */
    public static void insertUser(Connection con, Users user, byte[] hash, byte[] salt) throws SQLException {
        String sql = "INSERT INTO users (name, email, phone, team_id, role, image, hash, salt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = con.prepareStatement(sql), pstm1;
        pstm1 = setUserMethod(pstm, user);
        pstm1.setBytes(7, hash);
        pstm1.setBytes(8, salt);
        pstm1.executeUpdate();
    }

    /**
     * Set user method
     *
     * @param pstm prepared statement
     * @param user user
     * @return PreparedStatement pstm
     * @throws SQLException SQL exception
     */
    private static PreparedStatement setUserMethod(PreparedStatement pstm, Users user) throws SQLException {
        pstm.setString(1, user.getName());
        pstm.setString(2, user.getEmail());
        pstm.setString(3, user.getPhone());
        pstm.setInt(4, user.getTeam());
        pstm.setString(5, user.getRole().getRole());
        pstm.setString(6, user.getImage());
        return pstm;
    }

    /**
     * Delete user
     *
     * @param con   connection
     * @param email email
     * @throws SQLException throws SQLException
     */
    public static void deleteUser(Connection con, String email) throws SQLException {
        backupUser(con, email, "DELETE");
        String sql = "DELETE FROM users WHERE email = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, email);
        pstm.executeUpdate();
    }

    /**
     * Backup user
     *
     * @param con    connection
     * @param email  email
     * @param action action
     * @throws SQLException SQL exception
     */
    private static void backupUser(Connection con, String email, String action) throws SQLException {
        String sql = "INSERT INTO users_backup (name, email, phone, team_id, role, image, password, hash, salt, temp, action) SELECT name, email, phone, team_id, role, image, password, hash, salt, temp, ? FROM users WHERE email = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, action);
        pstm.setString(2, email);
        pstm.executeUpdate();
    }

    /**
     * Returns all what's on
     *
     * @param con connection
     * @return List<WhatsOn> list
     * @throws SQLException SQL exception
     */
    public static List<WhatsOn> queryWhatsOn(Connection con) throws SQLException {
        String sql = "SELECT * FROM whatson";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        return getWhatsOnMethodMultiple(rs);
    }

    /**
     * Returns all what's on by event date
     *
     * @param con connection
     * @return List<WhatsOn> list
     * @throws SQLException SQL exception
     */
    public static List<WhatsOn> queryWhatsOnEventDate(Connection con) throws SQLException {
        String sql = "SELECT * FROM whatson WHERE date_of_event >= ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        long epoch = MyUtils.getEpoch();
        pstm.setLong(1, epoch);
        ResultSet rs = pstm.executeQuery();
        return getWhatsOnMethodMultiple(rs);
    }

    /**
     * Get what's on method multiple
     *
     * @param rs result set
     * @return List<WhatsOn> list
     * @throws SQLException SQL exception
     */
    private static List<WhatsOn> getWhatsOnMethodMultiple(ResultSet rs) throws SQLException {
        List<WhatsOn> list = new ArrayList<>();
        while (rs.next()) {
            WhatsOn whatsOn = getWhatsOnMethodSingle(rs);
            list.add(whatsOn);
        }
        return list;
    }

    /**
     * Returns one what's on article
     *
     * @param con connection
     * @param id  id
     * @return WhatsOn whatsOn
     * @throws SQLException SQL exception
     */
    public static WhatsOn findWhatsOn(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM whatson WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) return getWhatsOnMethodSingle(rs);
        else return null;
    }

    /**
     * Returns latest what's on article
     *
     * @param con connection
     * @return WhatsOn whatsOn
     * @throws SQLException SQL exception
     */
    public static WhatsOn findWhatsOnLatest(Connection con) throws SQLException {
        String sql = "SELECT * FROM whatson WHERE date_of_event >= ? ORDER BY date_of_event";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setLong(1, MyUtils.getEpoch());
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) return getWhatsOnMethodSingle(rs);
        else return null;
    }

    /**
     * Get what's on method single
     *
     * @param rs result set
     * @return WhatsOn whatsOn
     * @throws SQLException SQL exception
     */
    private static WhatsOn getWhatsOnMethodSingle(ResultSet rs) throws SQLException {
        int id;
        String title, image, content;
        long date, dateOfEvent;
        id = rs.getInt("id");
        title = rs.getString("title");
        image = rs.getString("image");
        content = rs.getString("content");
        date = rs.getLong("date");
        dateOfEvent = rs.getLong("date_of_event");
        return new WhatsOn(id, title, image, content, date, dateOfEvent);
    }

    /**
     * Update what's on
     *
     * @param con     connection
     * @param whatsOn what's on
     * @throws SQLException SQL exception
     */
    public static void updateWhatsOn(Connection con, WhatsOn whatsOn) throws SQLException {
        backupWhatsOn(con, whatsOn.getID(), "UPDATE");
        String sql = "UPDATE whatson SET title = ?, image = ?, content = ?, date = ?, date_of_event = ? WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql), pstm1;
        pstm1 = setWhatsOnMethod(pstm, whatsOn);
        pstm1.setInt(6, whatsOn.getID());
        pstm1.executeUpdate();
    }

    /**
     * Insert what's on
     *
     * @param con     connection
     * @param whatsOn what's on
     * @throws SQLException SQL exception
     */
    public static void insertWhatsOn(Connection con, WhatsOn whatsOn) throws SQLException {
        String sql = "INSERT INTO whatson (title, image, content, date, date_of_event) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstm = con.prepareStatement(sql), pstm1;
        pstm1 = setWhatsOnMethod(pstm, whatsOn);
        pstm1.executeUpdate();
    }

    /**
     * Set what's on method
     *
     * @param pstm    prepared statement
     * @param whatsOn what's on
     * @return PreparedStatement pstm
     * @throws SQLException SQL exception
     */
    private static PreparedStatement setWhatsOnMethod(PreparedStatement pstm, WhatsOn whatsOn) throws SQLException {
        pstm.setString(1, whatsOn.getTitle());
        pstm.setString(2, whatsOn.getImage());
        pstm.setString(3, whatsOn.getContent());
        pstm.setLong(4, whatsOn.getDate());
        pstm.setLong(5, whatsOn.getDateOfEvent());
        return pstm;
    }

    /**
     * Delete what's on
     *
     * @param con connection
     * @param id  id
     * @throws SQLException SQL exception
     */
    public static void deleteWhatsOn(Connection con, int id) throws SQLException {
        backupWhatsOn(con, id, "DELETE");
        String sql = "DELETE FROM whatson WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    /**
     * Backup what's on
     *
     * @param con    connection
     * @param id     id
     * @param action action
     * @throws SQLException SQL exception
     */
    private static void backupWhatsOn(Connection con, int id, String action) throws SQLException {
        String sql = "INSERT INTO whatson_backup (id, title, image, content, date, date_of_event, action) SELECT id, title, image, content, date, date_of_event, ? FROM whatson WHERE id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, action);
        pstm.setInt(2, id);
        pstm.executeUpdate();
    }
}

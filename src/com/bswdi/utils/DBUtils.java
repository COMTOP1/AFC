package com.bswdi.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bswdi.beans.*;

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
        String sql = "SELECT * FROM AFFILIATIONS";
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
     * @param id id
     * @return Affiliations affiliation
     * @throws SQLException SQL exception
     */
    public static Affiliations findAffiliation(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM AFFILIATIONS WHERE ID = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) return getAffiliationMethod(rs);
        else return null;
    }

    /**
     * Get affiliation method
     * @param rs result set
     * @return Affiliations affiliation
     * @throws SQLException SQL exception
     */
    private static Affiliations getAffiliationMethod(ResultSet rs) throws SQLException {
        int id;
        String name, website, image;
        id = rs.getInt("ID");
        name = rs.getString("NAME");
        website = rs.getString("WEBSITE");
        image = rs.getString("IMAGE");
        return new Affiliations(id, name, website, image);
    }

    /**
     * Update affiliation
     *
     * @param con connection
     * @param affiliation affiliation
     * @throws SQLException SQL exception
     */
    public static void updateAffiliation(Connection con, Affiliations affiliation) throws SQLException {
        backupAffiliation(con, affiliation.getID(), "UPDATE");
        String sql = "UPDATE AFFILIATIONS SET NAME = ?, WEBSITE = ?, IMAGE = ? WHERE ID = ?";
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
     * @param con connection
     * @param affiliation affiliation
     * @throws SQLException SQL exception
     */
    public static void insertAffiliation(Connection con, Affiliations affiliation) throws SQLException {
        String sql = "INSERT INTO AFFILIATIONS (NAME, WEBSITE, IMAGE) VALUES (?, ?, ?)";
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
     * @param id id
     * @throws SQLException SQL exception
     */
    public static void deleteAffiliation(Connection con, int id) throws SQLException {
        backupAffiliation(con, id, "DELETE");
        String sql = "DELETE FROM AFFILIATIONS WHERE ID = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    /**
     * Backup affiliation
     *
     * @param con connection
     * @param id id
     * @param action action
     * @throws SQLException SQL exception
     */
    private static void backupAffiliation(Connection con, int id, String action) throws SQLException {
        String sql = "INSERT INTO AFFILIATIONS_BACKUP (ID, NAME, WEBSITE, IMAGE, ACTION) SELECT ID, NAME, WEBSITE, IMAGE, ? FROM AFFILIATIONS WHERE ID = ?";
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
        String sql = "SELECT * FROM DOCUMENTS";
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
     * @param id id
     * @return Documents document
     * @throws SQLException SQL exception
     */
    public static Documents findDocument(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM DOCUMENTS WHERE ID = ?";
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
        id = rs.getInt("ID");
        name = rs.getString("NAME");
        fileName = rs.getString("FILE_NAME");
        return new Documents(id, name, fileName);
    }

    /**
     * Update document
     *
     * @param con connection
     * @param document document
     * @throws SQLException SQL exception
     */
    public static void updateDocument(Connection con, Documents document) throws SQLException {
        backupDocument(con, document.getID(), "UPDATE");
        String sql = "UPDATE DOCUMENTS SET NAME = ?, FILE_NAME = ? WHERE ID = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, document.getName());
        pstm.setString(2, document.getFileName());
        pstm.setInt(3, document.getID());
        pstm.executeUpdate();
    }

    /**
     * Insert document
     *
     * @param con connection
     * @param document document
     * @throws SQLException SQL exception
     */
    public static void insertDocument(Connection con, Documents document) throws SQLException {
        String sql = "INSERT INTO DOCUMENTS (NAME, FILE_NAME) VALUES (?, ?)";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, document.getName());
        pstm.setString(2, document.getFileName());
        pstm.executeUpdate();
    }

    /**
     * Delete document
     *
     * @param con connection
     * @param id id
     * @throws SQLException SQL exception
     */
    public static void deleteDocument(Connection con, int id) throws SQLException {
        backupDocument(con, id, "DELETE");
        String sql = "DELETE FROM DOCUMENTS WHERE ID = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    /**
     * Backup document
     *
     * @param con connection
     * @param id id
     * @param action action
     * @throws SQLException SQL exception
     */
    private static void backupDocument(Connection con, int id, String action) throws SQLException {
        String sql = "INSERT INTO DOCUMENTS_BACKUP (ID, NAME, FILE_NAME, ACTION) SELECT ID, NAME, FILE_NAME, ? FROM DOCUMENTS WHERE ID = ?";
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
        String sql = "SELECT * FROM IMAGES";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        List<Images> list = new ArrayList<>();
        while (rs.next()) {
            int id;
            String image1, caption;
            id = rs.getInt("ID");
            image1 = rs.getString("IMAGE");
            caption = rs.getString("CAPTION");
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
        String sql = "SELECT * FROM IMAGES WHERE ID = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            String image1, caption;
            image1 = rs.getString("IMAGE");
            caption = rs.getString("CAPTION");
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
        String sql = "UPDATE IMAGES SET IMAGE = ?, CAPTION = ? WHERE ID = ?";
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
        String sql = "INSERT INTO IMAGES (IMAGE, CAPTION) VALUES (?, ?)";
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
        String sql = "DELETE FROM IMAGES WHERE ID = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    /**
     * Backup image
     *
     * @param con connection
     * @param id  id
     * @param action action
     * @throws SQLException SQL exception
     */
    private static void backupImage(Connection con, int id, String action) throws SQLException {
        String sql = "INSERT INTO IMAGES_BACKUP (ID, IMAGE, CAPTION, ACTION) SELECT ID, IMAGE, CAPTION, ? FROM IMAGES WHERE ID = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, action);
        pstm.setInt(2, id);
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
        String sql = "SELECT * FROM NEWS ORDER BY DATE DESC";
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
        String sql = "SELECT * FROM NEWS WHERE ID = ?";
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
        String sql = "SELECT * FROM NEWS ORDER BY ID DESC";
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
        id = rs.getInt("ID");
        title = rs.getString("TITLE");
        image = rs.getString("IMAGE");
        content = rs.getString("CONTENT");
        date = rs.getLong("DATE");
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
        String sql = "UPDATE NEWS SET TITLE = ?, IMAGE = ?, CONTENT = ?, DATE = ? WHERE ID = ?";
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
        String sql = "INSERT INTO NEWS (TITLE, IMAGE, CONTENT, DATE) VALUES (?, ?, ?, ?)";
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
        String sql = "DELETE FROM NEWS WHERE ID = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    /**
     * Backup news
     *
     * @param con connection
     * @param id  id
     * @param action action
     * @throws SQLException SQL exception
     */
    private static void backupNews(Connection con, int id, String action) throws SQLException {
        String sql = "INSERT INTO NEWS_BACKUP (ID, TITLE, IMAGE, CONTENT, DATE, ACTION) SELECT ID, TITLE, IMAGE, CONTENT, DATE, ? FROM NEWS WHERE ID = ?";
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
        String sql = "SELECT * FROM PLAYERS";
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
        String sql = "SELECT * FROM PLAYERS WHERE TEAM_ID = ?";
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
        String sql = "SELECT * FROM PLAYERS WHERE ID = ?";
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
        id = rs.getInt("ID");
        name = rs.getString("NAME");
        image = rs.getString("IMAGE");
        dateOfBirth = Long.parseLong(rs.getString("DATE_OF_BIRTH"));
        position = rs.getString("POSITION");
        captain = rs.getBoolean("CAPTAIN");
        teamID = rs.getInt("TEAM_ID");
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
        String sql = "UPDATE PLAYERS SET NAME = ?, IMAGE = ?, DATE_OF_BIRTH = ?, POSITION = ?, CAPTAIN = ?, TEAM_ID = ? WHERE ID = ?";
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
        String sql = "INSERT INTO PLAYERS (NAME, IMAGE, DATE_OF_BIRTH, POSITION, CAPTAIN, TEAM_ID) VALUES (?, ?, ?, ?, ?, ?)";
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
        String sql = "DELETE FROM PLAYERS WHERE ID = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    /**
     * Backup player
     *
     * @param con connection
     * @param id  id
     * @param action action
     * @throws SQLException SQL exception
     */
    private static void backupPlayer(Connection con, int id, String action) throws SQLException {
        String sql = "INSERT INTO PLAYERS_BACKUP (ID, NAME, IMAGE, DATE_OF_BIRTH, POSITION, CAPTAIN, TEAM_ID, ACTION) SELECT ID, NAME, IMAGE, DATE_OF_BIRTH, POSITION, CAPTAIN, TEAM_ID, ? FROM PLAYERS WHERE ID = ?";
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
        String sql = "SELECT * FROM PROGRAMMES";
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
     * @param id id
     * @return Programme programme
     * @throws SQLException SQL exception
     */
    public static Programmes findProgramme(Connection con, int id) throws SQLException {
        String sql = "SELECT * FROM PROGRAMMES WHERE ID = ?";
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
        int id;
        String name, fileName;
        long dateOfProgramme;
        id = rs.getInt("ID");
        name = rs.getString("NAME");
        fileName = rs.getString("FILE_NAME");
        dateOfProgramme = rs.getLong("DATE_OF_PROGRAMME");
        return new Programmes(id, name, fileName, dateOfProgramme);
    }

    /**
     * Insert programme
     *
     * @param con connection
     * @param programme programme
     * @throws SQLException SQL exception
     */
    public static void insertProgramme(Connection con, Programmes programme) throws SQLException {
        String sql = "INSERT INTO PROGRAMMES (NAME, FILE_NAME, DATE_OF_PROGRAMME) VALUES (?, ?, ?)";
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
        String sql = "DELETE FROM PROGRAMMES WHERE ID = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    /**
     * Backup programme
     *
     * @param con connection
     * @param id id
     * @throws SQLException SQL exception
     */
    private static void backupProgramme(Connection con, int id) throws SQLException {
        String sql = "INSERT INTO PROGRAMMES_BACKUP (ID, NAME, FILE_NAME, DATE_OF_PROGRAMME, ACTION) SELECT ID, NAME, FILE_NAME, DATE_OF_PROGRAMME, 'DELETE' FROM PROGRAMMES WHERE ID = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
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
        String sql = "SELECT * FROM TEAMS ORDER BY AGES DESC";
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
        String sql = "SELECT * FROM TEAMS WHERE ACTIVE = TRUE ORDER BY AGES DESC";
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
        String sql = "SELECT * FROM TEAMS WHERE ID = ?";
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
        id = rs.getInt("ID");
        name = rs.getString("NAME");
        league = rs.getString("LEAGUE");
        division = rs.getString("DIVISION");
        leagueTable = rs.getString("LEAGUE_TABLE");
        fixtures = rs.getString("FIXTURES");
        coach = rs.getString("COACH");
        physio = rs.getString("PHYSIO");
        teamPhoto = rs.getString("TEAM_PHOTO");
        active = rs.getBoolean("ACTIVE");
        youth = rs.getBoolean("YOUTH");
        ages = rs.getInt("AGES");
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
        String sql = "UPDATE TEAMS SET NAME = ?, LEAGUE = ?, DIVISION = ?, LEAGUE_TABLE = ?, FIXTURES = ?, COACH = ?, PHYSIO = ?, TEAM_PHOTO = ?, ACTIVE = ?, YOUTH = ?, AGES = ? WHERE ID = ?";
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
        String sql = "INSERT INTO TEAMS (NAME, LEAGUE, DIVISION, LEAGUE_TABLE, FIXTURES, COACH, PHYSIO, TEAM_PHOTO, ACTIVE, YOUTH, AGES) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
        String sql = "DELETE FROM TEAMS WHERE ID = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    /**
     * Backup team
     *
     * @param con connection
     * @param id  id
     * @param action action
     * @throws SQLException SQL exception
     */
    private static void backupTeam(Connection con, int id, String action) throws SQLException {
        String sql = "INSERT INTO TEAMS_BACKUP (ID, NAME, LEAGUE, DIVISION, LEAGUE_TABLE, FIXTURES, COACH, PHYSIO, TEAM_PHOTO, ACTIVE, YOUTH, AGES, ACTION) SELECT ID, NAME, LEAGUE, DIVISION, LEAGUE_TABLE, FIXTURES, COACH, PHYSIO, TEAM_PHOTO, ACTIVE, YOUTH, AGES, ? FROM TEAMS WHERE ID = ?";
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
        String sql = "SELECT * FROM SPONSORS ORDER BY TEAM_ID, NAME";
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
        String sql = "SELECT * FROM SPONSORS WHERE TEAM_ID = ? OR TEAM_ID = ? OR TEAM_ID = 'A'";
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
        String sql = "SELECT * FROM SPONSORS WHERE ID = ?";
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
        id = rs.getInt("ID");
        name = rs.getString("NAME");
        website = rs.getString("WEBSITE");
        image = rs.getString("IMAGE");
        purpose = rs.getString("PURPOSE");
        teamID = rs.getString("TEAM_ID");
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
        String sql = "UPDATE SPONSORS SET NAME = ?, WEBSITE = ?, IMAGE = ?, PURPOSE = ?, TEAM_ID = ? WHERE ID = ?";
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
        String sql = "INSERT INTO SPONSORS (NAME, WEBSITE, IMAGE, PURPOSE, TEAM_ID) VALUES (?, ?, ?, ?, ?)";
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
        String sql = "DELETE FROM SPONSORS WHERE ID = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    /**
     * Backup sponsor
     *
     * @param con connection
     * @param id  id
     * @param action action
     * @throws SQLException SQL exception
     */
    private static void backupSponsor(Connection con, int id, String action) throws SQLException {
        String sql = "INSERT INTO SPONSORS_BACKUP (ID, NAME, WEBSITE, IMAGE, PURPOSE, TEAM_ID, ACTION) SELECT ID, NAME, WEBSITE, IMAGE, PURPOSE, TEAM_ID, ? FROM SPONSORS WHERE ID = ?";
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
        String sql = "SELECT * FROM USERS ORDER BY ROLE";
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
        String sql = "SELECT * FROM USERS WHERE ROLE IN ('PROGRAMME_EDITOR', 'LEAGUE_SECRETARY', 'TREASURER', 'SAFEGUARDING_OFFICER', 'CLUB_SECRETARY', 'CHAIRPERSON') ORDER BY FIELD(ROLE, 'PROGRAMME_EDITOR', 'LEAGUE_SECRETARY', 'TREASURER', 'SAFEGUARDING_OFFICER', 'CLUB_SECRETARY', 'CHAIRPERSON') DESC";
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
        String sql = "SELECT * FROM USERS WHERE ROLE = 0 AND TEAM_ID = ?";
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
        String sql = "SELECT * FROM USERS WHERE EMAIL = ?";
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
     * @return Users user
     * @throws Exception throws SQLException
     */
    public static Users login(Connection con, String email, String password) throws Exception {
        String sql1 = "UPDATE USERS SET TEMP = SHA2(?, 512) WHERE EMAIL = ? AND ROLE != 'MANAGER'", sql2 = "SELECT * FROM USERS WHERE EMAIL = ? AND ROLE != 'MANAGER'", sql3 = "UPDATE USERS SET TEMP = NULL WHERE EMAIL = ? AND ROLE != 'MANAGER'";
        PreparedStatement pstm1 = con.prepareStatement(sql1), pstm2 = con.prepareStatement(sql2), pstm3 = con.prepareStatement(sql3);
        if (email == null) return null;
        pstm1.setString(1, password);
        pstm1.setString(2, email);
        try {
        	pstm1.executeUpdate();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        pstm2.setString(1, email);
        pstm3.setString(1, email);
        ResultSet rs = pstm2.executeQuery();
        rs.next();
        String password1 = null, temp = null;
        try {
        	password1 = rs.getString("PASSWORD");
        	temp = rs.getString("TEMP");
        } catch (Exception e) {
        	e.printStackTrace();
        }
        assert password1 != null;
        if (password1.equals(temp)) {
            pstm3.execute();
            String name, phone, image;
            int teamID;
            Role role;
            name = rs.getString("NAME");
            phone = rs.getString("PHONE");
            teamID = rs.getInt("TEAM_ID");
            role = Role.valueOf(rs.getString("ROLE"));
            image = rs.getString("IMAGE");
            return new Users(name, email, phone, teamID, role, image);
        }
        return null;
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
        name = rs.getString("NAME");
        email = rs.getString("EMAIL");
        phone = rs.getString("PHONE");
        teamID = rs.getInt("TEAM_ID");
        role = Role.valueOf(rs.getString("ROLE"));
        image = rs.getString("IMAGE");
        return new Users(name, email, phone, teamID, role, image);
    }

    /**
     * Change password
     *
     * @param con      connection
     * @param email    email
     * @param password password
     * @throws SQLException throws SQLException
     */
    public static void changePassword(Connection con, String email, String password) throws SQLException {
        backupUser(con, email, "CHANGE_PASSWORD");
        String sql = "UPDATE USERS SET PASSWORD = SHA2('" + password + "', 512) WHERE EMAIL = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, email);
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
        String sql1 = "UPDATE USERS SET TEMP = SHA2('" + password + "', 512) WHERE EMAIL = ?", sql2 = "SELECT * FROM USERS WHERE EMAIL = ?", sql3 = "UPDATE USERS SET TEMP = NULL WHERE EMAIL = ?";
        PreparedStatement pstm1 = con.prepareStatement(sql1), pstm2 = con.prepareStatement(sql2), pstm3 = con.prepareStatement(sql3);
        if (email == null) return false;
        pstm1.setString(1, email);
        pstm1.execute();
        pstm2.setString(1, email);
        pstm3.setString(1, email);
        ResultSet rs = pstm2.executeQuery();
        rs.next();
        if (rs.getString("PASSWORD").equals(rs.getString("TEMP"))) {
            pstm3.execute();
            return true;
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
        String sql = "UPDATE USERS SET PASSWORD = SHA2('password', 512) WHERE EMAIL = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, email);
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
        String sql = "UPDATE USERS SET NAME = ?, EMAIL = ?, PHONE = ?, TEAM_ID = ?, ROLE = ?, IMAGE = ? WHERE EMAIL = ?";
        PreparedStatement pstm = con.prepareStatement(sql), pstm1;
        pstm1 = setUserMethod(pstm, user);
        pstm1.setString(7, emailOld);
        pstm1.executeUpdate();
    }

    /**
     * Insert user
     *
     * @param con      connection
     * @param user     user
     * @param password password
     * @throws SQLException throws SQLException
     */
    public static void insertUser(Connection con, Users user, String password) throws SQLException {
        String sql = "INSERT INTO USERS (NAME, EMAIL, PHONE, TEAM_ID, ROLE, IMAGE, PASSWORD) VALUES (?, ?, ?, ?, ?, ?, SHA2(?, 512))";
        PreparedStatement pstm = con.prepareStatement(sql), pstm1;
        pstm1 = setUserMethod(pstm, user);
        pstm1.setString(7, password);
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
        pstm.setString(5, user.getRole().toString());
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
        String sql = "DELETE FROM USERS WHERE EMAIL = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, email);
        pstm.executeUpdate();
    }

    /**
     * Backup user
     *
     * @param con   connection
     * @param email email
     * @param action action
     * @throws SQLException SQL exception
     */
    private static void backupUser(Connection con, String email, String action) throws SQLException {
        String sql = "INSERT INTO USERS_BACKUP (NAME, EMAIL, PHONE, TEAM_ID, ROLE, IMAGE, PASSWORD, TEMP, ACTION) SELECT NAME, EMAIL, PHONE, TEAM_ID, ROLE, IMAGE, PASSWORD, TEMP, ? FROM USERS WHERE EMAIL = ?";
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
        String sql = "SELECT * FROM WHATSON";
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
        String sql = "SELECT * FROM WHATSON WHERE DATE_OF_EVENT >= ?";
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
        String sql = "SELECT * FROM WHATSON WHERE ID = ?";
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
        String sql = "SELECT * FROM WHATSON WHERE DATE_OF_EVENT >= ? ORDER BY DATE_OF_EVENT";
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
        id = rs.getInt("ID");
        title = rs.getString("TITLE");
        image = rs.getString("IMAGE");
        content = rs.getString("CONTENT");
        date = rs.getLong("DATE");
        dateOfEvent = rs.getLong("DATE_OF_EVENT");
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
        String sql = "UPDATE WHATSON SET TITLE = ?, IMAGE = ?, CONTENT = ?, DATE = ?, DATE_OF_EVENT = ? WHERE ID = ?";
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
        String sql = "INSERT INTO WHATSON (TITLE, IMAGE, CONTENT, DATE, DATE_OF_EVENT) VALUES (?, ?, ?, ?, ?)";
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
        String sql = "DELETE FROM WHATSON WHERE ID = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }

    /**
     * Backup what's on
     *
     * @param con connection
     * @param id  id
     * @param action action
     * @throws SQLException SQL exception
     */
    private static void backupWhatsOn(Connection con, int id, String action) throws SQLException {
        String sql = "INSERT INTO WHATSON_BACKUP (ID, TITLE, IMAGE, CONTENT, DATE, DATE_OF_EVENT, ACTION) SELECT ID, TITLE, IMAGE, CONTENT, DATE, DATE_OF_EVENT, ? FROM WHATSON WHERE ID = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, action);
        pstm.setInt(2, id);
        pstm.executeUpdate();
    }
}

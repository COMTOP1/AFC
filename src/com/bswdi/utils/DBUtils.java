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
     * @throws SQLException SQLException
     */
    public static void SQLTimeZone(Connection con) throws SQLException {
        String sql1 = "SET GLOBAL time_zone = '+00:00';", sql2 = "SET @@global.time_zone = '+00:00';";
        PreparedStatement pstm1 = con.prepareStatement(sql1), pstm2 = con.prepareStatement(sql2);
        pstm1.executeQuery();
        pstm2.executeQuery();
    }

    /**
     * Returns images
     *
     * @param con connection
     * @return List<Images> list
     * @throws SQLException SQLException
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
     * Return image
     *
     * @param con connection
     * @param id  id
     * @return Images image
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
     */
    private static void backupImage(Connection con, int id, String action) throws SQLException {
        String sql = "INSERT INTO IMAGES_BACKUP (ID, IMAGE, CAPTION, ACTION) SELECT ID, IMAGE, CAPTION, ? FROM IMAGES WHERE ID = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, action);
        pstm.setInt(2, id);
        pstm.executeUpdate();
    }

    /**
     * Returns news
     *
     * @param con connection
     * @return List<News> list
     * @throws SQLException SQLException
     */
    public static List<News> queryNews(Connection con) throws SQLException {
        String sql = "SELECT * FROM NEWS";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        return getNewsMethodMultiple(rs);
    }

    /**
     * Get news method multiple
     *
     * @param rs result set
     * @return List<News> list
     * @throws SQLException SQLException
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
     * Return news
     *
     * @param con connection
     * @param id  id
     * @return Images image
     * @throws SQLException SQLException
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
     * Return news latest
     *
     * @param con connection
     * @return News news
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
     */
    public static List<Players> queryPlayers(Connection con) throws SQLException {
        String sql = "SELECT * FROM PLAYERS";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        return getPlayersMethod(rs);
    }

    /**
     * Return players by team
     *
     * @param con    connection
     * @param teamID team id
     * @return List<Players> list
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * Return player
     *
     * @param con connection
     * @param id  id
     * @return Players player
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
     */
    private static void backupPlayer(Connection con, int id, String action) throws SQLException {
        String sql = "INSERT INTO PLAYERS_BACKUP (ID, NAME, IMAGE, DATE_OF_BIRTH, POSITION, CAPTAIN, TEAM_ID, ACTION) SELECT ID, NAME, IMAGE, DATE_OF_BIRTH, POSITION, CAPTAIN, TEAM_ID, ? FROM PLAYERS WHERE ID = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, action);
        pstm.setInt(2, id);
        pstm.executeUpdate();
    }

    /**
     * Return teams
     *
     * @param con connection
     * @return List<Teams> list
     * @throws SQLException SQLException
     */
    public static List<Teams> queryTeams(Connection con) throws SQLException {
        String sql = "SELECT * FROM TEAMS";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        return getTeamsMethod(rs);
    }

    /**
     * Return active teams
     *
     * @param con connection
     * @return List<Teams> list
     * @throws SQLException SQLException
     */
    public static List<Teams> queryTeamsActive(Connection con) throws SQLException {
        String sql = "SELECT * FROM TEAMS WHERE ACTIVE = TRUE";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        return getTeamsMethod(rs);
    }

    /**
     * Get teams method
     *
     * @param rs result set
     * @return List<Teams> list
     * @throws SQLException SQLException
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
     * Return team
     *
     * @param con connection
     * @param id  id
     * @return Teams team
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
     */
    private static Teams getTeamMethod(ResultSet rs) throws SQLException {
        int id, ages;
        String name, league, division, leagueTable, fixtures, coach, teamPhoto;
        boolean active, youth;
        id = rs.getInt("ID");
        name = rs.getString("NAME");
        league = rs.getString("LEAGUE");
        division = rs.getString("DIVISION");
        leagueTable = rs.getString("LEAGUE_TABLE");
        fixtures = rs.getString("FIXTURES");
        coach = rs.getString("COACH");
        teamPhoto = rs.getString("TEAM_PHOTO");
        active = rs.getBoolean("ACTIVE");
        youth = rs.getBoolean("YOUTH");
        ages = rs.getInt("AGES");
        return new Teams(id, name, league, division, leagueTable, fixtures, coach, teamPhoto, active, youth, ages);
    }

    /**
     * Update team
     *
     * @param con  connection
     * @param team team
     * @throws SQLException SQLException
     */
    public static void updateTeam(Connection con, Teams team) throws SQLException {
        backupTeam(con, team.getID(), "UPDATE");
        String sql = "UPDATE TEAMS SET NAME = ?, LEAGUE = ?, DIVISION = ?, LEAGUE_TABLE = ?, FIXTURES = ?, COACH = ?, TEAM_PHOTO = ?, ACTIVE = ?, YOUTH = ?, AGES = ? WHERE ID = ?";
        PreparedStatement pstm = con.prepareStatement(sql), pstm1;
        pstm1 = setTeamMethod(pstm, team);
        pstm1.setInt(11, team.getID());
        pstm1.executeUpdate();
    }

    /**
     * Insert team
     *
     * @param con  connection
     * @param team team
     * @throws SQLException SQLException
     */
    public static void insertTeam(Connection con, Teams team) throws SQLException {
        String sql = "INSERT INTO TEAMS (NAME, LEAGUE, DIVISION, LEAGUE_TABLE, FIXTURES, COACH, TEAM_PHOTO, ACTIVE, YOUTH, AGES) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
     * @throws SQLException SQLException
     */
    private static PreparedStatement setTeamMethod(PreparedStatement pstm, Teams team) throws SQLException {
        pstm.setString(1, team.getName());
        pstm.setString(2, team.getLeague());
        pstm.setString(3, team.getDivision());
        pstm.setString(4, team.getLeagueTable());
        pstm.setString(5, team.getFixtures());
        pstm.setString(6, team.getCoach());
        pstm.setString(7, team.getTeamPhoto());
        pstm.setBoolean(8, team.getActive());
        pstm.setBoolean(9, team.getYouth());
        pstm.setInt(10, team.getAges());
        return pstm;
    }

    /**
     * Delete team
     *
     * @param con connection
     * @param id  id
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
     */
    private static void backupTeam(Connection con, int id, String action) throws SQLException {
        String sql = "INSERT INTO TEAMS_BACKUP (ID, NAME, LEAGUE, DIVISION, LEAGUE_TABLE, FIXTURES, COACH, TEAM_PHOTO, ACTIVE, YOUTH, AGES, ACTION) SELECT ID, NAME, LEAGUE, DIVISION, LEAGUE_TABLE, FIXTURES, COACH, TEAM_PHOTO, ACTIVE, YOUTH, AGES, ? FROM TEAMS WHERE ID = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, action);
        pstm.setInt(2, id);
        pstm.executeUpdate();
    }

    /**
     * Return sponsors
     *
     * @param con connection
     * @return List<Sponsors> list
     * @throws SQLException SQLException
     */
    public static List<Sponsors> querySponsors(Connection con) throws SQLException {
        String sql = "SELECT * FROM SPONSORS";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        return getSponsorsMethod(rs);
    }

    /**
     * Return sponsors for team
     *
     * @param con    connection
     * @param teamID team id
     * @param youth  youth
     * @return List<Sponsors> list
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * Return sponsor
     *
     * @param con connection
     * @param id  id
     * @return Sponsors sponsor
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
     */
    private static void backupSponsor(Connection con, int id, String action) throws SQLException {
        String sql = "INSERT INTO SPONSORS_BACKUP (ID, NAME, WEBSITE, IMAGE, PURPOSE, TEAM_ID, ACTION) SELECT ID, NAME, WEBSITE, IMAGE, PURPOSE, TEAM_ID, ? FROM SPONSORS WHERE ID = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, action);
        pstm.setInt(2, id);
        pstm.executeUpdate();
    }

    /**
     * Return users
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
     * Return users for contact
     *
     * @param con connection
     * @return List<Users> list
     * @throws SQLException throws SQLException
     */
    public static List<Users> queryUsersContacts(Connection con) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE ROLE IN(1, 2, 3, 4, 5) ORDER BY ROLE";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        return getUsersMethod(rs);
    }

    /**
     * Return users who are managers for team
     *
     * @param con    connection
     * @param teamID team id
     * @return List<Users> list
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * Return user
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
     * Return user
     *
     * @param con      connection
     * @param email    email
     * @param password password
     * @return Users user
     * @throws Exception throws SQLException
     */
    public static Users login(Connection con, String email, String password) throws Exception {
        String sql1 = "UPDATE USERS SET TEMP = SHA2('" + password + "', 512) WHERE EMAIL = ? AND ROLE != 0", sql2 = "SELECT * FROM USERS WHERE EMAIL = ? AND ROLE != 0", sql3 = "UPDATE USERS SET TEMP = NULL WHERE EMAIL = ? AND ROLE != 0";
        PreparedStatement pstm1 = con.prepareStatement(sql1), pstm2 = con.prepareStatement(sql2), pstm3 = con.prepareStatement(sql3);
        if (email == null) return null;
        pstm1.setString(1, email);
        pstm1.execute();
        pstm2.setString(1, email);
        pstm3.setString(1, email);
        ResultSet rs = pstm2.executeQuery();
        rs.next();
        if (rs.getString("PASSWORD").equals(rs.getString("TEMP"))) {
            pstm3.execute();
            String name, phone, image;
            int teamID, role;
            name = rs.getString("NAME");
            phone = rs.getString("PHONE");
            teamID = rs.getInt("TEAM_ID");
            role = rs.getInt("ROLE");
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
     * @throws SQLException SQLException
     */
    private static Users getUserMethod(ResultSet rs) throws SQLException {
        String name, email, phone, image;
        int teamID, role;
        name = rs.getString("NAME");
        email = rs.getString("EMAIL");
        phone = rs.getString("PHONE");
        teamID = rs.getInt("TEAM_ID");
        role = rs.getInt("ROLE");
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
     * @throws SQLException SQLException
     */
    private static PreparedStatement setUserMethod(PreparedStatement pstm, Users user) throws SQLException {
        pstm.setString(1, user.getName());
        pstm.setString(2, user.getEmail());
        pstm.setString(3, user.getPhone());
        pstm.setInt(4, user.getTeam());
        pstm.setInt(5, user.getRole());
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
     * @throws SQLException SQLException
     */
    private static void backupUser(Connection con, String email, String action) throws SQLException {
        String sql = "INSERT INTO USERS_BACKUP (NAME, EMAIL, PHONE, TEAM_ID, ROLE, IMAGE, PASSWORD, TEMP, ACTION) SELECT NAME, EMAIL, PHONE, TEAM_ID, ROLE, IMAGE, PASSWORD, TEMP, ? FROM USERS WHERE EMAIL = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, action);
        pstm.setString(2, email);
        pstm.executeUpdate();
    }

    /**
     * Returns what's on
     *
     * @param con connection
     * @return List<WhatsOn> list
     * @throws SQLException SQLException
     */
    public static List<WhatsOn> queryWhatsOn(Connection con) throws SQLException {
        String sql = "SELECT * FROM WHATSON";
        PreparedStatement pstm = con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        return getWhatsOnMethodMultiple(rs);
    }

    /**
     * Returns what's on by event date
     *
     * @param con connection
     * @return List<WhatsOn> list
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * Return what's on
     *
     * @param con connection
     * @param id  id
     * @return WhatsOn whatsOn
     * @throws SQLException SQLException
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
     * Return what's on latest
     *
     * @param con connection
     * @return WhatsOn whatsOn
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
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
     * @throws SQLException SQLException
     */
    private static void backupWhatsOn(Connection con, int id, String action) throws SQLException {
        String sql = "INSERT INTO WHATSON_BACKUP (ID, TITLE, IMAGE, CONTENT, DATE, DATE_OF_EVENT, ACTION) SELECT ID, TITLE, IMAGE, CONTENT, DATE, DATE_OF_EVENT, ? FROM WHATSON WHERE ID = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, action);
        pstm.setInt(2, id);
        pstm.executeUpdate();
    }
}

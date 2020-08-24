package com.bswdi.beans;

/**
 * Team object
 *
 * @author BSWDI
 * @version 1.0
 */
@SuppressWarnings("unused")
public class Teams {

    private int id = 0, ages = 0;
    private String name = null, league = null, division = null, leagueTable = null, fixtures = null, coach = null, teamPhoto = null;
    boolean active = false, youth = false;

    /**
     * Blank constructor
     */
    public Teams() {

    }

    /**
     * Constructor
     *
     * @param id          id
     * @param name        name
     * @param league      league
     * @param division    division
     * @param leagueTable leagueTable
     * @param fixtures    fixtures
     * @param coach       coach
     * @param teamPhoto   team photo
     * @param active      active
     * @param youth       youth
     * @param ages        ages
     */
    public Teams(int id, String name, String league, String division, String leagueTable, String fixtures, String coach, String teamPhoto, boolean active, boolean youth, int ages) {
        this.id = id;
        this.name = name;
        this.league = league;
        this.division = division;
        this.leagueTable = leagueTable;
        this.fixtures = fixtures;
        this.coach = coach;
        this.teamPhoto = teamPhoto;
        this.active = active;
        this.youth = youth;
        this.ages = ages;
    }

    /**
     * Return id
     *
     * @return id id
     */
    public int getID() {
        return id;
    }

    /**
     * Sets id
     *
     * @param id id
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * Return name
     *
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return league
     *
     * @return String league
     */
    public String getLeague() {
        return league;
    }

    /**
     * Sets league
     *
     * @param league league
     */
    public void setLeague(String league) {
        this.league = league;
    }

    /**
     * Returns division
     *
     * @return String division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets division
     *
     * @param division division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Returns league table
     *
     * @return String leagueTable
     */
    public String getLeagueTable() {
        return leagueTable;
    }

    /**
     * Sets league table
     *
     * @param leagueTable leagueTable
     */
    public void setLeagueTable(String leagueTable) {
        this.leagueTable = leagueTable;
    }

    /**
     * Return fixtures
     *
     * @return String fixtures
     */
    public String getFixtures() {
        return fixtures;
    }

    /**
     * Sets fixtures
     *
     * @param fixtures fixtures
     */
    public void setFixtures(String fixtures) {
        this.fixtures = fixtures;
    }

    /**
     * Return coach
     *
     * @return String coach
     */
    public String getCoach() {
        return coach;
    }

    /**
     * Sets coach
     *
     * @param coach coach
     */
    public void setCoach(String coach) {
        this.coach = coach;
    }

    /**
     * Return team photo
     *
     * @return String teamPhoto
     */
    public String getTeamPhoto() {
        return teamPhoto;
    }

    /**
     * Sets team photo
     *
     * @param teamPhoto team photo
     */
    public void setTeamPhoto(String teamPhoto) {
        this.teamPhoto = teamPhoto;
    }

    /**
     * Return active
     *
     * @return boolean active
     */
    public boolean getActive() {
        return active;
    }

    /**
     * Sets active
     *
     * @param active active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Return youth
     *
     * @return boolean youth
     */
    public boolean getYouth() {
        return youth;
    }

    /**
     * Sets youth
     *
     * @param youth youth
     */
    public void setYouth(boolean youth) {
        this.youth = youth;
    }

    /**
     * Returns ages
     *
     * @return int ages
     */
    public int getAges() {
        return ages;
    }

    /**
     * Sets ages
     *
     * @param ages ages
     */
    public void setAges(int ages) {
        this.ages = ages;
    }
}

package com.bswdi.beans;

/**
 * Programme season object
 *
 * @author BSWDI
 * @version 1.0
 */
public class ProgrammeSeasons {

    int id = 0;
    String season = null;

    /**
     * Blank constructor
     */
    public ProgrammeSeasons() {

    }

    /**
     * Constructor
     *
     * @param id id
     * @param season season
     */
    public ProgrammeSeasons(int id, String season) {
        this.id = id;
        this.season = season;
    }

    /**
     * Returns id
     *
     * @return int id
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
     * Returns season
     *
     * @return String season
     */
    public String getSeason() {
        return season;
    }

    /**
     * Sets season
     *
     * @param season season
     */
    public void setSeason(String season) {
        this.season = season;
    }
}

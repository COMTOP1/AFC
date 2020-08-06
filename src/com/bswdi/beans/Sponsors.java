package com.bswdi.beans;

/**
 * Sponsors object
 *
 * @author Liam Burnand
 * @version 1.0
 */
public class Sponsors {

    private int id = 0;
    private String name = null, website = null, image = null, purpose = null, teamID = null;

    /**
     * Blank constructor
     */
    public Sponsors() {

    }

    /**
     * Constructor
     *
     * @param id      id
     * @param name    name
     * @param website website
     * @param image   image
     * @param purpose purpose
     * @param teamID  teamID
     */
    public Sponsors(int id, String name, String website, String image, String purpose, String teamID) {
        this.id = id;
        this.name = name;
        this.website = website;
        this.image = image;
        this.purpose = purpose;
        this.teamID = teamID;
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
     * Return website
     *
     * @return String website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Sets website
     *
     * @param website website
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * Return image
     *
     * @return String image
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets image
     *
     * @param image image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Return purpose
     *
     * @return String purpose
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Sets purpose
     *
     * @param purpose purpose
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    /**
     * Return teamID
     *
     * @return String teamID
     */
    public String getTeamID() {
        return teamID;
    }

    /**
     * Sets teamID
     *
     * @param teamID teamID
     */
    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }
}

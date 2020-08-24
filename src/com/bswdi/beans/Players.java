package com.bswdi.beans;

import java.time.LocalDate;
import java.time.Period;

/**
 * Player object
 *
 * @author BSWDI
 * @version 1.0
 */
@SuppressWarnings("unused")
public class Players {

    private int id = 0, teamID = 0;
    private String name = null, image = null, position = null;
    private long dateOfBirth = 0;
    private boolean captain = false;

    /**
     * Blank constructor
     */
    public Players() {

    }

    /**
     * Constructor
     *
     * @param id          id
     * @param name        name
     * @param image       image
     * @param dateOfBirth date of birth
     * @param captain     captain
     * @param teamID      team id
     */
    public Players(int id, String name, String image, long dateOfBirth, String position, boolean captain, int teamID) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.dateOfBirth = dateOfBirth;
        this.position = position;
        this.captain = captain;
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
     * Return image name
     *
     * @return String image
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets image name
     *
     * @param image image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Return date of birth
     *
     * @return long dateOfBirth
     */
    public long getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets date of birth
     *
     * @param dateOfBirth date of birth
     */
    public void setDateOfBirth(long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Return age
     *
     * @return int age
     */
    public int getAge() {
        LocalDate dateNow = LocalDate.now(), dateThen = LocalDate.ofEpochDay(dateOfBirth);
        return Period.between(dateThen, dateNow).getYears();
    }

    /**
     * Return position
     *
     * @return String position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets position
     *
     * @param position position
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Return captain
     *
     * @return boolean captain
     */
    public boolean getCaptain() {
        return captain;
    }

    /**
     * Sets captain
     *
     * @param captain captain
     */
    public void setTeam(boolean captain) {
        this.captain = captain;
    }

    /**
     * Return team id
     *
     * @return int teamID
     */
    public int getTeamID() {
        return teamID;
    }

    /**
     * Sets team id
     *
     * @param teamID team id
     */
    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }
}

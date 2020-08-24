package com.bswdi.beans;

/**
 * User object
 *
 * @author BSWDI
 * @version 1.0
 */
@SuppressWarnings("unused")
public class Users {

    private String name = null, email = null, image = null, phone = null;
    private int teamID = 0, role = -1;

    /**
     * Blank constructor
     */
    public Users() {

    }

    /**
     * Constructor
     *
     * @param name   name
     * @param email  email
     * @param phone  phone
     * @param teamID teamID
     * @param role   role
     */
    public Users(String name, String email, String phone, int teamID, int role, String image) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.teamID = teamID;
        this.role = role;
        this.image = image;
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
     * Return email
     *
     * @return String email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email
     *
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Return phone
     *
     * @return int phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone
     *
     * @param phone phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Return teamID
     *
     * @return int teamID
     */
    public int getTeam() {
        return teamID;
    }

    /**
     * Sets teamID
     *
     * @param teamID teamID
     */
    public void setTeam(int teamID) {
        this.teamID = teamID;
    }

    /**
     * Return role
     *
     * @return int role
     */
    public int getRole() {
        return role;
    }

    /**
     * Sets role
     *
     * @param role role
     */
    public void setRole(int role) {
        this.role = role;
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
}

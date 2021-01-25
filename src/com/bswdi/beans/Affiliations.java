package com.bswdi.beans;

/**
 * Affiliations object
 *
 * @author BSWDI
 * @version 1.0
 */
public class Affiliations {

    private int id = 0;
    private String name = null, website = null, image = null;

    /**
     * Blank constructor
     */
    public Affiliations() {

    }

    /**
     * Constructor
     *
     * @param id id
     * @param name name
     * @param website website
     * @param image image
     */
    public Affiliations(int id, String name, String website, String image) {
        this.id = id;
        this.name = name;
        this.website = website;
        this.image = image;
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
     * @param id id
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * Returns name
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
     * Returns website
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
     * Returns image
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

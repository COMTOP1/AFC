package com.bswdi.beans;

/**
 * Image object
 *
 * @author BSWDI
 * @version 1.0
 */
@SuppressWarnings("unused")
public class Images {

    private int id = 0;
    private String image = null, caption = null;

    /**
     * Blank constructor
     */
    public Images() {

    }

    /**
     * Constructor
     *
     * @param id      id
     * @param image   image
     * @param caption caption
     */
    public Images(int id, String image, String caption) {
        this.id = id;
        this.image = image;
        this.caption = caption;
    }

    /**
     * Return id
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
     * Return caption
     *
     * @return String caption
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Sets caption
     *
     * @param caption caption
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }
}

package com.bswdi.beans;

/**
 * News object
 *
 * @author BSWDI
 * @version 1.0
 */
public class News {

    private int id = 0;
    private String title = null, image = null, content = null;
    private long date = 0L;

    /**
     * Blank constructor
     */
    public News() {

    }

    /**
     * Constructor
     *
     * @param id      id
     * @param title   title
     * @param image   image
     * @param content content
     * @param date    date
     */
    public News(int id, String title, String image, String content, long date) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.content = content;
        this.date = date;
    }

    /**
     * Get id
     *
     * @return int id
     */
    public int getID() {
        return id;
    }

    /**
     * Set id
     *
     * @param id id
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * Get title
     *
     * @return String title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title
     *
     * @param title title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get image
     *
     * @return String image
     */
    public String getImage() {
        return image;
    }

    /**
     * Set image
     *
     * @param image image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Get content
     *
     * @return String content
     */
    public String getContent() {
        return content;
    }

    /**
     * Set content
     *
     * @param content content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get date
     *
     * @return long date
     */
    public long getDate() {
        return date;
    }

    /**
     * Set date
     *
     * @param date date
     */
    public void setDate(long date) {
        this.date = date;
    }
}

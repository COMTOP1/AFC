package com.bswdi.beans;

/**
 * Documents object
 *
 * @author BSWDI
 * @version 1.0
 */
@SuppressWarnings("unused")
public class Documents {

    private int id = 0;
    private String name = null, fileName = null;

    /**
     * Blank constructor
     */
    public Documents() {

    }

    /**
     * Constructor
     *
     * @param id id
     * @param name name
     * @param fileName file name
     */
    public Documents(int id, String name, String fileName) {
        this.id = id;
        this.name = name;
        this.fileName = fileName;
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
     * Returns file name
     *
     * @return String fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Set file name
     *
     * @param fileName file name
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

package com.bswdi.beans;

/**
 * Programme object
 *
 * @author BSWDI
 * @version 1.0
 */
public class Programmes {
	
	private int id = 0;
	private String name = null, fileName = null;

	/**
	 * Blank constructor
	 */
	public Programmes() {
		
	}

	/**
	 * Constructor
	 */
	public Programmes(int id, String name, String fileName) {
		this.id = id;
		this.name = name;
		this.fileName = fileName;
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
	 * Return file name
	 *
	 * @return String fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets file name
	 *
	 * @param fileName file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}

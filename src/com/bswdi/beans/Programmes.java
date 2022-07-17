package com.bswdi.beans;

/**
 * Programme object
 *
 * @author BSWDI
 * @version 1.0
 */
public class Programmes {
	
	private int id = 0, programmeSeasonID = 0;
	private String name = null, fileName = null;
	private long dateOfProgramme = 0L;

	/**
	 * Blank constructor
	 */
	public Programmes() {
		
	}

	/**
	 * Constructor
	 * 
	 * @param id id
	 * @param name name
	 * @param fileName file name
	 * @param date date
	 * @param programmeSeasonID programme season id
	 */
	public Programmes(int id, String name, String fileName, long date, int programmeSeasonID) {
		this.id = id;
		this.name = name;
		this.fileName = fileName;
		this.dateOfProgramme = date;
		this.programmeSeasonID = programmeSeasonID;
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
	
	/**
	 * Return date of programme
	 *
	 * @return Long date of programme
	 */
	public Long getDateOfProgramme() {
		return dateOfProgramme;
	}

	/**
	 * Sets date of programme
	 *
	 * @param dateOfProgramme date of programme
	 */
	public void setDateOfProgramme(Long dateOfProgramme) {
		this.dateOfProgramme = dateOfProgramme;
	}

	/**
	 * Return programme season id
	 * @return programmeSeasonID
	 */
	public int getProgrammeSeasonID() {
		return programmeSeasonID;
	}

	/**
	 * Sets programme season id
	 * @param programmeSeasonID programme season id
	 */
	public void setProgrammeSeasonID(int programmeSeasonID) {
		this.programmeSeasonID = programmeSeasonID;
	}
}

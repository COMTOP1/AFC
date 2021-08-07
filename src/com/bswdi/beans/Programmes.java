package com.bswdi.beans;

/**
 * Programme object
 *
 * @author BSWDI
 * @version 1.0
 */
public class Programmes {
	
	private int id = 0;

	/**
	 * Blank constructor
	 */
	public Programmes() {
		
	}

	/**
	 * Constructor
	 */
	public Programmes(int id) {
		this.id = id;
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
}

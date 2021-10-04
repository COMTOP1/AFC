package com.bswdi.beans;

/**
 * Role enum class
 *
 * @author BSWDI
 * @version 2.0
 */
public enum Role {
    MANAGER,
    PROGRAMME_EDITOR,
    LEAGUE_SECRETARY,
    TREASURER,
    SAFEGUARDING_OFFICER,
    CLUB_SECRETARY,
    CHAIRPERSON,
    WEBMASTER;
    
    @Override
    public String toString() {
    	switch (this) {
    		case MANAGER:
    			return "Manager";
    		case PROGRAMME_EDITOR:
    			return "Programme editor";
    		case LEAGUE_SECRETARY:
    			return "League secretary";
            case TREASURER:
                return "Treasurer";
            case SAFEGUARDING_OFFICER:
                return "Safeguarding officer";
            case CLUB_SECRETARY:
                return "Club secretary";
            case CHAIRPERSON:
                return "Chairperson";
            case WEBMASTER:
                return "Webmaster";
			default:
    			throw new IllegalArgumentException();
    	}
    }
}

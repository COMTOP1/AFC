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

    public static Role getRole(String role) {
        switch (role.toLowerCase()) {
            case "manager":
                return MANAGER;
            case "programme editor":
                return PROGRAMME_EDITOR;
            case "league secretary":
                return LEAGUE_SECRETARY;
            case "treasurer":
                return TREASURER;
            case "safeguarding officer":
                return SAFEGUARDING_OFFICER;
            case "club secretary":
                return CLUB_SECRETARY;
            case "chairperson":
                return CHAIRPERSON;
            case "webmaster":
                return WEBMASTER;
            default:
                throw new IllegalArgumentException();
        }
    }

    public String getRole() {
        switch (this) {
            case MANAGER:
                return "MANAGER";
            case PROGRAMME_EDITOR:
                return "PROGRAMME_EDITOR";
            case LEAGUE_SECRETARY:
                return "LEAGUE_SECRETARY";
            case TREASURER:
                return "TREASURER";
            case SAFEGUARDING_OFFICER:
                return "SAFEGUARDING_OFFICER";
            case CLUB_SECRETARY:
                return "CLUB_SECRETARY";
            case CHAIRPERSON:
                return "CHAIRPERSON";
            case WEBMASTER:
                return "WEBMASTER";
            default:
                throw new IllegalArgumentException();
        }
    }
    
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

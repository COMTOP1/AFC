package com.bswdi.beans;

/**
 * User class
 *
 * @author Liam Burnand
 * @version 1.0
 * @see #MANAGER
 * @see #SECRETARY
 * @see #TREASURER
 * @see #SAFEGUARDING_OFFICER
 * @see #PROGRAMME_EDITOR
 * @see #CHAIRPERSON
 * @see #WEBMASTER
 */
public abstract class UserRole {

    /**
     * Value for manager
     *
     * @see UserRole
     */
    public static final int MANAGER = 0;

    /**
     * Value for secretary
     *
     * @see UserRole
     */
    public static final int SECRETARY = 1;

    /**
     * Value for treasurer
     *
     * @see UserRole
     */
    public static final int TREASURER = 2;

    /**
     * Value for safeguarding officer
     *
     * @see UserRole
     */
    public static final int SAFEGUARDING_OFFICER = 3;

    /**
     * Value for programme editor
     *
     * @see UserRole
     */
    public static final int PROGRAMME_EDITOR = 4;

    /**
     * Value for chairperson
     *
     * @see UserRole
     */
    public static final int CHAIRPERSON = 5;

    /**
     * Value for webmaster
     *
     * @see UserRole
     */
    public static final int WEBMASTER = 6;

    /**
     * Return role
     *
     * @param role role
     * @return String roleString
     */
    public static String getRole(int role) {
        String roleString;
        switch (role) {
            case UserRole.MANAGER:
                roleString = "Manager";
                break;
            case UserRole.SECRETARY:
                roleString = "Secretary";
                break;
            case UserRole.TREASURER:
                roleString = "Treasurer";
                break;
            case UserRole.SAFEGUARDING_OFFICER:
                roleString = "Safeguarding officer";
                break;
            case UserRole.PROGRAMME_EDITOR:
                roleString = "Programme editor";
                break;
            case UserRole.CHAIRPERSON:
                roleString = "Chairperson";
                break;
            case UserRole.WEBMASTER:
                roleString = "Webmaster";
                break;
            default:
                roleString = "!ERROR!";
                break;
        }
        return roleString;
    }
}

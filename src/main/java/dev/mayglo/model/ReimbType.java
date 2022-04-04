package dev.mayglo.model;

/**
 * Enum for the different Reimbursement types
 */
public enum ReimbType
{
    /**
     * Types for reimbursements
     */
    LODGING("LODGING"),
    TRAVEL("TRAVEL"),
    FOOD("FOOD"),
    OTHER("OTHER");

    /**
     * The type of this reimbursement
     */
    public final String type;

    /**
     * Constructor --> Sets the type to a specific string
     * @param type String indicating type of this reimbursement
     */
    ReimbType(String type) { this.type = type;}
}

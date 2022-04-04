package dev.mayglo.model;

public enum ReimbStatus
{
    /**
     * States for reimbursements
     */
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    DENIED("DENIED");

    /**
     * The status of this reimbursement
     */
    public final String status;

    /**
     * Constructor --> Sets the status to a specific string
     * @param status String indicating status of this reimbursement
     */
    ReimbStatus(String status) { this.status = status;}
}

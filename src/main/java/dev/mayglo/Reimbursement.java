package dev.mayglo;

/**
 * Contains information relevant for a reimbursement
 */
public class Reimbursement
{
    // Reimbursement objects contain basic details.

    // Type:
    //      - Lodging
    //      - Travel
    //      - Food
    //      - Other

    // Status:
    //      - Pending
    //      - Approved
    //      - Denied

    /**
     * Fields
     */
    private Integer reimb_ID;
    private Double amount;
    private String timeSubmitted;
    private String timeResolved;
    private String description;
    // receipt would go here ----
    private Integer payment_ID;
    private Integer author_ID;
    private Integer resolver_ID;
    private Integer status_ID;
    private Integer type_ID;

    /**
     * Constructor --> Default constructor
     */
    public Reimbursement() {}

    /**
     * Constructor --> Creates a reimbursement object based on parameters for "ERS_REIMBURSEMENT" table
     * @param reimb_ID ID of this reimbursement
     * @param amount Amount of this reimbursement
     * @param timeSubmitted Time of submission
     * @param timeResolved Time of resolution
     * @param description Description of reimbursement
     * @param payment_ID ID of payment
     * @param author_ID ID of author
     * @param resolver_ID ID of resolver
     * @param status_ID ID of reimbursement status
     * @param type_ID ID of reimbursement type
     */
    public Reimbursement(Integer reimb_ID, Double amount, String timeSubmitted, String timeResolved, String description, Integer payment_ID, Integer author_ID, Integer resolver_ID, Integer status_ID, Integer type_ID)
    {
        this.reimb_ID = reimb_ID;
        this.amount = amount;
        this.timeSubmitted = timeSubmitted;
        this.timeResolved = timeResolved;
        this.description = description;
        this.payment_ID = payment_ID;
        this.author_ID = author_ID;
        this.resolver_ID = resolver_ID;
        this.status_ID = status_ID;
        this.type_ID = type_ID;
    }

    // GETTERS

    public Integer getReimb_ID()
    {
        return reimb_ID;
    }

    public Double getAmount()
    {
        return amount;
    }

    public String getTimeSubmitted()
    {
        return timeSubmitted;
    }

    public String getTimeResolved()
    {
        return timeResolved;
    }

    public String getDescription()
    {
        return description;
    }

    public Integer getPayment_ID()
    {
        return payment_ID;
    }

    public Integer getAuthor_ID()
    {
        return author_ID;
    }

    public Integer getResolver_ID()
    {
        return resolver_ID;
    }

    public Integer getStatus_ID()
    {
        return status_ID;
    }

    public Integer getType_ID()
    {
        return type_ID;
    }


    // SETTERS

    public void setReimb_ID(Integer reimb_ID)
    {
        this.reimb_ID = reimb_ID;
    }

    public void setAmount(Double amount)
    {
        this.amount = amount;
    }

    public void setTimeSubmitted(String timeSubmitted)
    {
        this.timeSubmitted = timeSubmitted;
    }

    public void setTimeResolved(String timeResolved)
    {
        this.timeResolved = timeResolved;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setPayment_ID(Integer payment_ID)
    {
        this.payment_ID = payment_ID;
    }

    public void setAuthor_ID(Integer author_ID)
    {
        this.author_ID = author_ID;
    }

    public void setResolver_ID(Integer resolver_ID)
    {
        this.resolver_ID = resolver_ID;
    }

    public void setStatus_ID(Integer status_ID)
    {
        this.status_ID = status_ID;
    }

    public void setType_ID(Integer type_ID)
    {
        this.type_ID = type_ID;
    }
}

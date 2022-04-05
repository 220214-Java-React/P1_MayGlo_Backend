package dev.mayglo.model;

/**
 * Contains information relevant for a reimbursement
 */
public class Reimbursement
{

    /**
     * Fields
     */
    private Integer reimb_ID;
    private Double amount;
    private String timeSubmitted;
    private String timeResolved;
    private String description;
    private Integer author_ID;
    private Integer resolver_ID;
    private String authorName;
    private String resolverName;
    private Integer status_ID;
    private Integer type_ID;

    /**
     * Constructor --> Default constructor
     */
    public Reimbursement() {}

    /**
     * A basic Reimbursement constructor for JUnit testing.
     * @param amount Amount of this reimbursement
     * @param author_ID User_ID for the auther of this reimbursement
     */
    public Reimbursement(Double amount, Integer author_ID)
    {
        this.amount = amount;
        this.author_ID = author_ID;
    }

    /**
     * Constructor --> Creates a reimbursement object based on parameters for "ERS_REIMBURSEMENT" table
     * @param reimb_ID ID of this reimbursement
     * @param amount Amount of this reimbursement
     * @param timeSubmitted Time of submission
     * @param timeResolved Time of resolution
     * @param description Description of reimbursement
     * @param author_ID ID of author
     * @param resolver_ID ID of resolver
     * @param status_ID ID of reimbursement status
     * @param type_ID ID of reimbursement type
     */
    public Reimbursement(Integer reimb_ID, Double amount, String timeSubmitted, String timeResolved, String description, Integer author_ID, Integer resolver_ID, Integer status_ID, Integer type_ID)
    {
        this.reimb_ID = reimb_ID;
        this.amount = amount;
        this.timeSubmitted = timeSubmitted;
        this.timeResolved = timeResolved;
        this.description = description;
        this.author_ID = author_ID;
        this.resolver_ID = resolver_ID;
        this.status_ID = status_ID;
        this.type_ID = type_ID;
    }

    /**
     * Constructor --> Creates a reimbursement object based on parameters for "ERS_REIMBURSEMENT" table
     * @param reimb_ID ID of this reimbursement
     * @param amount Amount of this reimbursement
     * @param timeSubmitted Time of submission
     * @param timeResolved Time of resolution
     * @param description Description of reimbursement
     * @param authorName Name of author
     * @param resolverName Name of resolver
     * @param status_ID ID of reimbursement status
     * @param type_ID ID of reimbursement type
     */
    public Reimbursement(Integer reimb_ID, Double amount, String timeSubmitted, String timeResolved, String description, String authorName, String resolverName, Integer status_ID, Integer type_ID)
    {
        this.reimb_ID = reimb_ID;
        this.amount = amount;
        this.timeSubmitted = timeSubmitted;
        this.timeResolved = timeResolved;
        this.description = description;
        this.authorName = authorName;
        this.resolverName = resolverName;
        this.status_ID = status_ID;
        this.type_ID = type_ID;
    }

    /**
     * Gets the ID of this Reimbursement.
     * @return ID (int)
     */
    public Integer getReimb_ID()
    {
        return reimb_ID;
    }

    /**
     * Gets the amount of this Reimbursement.
     * @return Amount (double)
     */
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

    /**
     * Gets the description of this Reimbursement.
     * @return Description (string)
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Gets the user_ID of the author of this Reimbursement.
     * @return User_ID (int) of the author
     */
    public Integer getAuthor_ID()
    {
        return author_ID;
    }

    /**
     * Gets the user_ID of the resolver of this Reimbursement.
     * @return User_ID (int) of the resolver
     */
    public Integer getResolver_ID()
    {
        return resolver_ID;
    }

    public String getAuthorName()
    {
        return authorName;
    }

    public String getResolverName()
    {
        return resolverName;
    }

    /**
     * Gets the status of this Reimbursement.
     * @return Status (int) of this Reimbursement
     */
    public Integer getStatus_ID()
    {
        return status_ID;
    }

    /**
     * Gets the type of this Reimbursement.
     * @return Type (int) of this Reimbursement
     */
    public Integer getType_ID()
    {
        return type_ID;
    }



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

    /**
     * Sets the author's user_ID for this Reimbursement.
     * @param author_ID User_ID (int) to set
     */
    public void setAuthor_ID(Integer author_ID)
    {
        this.author_ID = author_ID;
    }

    /**
     * Sets the resolver's user_ID for this Reimbursement.
     * @param resolver_ID User_ID (int) to set
     */
    public void setResolver_ID(Integer resolver_ID)
    {
        this.resolver_ID = resolver_ID;
    }

    public void setAuthorName(String authorName)
    {
        this.authorName = authorName;
    }

    public void setResolverName(String resolverName)
    {
        this.resolverName = resolverName;
    }

    /**
     * Sets the status_ID for this reimbursement.
     * @param status_ID Status ID (int) to set
     */
    public void setStatus_ID(Integer status_ID)
    {
        this.status_ID = status_ID;
    }

    /**
     * Sets the type_ID for this reimbursement.
     * @param type_ID Type ID (int) to set
     */
    public void setType_ID(Integer type_ID)
    {
        this.type_ID = type_ID;
    }
}

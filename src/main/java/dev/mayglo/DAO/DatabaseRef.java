package dev.mayglo.DAO;

public interface DatabaseRef
{
    // Reimbursement Table
    String REIMB_TABLE = "ERS_REIMBURSEMENT";
    String COL_REIMB_ID = "Reimb_ID";
    String COL_REIMB_AMT = "Amount";
    String COL_REIMB_SUBMITTED = "Submitted";
    String COL_REIMB_RESOLVED = "Resolved";
    String COL_REIMB_DESC = "Description";
    //String COL_REIMB_RECEIPT = "Receipt";     <- Optional
    //String COL_REIMB_PAYMENT_ID = "Payment_ID";   <- Optional
    String COL_REIMB_AUTHOR_ID = "Author_ID";
    String COL_REIMB_RESOLVER_ID = "Resolver_ID";
    String COL_REIMB_STATUS_ID = "Status_ID";
    String COL_REIMB_TYPE_ID = "Type_ID";

    // Users Table
    String USER_TABLE = "ERS_USER";
    String COL_USER_ID = "User_ID";
    String COL_USER_USERNAME = "Username";
    String COL_USER_EMAIL = "Email";
    String COL_USER_PASSWORD = "Password";
    String COL_USER_GIVEN_NAME = "Given_Name";
    String COL_USER_SURNAME = "Surname";
    String COL_USER_IS_ACTIVE = "Is_Active";
    String COL_USER_ROLE_ID = "Role_ID";

    // Role Table
    String ROLE_TABLE = "ERS_USER_ROLE";
    String COL_ROLE_ID = "Role_ID";
    String COL_ROLE_ENUM = "Role_ENUM";

    // Reimbursement Status Table
    String REIMB_STATUS_TABLE = "ERS_REIMBURSEMENT_STATUS";
    String COL_STATUS_ID = "Status_ID";
    String COL_STATUS_ENUM = "Status_ENUM";

    // Reimbursement Type Table
    String REIMB_TYPE_TABLE = "ERS_REIMBURSEMENT_TYPE";
    String COL_TYPE_ID = "Type_ID";
    String COL_TYPE_ENUM = "Type_ENUM";
}

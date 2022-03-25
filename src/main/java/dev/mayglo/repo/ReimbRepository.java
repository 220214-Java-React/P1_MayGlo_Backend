package dev.mayglo.repo;

import dev.mayglo.DAO.DatabaseRef;
import dev.mayglo.DAO.MainDAO;
import dev.mayglo.model.Reimbursement;
import dev.mayglo.util.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReimbRepository implements MainDAO<Reimbursement>, DatabaseRef
{
    private static final Logger logger = LogManager.getLogger(ReimbRepository.class);

    private static String query;
    private static String columnsToQuery;

    /**
     * Creates a reimbursement record to persist to database
     * @param reimbursement Reimbursement object to persist
     */
    @Override
    public void create(Reimbursement reimbursement)
    {
        try(Connection connection = ConnectionFactory.getConnection())
        {
            // Columns to query
            columnsToQuery = "(" + COL_REIMB_AMT + ", " + COL_REIMB_SUBMITTED + ", " + COL_REIMB_AUTHOR_ID + ", " + COL_REIMB_TYPE_ID + ")";

            // insert query to reimbursement table
            // insert into reimbursement table (amount, submitted, author id,  type id) values (getAmount, now(), getAuthor_ID, getType_ID)
            query = "insert into " + REIMB_TABLE + columnsToQuery + " values (?, now(), ?, ?);";

            // Prepare statement to query
            PreparedStatement stmt = connection.prepareStatement(query);

            // Set statement parameters
            stmt.setDouble(1, reimbursement.getAmount());
            stmt.setInt(2, reimbursement.getAuthor_ID());
            stmt.setInt(3, reimbursement.getType_ID());

            // Execute query
            stmt.executeUpdate();
        }
        catch (SQLException sqlE)
        {
            // Log exceptions
            logger.warn(sqlE);
        }
    }

    @Override
    public Reimbursement get(Reimbursement reimbursement)
    {
        return null;
    }

//    /**
//     * Calls the getByID method
//     * @param reimbursement
//     * @return Reimbursement found
//     */
//    @Override
//    public Reimbursement get(Reimbursement reimbursement)
//    {
//        return getByID(reimbursement.getReimb_ID());
//    }
//
//    /**
//     * Gets a reimbursement based on its ID
//     * @param id ID of the reimbursement to find
//     * @return Reimbursement found
//     */
//    private Reimbursement getByID(Integer id)
//    {
//        // Reference for reimbursement found in database
//        Reimbursement reimb = null;
//
//        try(Connection connection = ConnectionFactory.getConnection())
//        {
//            // select query to find a reimbursement based on its id
//            // select * from reimbursements table
//            query = "select * from " + REIMB_TABLE + " where " + COL_REIMB_ID + " = ?;";
//
//            // Create statement to query
//            PreparedStatement stmt = connection.prepareStatement(query);
//
//            // Set statement parameters
//            stmt.setInt(1, id);
//
//            // Get results from query
//            ResultSet rs = stmt.executeQuery();
//
//            // Read record found
//            if (rs.next())
//            {
//                // Instantiate object
//                reimb = new Reimbursement(
//                        rs.getInt(COL_REIMB_ID),            // ID
//                        rs.getDouble(COL_REIMB_AMT),        // Amount
//                        rs.getString(COL_REIMB_SUBMITTED),  // Time Submitted
//                        rs.getString(COL_REIMB_RESOLVED),   // Time Resolved
//                        rs.getString(COL_REIMB_DESC),       // Description
//                        rs.getInt(COL_REIMB_AUTHOR_ID),     // Author ID
//                        rs.getInt(COL_REIMB_RESOLVER_ID),   // Resolver ID
//                        rs.getInt(COL_REIMB_STATUS_ID),     // Status ID
//                        rs.getInt(COL_REIMB_TYPE_ID)        // Type ID
//                );
//            }
//        }
//        catch (SQLException sqlE)
//        {
//            // Log exceptions
//            logger.warn(sqlE);
//        }
//
//        return reimb;   // Null or reimbursement object
//    }


    /**
     * Gets all reimbursements found in the database
     * @return List of reimbursements
     */
    @Override
    public List<Reimbursement> getAll()
    {
        List<Reimbursement> reimbs = null;

        try (Connection connection = ConnectionFactory.getConnection())
        {
            // select query to get all reimbursements
            // select * from reimbursement table
            query = "select * from " + REIMB_TABLE;

            // Create statement to query
            PreparedStatement stmt = connection.prepareStatement(query);

            // Get results from query
            ResultSet rs = stmt.executeQuery();

            // Read all records found
            while(rs.next())
            {
                // Instantiate object
                if (reimbs == null) reimbs = new ArrayList<>();

                // Add to list
                reimbs.add(new Reimbursement(
                        rs.getInt(COL_REIMB_ID),            // ID
                        rs.getDouble(COL_REIMB_AMT),        // Amount
                        rs.getString(COL_REIMB_SUBMITTED),  // Time Submitted
                        rs.getString(COL_REIMB_RESOLVED),   // Time Resolved
                        rs.getString(COL_REIMB_DESC),       // Description
                        rs.getInt(COL_REIMB_AUTHOR_ID),     // Author ID
                        rs.getInt(COL_REIMB_RESOLVER_ID),   // Resolver ID
                        rs.getInt(COL_REIMB_STATUS_ID),     // Status ID
                        rs.getInt(COL_REIMB_TYPE_ID)        // Type ID
                ));
            }
        }
        catch (SQLException sqlE)
        {
            // Log exceptions
            logger.warn(sqlE);
        }

        return reimbs;  // Null or list of reimbursements
    }

    @Override
    public void update(Reimbursement reimbursement)
    {

    }

    @Override
    public void delete(Reimbursement reimbursement)
    {

    }
}

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

/**
 * ReimbRepository interacts with the SQL database to create, read, update, and delete Reimbursement data.
 */
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
            columnsToQuery = "(" + COL_REIMB_AMT + ", " +   // Amount column
                    COL_REIMB_SUBMITTED + ", " +            // Time of submission column
                    COL_REIMB_DESC + ", " +                 // Description column
                    COL_REIMB_AUTHOR_ID + ", " +            // Author ID column
                    COL_REIMB_TYPE_ID + ", " +              // Type ID column
                    COL_REIMB_STATUS_ID + ")";              // Status ID column

            // insert query to reimbursement table
            // insert into reimbursement table (amount, submitted, author id,  type id) values (getAmount, now(), getAuthor_ID, getType_ID)
            query = "insert into " + REIMB_TABLE + columnsToQuery + " values (?, now(), ?, ?, ?, ?);";

            // Prepare statement to query
            PreparedStatement stmt = connection.prepareStatement(query);

            // Set statement parameters
            stmt.setDouble(1, reimbursement.getAmount());       // Amount
            stmt.setString(2, reimbursement.getDescription());  // Description
            stmt.setInt(3, reimbursement.getAuthor_ID());       // Author ID (user ID)
            stmt.setInt(4, reimbursement.getType_ID());         // Type ID
            stmt.setInt(5, reimbursement.getStatus_ID());       // Status ID

            // Execute query
            stmt.executeUpdate();
        }
        catch (SQLException | ClassNotFoundException e)
        {
            // Log exceptions
            logger.warn(e);
        }
    }

    /**
     * Gets a reimbursement based on its ID
     * @param id ID of the reimbursement to find
     * @return Reimbursement found
     */
    @Override
    public Reimbursement getByID(Integer id)
    {
        // Reference for reimbursement found in database
        Reimbursement reimb = null;

        try(Connection connection = ConnectionFactory.getConnection())
        {
            // select query to find a reimbursement based on its id
            // select * from reimbursements table
            query = "select * from " + REIMB_TABLE + " where " + COL_REIMB_ID + " = ?;";

            // Create statement to query
            PreparedStatement stmt = connection.prepareStatement(query);

            // Set statement parameters
            stmt.setInt(1, id);

            // Get results from query
            ResultSet rs = stmt.executeQuery();

            // Read record found
            if (rs.next())
            {
                // Instantiate object
                reimb = new Reimbursement(
                        rs.getInt(COL_REIMB_ID),            // ID
                        rs.getDouble(COL_REIMB_AMT),        // Amount
                        rs.getString(COL_REIMB_SUBMITTED),  // Time Submitted
                        rs.getString(COL_REIMB_RESOLVED),   // Time Resolved
                        rs.getString(COL_REIMB_DESC),       // Description
                        rs.getInt(COL_REIMB_AUTHOR_ID),     // Author ID
                        rs.getInt(COL_REIMB_RESOLVER_ID),   // Resolver ID
                        rs.getInt(COL_REIMB_STATUS_ID),     // Status ID
                        rs.getInt(COL_REIMB_TYPE_ID)        // Type ID
                );
            }
        }
        catch (SQLException | ClassNotFoundException e)
        {
            // Log exceptions
            logger.warn(e);
        }

        return reimb;   // Null or reimbursement object
    }

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

        catch (SQLException | ClassNotFoundException e)
        {
            // Log exceptions
            logger.warn(e);
        }

        return reimbs;  // Null or list of reimbursements
    }

    /**
     * Returns a list of reimbursements made by users with the role ID 1 (Manager)
     * @return A Reimbursement List
     */
    public List<Reimbursement> getAllForManagers()
    {
        List<Reimbursement> reimbs = null;

        try (Connection connection = ConnectionFactory.getConnection())
        {
            final String USER_TABLE_1 = "eu";   // Alias for user table
            final String USER_TABLE_2 = "eu2";  // Alias for second user table
            final String ALIAS_AUTHOR = "authorName";
            final String ALIAS_RESOLVER = "resolverName";

            // Columns to include in query
            columnsToQuery = REIMB_TABLE + "." + COL_REIMB_ID + ", " +
                    REIMB_TABLE + "." + COL_REIMB_AMT + ", " +
                    REIMB_TABLE + "." + COL_REIMB_SUBMITTED + ", " +
                    REIMB_TABLE + "." + COL_REIMB_RESOLVED + ", " +
                    REIMB_TABLE + "." + COL_REIMB_DESC + ", " +
                    USER_TABLE_1 + "." + COL_USER_GIVEN_NAME + " as " + ALIAS_AUTHOR + ", " +
                    USER_TABLE_2 + "." + COL_USER_GIVEN_NAME + " as " + ALIAS_RESOLVER + ", " +
                    REIMB_TABLE + "." + COL_REIMB_STATUS_ID + ", " +
                    REIMB_TABLE + "." + COL_REIMB_TYPE_ID;

            // Join tables
            final String JOIN_QUERY = " left join " + USER_TABLE + " as " + USER_TABLE_1 +
                    " on " + REIMB_TABLE + "." + COL_REIMB_AUTHOR_ID + " = " + USER_TABLE_1 + "." + COL_USER_ID +
                    " left join " + USER_TABLE + " as " + USER_TABLE_2 +
                    " on " + REIMB_TABLE + "." + COL_REIMB_RESOLVER_ID + " = " + USER_TABLE_2 + "." + COL_USER_ID  + ";";

            // select query to get all reimbursements
            // Query the reimbursement table with names in place of the author_ID and resolver_ID
            query = "select " + columnsToQuery +
                    " from " + REIMB_TABLE + JOIN_QUERY;

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
                        rs.getString(ALIAS_AUTHOR),         // Author Name
                        rs.getString(ALIAS_RESOLVER),       // Resolver Name
                        rs.getInt(COL_REIMB_STATUS_ID),     // Status ID
                        rs.getInt(COL_REIMB_TYPE_ID)        // Type ID
                ));
            }
        }

        catch (SQLException | ClassNotFoundException e)
        {
            // Log exceptions
            logger.warn(e);
        }

        return reimbs;  // Null or list of reimbursements
    }

    /**
     * Gets a reimbursement based on its author's ID
     * @param id ID of the reimbursement to find
     * @return Reimbursement found
     */
    public List<Reimbursement> getByAuthorID(Integer id)
    {
        // List for all reimbursements found
        List<Reimbursement> reimbs = null;

        try(Connection connection = ConnectionFactory.getConnection())
        {
            // select query to find a reimbursement based on its id
            // select * from reimbursements table
            query = "select * from " + REIMB_TABLE + " where " + COL_REIMB_AUTHOR_ID + " = ?;";

            // Create statement to query
            PreparedStatement stmt = connection.prepareStatement(query);

            // Set statement parameters
            stmt.setInt(1, id);

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
        catch (SQLException | ClassNotFoundException e)
        {
            // Log exceptions
            logger.warn(e);
        }

        return reimbs;   // Null or reimbursements
    }

    /**
     * Updates a Reimbursement.
     * @param reimbursement Reimbursement to update
     */
    @Override
    public void update(Reimbursement reimbursement)
    {
        if (reimbursement == null)
        {
            logger.warn("Object is null");
            return;
        }

        try (Connection connection = ConnectionFactory.getConnection())
        {
            // update query to change an existing reimbursement
            // update reimbursement table set amount, description and type where reimbursement id matches
            query = "update " + REIMB_TABLE +               // Only employees will update, and can only update these fields:
                    " set " + COL_REIMB_AMT + " = ?," +     // Amount
                    COL_REIMB_DESC + " = ?," +              // Description
                    COL_REIMB_TYPE_ID + " = ?" +            // Type
                    " where " + COL_REIMB_ID + " = ?;";      // ID

            // Create statement to query
            PreparedStatement stmt = connection.prepareStatement(query);

            // Set statement parameters
            stmt.setDouble(1, reimbursement.getAmount());
            stmt.setString(2, reimbursement.getDescription());
            stmt.setInt(3, reimbursement.getType_ID());
            stmt.setInt(4, reimbursement.getReimb_ID());

            // Execute query
            stmt.executeUpdate();
        }
        catch (SQLException | ClassNotFoundException e)
        {
            // Log exceptions
            logger.warn(e);
        }
    }

    /**
     * Resolves an existing Reimbursement via update.
     * @param reimbursement Reimbursement to resolve
     */
    public void updateResolved(Reimbursement reimbursement)
    {
        if (reimbursement.getReimb_ID() == 0)
        {
            logger.warn("Object has no ID");
            return;
        }

        try (Connection connection = ConnectionFactory.getConnection())
        {
            // update query to change an existing reimbursement
            // update reimbursement table set amount, description and type where reimbursement id matches
            query = "update " + REIMB_TABLE +                       // Only managers will update these fields:
                    " set " + COL_REIMB_RESOLVER_ID + " = ?," +     // Resolver ID
                    " " + COL_REIMB_STATUS_ID + " = ?" +            // Status ID
                    " where " + COL_REIMB_ID + " = ?;";             // ID

            // Create statement to query
            PreparedStatement stmt = connection.prepareStatement(query);

            // Set statement parameters
            stmt.setInt(1, reimbursement.getResolver_ID());
            stmt.setInt(2, reimbursement.getStatus_ID());
            stmt.setInt(3, reimbursement.getReimb_ID());

            // Execute query
            stmt.executeUpdate();
        }
        catch (SQLException | ClassNotFoundException e)
        {
            // Log exceptions
            logger.warn(e);
        }
    }

    /**
     * Deletes a Reimbursement.
     * @param reimbursement Reimbursement to delete
     */
    @Override
    public void delete(Reimbursement reimbursement)
    {
        if (reimbursement == null)
        {
            logger.warn("Object is null");
            return;
        }

        try (Connection connection = ConnectionFactory.getConnection())
        {
            // delete query to delete a reimbursement from database
            // delete from reimbursement table where reimbursement id matches
            query = "delete  from " + REIMB_TABLE +         // Delete from this table
                    " where " + COL_REIMB_ID + "= ?;";      // ID

            // Create statement to query
            PreparedStatement stmt = connection.prepareStatement(query);

            // Set statement parameters
            stmt.setInt(1, reimbursement.getReimb_ID());

            // Execute query
            stmt.executeUpdate();
        }
        catch (SQLException | ClassNotFoundException e)
        {
            // Log exceptions
            logger.warn(e);
        }
    }
}

package dev.mayglo.repo;

import dev.mayglo.DAO.DatabaseRef;
import dev.mayglo.DAO.MainDAO;
import dev.mayglo.model.Reimbursement;
import dev.mayglo.model.User;
import dev.mayglo.util.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ReimbRepository implements MainDAO<Reimbursement>, DatabaseRef
{
    private static final Logger logger = LogManager.getLogger(ReimbRepository.class);

    private static String query;
    private static String columnsToQuery;

    @Override
    public void create(Reimbursement reimbursement)
    {
    }

    public void createReimbursement(Reimbursement reimbursement)
    {
        try(Connection connection = ConnectionFactory.getConnection())
        {
            // Columns to query
            columnsToQuery = "(" + COL_REIMB_AMT + ", " + COL_REIMB_SUBMITTED + ", " + COL_REIMB_AUTHOR_ID + ")";

            // insert query to reimbursement table
            query = "insert into " + REIMB_TABLE + columnsToQuery + " values (?, now(), ?)";

            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setDouble(1, reimbursement.getAmount());
            stmt.setInt(2, reimbursement.getAuthor_ID());

            stmt.executeUpdate();
        }
        catch (SQLException sqlE)
        {
            logger.warn(sqlE);
        }
    }

    @Override
    public Reimbursement get(Reimbursement reimbursement)
    {
        return null;
    }

    @Override
    public List<Reimbursement> getAll()
    {
        return null;
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

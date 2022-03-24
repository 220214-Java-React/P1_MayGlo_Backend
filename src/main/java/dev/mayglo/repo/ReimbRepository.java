package dev.mayglo.repo;

import dev.mayglo.DAO.DatabaseRef;
import dev.mayglo.DAO.MainDAO;
import dev.mayglo.model.Reimbursement;
import dev.mayglo.util.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ReimbRepository implements MainDAO<Reimbursement>, DatabaseRef
{
    private static final Logger logger = LogManager.getLogger(ReimbRepository.class);

    @Override
    public void create(Reimbursement reimbursement)
    {
        try (Connection connector = ConnectionFactory.getConnection())
        {
        }
        catch (SQLException e)
        {
            logger.warn(e);
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

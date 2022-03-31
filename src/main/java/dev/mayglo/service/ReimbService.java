package dev.mayglo.service;

import dev.mayglo.model.ReimbStatus;
import dev.mayglo.model.Reimbursement;
import dev.mayglo.repo.ReimbRepository;

import java.util.List;

/**
 * This class is used to handle reimbursement objects as well as
 * make any adjustments needed before persisting or showing user a reimbursement
 */
public class ReimbService
{
    private final ReimbRepository reimbRepository;

    public ReimbService()
    {
        this.reimbRepository = new ReimbRepository();
    }

    public void create(Reimbursement reimbursement)
    {
        reimbursement.setStatus_ID(ReimbStatus.PENDING.ordinal());  // Set status to pending
        reimbRepository.create(reimbursement);                      // Persist object
    }

    public List<Reimbursement> getAll()
    {
        return reimbRepository.getAll();
    }

    public List<Reimbursement> getAllForManagers()
    {
        return reimbRepository.getAllForManagers();
    }

    /**
     * Gets a user's list of reimbursements
     * @param user_ID The User's ID
     * @return The list found
     */
    public List<Reimbursement> getByAuthorID(Integer user_ID)
    {
        return reimbRepository.getByAuthorID(user_ID);
    }

    public Reimbursement getReimbursementByID(Integer id)
    {
        return reimbRepository.getByID(id);
    }

    // For managers to update with approvals/denials
    public void updateResolved(Reimbursement reimbursement)
    {
        reimbRepository.updateResolved(reimbursement);
    }

}

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
        reimbursement.setAuthor_ID(8);  // 8 = EmployeeUser in Database --------DELETE LINE AFTER USER RETRIEVAL COMPLETE

        reimbursement.setStatus_ID(ReimbStatus.PENDING.ordinal());  // Set status to pending
        reimbRepository.create(reimbursement);                      // Persist object
    }

    public List<Reimbursement> getAllReimbursements()
    {
        return reimbRepository.getAll();
    }

//    public Reimbursement getReimbursementByID(Reimbursement reimbursement)
//    {
//        return reimbRepository.get(reimbursement);
//    }

}

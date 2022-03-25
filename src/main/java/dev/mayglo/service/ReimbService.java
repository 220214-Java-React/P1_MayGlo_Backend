package dev.mayglo.service;

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
        reimbRepository.create(reimbursement);
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

package dev.mayglo.service;

import dev.mayglo.model.Reimbursement;
import dev.mayglo.repo.ReimbRepository;

public class ReimbService
{
    private final ReimbRepository reimbRepository;

    public ReimbService()
    {
        this.reimbRepository = new ReimbRepository();
    }

    public void create(Reimbursement reimbursement)
    {
        reimbRepository.createReimbursement(reimbursement);
    }

}

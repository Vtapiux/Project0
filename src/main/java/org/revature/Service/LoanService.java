package org.revature.Service;

import org.revature.DAO.LoanDAO;
import org.revature.Model.Loan;

import java.util.List;

public class LoanService {
    private LoanDAO loanDAO;

    public LoanService() {
        loanDAO = new LoanDAO();
    }

    public LoanService( LoanDAO loanDAO ) {
        this.loanDAO = loanDAO;
    }

    public Loan addLoan(Loan newLoan){
        return loanDAO.insertLoan(newLoan);
    }

    public List<Loan> getAllLoans(){
        return loanDAO.getAllLoans();
    }

    public void updateLoan(Loan loan){
        loanDAO.updateLoan(loan);
    }

    public Loan getLoanInfoWithId(int loan_id){
        return loanDAO.getLoanInfoWithId(loan_id);
    }

    public void updateStatus(Loan loan){
        loanDAO.updateStatus(loan);
    }
}

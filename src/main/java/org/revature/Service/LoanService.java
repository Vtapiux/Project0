package org.revature.Service;

import org.revature.DAO.LoanDAO;
import org.revature.Model.Loan;

import java.util.List;

public class LoanService {
    LoanDAO loanDAO;

    public LoanService() { loanDAO = new LoanDAO(); }

    public LoanService( LoanDAO loanDAO ) { this.loanDAO = loanDAO; }

    public Loan addLoan(Loan newLoan){
        return loanDAO.insertLoan(newLoan);
    }

    public List<Loan> getAllLoans(){
        return loanDAO.getAllLoans();
    }

    public void updateLoan(Loan loan){
        loanDAO.updateLoan(loan);
    }
}

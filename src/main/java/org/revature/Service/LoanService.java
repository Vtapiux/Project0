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

    public Loan addLoan(Loan newLoan, int userId){
        return loanDAO.insertLoan(newLoan, userId);
    }

    public List<Loan> getAllLoans(){
        return loanDAO.getAllLoans();
    }
    public List<Loan> getUserLoans(int userId) {return loanDAO.getUserLoans(userId);}

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

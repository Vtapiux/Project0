package org.revature.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.revature.DTO.LoanDTO;
import org.revature.Model.Loan;
import org.revature.Service.LoanService;

public class LoanController {
    LoanService loanService;

    public LoanController(LoanService loanService){
        this.loanService = loanService;
    }

    public void getAllLoansHandler (Context ctx){
        ctx.json(loanService.getAllLoans());
    }

    public void addLoanHandler (Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Loan loan = mapper.readValue(ctx.body(), Loan.class);
        Loan addedLoan = loanService.addLoan(loan);

        if(addedLoan==null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(addedLoan));
        }
    }

    public void updateLoanHandler(Context ctx){
        int loanId = Integer.parseInt(ctx.pathParam("loan_id"));
        LoanDTO req = ctx.bodyAsClass(LoanDTO.class);

        Loan loan = new Loan();
        loan.setLoanId(loanId);
        loan.setUserId(req.getUserId());
        loan.setAmountRequested(req.getAmountRequested());
        loan.setLoanType(req.getLoanType());
        loan.setStatus(req.getStatus());
        loan.setApprovedDate(req.getApprovedDate());
        loan.setRejectionReason(req.getRejectionReason());

        loanService.updateLoan(loan);
        ctx.status(200).json("{\"message\":\"Loan updated\"}");

    }
}

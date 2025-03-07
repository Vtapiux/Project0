package org.revature.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.revature.DTO.LoanDTO;
import org.revature.Model.Loan;
import org.revature.Service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LoanController {
    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);

    private LoanService loanService;
    private AuthController authController;

    public LoanController(LoanService loanService){
        this.loanService = loanService;
        this.authController = new AuthController();
    }

    public void getAllLoansHandler (Context ctx){
        if(authController.checkLogin(ctx)){
            if(authController.getRole(ctx) == 2){ //Only manager can see ALL loans
                ctx.json(loanService.getAllLoans());
            }else {
                int userId = authController.getUserID(ctx);
                ctx.json(loanService.getUserLoans(userId));
                //ctx.status(403).json("{\"error\":\"\"You do not have permission to perform this action.\"\"}");
            }
        }else{
            ctx.status(401).json("{\"error\":\"Not logged in\"}");
        }
    }

    public void addLoanHandler (Context ctx) throws JsonProcessingException {
        if(authController.checkLogin(ctx)){
            if(authController.getRole(ctx) == 1){ //Only normal users can create a loan
                ObjectMapper mapper = new ObjectMapper();
                Loan loan = mapper.readValue(ctx.body(), Loan.class);
                int userId = authController.getUserID(ctx);
                Loan addedLoan = loanService.addLoan(loan, userId);
                if(addedLoan==null){
                    ctx.status(400);
                }else{
                    ctx.json(mapper.writeValueAsString(addedLoan));
                    logger.info("User {} created loan {}", loan.getUserId(), loan.getLoanId());
                }
            } else{
                ctx.status(403).json("{\"error\":\"You do not have permission to perform this action.\"}");
            }
        }else{
            ctx.status(401).json("{\"error\":\"Not logged in\"}");
        }
    }

    public void updateLoanHandler(Context ctx){
        if(authController.checkLogin(ctx)){
            // both manager and customer can update their loans
            int loanId = Integer.parseInt(ctx.pathParam("loanId"));
            Loan loanUp = loanService.getLoanInfoWithId(loanId);
            if(authController.getRole(ctx) == 1){ //Customer
                if(loanUp.getUserId() == authController.getUserID(ctx)){ //The user match with the loan to edit
                    LoanDTO req = ctx.bodyAsClass(LoanDTO.class);
                    Loan loan = new Loan();
                    loan.setLoanId(loanId);
                    loan.setUserId(authController.getUserID(ctx));
                    loan.setAmountRequested(req.getAmountRequested());
                    loan.setLoanType(req.getLoanType());
                    loan.setStatus(req.getStatus());
                    loan.setApprovedDate(req.getApprovedDate());
                    loan.setRejectionReason(req.getRejectionReason());

                    loanService.updateLoan(loan);
                    ctx.status(200).json("{\"message\":\"Loan updated\"}");
                }else{
                    ctx.status(404).json("{\"message\":\"You do not own this loan.\"}");
                }
            }else{ //Manager
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
        } else{
            ctx.status(401).json("{\"error\":\"Not logged in\"}");
        }
    }

    public void getLoanInfoWithIdHandler(Context ctx){
        if(authController.checkLogin(ctx)){
            int loanId = Integer.parseInt(ctx.pathParam("loanId"));
            Loan loan = loanService.getLoanInfoWithId(loanId);
            if(authController.getRole(ctx) == 1){ //Customer
                if(loan.getUserId() == authController.getUserID(ctx)){
                    ctx.json(loan);
                } else{
                    ctx.status(404).json("{\"message\":\"You do not own this loan.\"}");
                }
            }else{ //Manager
                ctx.json(loan);
            }
        }else{
            ctx.status(401).json("{\"error\":\"Not logged in\"}");
        }
    }

    public void updateStatusHandler(Context ctx){
        if(authController.checkLogin(ctx)){
            if(authController.getRole(ctx) == 2){
                int loanId = Integer.parseInt(ctx.pathParam("loanId"));
                LoanDTO req = ctx.bodyAsClass(LoanDTO.class);

                Loan loan = new Loan();
                loan.setLoanId(loanId);
                loan.setUserId(req.getUserId());
                loan.setAmountRequested(req.getAmountRequested());
                loan.setLoanType(req.getLoanType());
                loan.setStatus(req.getStatus());
                loan.setApprovedDate(req.getApprovedDate());
                loan.setRejectionReason(req.getRejectionReason());

                loanService.updateStatus(loan);
                ctx.status(200).json("{\"message\":\"Loan updated\"}");
                logger.info("Manager {} updated status of loan {}", loan.getUserId(), loan.getLoanId());
            }else{
                ctx.status(403).json("{\"error\":\"You do not have permission to perform this action.\"}");
                logger.warn("User {} tried updating status (Manager only action)", authController.getUserID(ctx));
            }
        }else{
            ctx.status(401).json("{\"error\":\"Not logged in\"}");
        }
    }
}

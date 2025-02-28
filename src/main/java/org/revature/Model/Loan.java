package org.revature.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.time.LocalDate;

public class Loan {
    private int loanId;
    private int userId;
    private int approvedBy;
    private int amountRequested;
    private String loanType;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate appliedDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate approvedDate;
    private String rejectionReason;

    public Loan(){ }

    public Loan(int loanId, int userId, int approvedBy, int amountRequested, String loanType, String status, LocalDate appliedDate, LocalDate approvedDate, String rejectionReason) {
        this.loanId = loanId;
        this.userId = userId;
        this.approvedBy = approvedBy;
        this.amountRequested = amountRequested;
        this.loanType = loanType;
        this.status = status;
        this.appliedDate = appliedDate;
        this.approvedDate = approvedDate;
        this.rejectionReason = rejectionReason;
    }

    public int getLoanId() {return loanId;}
    public void setLoanId(int loanId) {this.loanId = loanId;}

    public int getUserId() {return userId;}
    public void setUserId(int userId) {this.userId = userId;}

    public int getApprovedBy() {return approvedBy;}
    public void setApprovedBy(int approvedBy) {this.approvedBy = approvedBy;}

    public int getAmountRequested() {return amountRequested;}
    public void setAmountRequested(int amountRequested) {this.amountRequested = amountRequested;}

    public String getLoanType() {return loanType;}
    public void setLoanType(String loanType) {this.loanType = loanType;}

    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}

    public LocalDate getAppliedDate() {return appliedDate;}
    public void setAppliedDate(LocalDate appliedDate) {this.appliedDate = appliedDate;}

    public LocalDate getApprovedDate() {return approvedDate;}
    public void setApprovedDate(LocalDate approvedDate) {this.approvedDate = approvedDate;}

    public String getRejectionReason() {return rejectionReason;}
    public void setRejectionReason(String rejectionReason) {this.rejectionReason = rejectionReason;}
}




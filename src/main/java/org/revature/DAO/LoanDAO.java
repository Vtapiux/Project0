package org.revature.DAO;

import org.revature.Model.Loan;
import org.revature.Util.ConnectionUtil;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class LoanDAO {

    public List<Loan> getAllLoans(){
        Connection connection = ConnectionUtil.getConnection();
        List<Loan> loans = new ArrayList<>();

        try{
            String sql = "SELECT * FROM LOAN;";

            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Date sqlDate = rs.getDate("applied_date");
                LocalDate appliedDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                Loan loan = new Loan(rs.getInt("loan_id"),
                        rs.getInt("user_id"),
                        rs.getInt("approved_by"),
                        rs.getInt("amount_requested"),
                        rs.getString("loan_type"),
                        rs.getString("status"),
                        appliedDate,
                        rs.getObject("approved_date",LocalDate.class),
                        rs.getString("rejection_reason"));
                loans.add(loan);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return loans;
    }

    public Loan insertLoan(Loan loan){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO LOAN (user_id, amount_requested, loan_type) VALUES (?, ?, ?);";

            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1,loan.getUserId());
            stmt.setInt(2, loan.getAmountRequested());
            stmt.setString(3, loan.getLoanType());

            stmt.executeUpdate();

            try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
                if(generatedKeys.next()) {
                    int newId = generatedKeys.getInt(1);
                    loan.setLoanId(newId);
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return loan;
    }

    public void updateLoan(Loan loan){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "UPDATE Loan SET user_id = ?, amount_requested = ?, " +
                    "loan_type = ?, " +
                    "approved_date = ?, rejection_reason = ? WHERE loan_id = ?;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1,loan.getUserId());
            stmt.setInt(2, loan.getAmountRequested());
            stmt.setString(3, loan.getLoanType());
            stmt.setObject(4, loan.getApprovedDate());
            stmt.setString(5, loan.getRejectionReason());
            stmt.setInt(6, loan.getLoanId());
            stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public Loan getLoanInfoWithId(int loanId){
        Connection connection = ConnectionUtil.getConnection();
        Loan loan = null;
        try{
            String sql = "SELECT * FROM LOAN WHERE loan_id = ?;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, loanId);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Date sqlDate = rs.getDate("applied_date");
                LocalDate appliedDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                 loan = new Loan(rs.getInt("loan_id"),
                        rs.getInt("user_id"),
                        rs.getInt("approved_by"),
                        rs.getInt("amount_requested"),
                        rs.getString("loan_type"),
                        rs.getString("status"),
                        appliedDate,
                        rs.getObject("approved_date",LocalDate.class),
                        rs.getString("rejection_reason"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return loan;
    }

    public void approveLoan (Loan loan){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "UPDATE Loan SET status = ?, rejection_reason = ?, approved WHERE loan_id = ?;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, loan.getStatus());
            stmt.setString(2, loan.getStatus());
            stmt.setInt(3, loan.getLoanId());
            stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}

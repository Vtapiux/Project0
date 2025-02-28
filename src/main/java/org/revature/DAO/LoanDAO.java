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
            String sql = "INSERT INTO LOAN (user_id, amount_requested, loan_type, status) VALUES (?, ?, ?, ?);";

            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1,loan.getUserId());
            stmt.setInt(2, loan.getAmountRequested());
            stmt.setString(3, loan.getLoanType());
            stmt.setString(4, loan.getStatus());

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
                    "loan_type = ?, status = ?, " +
                    "approved_date = ?, rejection_reason = ? WHERE loan_id = ?;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1,loan.getUserId());
            stmt.setInt(2, loan.getAmountRequested());
            stmt.setString(3, loan.getLoanType());
            stmt.setString(4, loan.getStatus());
            stmt.setObject(5, loan.getApprovedDate());
            stmt.setString(6, loan.getRejectionReason());
            stmt.setInt(7, loan.getLoanId());
            stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}

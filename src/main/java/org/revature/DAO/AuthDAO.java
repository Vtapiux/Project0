package org.revature.DAO;

import org.revature.Model.Account;
import org.revature.Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDAO {
    //private static final Logger logger = LoggerFactory.getLogger(org.revature.DAO.AuthDAO.class);

    //Checks if username already exists
    public boolean accountExists(String username){
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT 1 FROM Account WHERE username = ?;";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return true;
        }
    }

    //Checks if user row already exists
    public boolean userExists(String username){
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT 1 FROM Account WHERE username = ?;";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return true;
        }
    }

    //public Users createEmptyUserProfile(int accountId, Users user){
    public void createEmptyUserProfile(int accountId) {
        String sql = "INSERT INTO Users (account_id) VALUES (?);";
        Connection connection = ConnectionUtil.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, accountId);

            stmt.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
        //return user;
    }

    public Account createAccount(Account account) {
        String sql = "INSERT INTO Account (username, password, role_id) VALUES (?, ?, ?);";
        Connection connection = ConnectionUtil.getConnection();

        try {
            PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, account.getUsername());
            stmt.setString(2, account.getPassword());
            stmt.setInt(3, account.getRoleId());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int accId = rs.getInt(1);
                this.createEmptyUserProfile(accId);
                //return accId;
                account.setAccountId(accId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    public Account getAccountByUsername(String username){
        String sql = "SELECT * FROM account WHERE username = ?;";
        Connection connection = ConnectionUtil.getConnection();
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                Account acc = new Account();
                acc.setAccountId(rs.getInt("account_id"));
                acc.setUsername(rs.getString("username"));
                acc.setPassword(rs.getString("password"));
                acc.setRoleId(rs.getInt("role_id"));
                return acc;
            }
            return null;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}


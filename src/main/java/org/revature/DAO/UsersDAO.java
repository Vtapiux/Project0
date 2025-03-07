package org.revature.DAO;

import org.revature.Controller.AuthController;
import org.revature.Model.Account;
import org.revature.Model.Users;
import org.revature.Util.ConnectionUtil;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.time.*;

public class UsersDAO {

    //Only Manager
    public List<Users> getAllUsers(){

        Connection connection = ConnectionUtil.getConnection();
        List<Users> users = new ArrayList<>();

        try {
            String sql = "select * from Users ORDER BY user_id ASC;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
//                Date sqlDate = rs.getDate("created_at");
//                LocalDate createdAt = (sqlDate != null) ? sqlDate.toLocalDate() : null;

//                java.sql.Date sqlDate = rs.getDate("created_at");
//                Date createdAt = (sqlDate != null) ? new Date(sqlDate.getTime()) : null;

                Users user = new Users(rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getInt("address_id"),
                        rs.getInt("account_id"));
                users.add(user);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return users;
    }

    //Each user
    public void updateUser(Users user){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "UPDATE Users SET first_name = ?, last_name = ?, email = ?, phone_number = ? WHERE user_id = ?;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhoneNumber());
            stmt.setInt(5, user.getUserId());
            stmt.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public Users getUserInfoWithId(int userId){
        Connection connection = ConnectionUtil.getConnection();
        Users user = null;
        try {
            String sql = "SELECT * FROM Users WHERE user_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
//                Date sqlDate = rs.getDate("created_at");
//                LocalDate createdAt = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                 user = new Users(rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getInt("address_id"),
                        rs.getInt("account_id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public Users getUserByAccountId(int accountId){
        Connection connection = ConnectionUtil.getConnection();
        Users user = null;
        try {
            String sql = "SELECT * FROM Users WHERE account_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                user = new Users(rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getInt("address_id"),
                        rs.getInt("account_id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public void updateAddressIdInUsers(int addressId, int userId){
        String sql = "UPDATE Users SET address_id = ? WHERE user_id = ?;";
        Connection connection = ConnectionUtil.getConnection();
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, addressId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}

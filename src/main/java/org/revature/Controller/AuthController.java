package org.revature.Controller;

import jakarta.servlet.http.HttpSession;
import org.revature.Model.Account;
import io.javalin.http.Context;
import org.revature.Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthController {

    /**
     * POST /register
     * JSON body:
     * {
     *   "username": "alice",
     *   "password": "secret"
     * }
     */
    public static void register(Context ctx){
        Account reqAccount = ctx.bodyAsClass(Account.class);

        //Basic validation
        if(reqAccount.getUsername() == null || reqAccount.getPassword() == null){
            ctx.status(400).json("{\"error\":\"Missing username or password\"}");
            return;
        }

        //check if user already exists
        if(accountExists(reqAccount.getPassword())){
            ctx.status(409).json("{\"error\":\"Username already taken\"}");
            return;
        }

        //Insert new user
        boolean created = createAccountInDB(reqAccount);
        if(created){
            ctx.status(201).json("{\"message\":\"Account registered\"}");
        } else{
            ctx.status(500).json("{\"error\":\"Failed to register account\"}");
        }
    }

    /**
     * POST /login
     * JSON body:
     * {
     *   "username": "alice",
     *   "password": "secret"
     * }
     */
    public static void login(Context ctx){
        Account reqAccount = ctx.bodyAsClass(Account.class);
        if (reqAccount.getUsername() == null || reqAccount.getPassword() == null) {
            ctx.status(400).json("{\"error\":\"Missing username or password\"}");
            return;
        }

        // Check credentials. dbUser makes it clear we got this data from the db after verifying with the requestUser.
        Account dbAccount = getAccountFromDB(reqAccount.getUsername());
        if (dbAccount == null) {
            ctx.status(401).json("{\"error\":\"Invalid credentials\"}");
            return;
        }

        // Compare password (plain text for demo)
        if (!dbAccount.getPassword().equals(reqAccount.getPassword())) {
            ctx.status(401).json("{\"error\":\"Invalid credentials\"}");
            return;
        }

        HttpSession session = ctx.req().getSession(true);
        session.setAttribute("account", dbAccount);

        ctx.status(200).json("{\"message\":\"Login successful\"}");
    }

    /**
     * GET /check
     * Check if user is logged in
     */
    public static void checkLogin(Context ctx) {
        HttpSession session = ctx.req().getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            ctx.status(200).json("{\"message\":\"You are logged in\"}");
        } else {
            ctx.status(401).json("{\"error\":\"Not logged in\"}");
        }
    }

    /**
     * POST /logout
     */
    public static void logout(Context ctx) {
        HttpSession session = ctx.req().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        ctx.status(200).json("{\"message\":\"Logged out\"}");
    }

    //Checks if username already exists
    public static boolean accountExists(String username){
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

    public static boolean createAccountInDB(Account account){
        String sql = "INSERT INTO Account (username, password) VALUES (?, ?);";
        Connection connection = ConnectionUtil.getConnection();

        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, account.getUsername());
            stmt.setString(2, account.getPassword());
            int rows = stmt.executeUpdate();
            return rows > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public static Account getAccountFromDB(String username){
        String sql = "SELECT * FROM account WHERE username = ?;";
        Connection connection = ConnectionUtil.getConnection();
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                Account acc = new Account();
                acc.setAccount_id(rs.getInt("account_id"));
                acc.setUsername(rs.getString("username"));
                acc.setPassword(rs.getString("password"));
                return acc;
            }
            return null;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}

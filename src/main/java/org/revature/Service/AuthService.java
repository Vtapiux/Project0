package org.revature.Service;

import io.javalin.http.Context;
import org.revature.DAO.AuthDAO;
import org.revature.Model.Account;

public class AuthService {
    private AuthDAO authDAO;

    public AuthService(){
        this.authDAO = new AuthDAO();
    }

    public AuthService(AuthDAO authDAO){
        this.authDAO = authDAO;
    }

    public boolean checkLogin(Context ctx){
        return authDAO.checkLogin(ctx);
    }

    public void logout (Context ctx){
        authDAO.logout(ctx);
    }

    public boolean accountExists(String username){
        return authDAO.accountExists(username);
    }

    public Account createAccountInDB(Account account){
        authDAO.createAccount(account);
        return account;
    }

    public Account getAccountFromDB(String username){
        return authDAO.getAccountFromDB(username);
    }

    public Account registerUser(String username, String password, int roleId) {
        if(authDAO.getAccountFromDB(username) != null){
            return null; //username exists
        }

        Account newAcc = new Account();
        newAcc.setUsername(username);
        newAcc.setPassword(password);
        newAcc.setRoleId(roleId);

        return authDAO.createAccount(newAcc);
    }
}

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

    public void login(Context ctx){
        authDAO.login(ctx);
    }

    public boolean checkLogin(Context ctx){
        return authDAO.checkLogin(ctx);
    }

    public void register(Context ctx){
        authDAO.register(ctx);
    }

    public void logout (Context ctx){
        authDAO.logout(ctx);
    }

    public boolean accountExists(String username){
        return authDAO.accountExists(username);
    }

    public int createAccountInDB(Account account){
        return authDAO.createAccountInDB(account);
    }

    public Account getAccountFromDB(String username){
        return authDAO.getAccountFromDB(username);
    }

//    public int getRole (Context ctx){
//        return authDAO.getRole(ctx);
//    }
//
//    public int getAccountId(Context ctx){
//        return authDAO.getAccountId(ctx);
//    }
}

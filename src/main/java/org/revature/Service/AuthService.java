package org.revature.Service;

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

    public boolean accountExists(String username){
        return authDAO.accountExists(username);
    }

    public Account createAccountInDB(Account account){
        authDAO.createAccount(account);
        return account;
    }

    public Account getAccountFromDB(String username){
        return authDAO.getAccountByUsername(username);
    }

    public Account registerUser(String username, String password, int roleId) {
        if(authDAO.getAccountByUsername(username) != null){
            return null; //username exists
        }

        Account newAcc = new Account();
        newAcc.setUsername(username);
        newAcc.setPassword(password);
        newAcc.setRoleId(roleId);

        return authDAO.createAccount(newAcc);
    }

    public boolean loginAccount(String username, String password) {
        Account existingAcc = authDAO.getAccountByUsername(username);
        if(existingAcc == null){
            return false; //user not found
        }
        return existingAcc.getPassword().equals(password);
    }
}

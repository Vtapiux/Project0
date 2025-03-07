package org.revature.Controller;
import io.javalin.http.Context;
import jakarta.servlet.http.HttpSession;
import org.revature.DAO.AuthDAO;
import org.revature.Model.Account;
import org.revature.Model.Loan;
import org.revature.Model.Users;
import org.revature.Service.AuthService;
import org.revature.Service.LoanService;
import org.revature.Service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AuthController{
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private AuthService authService;
    private UsersService usersService;
    private LoanService loanService;

    public AuthController(){
        this.authService = new AuthService();
        this.usersService = new UsersService();
        this.loanService = new LoanService();
    }

    public AuthController (AuthService authService){
        this.authService = authService;
        this.usersService = new UsersService();
        this.loanService = new LoanService();
    }

    public void register(Context ctx){
        Account reqAccount = ctx.bodyAsClass(Account.class);

        //Basic validation
        if(reqAccount.getUsername() == null || reqAccount.getPassword() == null){
            ctx.status(400).json("{\"error\":\"Missing username or password\"}");
            return;
        }

        //check if user already exists
        if(authService.accountExists(reqAccount.getPassword())){
            ctx.status(409).json("{\"error\":\"Username already taken\"}");
            return;
        }

        int accountId = authService.createAccountInDB(reqAccount);
        if(accountId > 0){
            ctx.status(201).json("{\"message\":\"Account registered\", \"account_id\":" + accountId + "}");
        } else {
            ctx.status(500).json("{\"error\":\"Failed to register account\"}");
        }
    }

    public void login(Context ctx){
        Account reqAccount = ctx.bodyAsClass(Account.class);

        if (reqAccount.getUsername() == null || reqAccount.getPassword() == null) {
            ctx.status(400).json("{\"error\":\"Missing username or password\"}");
            return;
        }

        // Check credentials. dbUser makes it clear we got this data from the db after verifying with the requestUser.
        Account dbAccount = authService.getAccountFromDB(reqAccount.getUsername());

        if (dbAccount != null) {
            HttpSession session = ctx.req().getSession(true);
            session.setAttribute("account", dbAccount);
            session.setAttribute("roleId", dbAccount.getRoleId());
            session.setAttribute("accountId", dbAccount.getAccount_id());
            logger.info("User logged: {}", reqAccount.getUsername());
            ctx.status(200).json("{\"message\":\"Login successful\"}");
            return;
        }else{
            ctx.status(401).json("{\"error\":\"Invalid credentials\"}");
        }

        // Compare password (plain text for demo)
        if (!dbAccount.getPassword().equals(reqAccount.getPassword())) {
            ctx.status(401).json("{\"error\":\"Invalid credentials\"}");
            return;
        }

    }

    public void logout(Context ctx) {
        HttpSession session = ctx.req().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        ctx.status(200).json("{\"message\":\"Logged out\"}");
        authService.logout(ctx);
    }

    public boolean checkLogin(Context ctx) {
        HttpSession session = ctx.req().getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            ctx.status(200).json("{\"message\":\"You are logged in\"}");
            authService.checkLogin(ctx);
            return true;
        } else {
            ctx.status(401).json("{\"error\":\"Not logged in\"}");
            return false;
        }
    }

    public int getRole(Context ctx){
        HttpSession session = ctx.req().getSession(false);
        if(session != null && session.getAttribute("roleId") != null){
            return (int) session.getAttribute("roleId");
        }
        return -1;
    }

    public int getAccountId(Context ctx){
        HttpSession session = ctx.req().getSession(false);
        if(session != null && session.getAttribute("accountId") != null){
            return (int) session.getAttribute("accountId");
        }
        return -1;
    }

    public int getUserID(Context ctx){
        HttpSession session = ctx.req().getSession(false);
        if(session != null && session.getAttribute("accountId") != null){
           int accountId = (int) session.getAttribute("accountId");
            Users users = usersService.getUserByAccountId(accountId);
            return users.getUserId();
        }
        return -1;
    }

//    public Loan getLoanID(Context ctx){
//        HttpSession session = ctx.req().getSession(false);
//        if(session != null && session.getAttribute("loanId") != null){
//            int loanId = (int) session.getAttribute("loanId");
//            return loanService.getLoanByUserId(loanId);
//        }
//        return null;
//    }


//    public void login(Context ctx){
//        authService.login(ctx);
//    }

//    public void register(Context ctx){
//        authService.register(ctx);
//    }

//    public void logout (Context ctx){
//        authService.logout(ctx);
//    }

//    public boolean checkLogin(Context ctx){
//        return authService.checkLogin(ctx);
//    }

//    public int getRole (Context ctx){return authService.getRole(ctx);}

//    public int getAccountId(Context ctx){return authService.getAccountId(ctx);}

}
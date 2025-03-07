package org.revature.Controller;
import io.javalin.http.Context;
import jakarta.servlet.http.HttpSession;
import org.revature.DTO.AuthDTO;
import org.revature.Model.Account;
import org.revature.Model.Address;
import org.revature.Model.Users;
import org.revature.Service.AddressService;
import org.revature.Service.AuthService;
import org.revature.Service.LoanService;
import org.revature.Service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthController{
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private AuthService authService;
    private UsersService usersService;
    private LoanService loanService;
    private AddressService addressService;

    public AuthController(){
        this.authService = new AuthService();
        this.usersService = new UsersService();
        this.loanService = new LoanService();
        this.addressService = new AddressService();
    }

    public AuthController (AuthService authService){
        this.authService = authService;
        this.usersService = new UsersService();
        this.loanService = new LoanService();
        this.addressService = new AddressService();

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

        Account accountRegister = authService.registerUser(reqAccount.getUsername(), reqAccount.getPassword(), reqAccount.getRoleId());
        if(accountRegister != null){
            ctx.status(201).json("{\"message\":\"Account registered\", \"account_id\":" + accountRegister.getAccountId() + "}");
            logger.info("New account register: {}", reqAccount.getUsername());
        } else {
            ctx.status(500).json("{\"error\":\"Failed to register account\"}");
        }
    }

    public void login(Context ctx){
        Account reqAccount = ctx.bodyAsClass(Account.class);
        AuthDTO account = ctx.bodyAsClass(AuthDTO.class);

        if (account.getUsername() == null || account.getPassword() == null) {
            ctx.status(400).json("{\"error\":\"Missing username or password\"}");
            return;
        }

        // Check credentials. dbUser makes it clear we got this data from the db after verifying with the requestUser.
        Account dbAccount = authService.getAccountFromDB(reqAccount.getUsername());

        if (dbAccount != null) {
            HttpSession session = ctx.req().getSession(true);
            session.setAttribute("account", dbAccount);
            session.setAttribute("roleId", dbAccount.getRoleId());
            session.setAttribute("accountId", dbAccount.getAccountId());
            logger.info("User logged: {}, Id: {}", dbAccount.getUsername(), dbAccount.getAccountId());
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
        //Account reqAccount = ctx.bodyAsClass(Account.class);
        HttpSession session = ctx.req().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        ctx.status(200).json("{\"message\":\"Logged out\"}");
        //logger.info("{} logged out", reqAccount.getUsername());
        //authService.logout(ctx);
    }

    public boolean checkLogin(Context ctx) {
        HttpSession session = ctx.req().getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            ctx.status(200).json("{\"message\":\"You are logged in\"}");
            //authService.checkLogin(ctx);
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

    public int getUserID(Context ctx){
        HttpSession session = ctx.req().getSession(false);
        if(session != null && session.getAttribute("accountId") != null){
           int accountId = (int) session.getAttribute("accountId");
            Users users = usersService.getUserByAccountId(accountId);
            return users.getUserId();
        }
        return -1;
    }

    public int getAddressId(Context ctx){
        HttpSession session = ctx.req().getSession(false);
        if(session != null && session.getAttribute("accountId") != null){
            int accountId = (int) session.getAttribute("accountId");
            Users users = usersService.getUserByAccountId(accountId);
            return users.getAddressId();
        }
        return -1;
    }

}
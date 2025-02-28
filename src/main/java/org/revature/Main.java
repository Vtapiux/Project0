package org.revature;

import io.javalin.Javalin;
import org.revature.Controller.AuthController;
import org.revature.Controller.LoanController;
import org.revature.Controller.UsersController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.json.JavalinJackson;
import org.revature.DAO.LoanDAO;
import org.revature.DAO.UsersDAO;
import org.revature.Service.LoanService;
import org.revature.Service.UsersService;

public class Main {
    public static void main(String[] args) {
        //JSON needs to identify the dates datatype, so it needs to be initialized
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());// 🔥 Enables LocalDate parsing

        UsersDAO userDAO = new UsersDAO();
        UsersService userService = new UsersService(userDAO);
        UsersController usersController = new UsersController(userService);

        LoanDAO loanDAO = new LoanDAO();
        LoanService loanService = new LoanService(loanDAO);
        LoanController loanController = new LoanController(loanService);

        Javalin app = Javalin.create(config -> {
            //support for LocalDate through Jackson
            config.jsonMapper(new JavalinJackson());
        }).start(7070);

        app.post("/auth/register", AuthController::register);
        app.post("/auth/login", AuthController::login);
        app.get("/check", AuthController::checkLogin);
        app.post("/auth/logout", AuthController::logout);

        app.get("/users", usersController::getAllUsersHandler); //See all users
        app.post("/users", usersController::addUserHandler); //Create user
        app.put("/users/{user_id}", usersController::updateUserHandler); //Update user

        app.get("/loans", loanController::getAllLoansHandler); //see all loans
        app.post("/loans", loanController::addLoanHandler); //add user
        app.put("/loans/{loan_id}", loanController::updateLoanHandler); //update loan

    }
}
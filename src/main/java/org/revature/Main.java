package org.revature;

import io.javalin.Javalin;
import org.revature.Controller.AddressController;
import org.revature.Controller.AuthController;
import org.revature.Controller.LoanController;
import org.revature.Controller.UsersController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.json.JavalinJackson;
import org.revature.DAO.AddressDAO;
import org.revature.DAO.AuthDAO;
import org.revature.DAO.LoanDAO;
import org.revature.DAO.UsersDAO;
import org.revature.Service.AddressService;
import org.revature.Service.AuthService;
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

        AuthDAO authDAO = new AuthDAO();
        AuthService authService = new AuthService(authDAO);
        AuthController authController = new AuthController(authService);

        AddressDAO addressDAO = new AddressDAO();
        AddressService addressService = new AddressService(addressDAO);
        AddressController addressController = new AddressController(addressService);

        Javalin app = Javalin.create(config -> {
            //support for LocalDate through Jackson
            config.jsonMapper(new JavalinJackson());
        }).start(7070);

        app.post("/auth/register", authController::register);
        app.post("/auth/login", authController::login);
        app.get("/check", authController::checkLogin);
        app.post("/auth/logout", authController::logout);

        app.get("/users", usersController::getAllUsersHandler); //See all users

        app.get("/user/{userId}", usersController::getUserInfoWithIdHandler); //See specific user
        app.put("/users/{userId}", usersController::updateUserHandler); //Update user

        app.post("/loans", loanController::addLoanHandler); //create loan
        app.get("/loans", loanController::getAllLoansHandler); //see all loans
        app.get("/loan/{loanId}",loanController::getLoanInfoWithIdHandler); //See specific loan
        app.put("/loans/{loanId}", loanController::updateLoanHandler); //update loan
        app.put("/loan/{loanId}/status", loanController::updateStatusHandler); //Update loan status

        app.get("/address", addressController::getAllAddressHandler);
        app.get("/address/{addressId}", addressController::getAddressWithIdHandler);
        app.put("/address/{addressId}", addressController::updateAddressHandler);

    }
}
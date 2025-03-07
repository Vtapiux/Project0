package org.revature.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import jakarta.servlet.http.HttpSession; //User role validation
import org.revature.DAO.UsersDAO;
import org.revature.DTO.UserDTO;
import org.revature.Model.Account; //User role validation
import org.revature.Model.Users;
import org.revature.Service.UsersService;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UsersController {
    UsersService usersService;
    AuthController authController;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
        this.authController = new AuthController();
    }

    public UsersController (){
        this.usersService = new UsersService();
        this.authController = new AuthController();
    }

    public void getAllUsersHandler(Context ctx) {
        //This is extra
        HttpSession session = ctx.req().getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            ctx.status(401).json("{\"error\":\"Not logged in\"}");
            return;
        }

        Account account = (Account)session.getAttribute("account");
        if (account.getRoleId() != 1) {
            ctx.status(403).json("{\"error\":\"Forbidden: Insufficient permissions\"}");
            return;
        }

        ctx.json(usersService.getAllUsers(account));
    }


    public void addUserHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Users user = mapper.readValue(ctx.body(), Users.class);
        Users addedUser = usersService.addUser(user);
        if(addedUser==null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(addedUser));
        }
    }

    public void addUserInfoHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Users user = mapper.readValue(ctx.body(), Users.class);

        // Validate required fields
        if (user.getAccountId() == 0 || user.getFirstName() == null || user.getLastName() == null) {
            ctx.status(400).json("{\"error\":\"Missing required fields\"}");
            return;
        }

        // Use the service to add the user
        Users addedUser = usersService.addUser(user);

        // Handle the response based on the outcome
        if (addedUser == null) {
            ctx.status(500).json("{\"error\":\"Failed to add user information\"}");
        } else {
            ctx.status(201).json("{\"message\":\"User information added\", \"userId\":" + addedUser.getUserId() + "}");
        }
    }

    public void updateUserHandler(Context ctx){
        if(authController.checkLogin(ctx)){
            if(authController.getRole(ctx) == 1){ //only customer can update this
                int userId = Integer.parseInt(ctx.pathParam("userId"));
                if(authController.getUserID(ctx) == userId){ //they can only update their own info
                    UserDTO request = ctx.bodyAsClass(UserDTO.class);

                    Users user = new Users();
                    user.setUserId(userId);
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setEmail(request.getEmail());
                    user.setPhoneNumber(request.getPhoneNumber());

                    usersService.updateUser(user);
                    ctx.status(200).json("{\"message\":\"User updated\"}");
                }else { //check same user else
                    ctx.status(403).json("{\"error\":\"You do not have access to this user.\"}");
                }
            }else { // check role else -> manager cant modify this information
                ctx.status(403).json("{\"error\":\"You do not have permission to perform this action.\"}");
            }
        } else { //check login else
            ctx.status(401).json("{\"error\":\"Not logged in\"}");
        }

    }

    public void getUserInfoWithIdHandler(Context ctx){
        if(authController.checkLogin(ctx)){
            int userId = Integer.parseInt(ctx.pathParam("userId"));
            if(authController.getRole(ctx) == 1) {
                if(authController.getUserID(ctx) == userId){
                    Users user = usersService.getUserInfoWithId(userId);
                    if(user == null){
                        ctx.status(404).json("{\"message\":\"User not found\"}");
                    } else {
                        ctx.json(user);
                    }
                } else { // compare userId's -> User is different to session
                    ctx.status(403).json("{\"error\":\"You do not have permission to see this user.\"}");
                }
            } else{ //get role else -> is manager
                Users user = usersService.getUserInfoWithId(userId);
                if(user == null){
                    ctx.status(404).json("{\"message\":\"User not found\"}");
                } else {
                    ctx.json(user);
                }
            }
        } else { //check login else
            ctx.status(401).json("{\"error\":\"Not logged in\"}");
        }

    }
}

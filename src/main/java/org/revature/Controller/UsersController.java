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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class UsersController {
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

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
        if(authController.checkLogin(ctx)){
            if(authController.getRole(ctx) == 2){//Is Manager
                ctx.json(usersService.getAllUsers());
            }else{
                ctx.status(403).json("{\"error\":\"You do not have permission to perform this action.\"}");
            }
        }else { //check login else
            ctx.status(401).json("{\"error\":\"Not logged in\"}");
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
                    logger.info("User {} - {} updated ", authController.getUserID(ctx), request.getFirstName());
                }else { //check same user else
                    ctx.status(403).json("{\"error\":\"You do not have access to this user.\"}");
                }
            }else { // check role else -> manager can modify any user info
                int userId = Integer.parseInt(ctx.pathParam("userId"));
                UserDTO request = ctx.bodyAsClass(UserDTO.class);

                Users user = new Users();
                user.setUserId(userId);
                user.setFirstName(request.getFirstName());
                user.setLastName(request.getLastName());
                user.setEmail(request.getEmail());
                user.setPhoneNumber(request.getPhoneNumber());

                usersService.updateUser(user);
                ctx.status(200).json("{\"message\":\"User updated\"}");
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

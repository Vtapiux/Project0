package org.revature.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.revature.DTO.UserDTO;
import org.revature.Model.Users;
import org.revature.Service.UsersService;
import java.time.format.DateTimeFormatter;

public class UsersController {
    UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    public void getAllUsersHandler (Context ctx){
        ctx.json(usersService.getAllUsers());
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

    public void updateUserHandler(Context ctx){
        int userId = Integer.parseInt(ctx.pathParam("user_id"));
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

    public void getUserInfoWithIdHandler(Context ctx){
        int userId = Integer.parseInt(ctx.pathParam("user_id"));
        Users user = usersService.getUserInfoWithId(userId);
        if(user == null){
            ctx.status(404).json("{\"message\":\"User not found\"}");
        } else {
            ctx.json(user);
        }
    }
}

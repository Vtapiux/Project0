package org.revature.Controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import jakarta.servlet.http.HttpSession;
import org.revature.Model.Address;
import org.revature.Service.AddressService;
import org.revature.Service.UsersService;


public class AddressController {
    private AddressService addressService;
    private AuthController authController;
    private UsersService usersService;

    public AddressController(AddressService addressService){
        this.addressService = addressService;
        this.authController = new AuthController();
        this.usersService = new UsersService();
    }

    public void addAddressHandler (Context ctx) throws JsonProcessingException{
        if(authController.checkLogin(ctx)){
            if(authController.getRole(ctx) == 1) {//Only customers can add addresses
                ObjectMapper mapper = new ObjectMapper();
                Address address = mapper.readValue(ctx.body(), Address.class);
                Address addedAddress = addressService.insertAddress(address);
                int userId = authController.getUserID(ctx);
                usersService.updateAddressId(addedAddress.getAddressId(), userId);
                if(addedAddress == null){
                    ctx.status(400);
                }else{
                    ctx.json(mapper.writeValueAsString(addedAddress));
                }
            }else{
                ctx.status(403).json("{\"error\":\"You do not have permission to perform this action.\"}");
            }
        }else{
            ctx.status(401).json("{\"error\":\"Not logged in\"}");
        }
    }
}

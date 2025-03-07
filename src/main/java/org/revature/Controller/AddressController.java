package org.revature.Controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import jakarta.servlet.http.HttpSession;
import org.jetbrains.annotations.NotNull;
import org.revature.DTO.AddressDTO;
import org.revature.Model.Address;
import org.revature.Model.Loan;
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

    public void updateAddressHandler (Context ctx) throws JsonProcessingException{
        if(authController.checkLogin(ctx)){
            int addressId = Integer.parseInt(ctx.pathParam("addressId"));
            Address addressUpd = addressService.getAddressWithId(addressId);
            if(addressUpd.getAddressId() == authController.getAddressId(ctx)){ //They match
                AddressDTO req = ctx.bodyAsClass(AddressDTO.class);
                Address address = new Address();
                address.setAddressId(addressId);
                address.setCountry(req.getCountry());
                address.setState(req.getState());
                address.setCity(req.getCity());
                address.setStreet(req.getStreet());
                address.setStreetNum(req.getStreetNum());
                address.setZip(req.getZip());

                addressService.updateAddress(address);
                ctx.status(200).json("{\"message\":\"Address updated\"}");
            }else{
                ctx.status(404).json("{\"message\":\"You do not own this address.\"}");
            }
        }else{
            ctx.status(401).json("{\"error\":\"Not logged in\"}");
        }
    }

    public void getAddressWithIdHandler (Context ctx){
        if(authController.checkLogin(ctx)){
            int addressId = Integer.parseInt(ctx.pathParam("addressId"));
            Address address = addressService.getAddressWithId(addressId);
            if(address.getAddressId() == authController.getAddressId(ctx)){
                ctx.json(address);
            } else{
                ctx.status(404).json("{\"message\":\"You do not own this address.\"}");
            }
        }else{
            ctx.status(401).json("{\"error\":\"Not logged in\"}");
        }
    }

    public void getAllAddressHandler( Context ctx) {
        if(authController.checkLogin(ctx)){
            if(authController.getRole(ctx) == 2){ //Is manager
                ctx.json(addressService.getAllAddress());
            }else {
                ctx.status(403).json("{\"error\":\"You do not have permission to perform this action.\"}");
            }
        }else{
            ctx.status(401).json("{\"error\":\"Not logged in\"}");
        }
    }
}

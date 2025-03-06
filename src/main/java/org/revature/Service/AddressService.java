package org.revature.Service;

import org.revature.DAO.AddressDAO;
import org.revature.Model.Address;

public class AddressService {
    private AddressDAO addressDAO;

    public AddressService(){
        addressDAO = new AddressDAO();
    }
    public AddressService(AddressDAO addressDAO){
        this.addressDAO = addressDAO;
    }

    public Address insertAddress(Address newAddress){
        return addressDAO.insertAddress(newAddress);
    }



}

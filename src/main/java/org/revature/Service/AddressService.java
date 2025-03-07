package org.revature.Service;

import org.revature.DAO.AddressDAO;
import org.revature.Model.Address;
import java.util.List;

public class AddressService {
    private AddressDAO addressDAO;

    public AddressService(){
        addressDAO = new AddressDAO();
    }
    public AddressService(AddressDAO addressDAO){
        this.addressDAO = addressDAO;
    }

    public void updateAddress(Address newAddress){
        addressDAO.updateAddress(newAddress);
    }

    public Address getAddressWithId(int addressId){
        return addressDAO.getAddressWithId(addressId);
    }

    public List<Address> getAllAddress() {
        return addressDAO.getAllAddress();
    }
}

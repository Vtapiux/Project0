package org.revature.Service;

import org.revature.DAO.UsersDAO;
import org.revature.Model.Account;
import org.revature.Model.Users;

import java.util.List;
import java.time.LocalDate;

public class UsersService {
    UsersDAO usersDAO;

    public UsersService(){ usersDAO = new UsersDAO(); }

    public UsersService( UsersDAO usersDAO ){ this.usersDAO = usersDAO; }

    public Users addUser(Users user){
        return usersDAO.insertUser(user);
    }

    public List<Users> getAllUsers(){
        return usersDAO.getAllUsers();
    }

    public void updateUser(Users user){
        usersDAO.updateUser(user);
    }

    public Users getUserInfoWithId(int user_id){return usersDAO.getUserInfoWithId(user_id);}

    public Users getUserByAccountId(int accountId) {
        return usersDAO.getUserByAccountId(accountId);
    }

    public void updateAddressId(int addressId, int userId) {
        usersDAO.updateAddressIdInUsers(addressId, userId);
    }
}

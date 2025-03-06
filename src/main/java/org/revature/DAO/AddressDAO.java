package org.revature.DAO;

import org.revature.Model.Address;
import org.revature.Util.ConnectionUtil;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.*;

public class AddressDAO {
    private UsersDAO usersDAO;
    public Address insertAddress(Address address){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO ADDRESS (country, state, city, street, street_num, zip) VALUES (?,?,?,?,?,?);";
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, address.getCountry());
            stmt.setString(2, address.getState());
            stmt.setString(3, address.getCity());
            stmt.setString(4, address.getStreet());
            stmt.setInt(5, address.getStreetNum());
            stmt.setString(6, address.getZip());

            stmt.executeUpdate();

            try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
                if(generatedKeys.next()){
                    int newId = generatedKeys.getInt(1);
                    address.setAddressId(newId);
                    //usersDAO.updateAddressIdInUsers(newId);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return address;
    }




}

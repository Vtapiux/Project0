package org.revature.DAO;

import org.revature.Model.Address;
import org.revature.Util.ConnectionUtil;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.*;

public class AddressDAO {
    public void updateAddress(Address address){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "UPDATE Address SET country = ?, state = ?, city = ?, street = ?, street_num = ?, zip = ? WHERE address_id = ?;";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, address.getCountry());
            stmt.setString(2, address.getState());
            stmt.setString(3, address.getCity());
            stmt.setString(4, address.getStreet());
            stmt.setInt(5, address.getStreetNum());
            stmt.setString(6, address.getZip());
            stmt.setInt(7, address.getAddressId());
            stmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Address getAddressWithId(int addressId) {
        Connection connection = ConnectionUtil.getConnection();
        Address address = null;
        try{
           String sql = "SELECT * FROM Address WHERE address_id = ?;";
           PreparedStatement stmt = connection.prepareStatement(sql);
           stmt.setInt(1, addressId);

           ResultSet rs = stmt.executeQuery();
           if(rs.next()){
               address = new Address(rs.getInt("address_id"),
                       rs.getString("country"),
                       rs.getString("state"),
                       rs.getString("city"),
                       rs.getString("street"),
                       rs.getInt("street_num"),
                       rs.getString("zip"));
           }
        }catch( SQLException e){
            e.printStackTrace();
        }
        return address;
    }

    public List<Address> getAllAddress() {
        Connection connection = ConnectionUtil.getConnection();
        List<Address> addressList = new ArrayList<>();
        try{
            String sql = "Select * from Address ORDER BY address_id ASC;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Address address = new Address(rs.getInt("address_id"),
                        rs.getString("country"),
                        rs.getString("state"),
                        rs.getString("city"),
                        rs.getString("street"),
                        rs.getInt("street_num"),
                        rs.getString("zip"));
                addressList.add(address);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return addressList;
    }

}

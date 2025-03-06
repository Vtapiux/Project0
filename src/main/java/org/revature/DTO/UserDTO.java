package org.revature.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class UserDTO {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private int addressId;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
//    private LocalDate createdAt;

    //Getters and Setters
    public int getUserId() {return userId;}
    public void setUserId(int userId) {this.userId = userId;}

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
    //    public LocalDate getCreatedAt() {return createdAt;}
//    public void setCreatedAt(LocalDate createdAt) {this.createdAt = createdAt;}
}

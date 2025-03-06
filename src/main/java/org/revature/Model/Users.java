package org.revature.Model;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Date;

public class Users {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
    private int addressId;
    private int accountId;

    public Users (){ }

    public Users(int userId, String firstName, String lastName, String email, String phoneNumber, int addressId, int accountId) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.addressId = addressId;
        this.accountId = accountId;
    }

//    public Users(int userID, String firstName, String lastName, String email, String phoneNumber) {
//        this.userId = userID;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//    }
//
//    public Users(String firstName, String lastName, String email, String phoneNumber) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.phoneNumber = phoneNumber;
//    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public int getAddressId() {
        return addressId;
    }
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getAccountId() {return accountId;}
    public void setAccountId(int accountId) {this.accountId = accountId;}
}

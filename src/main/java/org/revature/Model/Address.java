package org.revature.Model;

public class Address {
    private int addressId;
    private String country;
    private String state;
    private String city;
    private String street;
    private int streetNum;
    private String zip;

    public Address(){    }

    public Address(int addressId, String country, String state, String city, String street, int streetNum, String zip) {
        this.addressId = addressId;
        this.country = country;
        this.state = state;
        this.city = city;
        this.street = street;
        this.streetNum = streetNum;
        this.zip = zip;
    }

    public int getAddressId() {return addressId;}
    public void setAddressId(int addressId) {this.addressId = addressId;}

    public String getCountry() {return country;}
    public void setCountry(String country) {this.country = country;}

    public String getState() {return state;}
    public void setState(String state) {this.state = state;}

    public String getCity() {return city;}
    public void setCity(String city) {this.city = city;}

    public String getStreet() {return street;}
    public void setStreet(String street) {this.street = street;}

    public int getStreetNum() {return streetNum;}
    public void setStreetNum(int streetNum) {this.streetNum = streetNum;}

    public String getZip() {return zip;}
    public void setZip(String zip) {this.zip = zip;}
}

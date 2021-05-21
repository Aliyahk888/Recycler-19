package com.example.recycler_19;

public class Organizations {

    public String orgName;
    public String orgEmail;
    public String orgPhone;
    public String orgLocation;
    public String orgAbout;
    public String orgRecycleType;
    public String connectedUsers;
    public String covidFlag;
    public String deliveryFlag;

    public Organizations() {
    }

    public Organizations(String name, String email, String phone, String location, String about , String type, String connects,String cFlag, String dFlag) {
        this.orgName = name;
        this.orgEmail = email;
        this.orgPhone = phone;
        this.orgLocation = location;
        this.orgAbout = about;
        this.orgRecycleType = type;
        this.connectedUsers = connects;
        this.covidFlag = cFlag;
        this.deliveryFlag = dFlag;
    }

    public String getName(){
        return this.orgName;
    }

    public String getOrgRecycleType(){
        return this.orgRecycleType;
    }

    public String getCovidFlag(){
        return this.covidFlag;
    }

    public String getDeliveryFlag(){
        return this.deliveryFlag;
    }
}

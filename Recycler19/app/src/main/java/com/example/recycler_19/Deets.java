package com.example.recycler_19;

public class Deets {

    public String fullName;
    public String email;
    public String phone;
    public String connected;


    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Deets() {
    }

    public Deets(String fullName, String email, String phone, String connected) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.connected=connected;
    }

    public String getUserName(){
        return this.fullName;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPhone(){
        return this.phone;
    }
}

package com.example.recycler_19;

public class Deets {

    public String fullName;
    public String email;
    public String phone;
    public int recyclePoints;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Deets() {
    }

    public Deets(String fullName, String email, String phone, int recyclePoints) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.recyclePoints = recyclePoints;
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

    public int getRecyclePoints(){
        return this.recyclePoints;
    }
}

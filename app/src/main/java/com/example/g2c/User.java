package com.example.g2c;

import com.google.firebase.database.IgnoreExtraProperties;

//public class MyListData {
//        private String description;
//    public  MyListData(String description){
//        this.description = description;
//    }
//    public  String getDescription(){
//        return description;
//    }
//    public  void setDescription(String description){
//        this.description =  description;
//    }
//}
@IgnoreExtraProperties
public class User {

    public String username;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username) {
        this.username = username;

    }

}
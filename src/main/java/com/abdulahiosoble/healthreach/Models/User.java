package com.abdulahiosoble.healthreach.Models;

/**
 * Created by abdulahiosoble on 8/10/17.
 */

public class User {

    public static final String TAG = User.class.getSimpleName();

    private String username;
    private String password;
    private String email;
    private String name;
    private String number;


    //Constructor Setter
    public User(String username, String password, String email, String name, String number) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.number = number;
    }

    //Default Constructor
    public User(){}

    //Getters
    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getEmail(){
        return email;
    }

    public String getName(){
        return name;
    }

    public String getNumber(){
        return number;
    }


}

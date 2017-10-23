package com.abdulahiosoble.healthreach.Models;


public class Doctor {

    public static final String TAG = Doctor.class.getSimpleName();

    private String name;
    private String email;
    private String number;
    private String affiliation;
    private String education;
    private String department;
    private String specialty;
    private String experience;
    private String image;
    private String id;

    //Constructor Setter
    public Doctor(String name, String email, String number, String affiliation, String education, String specialty, String experience, String department, String image, String id) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.affiliation = affiliation;
        this.education = education;
        this.specialty = specialty;
        this.department = department;
        this.experience = experience;
        this.image = image;
        this.id = id;
    }

    public void setR(String name, String specialty, String affiliation){
        this.name = name;
        this.specialty = specialty;
        this.affiliation = affiliation;
    }

    //Default Constructor
    public Doctor(){}

    //Getters
    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getNumber(){
        return number;
    }

    public String getAffiliation(){
        return affiliation;
    }

    public String getSpecialty(){
        return specialty;
    }

    public String getExperience(){
        return experience;
    }

    public String getDepartment() {
        return department;
    }

    public String getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

    public String getEducation() {
        return education;
    }

}

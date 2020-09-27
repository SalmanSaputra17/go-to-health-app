package com.example.hp.gotohealth.Model;

public class User {
    private int id;
    private String username;
    private String email;
    private String gender;
    private String dateOfBirth;
    private String createdAt;
    private String updatedAt;

    /*
     * CONSTRUCTOR
     */
    public User() {
    }

    public User(int id, String username, String email, String gender, String dateOfBirth, String createdAt, String updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /*
    * GETTER
    */

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}

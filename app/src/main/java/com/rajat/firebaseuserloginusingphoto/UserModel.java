package com.rajat.firebaseuserloginusingphoto;

public class UserModel {
    String FirstName;
    String LastName;
    String jobrole;
    String pimage;
    String username;
    String userpassword;

    public UserModel() {
    }

    public UserModel(String firstName, String lastName, String jobrole, String pimage, String username, String userpassword) {
        FirstName = firstName;
        LastName = lastName;
        this.jobrole = jobrole;
        this.pimage = pimage;
        this.username = username;
        this.userpassword = userpassword;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getJobrole() {
        return jobrole;
    }

    public void setJobrole(String jobrole) {
        this.jobrole = jobrole;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }
}

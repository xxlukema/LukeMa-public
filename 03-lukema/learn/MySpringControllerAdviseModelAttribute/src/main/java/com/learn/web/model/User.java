package com.learn.web.model;


import javax.validation.constraints.Size;


public class User {

    @Size(min = 5, max = 35)
    private String name;
    private String password;
    private String email;

    private String dob;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return String.format("User [name=%s, password=%s, email=%s, dob=%s]", name, password, email, dob);
    }
}

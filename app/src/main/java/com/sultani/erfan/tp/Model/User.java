package com.sultani.erfan.tp.Model;

/**
 * Created by Sultani on 10/16/2017.
 */

public class User {


    private String Name;
    private String Password;
    private String Phone;
    private String IsStaff;
    private String secureCode;

    public User(){

    }

    public User(String name, String password,String sCode) {
        Name = name;
        Password = password;
        IsStaff = "false";
        secureCode = sCode;


    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getIsStaff() {
        return IsStaff;
    }

    public void setIsStaff(String isStaff) {
        IsStaff = isStaff;
    }

    public String getSecureCode() {
        return secureCode;
    }

    public void setSecureCode(String secureCode) {
        this.secureCode = secureCode;
    }
}

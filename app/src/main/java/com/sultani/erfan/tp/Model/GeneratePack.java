package com.sultani.erfan.tp.Model;

/**
 * Created by Erfan on 12/15/2017.
 */

public class GeneratePack {

    private String days,price,name,image,payment,phone;

    public GeneratePack() {
    }

    public GeneratePack(String days, String price, String name, String image, String payment, String phone) {
        this.days = days;
        this.price = price;
        this.name = name;
        this.image = image;
        this.payment = payment;
        this.phone = phone;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

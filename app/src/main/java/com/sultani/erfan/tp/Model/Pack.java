package com.sultani.erfan.tp.Model;

/**
 * Created by Sultani on 10/18/2017.
 */

public class Pack {

    private String name,image,description,discount,menuId,price,days,tourpoints;
    private String foods,place,transport;


    public Pack() {
    }

    public Pack(String name, String image, String description, String discount, String menuId, String price, String days, String tourpoints, String foods, String place, String transport) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.discount = discount;
        this.menuId = menuId;
        this.price = price;
        this.days = days;
        this.tourpoints = tourpoints;
        this.foods = foods;
        this.place = place;
        this.transport = transport;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTourpoints() {
        return tourpoints;
    }

    public void setTourpoints(String tourpoints) {
        this.tourpoints = tourpoints;
    }

    public String getFoods() {
        return foods;
    }

    public void setFoods(String foods) {
        this.foods = foods;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }
}

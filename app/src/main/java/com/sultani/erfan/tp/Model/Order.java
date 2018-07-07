package com.sultani.erfan.tp.Model;

/**
 * Created by Sultani on 11/20/2017.
 */

public class Order {
    private String PackageID;
    private String PackageName;
    private String Quantity;
    private String Price;
    private String Discount;
    private String Days;

    public Order() {
    }

    public Order(String packageID, String packageName, String quantity, String price, String discount, String days) {
        PackageID = packageID;
        PackageName = packageName;
        Quantity = quantity;
        Price = price;
        Discount = discount;
        Days = days;
    }

    public String getPackageID() {
        return PackageID;
    }

    public void setPackageID(String packageID) {
        PackageID = packageID;
    }

    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String packageName) {
        PackageName = packageName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getDays() {
        return Days;
    }

    public void setDays(String days) {
        Days = days;
    }
}

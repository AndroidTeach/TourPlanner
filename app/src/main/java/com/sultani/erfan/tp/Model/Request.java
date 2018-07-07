package com.sultani.erfan.tp.Model;

import java.util.List;

/**
 * Created by Sultani on 11/23/2017.
 */

public class Request {

    private String phone;
    private String name;
    private String creditNum;

    private String total;
    private String status;
    private List<Order> pack;  // list of order packages

    public Request() {

    }


    public Request(String phone, String name, String creditNum, String total, List<Order> pack) {
        this.phone = phone;
        this.name = name;
        this.creditNum = creditNum;

        this.total = total;
        this.status = "0";
        this.pack = pack;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreditNum() {
        return creditNum;
    }

    public void setCreditNum(String creditNum) {
        this.creditNum = creditNum;
    }



    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Order> getPack() {
        return pack;
    }

    public void setPack(List<Order> pack) {
        this.pack = pack;
    }
}
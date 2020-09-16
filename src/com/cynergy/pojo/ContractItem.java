package com.cynergy.pojo;


import lombok.Data;

import java.util.List;

public class ContractItem {
    private String id;
    private  String  purno;
    private String itemchn;
    private String amount;
    private String itemId;
    private String proid;
    private String refundAmount;
    private String quantity;
    private String weight;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPurno() {
        return purno;
    }

    public void setPurno(String purno) {
        this.purno = purno;
    }

    public String getItemchn() {
        return itemchn;
    }

    public void setItemchn(String itemchn) {
        this.itemchn = itemchn;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}

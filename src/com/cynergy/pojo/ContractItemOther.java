package com.cynergy.pojo;

public class ContractItemOther {
    private int id;
    private int itemid;
    private String contractNo;
    private String factoryName;
    private String declareAmount;
    private String declareQuantity;

    public String getContractNo() {
        return contractNo;
    }

    public ContractItemOther setContractNo(String contractNo) {
        this.contractNo = contractNo;
        return this;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public ContractItemOther setFactoryName(String factoryName) {
        this.factoryName = factoryName;return this;
    }

    public String getDeclareAmount() {
        return declareAmount;
    }

    public ContractItemOther setDeclareAmount(String declareAmount) {
        this.declareAmount = declareAmount;
        return this;
    }

    public String getDeclareQuantity() {
        return declareQuantity;
    }

    public ContractItemOther setDeclareQuantity(String declareQuantity) {
        this.declareQuantity = declareQuantity;
        return this;
    }

    public int getId() {
        return id;
    }

    public ContractItemOther setId(int id) {
        this.id = id;
        return this;
    }

    public int getItemid() {
        return itemid;
    }

    public ContractItemOther setItemid(int itemid) {
        this.itemid = itemid;
        return this;
    }
}

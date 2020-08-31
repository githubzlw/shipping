package com.cynergy.pojo;

public class ContractFundWrap {

    /**
     * 合同号
     */
    private String contractno;
    /**
     * 合同金额
     */
    private String friMoney;
    /**
     * 报关金额
     */
    private String totalPrice;


    /**
     * 货币
     */
    private String currency;


    /**
     * 时间
     */
    private String dateTime;


    private int flag;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getContractno() {
        return contractno;
    }

    public void setContractno(String contractno) {
        this.contractno = contractno;
    }

    public String getFriMoney() {
        return friMoney;
    }

    public void setFriMoney(String friMoney) {
        this.friMoney = friMoney;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}

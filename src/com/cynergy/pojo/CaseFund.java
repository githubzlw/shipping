package com.cynergy.pojo;

public class CaseFund {
    private String caseno;
    /**
     * 合同金额
     */
    private String  orderActualMoney;
    /**
     * 实际紧张金额
     */
    private String orderAmountReceived;

    public String getCaseno() {
        return caseno;
    }

    public void setCaseno(String caseno) {
        this.caseno = caseno;
    }

    public String getOrderActualMoney() {
        return orderActualMoney;
    }

    public void setOrderActualMoney(String orderActualMoney) {
        this.orderActualMoney = orderActualMoney;
    }

    public String getOrderAmountReceived() {
        return orderAmountReceived;
    }

    public void setOrderAmountReceived(String orderAmountReceived) {
        this.orderAmountReceived = orderAmountReceived;
    }
}

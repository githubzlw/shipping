package com.cynergy.main;

import java.io.Serializable;

public class ReadExcelVO implements Serializable {

	
	/**
	 * 报价单解析对象
	 * @fieldName serialVersionUID
	 * @fieldType long
	 * @Description 
	 */
	private static final long serialVersionUID = 1L;
	private String purno;         //合同号
	private String itemchn;       //产品名
	private Double amount;        //金额
	private int itemId;           //产品表id
	private int proId;            //出运单id
	private Double refundAmount;  //退税金额
	private int quantity;         //数量
	private int weight;           //重量
	
	
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Double getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}
	public int getProId() {
		return proId;
	}
	public void setProId(int proId) {
		this.proId = proId;
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
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	@Override
	public String toString() {
		return "ReadExcelVO [purno=" + purno + ", itemchn=" + itemchn
				+ ", amount=" + amount + ", itemId=" + itemId + ", proId="
				+ proId + ", refundAmount=" + refundAmount + ", quantity="
				+ quantity + ", weight=" + weight + "]";
	}
	

	
	
	
	
	
}

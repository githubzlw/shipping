package com.cynergy.main;

public class ContractVO {

	private String purno;       //合同号
	private String factory;     //工厂名
	private String amount;      //采购总金额
	private int quantity;       //数量
	
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getPurno() {
		return purno;
	}
	public void setPurno(String purno) {
		this.purno = purno;
	}
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "ContractVO [purno=" + purno + ", factory=" + factory
				+ ", amount=" + amount + ", quantity=" + quantity + "]";
	}

	
	
	
	
	
	
	
	
}

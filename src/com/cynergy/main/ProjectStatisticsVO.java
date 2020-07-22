package com.cynergy.main;

public class ProjectStatisticsVO {

	private String month;       //月份
	private String sales;       //销售
	private int id;             //出口id
	private String totalAmount; //采购总金额
	private String itemchn;     //产品名称
	private String projectNo;   //项目号
	private String truePrice;   //报关总金额
	private String hscode;      //海关编码
	private String rate;        //退税率
	private String refundAmount; //预计退税金额
	private String exportDate;   //出口日期
	private String factoryName;  //工厂名
	private int contractId;      //合同表id
	private String unit;         //单位
	private int quantity;     //数量
	
	
	
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getContractId() {
		return contractId;
	}
	public void setContractId(int contractId) {
		this.contractId = contractId;
	}
	public String getFactoryName() {
		return factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	public String getExportDate() {
		return exportDate;
	}
	public void setExportDate(String exportDate) {
		this.exportDate = exportDate;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getItemchn() {
		return itemchn;
	}
	public void setItemchn(String itemchn) {
		this.itemchn = itemchn;
	}
	public String getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	public String getTruePrice() {
		return truePrice;
	}
	public void setTruePrice(String truePrice) {
		this.truePrice = truePrice;
	}
	public String getHscode() {
		return hscode;
	}
	public void setHscode(String hscode) {
		this.hscode = hscode;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	@Override
	public String toString() {
		return "ProjectStatisticsVO [month=" + month + ", sales=" + sales
				+ ", id=" + id + ", totalAmount=" + totalAmount + ", itemchn="
				+ itemchn + ", projectNo=" + projectNo + ", truePrice="
				+ truePrice + ", hscode=" + hscode + ", rate=" + rate
				+ ", refundAmount=" + refundAmount + ", exportDate="
				+ exportDate + ", factoryName=" + factoryName + ", contractId="
				+ contractId + ", unit=" + unit + ", quantity=" + quantity
				+ "]";
	}
	
	
	
	
	
}

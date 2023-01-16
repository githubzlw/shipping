package com.cynergy.main;

public class BankArrival {

	//认领表2id
	private int rId;

	//到账表2id
	private int aId;

	// 出运号
	private int proId;

	// 出运品名
	private String itemchn;

	public String getItemchn() {
		return itemchn;
	}

	public void setItemchn(String itemchn) {
		this.itemchn = itemchn;
	}

	public int getProId() {
		return proId;
	}

	public void setProId(int proId) {
		this.proId = proId;
	}

	public int getaId() {
		return aId;
	}

	public void setaId(int aId) {
		this.aId = aId;
	}

	public int getrId() {
		return rId;
	}

	public void setrId(int rId) {
		this.rId = rId;
	}

	//表2关联id
	private int amountClaimFormId;
	//是否配对
	private String paired;
	//银行交易序号
	private String transactionReferenceNumber;
	//分配的预计到账
	private int iimoney;
	//分配的实际到账
	private int ifmoney;

	//erp客户id
	private int erpCustomerId;
	//金蝶id
	private int kingdeeId;
	//发票号
	private String invoice;
	//项目号
	private String caseNo;

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}

	//货比类型
	private String tradeCurrency;

	//亟待配对
	private String needPaired;

	public String getNeedPaired() {
		return needPaired;
	}

	public void setNeedPaired(String needPaired) {
		this.needPaired = needPaired;
	}

	public String getTradeCurrency() {
		return tradeCurrency;
	}

	public void setTradeCurrency(String tradeCurrency) {
		this.tradeCurrency = tradeCurrency;
	}

	public int getKingdeeId() {
		return kingdeeId;
	}

	public void setKingdeeId(int kingdeeId) {
		this.kingdeeId = kingdeeId;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	//到账总额
	private int tradeAmount;
	//总应收
	private int totalReceipts;

	//实际销售额（清关金额）
	private int unitPriceall;
	//实际对外发票金额（报关金额）
	private int truePrice;

	public int getUnitPriceall() {
		return unitPriceall;
	}

	public void setUnitPriceall(int unitPriceall) {
		this.unitPriceall = unitPriceall;
	}

	public int getTruePrice() {
		return truePrice;
	}

	public void setTruePrice(int truePrice) {
		this.truePrice = truePrice;
	}

	public int getTotalReceipts() {
		return totalReceipts;
	}

	public void setTotalReceipts(int totalReceipts) {
		this.totalReceipts = totalReceipts;
	}

	//项目号
	private String itemNo;
	//表4 报关金额
	private int declarationAmountF;
	// 表4申报发票
	private int generalDeclarationInvoicF;
	// 已配对金额
	private int pairedAmount;

	// 报关金额与已配对金额差额
	private int chaE;

	public int getChaE() {
		return chaE;
	}

	public void setChaE(int chaE) {
		this.chaE = chaE;
	}

	// 本次配对金额
	private int pairedLcamount;

	// 本次财务配对金额
	private int pairedSsamount;

	// 应收金额
	private int pairedYs;

	// 到账金额大于跟单填写的产品总价标识
	private int bsFlag;

	//是否带票 0：正常，1：带票
    private int isExtraInvoice;

    public int getIsExtraInvoice() {
        return isExtraInvoice;
    }

    public void setIsExtraInvoice(int isExtraInvoice) {
        this.isExtraInvoice = isExtraInvoice;
    }

    public int getBsFlag() {
		return bsFlag;
	}

	public void setBsFlag(int bsFlag) {
		this.bsFlag = bsFlag;
	}

	public int getPairedYs() {
		return pairedYs;
	}

	public void setPairedYs(int pairedYs) {
		this.pairedYs = pairedYs;
	}

	public int getPairedSsamount() {
		return pairedSsamount;
	}

	public void setPairedSsamount(int pairedSsamount) {
		this.pairedSsamount = pairedSsamount;
	}

	public int getPairedLcamount() {
		return pairedLcamount;
	}

	public void setPairedLcamount(int pairedLcamount) {
		this.pairedLcamount = pairedLcamount;
	}

	public int getPairedAmount() {
		return pairedAmount;
	}

	public void setPairedAmount(int pairedAmount) {
		this.pairedAmount = pairedAmount;
	}

	//到账日期
	private String transactionDate;
	//认领日期
	private String financialConfirmationTime;

	private String payersName;

	public String getPayersName() {
		return payersName;
	}

	public void setPayersName(String payersName) {
		this.payersName = payersName;
	}

	public String getFinancialConfirmationTime() {
		return financialConfirmationTime;
	}

	public void setFinancialConfirmationTime(String financialConfirmationTime) {
		this.financialConfirmationTime = financialConfirmationTime;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public int getGeneralDeclarationInvoicF() {
		return generalDeclarationInvoicF;
	}
	public void setGeneralDeclarationInvoicF(int generalDeclarationInvoicF) {
		this.generalDeclarationInvoicF = generalDeclarationInvoicF;
	}
	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public int getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(int tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public int getDeclarationAmountF() {
		return declarationAmountF;
	}

	public void setDeclarationAmountF(int declarationAmountF) {
		this.declarationAmountF = declarationAmountF;
	}

	public int getErpCustomerId() {
		return erpCustomerId;
	}

	public void setErpCustomerId(int erpCustomerId) {
		this.erpCustomerId = erpCustomerId;
	}

	public int getAmountClaimFormId() {
		return amountClaimFormId;
	}

	public void setAmountClaimFormId(int amountClaimFormId) {
		this.amountClaimFormId = amountClaimFormId;
	}

	public String getPaired() {
		return paired;
	}

	public void setPaired(String paired) {
		this.paired = paired;
	}

	public String getTransactionReferenceNumber() {
		return transactionReferenceNumber;
	}

	public void setTransactionReferenceNumber(String transactionReferenceNumber) {
		this.transactionReferenceNumber = transactionReferenceNumber;
	}

	public int getIfmoney() {
		return ifmoney;
	}

	public void setIfmoney(int ifmoney) {
		this.ifmoney = ifmoney;
	}

	public int getIimoney() {
		return iimoney;
	}

	public void setIimoney(int iimoney) {
		this.iimoney = iimoney;
	}
}

package com.cynergy.server;
/**
 * 汇总
 */

import com.cynergy.main.BankArrival;
import com.cynergy.main.DBHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShipmentPaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
//		String year = request.getParameter("select1");
//		String month = request.getParameter("select2");
//		String ymString=year+"-"+month;
//		String ymString=year+"-"+month+"-01";
		Connection connection = DBHelper.getConnection();
		Connection connectionErp = DBHelper.getConnectionERP();
		try {
			Statement createStatement1 = connection.createStatement();
			Statement createStatement2 = connection.createStatement();

			Statement createStatementErp1 = connectionErp.createStatement();
			Statement createStatementErp2 = connectionErp.createStatement();

			String sql1="select b.agent_status,b.agent,a.shipment_no,a.item_no,a.erp_customer_id,a.actual_sales,a.actual_invoice_amount   from shipment_wait_pair a  " +
					"left join shipment_object b on a.erp_customer_id=b.erp_customer_id  ";
			ResultSet res1 = createStatement1.executeQuery(sql1);

			int total=0;
			//表4 出运号
			String shipmentNoF="";
			//表4 客户id
			int erpCustomerIdF=0;
			//项目号
			String itemNoF="";
			//这票货对应的实际销售额
			double actualSalesF=0;
			// 这票货对应的实际发票金额
			double actualInvoiceAmountF=0;
			//表4 配对的银行序号
			String pairedBankNoF  ="";
			//表4 报关金额
			double declarationAmountF=0;
			//表4 申报发票
			int generalDeclarationInvoiceF  =0;

			while (res1.next()) {
				//代理状态
				int agenttatus = res1.getInt("agent_status");
				//表3项目号
				String itemNo = res1.getString("item_no");
				//表3erp客户id
				int erpCustomerId = res1.getInt("erp_customer_id");
				//代理者
				int agent = res1.getInt("agent");
				// 表3出运号
				String shipmentNo =  res1.getString("shipment_no");
				//表3这票货对应的实际销售额
				double actualSales = res1.getInt("actual_sales");
				//表3这票货对应的实际发票金额
				int actualInvoiceAmount = res1.getInt("actual_invoice_amount");


				//表2 数据集合
				List<BankArrival> bankAList = new ArrayList<BankArrival>();

				//表3客户 对应的  表1：代理状态” = 1 或者3，那么数据就直接 用  项目号 查询
				if(agenttatus==1 || agenttatus==3){
					    //表2 分配表 及主表银行序号
						String sqlErp2="select a.id,a.AmountClaimFormId,a.iimoney,a.ifmoney,a.paired,b.TransactionReferenceNumber,b.NBEmailId,b.TradeAmount,b.TransactionDate,a.paired_amount from PreparatorEntryForm a " +
								"inner join AccountEntryForm b on a.AmountClaimFormId=b.id and a.ifmoney!=0 " +
								"where caseno='"+itemNo+"' ";
						ResultSet resErp2 = createStatementErp2.executeQuery(sqlErp2);
						//表2金额
//						double ifmoneySum =0;
						while (resErp2.next()) {
							//预计到账
							int iimoney = resErp2.getInt("iimoney");
							//实际到账
							int ifmoney = resErp2.getInt("ifmoney");
//							ifmoneySum = ifmoneySum+ifmoney;
							//是否已经配对
							String paired = resErp2.getString("paired");
							//表2主表配对id
							String transactionReferenceNumber = resErp2.getString("TransactionReferenceNumber");
                            String transactionDate = resErp2.getString("TransactionDate");
                            int pairedAmount = resErp2.getInt("paired_amount");

							BankArrival bankA = new BankArrival();
							//“表4 银行序号 = 表2：银行序号”
							bankA.setTransactionReferenceNumber(transactionReferenceNumber);
							//表4 报关金额 =  “表2：金额”分配
							bankA.setDeclarationAmountF(ifmoney);
							bankA.setPaired(paired);
							bankA.setErpCustomerId(resErp2.getInt("NBEmailId"));
							//表2 总金额A
							bankA.setTradeAmount(resErp2.getInt("TradeAmount"));

							bankA.setAmountClaimFormId(resErp2.getInt("AmountClaimFormId"));
							bankA.setrId(resErp2.getInt("id"));
							bankA.setTransactionDate(transactionDate);
							//已配对金额
							bankA.setPairedAmount(pairedAmount);

							bankA.setItemNo(itemNo);

							//申报发票 = “表6：发票金额 - 已经使用部分的金额 TODO
							//（其实发票存在多对多的情况比较复杂，我们先简单点，或者你们给出 进一步的方案，或者导入 现在出运系统中 发票的相关数据）
//							String sql2="select invoice_amount,amount_used_part    from shipment_invoice   where item_no='"+itemNo+"' and accountentryform_id="+resErp2.getInt("AmountClaimFormId")+" ";
//							ResultSet res2 = createStatement2.executeQuery(sql2);
//							while (res2.next()) {
//								double invoiceAmount = res2.getInt("invoice_amount");
//								double amountUsedPart = res2.getInt("amount_used_part");
//								generalDeclarationInvoiceF = invoiceAmount-amountUsedPart;
//							}

							String sql2="select Get_Moeny from Tab_Factory_Money where case_no='"+itemNo+"' ";
							ResultSet resErp1 = createStatementErp1.executeQuery(sql2);

							double sumGetMoeny=0;
							while (resErp1.next()) {
								double getMoeny = resErp1.getInt("Get_Moeny");
								sumGetMoeny = sumGetMoeny + getMoeny;
							}
//							generalDeclarationInvoiceF = new BigDecimal(sumGetMoeny).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
							bankA.setGeneralDeclarationInvoicF(generalDeclarationInvoiceF);

							bankAList.add(bankA);

							//四舍五入
//							bankA.setIfmoney(new BigDecimal(ifmoneySum).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
						}
					//⦁	表3客户 对应的  “表1：代理状态” = 2，就按下面的条件  在表2中 寻找 银行序号
				}else if(agenttatus==2 ){
					//第1步，选 客户ID
					//表2 分配表 及主表银行序号
					String sqlErp2="select a.id,a.AmountClaimFormId,a.iimoney,a.ifmoney,a.paired,b.TransactionReferenceNumber,b.NBEmailId,b.TradeAmount,b.TransactionDate ,a.paired_amount from PreparatorEntryForm a " +
							"inner join AccountEntryForm b on a.AmountClaimFormId=b.id and a.ifmoney!=0 " +
							"where caseno='"+itemNo+"' ";
					ResultSet resErp2 = createStatementErp2.executeQuery(sqlErp2);
					while (resErp2.next()) {
						//预计到账分配金额
						double iimoney = resErp2.getInt("iimoney");
						//实际到账
						int ifmoney = resErp2.getInt("ifmoney");
						//是否已经配对
						String paired = resErp2.getString("paired");
                        String transactionDate = resErp2.getString("TransactionDate");
                        int pairedAmount = resErp2.getInt("paired_amount");
						//表2主表配对id
						String transactionReferenceNumber = resErp2.getString("TransactionReferenceNumber");
						BankArrival bankA = new BankArrival();
						//“表4 银行序号 = 表2：银行序号”
						bankA.setTransactionReferenceNumber(transactionReferenceNumber);
						//表4 报关金额 =  “表2：金额”分配
						bankA.setIfmoney(ifmoney);
						bankA.setPaired(paired);
						bankA.setErpCustomerId(resErp2.getInt("NBEmailId"));
						//表2 总金额
						bankA.setTradeAmount(resErp2.getInt("TradeAmount"));
						bankA.setAmountClaimFormId(resErp2.getInt("AmountClaimFormId"));
						bankA.setItemNo(itemNo);
                        bankA.setrId(resErp2.getInt("id"));
                        bankA.setTransactionDate(transactionDate);
                        //已配对金额
                        bankA.setPairedAmount(pairedAmount);

						//申报发票 = “表6：发票金额 - 已经使用部分的金额 TODO
						//（其实发票存在多对多的情况比较复杂，我们先简单点，或者你们给出 进一步的方案，或者导入 现在出运系统中 发票的相关数据）
//						String sql2="select invoice_amount,amount_used_part    from shipment_invoice   where item_no='"+itemNo+"' ";
//						ResultSet res2 = createStatement2.executeQuery(sql2);
//						while (res2.next()) {
//							double invoiceAmount = res2.getInt("invoice_amount");
//							double amountUsedPart = res2.getInt("amount_used_part");
//							generalDeclarationInvoiceF = invoiceAmount-amountUsedPart;
//						}
//						bankA.setGeneralDeclarationInvoicF(generalDeclarationInvoiceF);

						String sql2="select Get_Moeny from Tab_Factory_Money where case_no='"+itemNo+"' ";
						ResultSet resErp1 = createStatementErp1.executeQuery(sql2);

						double sumGetMoeny=0;
						while (resErp1.next()) {
							double getMoeny = resErp1.getInt("Get_Moeny");
							sumGetMoeny = sumGetMoeny + getMoeny;
						}
//						generalDeclarationInvoiceF = new BigDecimal(sumGetMoeny).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						bankA.setGeneralDeclarationInvoicF(generalDeclarationInvoiceF);
						bankAList.add(bankA);

					}

					// 结果集过滤表3客户
					List<BankArrival> bankAListF = bankAList.stream().filter(bankArrival ->
							bankArrival.getErpCustomerId()==erpCustomerId).collect(Collectors.toList());
					//如果  表2客户 = 表3客户，就用它
					if(!bankAListF.isEmpty()){
						bankAList =null;
						bankAList =bankAListF;
					}else{
						//否则
						//如果 表2客户 有 “代理者”， 就用这个 代理者
						int countAgent=0;
						for(int i =0;i<bankAList.size();i++){
							String sql2="select agent_id,agent  from shipment_object  where erp_customer_id="+bankAList.get(i).getErpCustomerId() ;
							ResultSet res2 = createStatement2.executeQuery(sql1);
							while (res2.next()) {
								bankAList.get(i).setErpCustomerId(res2.getInt("agent_id"));
								countAgent++;
							}
						}

						if(countAgent==0){
							//否则
							//报错，不做配对
							return;
						}
					}

				}


				for(int j =0; j<bankAList.size(); j++){

					String paired = bankAList.get(j).getPaired();
					//实际到账
					int ifmoney = bankAList.get(j).getIfmoney();
					String itemNoI = bankAList.get(j).getItemNo();
					//第2步, 在选定的客户ID下面 找到  最早的 银行到账序号	但要满足金额条件
					//如果配对状态是：未配对
					if(paired.equals("未配对")){
						//要求 “表2金额” >“表3 实际销售额”*0.95   但小于 1.5 倍
						if(ifmoney>actualSales*0.95 && ifmoney<actualSales*1.5){
							//报关金额 =  “表2：金额”
							bankAList.get(j).setDeclarationAmountF(ifmoney);
						}
					}
					//如果配对状态是：部分配对
					if(paired.equals("部分配对")){

						// 结果集过滤表3客户
						List<BankArrival> bankAListP = bankAList.stream().filter(bankArrival ->
								bankArrival.getPaired().equals("已配对") && itemNoI.equals(bankArrival.getItemNo())).collect(Collectors.toList());
						double pairedIimoneySum=0;
						for(int z=0 ;z<bankAListP.size();z++){

							//表2已经配对金额
							pairedIimoneySum = pairedIimoneySum+ bankAListP.get(z).getIfmoney();

						}

						//要求 （“表2金额” - “表2已经配对金额” ）  > “表3 实际销售额”
						if((ifmoney-pairedIimoneySum)>actualInvoiceAmount){
							//报关金额 =  “表3 实际销售额”
							bankAList.get(j).setDeclarationAmountF(actualInvoiceAmount);
						}
					}
				}
				//出运号
				shipmentNoF=shipmentNo;
				//这票货对应的实际销售额
				actualSalesF=actualSales;
				// 这票货对应的实际发票金额
				actualInvoiceAmountF=actualInvoiceAmount;

				// 结果集过滤表3客户
				bankAList = bankAList.stream().filter(bankArrival ->
						!bankArrival.getPaired().equals("已配对") ).collect(Collectors.toList());

				for(int f=0;f<bankAList.size();f++){
					//出运号
					request.setAttribute("shipmentNoF"+total, shipmentNoF);
					//ERP客户ID
					request.setAttribute("erpCustomerIdF"+total, bankAList.get(f).getErpCustomerId());
					//项目号
					request.setAttribute("itemNoF"+total, bankAList.get(f).getItemNo());
					//这票货对应的实际销售额
					request.setAttribute("actualSalesF"+total, actualSalesF);
					//这票货对应的实际发票金额
					request.setAttribute("actualInvoiceAmountF"+total, actualInvoiceAmountF);
					//配对的银行序号
					request.setAttribute("pairedBankNoF"+total, bankAList.get(f).getTransactionReferenceNumber());
					//报关金额
					request.setAttribute("declarationAmountF"+total, bankAList.get(f).getDeclarationAmountF());
					//到账总额
					request.setAttribute("tradeAmountF"+total, bankAList.get(f).getTradeAmount());
					//关联id
					request.setAttribute("amountClaimFormIdF"+total, bankAList.get(f).getAmountClaimFormId());
					//申报发票
					request.setAttribute("generalDeclarationInvoiceF"+total, bankAList.get(f).getGeneralDeclarationInvoicF());

                    request.setAttribute("transactionDateF"+total, bankAList.get(f).getTransactionDate());
                    request.setAttribute("pairedF"+total, bankAList.get(f).getPaired());
                    //表 2PreparatorEntryForm [id]
                    request.setAttribute("idF"+total, bankAList.get(f).getrId());
                    request.setAttribute("pairedAmountF"+total, bankAList.get(f).getPairedAmount());

					total++;
				}

				//第3步：把上述的配对结果展现在页面上，等待审核

			}

			request.setAttribute("total", total);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
 			DBHelper.returnConnection(connection);
			DBHelper.returnConnection(connectionErp);
 		}
		RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/shipmentpayment.jsp");
		homeDispatcher.forward(request, response);
		out.flush();
		out.close();
	}




}

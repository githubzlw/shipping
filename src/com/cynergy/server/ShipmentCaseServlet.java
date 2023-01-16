package com.cynergy.server;
/**
 * 汇总
 */

import com.cynergy.main.BankArrival;
import com.cynergy.main.DBHelper;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShipmentCaseServlet extends HttpServlet {
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

		Connection connectionErp = DBHelper.getConnectionERP();
		try {

			Statement createStatementErp1 = connectionErp.createStatement();


//			//1）有银行到账 但没关联报关金额的全部
//			String sqlErp1A="select a.TransactionReferenceNumber,a.id,a.TradeAmount,a.BeneficiaryAccountBank,a.PayersName,b.caseno,max(isnull(cnt.proId,0)) proId,max(isnull(itm.trueprice,'0')) trueprice,b.ifmoney,b.financialConfirmationTime from AccountEntryForm a " +
//					"left join PreparatorEntryForm b on b.AmountClaimFormId=a.id and b.ifmoney!=0  "+
//					"left join  (select * from reportform.[dbo].contract) cnt   on b.caseno =left(replace(cnt.purno,'合','SHS'),len(replace(cnt.purno,'合','SHS'))-1)    "+
//					"left join reportform.[dbo].items itm on  cnt.proId=itm.proId  "+
//					"group by a.TransactionReferenceNumber,a.id,a.TradeAmount,a.BeneficiaryAccountBank,a.PayersName,b.caseno,b.ifmoney,b.financialConfirmationTime  "+
//					"having max(isnull(itm.trueprice,'0'))='0' and b.caseno is not null  ";
//			ResultSet resErp1A = createStatementErp1.executeQuery(sqlErp1A);
//			List<BankArrival> bankATwoAList = new ArrayList<BankArrival>();
//
//			while (resErp1A.next()) {
//
//				BankArrival bankA = new BankArrival();
//				//银行序号
//				bankA.setTransactionReferenceNumber(resErp1A.getString("TransactionReferenceNumber"));
//				//到账总金额
//				bankA.setTradeAmount(resErp1A.getInt("TradeAmount"));
//				//项目号
//				bankA.setCaseNo(resErp1A.getString("caseno"));
//				//出运单号
//				bankA.setProId(resErp1A.getInt("proId"));
//				//报关金额
//				bankA.setDeclarationAmountF(resErp1A.getInt("trueprice"));
//				// 认领金额
//				bankA.setIfmoney(resErp1A.getInt("ifmoney"));
//				// 认领日期
//				bankA.setFinancialConfirmationTime(resErp1A.getString("financialConfirmationTime"));
//				//回款人
//				bankA.setPayersName(resErp1A.getString("PayersName"));
//
//				bankATwoAList.add(bankA);
//
//			}
//			request.setAttribute("bankATwoAList", bankATwoAList);
//
//			//2）有报关金额但没关联银行到账的  全部
//			String sqlErp2A="select a.TransactionReferenceNumber,a.id,a.TradeAmount,a.BeneficiaryAccountBank,a.PayersName,b.caseno,max(isnull(cnt.proId,0)) proId,max(isnull(itm.trueprice,'0')) trueprice,b.ifmoney,b.financialConfirmationTime   " +
//					"from   (select * from reportform.[dbo].contract) cnt    "+
//					"left join PreparatorEntryForm b on b.caseno =left(replace(cnt.purno,'合','SHS'),len(replace(cnt.purno,'合','SHS'))-1)     "+
//					"left join  AccountEntryForm a  on b.AmountClaimFormId=a.id  and b.ifmoney!=0   "+
//					"left join reportform.[dbo].items itm on  cnt.proId=itm.proId "+
//					"group by a.TransactionReferenceNumber,a.id,a.TradeAmount,a.BeneficiaryAccountBank,a.PayersName,b.caseno,b.ifmoney,b.financialConfirmationTime "+
//					"having max(isnull(itm.trueprice,'0'))<>'0' and a.id is null  ";
//			ResultSet resErp2A = createStatementErp1.executeQuery(sqlErp2A);
//			List<BankArrival> bankBTwoAList = new ArrayList<BankArrival>();
//
//			while (resErp2A.next()) {
//
//				BankArrival bankA = new BankArrival();
//				//银行序号
//				bankA.setTransactionReferenceNumber(resErp2A.getString("TransactionReferenceNumber"));
//				//到账总金额
//				bankA.setTradeAmount(resErp2A.getInt("TradeAmount"));
//				//项目号
//				bankA.setCaseNo(resErp2A.getString("caseno"));
//				//出运单号
//				bankA.setProId(resErp2A.getInt("proId"));
//				String truepriceStr = resErp2A.getString("trueprice");
//				if(truepriceStr.indexOf(".")>-1){
//					truepriceStr = truepriceStr.substring(0,truepriceStr.indexOf("."));
//				}
//				if(StringUtils.isEmpty(truepriceStr)){
//					truepriceStr = "0";
//				}
//				//报关金额
//				bankA.setDeclarationAmountF(Integer.valueOf(truepriceStr));
//				// 认领金额
//				bankA.setIfmoney(resErp2A.getInt("ifmoney"));
//				// 认领日期
//				bankA.setFinancialConfirmationTime(resErp2A.getString("financialConfirmationTime"));
//				//回款人
//				bankA.setPayersName(resErp2A.getString("PayersName"));
//				bankBTwoAList.add(bankA);
//
//			}
//			request.setAttribute("bankBTwoAList", bankBTwoAList);

			//3）两者关联的  全部
//			String sqlErp3A="select b.shipping_id as proId,sum(b.paired_amount) as paired_amount,sum(b.ifmoney) as ifmoney,max(b.caseno) as caseno,max(b.financialConfirmationTime) as financialConfirmationTime,   " +
//					" (select sum(cast( (case when itm.trueprice='' then '0'  else itm.trueprice end) as decimal ))  from  reportform.[dbo].items itm where   b.shipping_id=itm.proId ) as trueprice, "+
//					"max(a.NBEmailId) as NBEmailId,max(a.TransactionReferenceNumber) as TransactionReferenceNumber,max(a.BeneficiaryAccountBank) as BeneficiaryAccountBank,max(a.PayersName) as PayersName ,sum(a.TradeAmount) as TradeAmount,    "+
//                    "(select sum(ar.uncollected_amount)  from reportform.[dbo].accounts_receivable ar where ar.pro_id=b.shipping_id) as uncollectedAmount "+
//					"from AccountEntryForm a     "+
//					"left join PreparatorEntryForm b on b.AmountClaimFormId=a.id   and b.ifmoney!=0       "+
//					"where b.ifmoney!=0   and b.shipping_id!=0 and b.paired_amount !=0     "+
//					"group by b.shipping_id ";

			String sqlErp3A="select b.shipping_id as proId,sum(b.paired_amount) as paired_amount,sum(b.ifmoney) as ifmoney,max(b.caseno) as caseno,max(b.update_time) as financialConfirmationTime, "+
					"(select sum(cast( (case when itm.trueprice='' then '0'  else itm.trueprice end) as decimal ))  from  reportform.[dbo].items itm where   b.shipping_id=itm.proId ) as trueprice,"+
					"(select sum(ar.uncollected_amount)  from reportform.[dbo].accounts_receivable ar where ar.pro_id=b.shipping_id) as uncollectedAmount  "+
					"from PreparatorEntryForm b   "+
					"where b.ifmoney!=0   and b.shipping_id!=0 and b.paired_amount !=0   "+
					"group by b.shipping_id ";

			ResultSet resErp3A = createStatementErp1.executeQuery(sqlErp3A);
			List<BankArrival> bankWTwoAList = new ArrayList<BankArrival>();

			while (resErp3A.next()) {

				BankArrival bankA = new BankArrival();
				//银行序号
//				bankA.setTransactionReferenceNumber(resErp3A.getString("TransactionReferenceNumber"));
				//到账总金额
//				bankA.setTradeAmount(resErp3A.getInt("TradeAmount"));
				//项目号
//				bankA.setCaseNo(resErp3A.getString("caseno"));
				//出运单号
				bankA.setProId(resErp3A.getInt("proId"));
				String truepriceStr = resErp3A.getString("trueprice");
				if(truepriceStr.indexOf(".")>-1){
					truepriceStr = truepriceStr.substring(0,truepriceStr.indexOf("."));
				}
				if(StringUtils.isEmpty(truepriceStr)){
					truepriceStr = "0";
				}
				//报关金额
				bankA.setDeclarationAmountF(Integer.valueOf(truepriceStr));
				// 认领金额
				bankA.setIfmoney(resErp3A.getInt("ifmoney"));
				// 认领日期
				bankA.setFinancialConfirmationTime(resErp3A.getString("financialConfirmationTime"));
				//回款人
//				bankA.setPayersName(resErp3A.getString("PayersName"));
				//已配对金额
				bankA.setPairedAmount(resErp3A.getInt("paired_amount"));
				//未收到但本次要出口的金额
				bankA.setChaE(resErp3A.getInt("uncollectedAmount"));

				bankWTwoAList.add(bankA);

			}
			request.setAttribute("bankWTwoAList", bankWTwoAList);



		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBHelper.returnConnection(connectionErp);
 		}
		RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/shipmentcase.jsp");
		homeDispatcher.forward(request, response);
		out.flush();
		out.close();
	}

}

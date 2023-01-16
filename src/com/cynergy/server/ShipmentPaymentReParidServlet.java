package com.cynergy.server;
/**
 * 汇总
 */

import com.cynergy.main.BankArrival;
import com.cynergy.main.DBHelper;
import com.cynergy.main.ProjectStatisticsPrint;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShipmentPaymentReParidServlet extends HttpServlet {
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
		String year = request.getParameter("year");
		String customerName = request.getParameter("customerName");
		String parid = request.getParameter("parid");

		List<BankArrival> bankAList = this.getBankAList(year,customerName,parid);

		request.setAttribute("bankAList", bankAList);
		request.setAttribute("year", year);
		request.setAttribute("customerName", customerName);
		request.setAttribute("parid", parid);

		RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/shipmentpayment.jsp");
		homeDispatcher.forward(request, response);
		out.flush();
		out.close();


	}

	public List<BankArrival> getBankAList(String year,String customerName,String parid){

		if(StringUtils.isNotBlank(year)){
			year = year+"-01-01 00:00:00";
		}

		List<BankArrival> bankAList = new ArrayList<BankArrival>();
		Connection connectionErp = DBHelper.getConnectionERP();
		try {

			PreparedStatement stmt = null;

			String sqlErp2="select c.proId,(select customercode from itemCase where caseno=b.caseno) as customercode ,    " +
					"c.itemchn ,c.unitpriceall,c.trueprice,b.caseno,a.TransactionReferenceNumber,a.TransactionDate,a.TradeAmount,b.paired_amount ,b.paired " +
					"from AccountEntryForm  a  left join PreparatorEntryForm b on b.AmountClaimFormId=a.id and b.ifmoney!=0    "+
					"left join reportform.[dbo].items c on b.shipping_id= c.proId "+
					"where b.shipping_id is not null and c.trueprice !=''   ";
			if(StringUtils.isNotBlank(year)){
				sqlErp2+=" and b.update_time>'"+year+"'  ";
			}
			if(StringUtils.isNotBlank(customerName)){
				sqlErp2+="and  a.PayersName='"+customerName+"' ";
			}
			if(StringUtils.isNotBlank(parid)){
				sqlErp2+=" and b.paired='"+parid+"' ";
			}
			sqlErp2+=" order by  b.caseno desc, b.update_time desc  ";


			stmt = connectionErp.prepareStatement(sqlErp2);
//			if(StringUtils.isNotBlank(year)){
//				stmt.setString(1, year);
//			}
//			if(StringUtils.isNotBlank(customerName)){
//				stmt.setString(2, customerName);
//			}
//			if(StringUtils.isNotBlank(parid)){
//				stmt.setString(3, parid);
//			}
			ResultSet resErp2 = stmt.executeQuery();

			while (resErp2.next()) {

				BankArrival bankA = new BankArrival();
				//出运号
				bankA.setProId(resErp2.getInt("proId"));
				//客户id
				bankA.setErpCustomerId(resErp2.getInt("customercode"));
				//出运品名
				bankA.setItemchn(resErp2.getString("itemchn"));
				//实际销售额（清关金额）
				bankA.setUnitPriceall(resErp2.getInt("unitpriceall"));
				//实际对外发票金额（报关金额）
				bankA.setTruePrice(resErp2.getInt("trueprice"));
				//项目号
				bankA.setItemNo(resErp2.getString("caseno"));
				//银行序号
				bankA.setTransactionReferenceNumber(resErp2.getString("TransactionReferenceNumber"));
				//到账日期
				bankA.setTransactionDate(resErp2.getString("TransactionDate"));
				//到账金额
				bankA.setTradeAmount(resErp2.getInt("TradeAmount"));
				//配对金额
				bankA.setPairedAmount(resErp2.getInt("paired_amount"));
				//配对状态
				bankA.setPaired(resErp2.getString("paired"));

				bankAList.add(bankA);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.returnConnection(connectionErp);
		}
		return bankAList;
	}

}





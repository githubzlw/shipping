package com.cynergy.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cynergy.main.DBHelper;
import com.cynergy.main.MainSql;
import com.cynergy.main.TranMoney;

public class Contract extends HttpServlet {
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
//		String nonum = request.getParameter("nonum");
//		String dateTime = request.getParameter("dateTime");
		String idString = request.getParameter("id");
		int id=Integer.parseInt(idString);
//		System.out.println("id:"+id);
		Connection connection = DBHelper.getConnection();
		try {
			Statement createStatement = connection.createStatement();
			String sqlString="select id,nonum,date,clientName,address,transaction1,transaction2,volume,saildate,fromwhere,towhere,currency,yunfei,waixiaotime from products where id="+id;
			ResultSet resultSet = createStatement.executeQuery(sqlString);
			request.setAttribute("seller", "SHANGHAI CS MANUFACTURING CO.");
			request.setAttribute("comAddress", "Room 605, 6 floor, Building No. 1, Hui Yin Ming Zun, No. 609 East Yun Ling Road, Putuo district, Shanghai City, China. 200062.");
			String currency="";
			while(resultSet.next()){
				//产品编号
				request.setAttribute("nonum", resultSet.getString("nonum"));
				request.setAttribute("clientName", resultSet.getString("clientName"));
				
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
				SimpleDateFormat format2=new SimpleDateFormat("d-MMM-yy",Locale.ENGLISH);
				String waixiaotime = resultSet.getString("waixiaotime");
				if(waixiaotime!=null&&!waixiaotime.equals("")){
					waixiaotime=format2.format(format.parse(waixiaotime));
				}else{
					waixiaotime="";
				}
				//编号日期
				request.setAttribute("date", waixiaotime);
				//发送地址
				request.setAttribute("address", resultSet.getString("address"));
				//交易方式
//				request.setAttribute("transaction1", resultSet.getString("transaction1"));
//				//货运方式
//				request.setAttribute("transaction2", resultSet.getString("transaction2"));
				//体积
//				request.setAttribute("volume", resultSet.getString("volume"));
				//发货时间
				request.setAttribute("saildate", resultSet.getString("saildate"));
				//起点
				request.setAttribute("fromwhere", resultSet.getString("fromwhere"));
				//终点
				request.setAttribute("towhere", resultSet.getString("towhere"));
				DecimalFormat df=new DecimalFormat("$###,##0.00");
				//运费
				String yunfei = resultSet.getString("yunfei");
				if(yunfei!=null&&!yunfei.trim().equals("")){
					request.setAttribute("yunfei", df.format(Double.parseDouble(yunfei)));
				}else{
					request.setAttribute("yunfei", "$0");
				}
				//币种
				currency = resultSet.getString("currency");
				request.setAttribute("currency", currency);
//				if(currency.equals("$")){
//					request.setAttribute("currency", "USD");
//				}
//				if(currency.equals("€")){
//					request.setAttribute("currency", "EUR");
//				}
//				if(currency.equals("￡")){
//					request.setAttribute("currency", "GBP");
//				}
//				if(currency.equals("¥")){
//					request.setAttribute("currency", "RMB");
//				}
//				if(currency.equals("￥")){
//					request.setAttribute("currency", "JPY");
//				}
				if(currency.equals("USD")){
					currency="$";
				}
				if(currency.equals("EUR")){
					currency="€";
				}
				if(currency.equals("GBP")){
					currency="￡";
				}
				if(currency.equals("RMB")){
					currency="¥";
				}
				if(currency.equals("AUD")){
					currency="$";
				}
//				if(currency.equals("JPY")){
//					currency="￥";
//				}
			}
			String sql2="select itemeng,itemchn,shopingmark,quantity,trueprice from items where proId="+id;
			ResultSet resultSet2 = createStatement.executeQuery(sql2);
			int total=0;
			double totalPrice=0;
			DecimalFormat df=new DecimalFormat(currency+"###,##0.00");
			while(resultSet2.next()){
				String itemeng = resultSet2.getString("itemeng");
				String itemchn = resultSet2.getString("itemchn");
				String shopingmark = resultSet2.getString("shopingmark");
				String quantity = resultSet2.getString("quantity");
				int quant=Integer.parseInt(quantity);
				String trueprice = resultSet2.getString("trueprice");
//				request.setAttribute("itemeng"+total, itemeng);
//				request.setAttribute("itemchn"+total, itemchn);
//				request.setAttribute("itemall"+total, itemeng+"  "+itemchn);
				request.setAttribute("itemall"+total, itemeng);
				request.setAttribute("quantity"+total, quantity);
				
				double truessss = Double.parseDouble(trueprice);
				double perunit=truessss/quant;
				
				request.setAttribute("trueprice"+total, df.format(perunit));
				request.setAttribute("shopingmark"+total, shopingmark);
				
//				Number parse;
//				double price = Double.parseDouble(trueprice);
				totalPrice=totalPrice+truessss;
				request.setAttribute("amount"+total, df.format(truessss));
				total++;
			}
			request.setAttribute("total", total);
//			System.out.println("总是："+total);
			if(df==null){
				request.setAttribute("totalPrice", totalPrice);
			}else{
				request.setAttribute("totalPrice", df.format(totalPrice));
			}
			request.setAttribute("totalMoney", TranMoney.parse(totalPrice+""));
			
			RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/contract.jsp");
			homeDispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
 			DBHelper.returnConnection(connection);
 		}
		out.flush();
		out.close();
	}
}

package com.cynergy.server;
/**
 * 汇总
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cynergy.main.DBHelper;
import com.cynergy.main.MainSql;

public class SummaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		COLLECT 到付
//		 	PREPAID  预付
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String year = request.getParameter("select1");
		String month = request.getParameter("select2");
		String ymString=year+"-"+month;
//		String ymString=year+"-"+month+"-01";
		Connection connection = DBHelper.getConnection();
		try {
			Statement createStatement = connection.createStatement();
			Statement createStatement1 = connection.createStatement();
			Statement createStatement3 = connection.createStatement();
			String sql="select * from products where saildate like '"+ymString+"%' and date != '' order by date";
			System.out.println("汇总："+sql);
			ResultSet resultSet = createStatement.executeQuery(sql);
			int total=0;
			int totalTrue=0;
			while (resultSet.next()) {
				totalTrue++;
				String nonum = resultSet.getString("nonum");
				int id = resultSet.getInt("id");
				String saildate = resultSet.getString("saildate");
				String transaction1 = resultSet.getString("transaction1");
				String transaction2 = resultSet.getString("transaction2");
				String fromwhere = resultSet.getString("fromwhere");
				String towhere = resultSet.getString("towhere");
				String currency = resultSet.getString("currency");
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
				String sale = resultSet.getString("sale");
				String purchase = resultSet.getString("purchase");
				String clientName = resultSet.getString("clientName");
				String huodai = resultSet.getString("huodai");
				String yunfei = resultSet.getString("yunfei");
				if(yunfei==null||yunfei.trim().equals("")){
					yunfei="0";
				}
				String sql3="select purno,times,totaltimes,money from contract where proId="+id;
				System.out.println(sql3);
				String purnoall="";
				String timesall="";
//				int moneyall=0;
				float moneyall=0;
				int purnoalltotal=0;
				ResultSet res3 = createStatement3.executeQuery(sql3);
				while (res3.next()) {
					purnoall=purnoall+res3.getString("purno")+" ";
					timesall=timesall+res3.getString("times")+"/"+res3.getString("totaltimes")+"  ";
					String money = res3.getString("money");
					if(money!=null){
						moneyall=moneyall+Float.parseFloat(money);
					}
					purnoalltotal++;
				}
				if(purnoalltotal>1){
					purnoall="合  "+purnoall;
				}
				
				
				DecimalFormat df=new DecimalFormat(currency+"###,##0.00");
				DecimalFormat df2=new DecimalFormat("¥###,##0.00");
				String sql2="select hscode,itemeng,itemchn,rate,purprice,quantity,trueprice,unitpriceall from items where proId="+id;
				ResultSet res = createStatement1.executeQuery(sql2);
				int yunfeitotal=0;
				while (res.next()) {
//					System.out.println(sql2);
					request.setAttribute("id"+total, id);
					request.setAttribute("num"+total, totalTrue);
					request.setAttribute("nonum"+total, nonum);
					request.setAttribute("saildate"+total, saildate);
					request.setAttribute("tran1"+total, transaction1);
					request.setAttribute("tran2"+total, transaction2);
					request.setAttribute("fromwhere"+total, fromwhere);
					request.setAttribute("towhere"+total, towhere);
					request.setAttribute("hscode"+total, res.getString("hscode"));
					request.setAttribute("itemeng"+total, res.getString("itemeng"));
					request.setAttribute("itemchn"+total, res.getString("itemchn"));
//					String quantity = res.getString("quantity");
//					int quant=Integer.parseInt(quantity);
					String trueprice = res.getString("trueprice");
					if(trueprice==null||trueprice.trim().equals("")){
						trueprice="0";
					}
					double price = Double.parseDouble(trueprice);
					String unitpriceall = res.getString("unitpriceall");
					
					if(unitpriceall==null||unitpriceall.trim().equals("")||unitpriceall.trim().equals("null")){
						unitpriceall="0";
					}
					System.out.println(id+"==="+unitpriceall);
					double unitpriceallss = Double.parseDouble(unitpriceall);
					String purprice = res.getString("purprice");
					double purprices=Double.parseDouble(purprice);
					
					request.setAttribute("totalmoney"+total, df.format(price));
					request.setAttribute("unitpriceall"+total, df.format(unitpriceallss));
					request.setAttribute("moneyall"+total, df2.format(purprices));
					request.setAttribute("sale"+total, sale);
					request.setAttribute("purchase"+total, purchase);
					request.setAttribute("clientName"+total, clientName);
					request.setAttribute("huodai"+total, huodai);
					if(yunfeitotal==0){
						request.setAttribute("yunfei"+total, yunfei);
					}else{
						request.setAttribute("yunfei"+total, 0);
					}
					request.setAttribute("rate"+total, res.getString("rate"));
					request.setAttribute("purnoall"+total, purnoall);
					request.setAttribute("timesall"+total, timesall);
					total++;
					yunfeitotal++;
				}
			}
//			System.out.println("total:"+total+";totalTrue:"+totalTrue);
			request.setAttribute("total", total);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
 			DBHelper.returnConnection(connection);
 		}
		RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/summary.jsp");
		homeDispatcher.forward(request, response);
		out.flush();
		out.close();
	}

}

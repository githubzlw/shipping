package com.cynergy.server;
/**
 * 退税汇总
 */

import com.cynergy.main.DBHelper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;

public class NewDrawBackSummaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		String year = request.getParameter("select1");
		String month = request.getParameter("select2");
		String ymString=year+"-"+month;
		Connection connection = DBHelper.getConnection();
		DecimalFormat dfss = new DecimalFormat("0.00%");
		DecimalFormat df2=new DecimalFormat("¥###,##0.00");
		DecimalFormat df=new DecimalFormat("###,##0.00");
		try {
			Statement createStatement = connection.createStatement();
			Statement createStatement1 = connection.createStatement();
			Statement createStatement3 = connection.createStatement();
			String sql="select * from products where saildate like '"+ymString+"%' and date != '' order by date";
			System.out.println("退税汇总："+sql);
			ResultSet resultSet = createStatement.executeQuery(sql);
			int total=0;
			int totalTrue=0;
			//合同总金额
			Double totalPurprice = 0.0;
			//报关总金额(USD)
			Double totalTruepriceUSD = 0.0;
			//报关总金额(EUR)
			Double totalTruepriceEUR = 0.0;
			//报关总金额(GBP)
			Double totalTruepriceGBP = 0.0;
			//报关总金额(AUD)
			Double totalTruepriceAUD = 0.0;
			//退税总金额
			Double totalbackMoney = 0.0;
			while (resultSet.next()) {
				totalTrue++;
				int id = resultSet.getInt("id");
				String saildate = resultSet.getString("saildate");
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
					currency="A$";
				}
				String sale = resultSet.getString("sale");
				String clientName = resultSet.getString("clientName");
				String yunfei = resultSet.getString("yunfei");
				if(yunfei==null||yunfei.trim().equals("")){
					yunfei="0";
				}
				String sql3="select purno,rmb from contract where proId="+id;

				ResultSet res3 = createStatement3.executeQuery(sql3);
				int yunfeitotal=0;
				while (res3.next()) {
					String purno = res3.getString("purno");
					String rmb = res3.getString("rmb");
//
					String sql2="select i.hscode,i.itemeng,i.itemchn,i.rate,i.purprice,i.quantity,i.trueprice,c.purno from items i " +
							"left join contract_items c  on i.id=c.item_id " +
							"where c.purno='"+purno+"'";
					ResultSet res = createStatement1.executeQuery(sql2);

					request.setAttribute("month"+total, year+"年"+month+"月");
					request.setAttribute("sale"+total, sale);
					request.setAttribute("num"+total, totalTrue);
					request.setAttribute("purnoall"+total, purno);

					if(yunfeitotal==0){
						request.setAttribute("yunfei"+total, df2.format(Double.parseDouble(yunfei)));
					}else{
						request.setAttribute("yunfei"+total, df2.format(0));
					}

					request.setAttribute("clientName"+total, clientName);
					request.setAttribute("saildate"+total, saildate);

					String itemchn = "";
					String rated = "";
					String hscode = "";
					double parseDoubless = 0;
					double truepriceD = 0;
					double truepriceTotalD = Double.parseDouble(rmb);
					double backMoney=0;
					while (res.next()) {
						itemchn +=" "+res.getString("itemchn");

						String purprice = res.getString("purprice");
						if(purprice==null||purprice.trim().equals("")){
							purprice="0";
						}
						parseDoubless = Double.parseDouble(purprice);
						totalPurprice += parseDoubless;

						String trueprice = res.getString("trueprice");
						if(trueprice==null||trueprice.trim().equals("")||trueprice.trim().equals("null")){
							trueprice="0";
						}
						truepriceD +=Double.parseDouble(trueprice);
						if("$".equals(currency)){
							totalTruepriceUSD +=Double.parseDouble(trueprice);
						}
						if("€".equals(currency)){
							totalTruepriceEUR +=Double.parseDouble(trueprice);
						}
						if("￡".equals(currency)){
							totalTruepriceGBP +=Double.parseDouble(trueprice);
						}
						if("A$".equals(currency)){
							totalTruepriceAUD +=Double.parseDouble(trueprice);
						}
						hscode += " "+res.getString("hscode");

						String rate = res.getString("rate");
						if(rate == null||rate.trim().equals("")){
							rate="";
						}else{
							rated +=" "+rate;
						}

						/*if(!rate.equals("")){
							Number parse = dfss.parse(rate);
							backMoney +=(parseDoubless/1.13)*parse.doubleValue();
							totalbackMoney +=backMoney;
						}*/
					}
					request.setAttribute("itemchn"+total, itemchn);
					request.setAttribute("purprice"+total, df2.format(truepriceTotalD));
					request.setAttribute("trueprice"+total, df.format(truepriceD));
					request.setAttribute("hscode"+total, hscode);
					request.setAttribute("backMoney"+total, df2.format(truepriceTotalD/1.13*0.13));
					request.setAttribute("rate"+total,rated);
					total++;
					yunfeitotal++;
				}
			}

			request.setAttribute("total", total);
			request.setAttribute("year", Integer.parseInt(year));
			request.setAttribute("month", Integer.parseInt(month));
			request.setAttribute("totalPurprice", df.format(totalPurprice));
			request.setAttribute("totalTruepriceUSD", df.format(totalTruepriceUSD));
			request.setAttribute("totalTruepriceEUR", df.format(totalTruepriceEUR));
			request.setAttribute("totalTruepriceGBP", df.format(totalTruepriceGBP));
			request.setAttribute("totalTruepriceAUD", df.format(totalTruepriceAUD));
			request.setAttribute("totalbackMoney", df.format(totalbackMoney));
		} catch (SQLException  e) {
			e.printStackTrace();
		}finally {
 			DBHelper.returnConnection(connection);
 		}
		RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/drawbackSummary.jsp");
		homeDispatcher.forward(request, response);
	}

}

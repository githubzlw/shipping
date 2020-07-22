package com.cynergy.server;

/**
 * 备案
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cynergy.main.DBHelper;
import com.cynergy.main.MainSql;
import com.cynergy.main.TranMoney;

public class ForOnRecord extends HttpServlet {
	private static final long serialVersionUID = -5385656382724957646L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String idString = request.getParameter("id");
		int id = Integer.parseInt(idString);
		Connection connection = DBHelper.getConnection();
		try {
			Statement createStatement = connection.createStatement();
			Statement createStatement1 = connection.createStatement();
			Statement createStatement3 = connection.createStatement();
			String sqlString = "select id,clientName,address,volume,saildate,fromwhere,towhere,currency,yunfei,package,packagenum,totalGW,yunfeifs,saildate from products where id="
					+ id;
			ResultSet resultSet = createStatement.executeQuery(sqlString);
			// System.out.println("出口货物明细单："+sqlString);
			String packagess = "";
			String packagenum = "";
			String totalGW = "";
			String sailDateTime="";
			while (resultSet.next()) {
				String currency = resultSet.getString("currency");
				request.setAttribute("clientName", resultSet
						.getString("clientName"));
				request.setAttribute("fromwhere", resultSet
						.getString("fromwhere"));
				request.setAttribute("towhere", resultSet.getString("towhere"));
				request.setAttribute("yunfei", resultSet.getString("yunfei"));
				request.setAttribute("volume", resultSet.getString("volume"));
				packagess = resultSet.getString("package");
				packagenum = resultSet.getString("packagenum");
				totalGW = resultSet.getString("totalGW");
				request.setAttribute("yunfeifs", resultSet
						.getString("yunfeifs"));
				sailDateTime=resultSet.getString("saildate");
				String sql3 = "select purno,times,totaltimes,money from contract where proId="
						+ id;
				System.out.println(sql3);
				String purnoall = "";
				String timesall = "";
				double moneyall = 0;
				int purnoalltotal = 0;
				ResultSet res3 = createStatement3.executeQuery(sql3);
				while (res3.next()) {
					purnoall = purnoall + res3.getString("purno") + " ";
					timesall = timesall + res3.getString("times") + "/"
							+ res3.getString("totaltimes") + "  ";
					String money = res3.getString("money");
					// System.out.println(money+"=++++++");
					if (money != null) {
						moneyall = moneyall + Double.parseDouble(money);
					}
					purnoalltotal++;
				}
				if (purnoalltotal > 1) {
					purnoall = "合  " + purnoall;
				}
				// System.out.println(purnoall+"=======");
				request.setAttribute("purnoall", purnoall);
				String currss = "$";
				if (currency.equals("USD")) {
					currss = "$";
				}
				if (currency.equals("EUR")) {
					currss = "€";
				}
				if (currency.equals("GBP")) {
					currss = "￡";
				}
				if (currency.equals("RMB")) {
					currss = "¥";
				}
				if (currency.equals("AUD")) {
					currss = "$";
				}
				DecimalFormat df = new DecimalFormat("###,##0.00");
				DecimalFormat df2 = new DecimalFormat(currss + "##,##0.00");
				String sql2 = "select hscode,itemeng,itemchn,shopingmark,rate,quantity,trueprice,nw from items where proId="
						+ id;
				ResultSet res = createStatement1.executeQuery(sql2);
				double totalPrice = 0;
				StringBuffer allContent = new StringBuffer();

				int flag = 0;
				while (res.next()) {
					String itemeng = res.getString("itemeng");
					String itemchn = res.getString("itemchn");
					// String shopingmark = res.getString("shopingmark");
					String nw = res.getString("nw");
					String quantity = res.getString("quantity");
					int quant = Integer.parseInt(quantity);
					String trueprice = res.getString("trueprice");
					double price = 0;
					if (trueprice != null && !trueprice.trim().equals("")) {
						price = Double.parseDouble(trueprice);
					}
					totalPrice = totalPrice + price;
					if (flag == 0) {
						allContent.append(itemeng).append("-").append(itemchn)
								.append(";").append(packagenum).append(
										packagess).append(";").append(quantity)
								.append("PCS;").append(totalGW).append("KGS;")
								.append(nw).append("KGS;").append(
										df2.format(price / quant)).append(";")
								.append(
										"TOTAL:" + currency + df.format(price)
												+ " <br/>");
					} else {
						allContent.append(itemeng).append("-").append(itemchn)
								.append(";----;").append(quantity).append(
										"PCS;----KGS;").append(nw).append(
										"KGS;").append(
										df2.format(price / quant)).append(";")
								.append(
										"TOTAL:" + currency + df.format(price)
												+ " <br/>");
					}
					flag++;
				}
				
//				sailDateTime;
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat format2=new SimpleDateFormat("yyyy年MM月dd日");
				String format3 = "";
				if(!(sailDateTime == null || "".equals(sailDateTime))){
					format3 = format2.format(format.parse(sailDateTime));	
				}
				System.out.println(format3+"==========");
				request.setAttribute("sailDateTime", format3);
				request.setAttribute("allContent", allContent.toString());
				request.setAttribute("totalMoney", currency + " "
						+ df.format(totalPrice));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
 			DBHelper.returnConnection(connection);
 		}
		RequestDispatcher homeDispatcher = request
				.getRequestDispatcher("view/foronrecord.jsp");
		homeDispatcher.forward(request, response);
		out.flush();
		out.close();
	}
}

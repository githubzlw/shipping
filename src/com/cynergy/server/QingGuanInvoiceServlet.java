package com.cynergy.server;
/**
 * 清关 INvoice
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
import com.cynergy.main.TranMoney;

public class QingGuanInvoiceServlet extends HttpServlet {
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
		String idString = request.getParameter("id");
		int id=Integer.parseInt(idString);
		Connection connection = DBHelper.getConnection();
		try {
			Statement createStatement = connection.createStatement();
			String sqlString="select id,nonum,date,address,transaction1,transaction2,volume,saildate,fromwhere,towhere,currency,yunfei from products where id="+id;
			ResultSet resultSet = createStatement.executeQuery(sqlString);
			request.setAttribute("seller", "SHANGHAI CS MANUFACTURING CO.");
			request.setAttribute("comAddress", "Room 605, 6 floor, Building No. 1, Hui Yin Ming Zun, No. 609 East Yun Ling Road, Putuo district, Shanghai City, China. 200062.");			
			String currency="";
			while(resultSet.next()){
				//产品编号
				request.setAttribute("nonum", resultSet.getString("nonum"));
				//编号日期
				request.setAttribute("date", resultSet.getString("date"));
				//发送地址
				request.setAttribute("address", resultSet.getString("address"));
				//交易方式
				request.setAttribute("transaction1", resultSet.getString("transaction1"));
				//货运方式
				request.setAttribute("transaction2", resultSet.getString("transaction2"));
				//体积
				request.setAttribute("volume", resultSet.getString("volume"));
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
			}
			String sql2="select itemeng,itemchn,shopingmark,quantity,unitprice,unitpriceall from items where proId="+id;
			ResultSet resultSet2 = createStatement.executeQuery(sql2);
			int total=0;
			double totalPrice=0;
			DecimalFormat df=new DecimalFormat(currency+"###,##0.00");
			while(resultSet2.next()){
				String itemeng = resultSet2.getString("itemeng");
				String itemchn = resultSet2.getString("itemchn");
				String shopingmark = resultSet2.getString("shopingmark");
				String quantity = resultSet2.getString("quantity");
//				int quant=Integer.parseInt(quantity);
				String unitprice = resultSet2.getString("unitprice");
				String unitpriceall = resultSet2.getString("unitpriceall");
				request.setAttribute("itemall"+total, itemeng+"  "+itemchn);
				request.setAttribute("quantity"+total, quantity);
				
				
				double unitpricessss = 0;
				double unitpriceallssss = 0;
				if(!("".equals(unitprice) || unitprice == null)){
					unitpricessss = Double.parseDouble(unitprice);
				}
				if(!(unitpriceall.trim().equals("") || unitpriceall == null)){
					unitpriceallssss = Double.parseDouble(unitpriceall);
				}
//				double perunit=unitpricessss/quant;
				
				request.setAttribute("unitprice"+total, df.format(unitpricessss));
				request.setAttribute("shopingmark"+total, shopingmark);
				
				totalPrice=totalPrice+unitpriceallssss;
				request.setAttribute("amount"+total, df.format(unitpriceallssss));
				total++;
			}
			request.setAttribute("total", total);
//			System.out.println("总是："+total);
			if(df==null || "".equals(df)){
				request.setAttribute("totalPrice", totalPrice);
			}else{
				request.setAttribute("totalPrice", df.format(totalPrice));
			}
			request.setAttribute("totalMoney", TranMoney.parse(totalPrice+""));
			
			RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/qingguaninvoice.jsp");
			homeDispatcher.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
 			DBHelper.returnConnection(connection);
 		}
		out.flush();
		out.close();
	}

}

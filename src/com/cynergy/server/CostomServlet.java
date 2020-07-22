package com.cynergy.server;
/**
 * 查看报关单
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cynergy.main.DBHelper;
import com.cynergy.main.MainSql;

public class CostomServlet extends HttpServlet {
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
//		System.out.println("nonum:+"+nonum+",dateTime:"+dateTime);
		String idString = request.getParameter("id");
		int id=Integer.parseInt(idString);
//		System.out.println("id:"+id);
		Connection connection = DBHelper.getConnection();
		try {
			Statement createStatement = connection.createStatement();
			String sqlString="select company_name,nonum,date,address,transaction1,transaction2,volume,saildate,fromwhere,towhere,currency,package,packagenum,totalGW,totalNW,yunfei,premium from products where id="+id;
			ResultSet resultSet = createStatement.executeQuery(sqlString);
			request.setAttribute("seller", "SHANGHAI CS MANUFACTURING CO.");
			request.setAttribute("comAddress", "Room 605, 6 floor, Building No. 1, Hui Yin Ming Zun, No. 609 East Yun Ling Road, Putuo district, Shanghai City, China. 200062.");
			String currency="";
			while(resultSet.next()){
				//客户公司名称
				request.setAttribute("companyName", resultSet.getString("company_name"));
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
				//包装类型
				request.setAttribute("package", resultSet.getString("package"));
				//件数
				request.setAttribute("packagenum", resultSet.getString("packagenum"));
				//毛重
				request.setAttribute("gw", resultSet.getString("totalGW"));
				//净重
				request.setAttribute("nw", resultSet.getString("totalNW"));
				//运费
				DecimalFormat df=new DecimalFormat("$###,##0.00");
				//运费
				String yunfei = resultSet.getString("yunfei");
				if(yunfei==null||yunfei.equals("")){
					request.setAttribute("yunfei", "");
				}else{
					request.setAttribute("yunfei", df.format(Double.parseDouble(yunfei)));
				}
				//保费
				String premium = resultSet.getString("premium");
				if(premium==null||premium.equals("")){
					request.setAttribute("premium", "");
				}else{
					request.setAttribute("premium", df.format(Double.parseDouble(premium)));
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
			}
			String sql2="select itemeng,itemchn,hscode,quantity,trueprice,nw,source_destination,unit from items where proId="+id;
			ResultSet resultSet2 = createStatement.executeQuery(sql2);
			int total=0;
			double totalPrice=0;
//			double totalgw=0;
//			double totalnw=0;
			DecimalFormat df=new DecimalFormat("###,##0.00");
			
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			while(resultSet2.next()){
				Map<String,Object> map = new HashMap<String,Object>();
//				String item = resultSet2.getString("item");
				String itemeng = resultSet2.getString("itemeng");
				String itemchn = resultSet2.getString("itemchn");
				String hscode = resultSet2.getString("hscode");
				String quantity = resultSet2.getString("quantity");
				String nw = resultSet2.getString("nw");
				String sourceDestination = resultSet2.getString("source_destination");
				String unit = resultSet2.getString("unit");
				double nwqq=Double.parseDouble(nw);
				int quant=Integer.parseInt(quantity);
				String trueprice = resultSet2.getString("trueprice");
				request.setAttribute("itemall"+total, itemeng+"  "+itemchn);
				request.setAttribute("quantity"+total, quantity+"  PCS");
				request.setAttribute("unitprice"+total, df.format(Double.parseDouble(trueprice)/quant));
				request.setAttribute("hscode"+total, hscode);
				request.setAttribute("nw"+total, nw+"  KGS");
				request.setAttribute("sourceDestination"+total, sourceDestination == null ? "" : sourceDestination);
				double price = Double.parseDouble(trueprice);
				totalPrice=totalPrice+price;
				request.setAttribute("amount"+total, df.format(price));
				request.setAttribute("amounts"+total, df.format(price/nwqq));
				
//				String gw = resultSet2.getString("gw");
//				if(gw!=null){
//					double gwD = Double.parseDouble(gw);
//					totalgw=totalgw+gwD;
//				}
//				if(nw!=null){
//					double nwD = Double.parseDouble(nw);
//					totalnw=totalnw+nwD;
//				}
				total++;
				
				map.put("itemall", itemeng+"  "+itemchn);
				map.put("quantity", quantity + " "+(unit == null ? "PCS" : unit));
				map.put("unitprice", df.format(Double.parseDouble(trueprice)/quant));
				map.put("hscode", hscode);
				map.put("nw", nw+"  KGS");
				map.put("sourceDestination", sourceDestination == null ? "" : sourceDestination);
				map.put("amount", df.format(price));
				map.put("amounts", df.format(price/nwqq));
				list.add(map);
				
			}
			request.setAttribute("total", total);
			request.setAttribute("list", list);
//			System.out.println("总是："+total);
			if(df==null){
				request.setAttribute("totalPrice", totalPrice);
			}else{
				request.setAttribute("totalPrice", df.format(totalPrice));
			}
//			DecimalFormat dfss=new DecimalFormat("#0.00");
//			request.setAttribute("gw", dfss.format(totalgw));
//			request.setAttribute("nw", dfss.format(totalnw));
		RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/costom.jsp");
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

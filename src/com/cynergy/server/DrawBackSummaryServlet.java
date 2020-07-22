package com.cynergy.server;
/**
 * 退税汇总
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cynergy.main.DBHelper;
import com.cynergy.main.MainSql;

public class DrawBackSummaryServlet extends HttpServlet {
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
		String year = request.getParameter("select1");
		String month = request.getParameter("select2");
		String ymString=year+"-"+month;
		Connection connection = DBHelper.getConnection();
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
				String sql2="select hscode,itemeng,itemchn,rate,purprice,quantity,trueprice from items where proId="+id;
				ResultSet res = createStatement1.executeQuery(sql2);
				int yunfeitotal=0;
	
				while (res.next()) {
//					request.setAttribute("id"+total, id);
					request.setAttribute("month"+total, year+"年"+month+"月");
					request.setAttribute("sale"+total, sale);
					request.setAttribute("num"+total, totalTrue);
					request.setAttribute("purnoall"+total, purnoall);
					if(yunfeitotal==0){
						request.setAttribute("yunfei"+total, df2.format(Double.parseDouble(yunfei)));
					}else{
						request.setAttribute("yunfei"+total, df2.format(0));
					}
					request.setAttribute("itemchn"+total, res.getString("itemchn"));
					request.setAttribute("clientName"+total, clientName);
					request.setAttribute("saildate"+total, saildate);
					
					String purprice = res.getString("purprice");
					if(purprice==null||purprice.trim().equals("")){
						purprice="0";
					}
					double parseDoubless = Double.parseDouble(purprice);
					totalPurprice+=parseDoubless;
					request.setAttribute("purprice"+total, df2.format(parseDoubless));
					
					String trueprice = res.getString("trueprice");
					if(trueprice==null||trueprice.trim().equals("")||trueprice.trim().equals("null")){
						trueprice="0";
					}
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
					
					request.setAttribute("trueprice"+total, df.format(Double.parseDouble(trueprice)));
					request.setAttribute("hscode"+total, res.getString("hscode"));
					String rate = res.getString("rate");
					System.out.println(rate+"=======");
					if(rate==null||rate.trim().equals("")){
						rate="";
					}
					request.setAttribute("rate"+total, res.getString("rate"));
					
//					double backMoney=0;
					DecimalFormat dfss = new DecimalFormat("0.00%");
					Number parse = null;
					double backMoney=0;
					try {
						if(!rate.equals("")){
							parse = dfss.parse(rate);
							backMoney=(parseDoubless/1.13)*parse.doubleValue();
							totalbackMoney +=backMoney;
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					request.setAttribute("backMoney"+total, df2.format(backMoney));
					total++;
					yunfeitotal++;
				}
			}
//			System.out.println("total:"+total+";totalTrue:"+totalTrue);
			DecimalFormat df=new DecimalFormat("###,##0.00");			
			request.setAttribute("total", total);
			request.setAttribute("year", Integer.parseInt(year));
			request.setAttribute("month", Integer.parseInt(month));
			request.setAttribute("totalPurprice", df.format(totalPurprice));
			request.setAttribute("totalTruepriceUSD", df.format(totalTruepriceUSD));
			request.setAttribute("totalTruepriceEUR", df.format(totalTruepriceEUR));
			request.setAttribute("totalTruepriceGBP", df.format(totalTruepriceGBP));
			request.setAttribute("totalTruepriceAUD", df.format(totalTruepriceAUD));
			request.setAttribute("totalbackMoney", df.format(totalbackMoney));
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
 			DBHelper.returnConnection(connection);
 		}
		RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/drawbackSummary.jsp");
		homeDispatcher.forward(request, response);
		out.flush();
		out.close();
	}

}

package com.cynergy.server;
/**
 * 点击查看packing
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

public class PackingServlet extends HttpServlet {
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
			String sqlString="select nonum,date,address,transaction1,transaction2,volume,saildate,fromwhere,towhere,package,packagenum,totalGW,totalNW from products where id="+id;
			ResultSet resultSet = createStatement.executeQuery(sqlString);
			request.setAttribute("seller", "SHANGHAI CS MANUFACTURING CO.");
			request.setAttribute("comAddress", "Room 605, 6 floor, Building No. 1, Hui Yin Ming Zun, No. 609 East Yun Ling Road, Putuo district, Shanghai City, China. 200062.");
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
				//包裹类型
				request.setAttribute("package", resultSet.getString("package"));
				//包裹数量
				request.setAttribute("packagenum", resultSet.getString("packagenum")+" "+resultSet.getString("package"));
				DecimalFormat df=new DecimalFormat("#0.00"); 
				String totalGW = resultSet.getString("totalGW");
				String totalNW = resultSet.getString("totalNW");
				
				if(totalGW!=null && !"".equals(totalGW)){
					request.setAttribute("totalgw", df.format(Double.parseDouble(totalGW)));
				}else{
					request.setAttribute("totalgw", df.format(0));
				}
				if(totalNW!=null  && !"".equals(totalNW)){
					request.setAttribute("totalnw", df.format(Double.parseDouble(totalNW)));
				}else{
					request.setAttribute("totalnw", df.format(0));
				}
			}
			String sql2="select itemeng,itemchn,shopingmark,quantity,nw from items where proId="+id;
			ResultSet resultSet2 = createStatement.executeQuery(sql2);
			int total=0;
			while(resultSet2.next()){
				String itemeng = resultSet2.getString("itemeng");
				String itemchn = resultSet2.getString("itemchn");
				request.setAttribute("itemall"+total, itemeng+"  "+itemchn);
				request.setAttribute("quantity"+total, resultSet2.getString("quantity"));
				request.setAttribute("shopingmark"+total, resultSet2.getString("shopingmark"));
				String nw = resultSet2.getString("nw");
				request.setAttribute("nw"+total, nw);
				total++;
			}
			request.setAttribute("total", total);
			RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/packing.jsp");
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
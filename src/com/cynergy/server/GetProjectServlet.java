package com.cynergy.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cynergy.main.DBHelper;
import com.cynergy.main.MainSql;

/**
 * 获取所有项目
 */
public class GetProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetProjectServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Connection connection = DBHelper.getConnection();
		String state=request.getParameter("state");
		if(state == null){
			state = "0";
		}
		System.out.println(state);
		try {
			Statement createStatement = connection.createStatement();
			String sqlDelivery = "select id,proId,proName,documentarySales,purchase,proThisUSD,proYearUSD,scheduledDelivery,newestDelivery,deductMoney,expoundPurchase,expoundTesting,expoundSale,instruction,correcting,qualityName from project where display=0 and  classification like '%交期拖延%' and state="+state+" order by createtime desc";
			 
			ResultSet res1 = createStatement.executeQuery(sqlDelivery);
			
			List<Object[]> list = new ArrayList<Object[]>();
			while (res1.next()) {
				Object[] objects = {res1.getString("proId"),res1.getString("proName"),res1.getString("documentarySales"),
						res1.getString("purchase"),res1.getString("proThisUSD"),res1.getString("proYearUSD"),res1.getString("scheduledDelivery"),
						res1.getString("newestDelivery"),res1.getString("id"),res1.getString("deductMoney"),res1.getString("expoundSale"),res1.getString("expoundPurchase"),res1.getString("expoundTesting"),res1.getString("instruction"),res1.getString("correcting"),res1.getString("qualityName")};
				list.add(objects);
			}
			request.setAttribute("delivery", list);
			request.setAttribute("state", state);
			
			String sqlQuality = "select id,proId,proName,documentarySales,purchase,proThisUSD,proYearUSD,scheduledDelivery,newestDelivery,expoundPurchase,expoundTesting,expoundSale,instruction,tdeductMoney,deductMoney,correcting,qualityName from project where display=0 and classification like '%质量有问题%' and state="+state+" order by createtime desc";
			ResultSet res2 = createStatement.executeQuery(sqlQuality);
			List<Object[]> listQuality = new ArrayList<Object[]>();
			while (res2.next()) {
				Object[] objects = {res2.getString("proName"),res2.getString("documentarySales"),res2.getString("purchase"),
						res2.getString("proThisUSD"),res2.getString("proYearUSD"),res2.getString("scheduledDelivery"),res2.getString("newestDelivery"),
						res2.getString("expoundSale"),res2.getString("expoundPurchase"),res2.getString("expoundTesting"),res2.getString("id"),res2.getString("proId"),res2.getString("instruction"),res2.getString("tdeductMoney"),res2.getString("deductMoney"),res2.getString("correcting"),res2.getString("qualityName")};
				listQuality.add(objects);
			}
			request.setAttribute("quality", listQuality);
			
			
			String sqlDelivery1 = "select id,proId,proName,documentarySales,purchase,proThisUSD,proYearUSD,scheduledDelivery,newestDelivery,deductMoney,expoundPurchase,expoundTesting,expoundSale,instruction,tdeductMoney,correcting,qualityName from project where display=0 and  classification like '%质量交期都有问题%' and state="+state+" order by createtime desc";
			 
			ResultSet res3 = createStatement.executeQuery(sqlDelivery1);
			
			List<Object[]> list1 = new ArrayList<Object[]>();
			while (res3.next()) {
				Object[] objects = {res3.getString("proId"),res3.getString("proName"),res3.getString("documentarySales"),
						res3.getString("purchase"),res3.getString("proThisUSD"),res3.getString("proYearUSD"),res3.getString("scheduledDelivery"),
						res3.getString("newestDelivery"),res3.getString("id"),res3.getString("deductMoney"),res3.getString("expoundSale"),res3.getString("expoundPurchase"),res3.getString("expoundTesting"),res3.getString("instruction"),res3.getString("tdeductMoney"),res3.getString("correcting"),res3.getString("qualityName")};
				list1.add(objects);
			}
			request.setAttribute("deliveryQuality", list1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
 			DBHelper.returnConnection(connection);
 		}
		RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/getproject.jsp");
		homeDispatcher.forward(request, response);
	}

}

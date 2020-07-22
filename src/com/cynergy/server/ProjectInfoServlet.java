package com.cynergy.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cynergy.main.DBHelper;
import com.cynergy.main.MainSql;

/**
 * 获取项目详细信息
 */
public class ProjectInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ProjectInfoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		Connection connection = DBHelper.getConnection();
		try {
			Statement createStatement = connection.createStatement();
			String sql = "select  id,proId,proName,proThisUSD,proYearUSD,qualityDepict,qualityTesting,deliveryDepict,scheduledDelivery,"
					+ "newestDelivery,documentarySales,purchase,classification,expoundPurchase,expoundTesting,expoundSale,"
					+ "liabilityRatio,lossesExpound,lossesMonney,FactoryDeduction,FactoryCompute,delayDay,createtime,state,"
					+ "newestDeliveryTeason,qualityLiabilityRatio,firmDelayDay,statetime,explain,delay,psdeductMoney,tdeductMoney,qualityName,correcting,isend from project where id = "+id; 
			ResultSet res = createStatement.executeQuery(sql); 
			 Map<String, Object> map = new HashMap<String, Object>();
			while (res.next()) {
				 map.put("id", res.getString("id"));
				 map.put("proId", res.getString("proId"));
				 map.put("proName", res.getString("proName"));
				 map.put("proThisUSD", res.getString("proThisUSD"));
				 map.put("proYearUSD", res.getString("proYearUSD"));
				 map.put("qualityDepict", res.getString("qualityDepict"));
				 map.put("qualityTesting", res.getString("qualityTesting"));
				 map.put("deliveryDepict", res.getString("deliveryDepict"));
				 map.put("scheduledDelivery", res.getString("scheduledDelivery"));
				 map.put("newestDelivery", res.getString("newestDelivery"));
				 map.put("documentarySales", res.getString("documentarySales"));
				 map.put("purchase", res.getString("purchase"));
				 map.put("classification", res.getString("classification"));
				 map.put("expoundPurchase", res.getString("expoundPurchase"));
				 map.put("expoundTesting", res.getString("expoundTesting"));
				 map.put("expoundSale", res.getString("expoundSale"));
				 map.put("liabilityRatio", res.getString("liabilityRatio"));
				 map.put("lossesExpound", res.getString("lossesExpound"));
				 map.put("lossesMonney", res.getString("lossesMonney"));
				 map.put("FactoryDeduction", res.getString("FactoryDeduction"));
				 map.put("FactoryCompute", res.getString("FactoryCompute"));
				 map.put("delayDay", res.getString("delayDay"));
				 map.put("createtime", res.getString("createtime"));
				 map.put("state", res.getString("state"));
				 map.put("newestDeliveryTeason", res.getString("newestDeliveryTeason"));
				 map.put("qualityLiabilityRatio", res.getString("qualityLiabilityRatio"));
				 map.put("firmDelayDay", res.getString("firmDelayDay"));
				 map.put("statetime", res.getString("statetime"));
				 map.put("explain", res.getString("explain"));
				 map.put("delay", res.getString("delay"));
				 map.put("psdeductMoney", res.getString("psdeductMoney"));
				 map.put("tdeductMoney", res.getString("tdeductMoney"));
				 map.put("qualityName", res.getString("qualityName"));
				 map.put("correcting", res.getString("correcting"));
				 map.put("isend", res.getString("isend"));
			}
			createStatement.close();
			request.setAttribute("project", map);
			RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/detailsProject.jsp");
			homeDispatcher.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
 			DBHelper.returnConnection(connection);
 		}
	}

}

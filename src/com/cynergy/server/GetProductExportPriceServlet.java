package com.cynergy.server;


import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

import com.cynergy.main.DBHelper;

/**
 * 获取product表出口金额
 * @ClassName GetProductExportPriceServlet 
 * @Description
 * @author zj
 * @date 2018年7月31日
 */
public class GetProductExportPriceServlet extends HttpServlet {
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
        Integer id = Integer.parseInt(request.getParameter("id"));
		Connection connection = DBHelper.getConnection();
		try {
			
			Statement createStatement = connection.createStatement();
			String sql = "select trueprice from items where id ="+id;
			ResultSet res1 = createStatement.executeQuery(sql);
			
			//出口总金额
			Double trueprice = 0.0;
			JSONObject jsonobj = new JSONObject(); 
			while (res1.next()) {
				trueprice = Double.parseDouble(res1.getString("trueprice"));
			}			
			jsonobj.put("trueprice", trueprice);
			   // 输出数据
		    out = response.getWriter();
		    out.println(jsonobj);
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

}

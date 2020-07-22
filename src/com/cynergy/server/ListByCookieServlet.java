package com.cynergy.server;
/**
 * 通过cookie信息 获取录入历史
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cynergy.main.DBHelper;
import com.cynergy.main.MainSql;

public class ListByCookieServlet extends HttpServlet {
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
		Connection connection = DBHelper.getConnection();
		Cookie[] cookies = request.getCookies();
		StringBuffer cookieValue=new StringBuffer();
		for(Cookie c:cookies){
			String name = c.getName();
			if(name.equals("shipcookie")){
				cookieValue.append(c.getValue());
				break;
			}
		}
		String allId=cookieValue.toString();
		String[] split = allId.split("&");
		
		
//		for(String ids:split){
//			if(ids!=null&&!ids.equals("")&&!ids.equals("0")){
//				int id = Integer.parseInt(ids);
//				
//			}
//		}
		
		
		
		
		try {
			Statement createStatement = connection.createStatement();
			int total = 0;
			for(String ids:split){
				if(ids!=null&&!ids.equals("")&&!ids.equals("0")){
					int id = Integer.parseInt(ids);
					String sql = "select timeDate,id,clientName from products where id="+id;
					ResultSet res = createStatement.executeQuery(sql);
					while (res.next()) {
						Timestamp timestamp = res.getTimestamp("timeDate");
						long time = timestamp.getTime();
						long currentTimeMillis = System.currentTimeMillis();
						if(currentTimeMillis>time+15*24*3600000){
//						if(currentTimeMillis>time+60000*2){
							request.setAttribute("status"+ total, 0);
						}else{
							request.setAttribute("status"+ total, 1);
						}
						request.setAttribute("clientName" + total, res.getString("clientName"));
						request.setAttribute("timeDate" + total, res.getString("timeDate"));
						request.setAttribute("id" + total, res.getString("id"));
						total++;
					}
				}
			}
			request.setAttribute("total", total);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
 			DBHelper.returnConnection(connection);
 		}
		RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/listbycookie.jsp");
		homeDispatcher.forward(request, response);
		out.flush();
		out.close();
	}
}

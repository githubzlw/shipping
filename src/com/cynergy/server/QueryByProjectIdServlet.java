package com.cynergy.server;
/**
 * 获取用户录入历史
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cynergy.main.DBHelper;

public class QueryByProjectIdServlet extends HttpServlet {
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
		HttpSession session = request.getSession();		
		try {
			Statement createStatement = connection.createStatement();
			String proIds = (String) session.getAttribute("proIds");
			String ids = "";
			if(!(proIds == null || "".equals(proIds))){
				proIds = proIds.substring(1, proIds.length());
				ids = "("+proIds+")";
			}
            if(ids == null || "".equals(ids)){
            	return;
            }
			int total = 0;
			String projectId = request.getParameter("projectId");
					String sql = "select timeDate,id,clientName,nonum from products where id in "+ids+" and nonum like "+"'%"+projectId+"%'";
					ResultSet res = createStatement.executeQuery(sql);
					while (res.next()) {
						Timestamp timestamp = res.getTimestamp("timeDate");
						long time = timestamp.getTime();
						long currentTimeMillis = System.currentTimeMillis();
						if(currentTimeMillis>time+15*24*3600000){
							request.setAttribute("status"+ total, 0);
						}else{
							request.setAttribute("status"+ total, 1);
						}
						request.setAttribute("clientName" + total, res.getString("clientName"));
						request.setAttribute("timeDate" + total, res.getString("timeDate"));
						request.setAttribute("id" + total, res.getString("id"));
						request.setAttribute("nonum" + total, res.getString("nonum"));
						total++;
					}
			request.setAttribute("total", total);
			
			
			//查询所有跟单
			int totalAuth = 0;
			String sql1 = "select name,id from admin where auth_post = 10";
			ResultSet res1 = createStatement.executeQuery(sql1);
			while (res1.next()) {
				request.setAttribute("authName" + totalAuth, res1.getString("name"));
				request.setAttribute("authId" + totalAuth, res1.getString("id"));
				totalAuth++;
			}
		
			
			request.setAttribute("total", total);
			request.setAttribute("totalAuth", totalAuth);
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

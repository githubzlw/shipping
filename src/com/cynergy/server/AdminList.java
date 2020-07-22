package com.cynergy.server;
/**
 * 管理员列表
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cynergy.main.DBHelper;
import com.cynergy.main.MainSql;

public class AdminList extends HttpServlet {
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
		int auth = (Integer) session.getAttribute("auth");
		if(auth==1){
			try {
				Statement createStatement = connection.createStatement();
				String sql = "select id,name,pass,auth from admin";
				ResultSet res = createStatement.executeQuery(sql);
				int total = 0;
				while (res.next()) {
					request.setAttribute("id" + total, res.getInt("id"));
					request.setAttribute("name" + total, res.getString("name"));
					request.setAttribute("pass" + total, res.getString("pass"));
					int int1 = res.getInt("auth");
					if(int1==1){
						request.setAttribute("auth" + total, "管理员");
					}else{
						request.setAttribute("auth" + total, "录入者");
					}
					total++;
				}
				request.setAttribute("total", total);
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
	 			DBHelper.returnConnection(connection);
	 		}
		}
		RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/adminList.jsp");
		homeDispatcher.forward(request, response);
		out.flush();
		out.close();
	}
}

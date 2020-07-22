package com.cynergy.server;
/**
 * 查看管理员信息
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

import com.cynergy.main.DBHelper;
import com.cynergy.main.MainSql;

public class AdminInfo extends HttpServlet {
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
			String sqlString="select id,name,pass,auth from admin where id="+id;
			ResultSet resultSet = createStatement.executeQuery(sqlString);
			if(resultSet.next()){
				//admin  id
				request.setAttribute("id", resultSet.getInt("id"));
				//名称
				request.setAttribute("name", resultSet.getString("name"));
				//密码
				request.setAttribute("pass", resultSet.getString("pass"));
				//权限
				request.setAttribute("auth", resultSet.getInt("auth"));
			}
			RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/adminupdate.jsp");
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

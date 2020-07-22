package com.cynergy.server;
/**
 * 获取hscode解释
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

public class ExplainServlet extends HttpServlet {
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
		String hscode = request.getParameter("hscode");
		Connection connection = DBHelper.getConnection();
		try {
//			System.out.println("hscode:"+hscode);
			Statement createStatement = connection.createStatement();
//			String createSql="create table hscodes (id int identity (1,1) primary key,hscode varchar(50),content varchar(250));";
//			System.out.println(createSql);
//			boolean execute = createStatement.execute(createSql);
//			System.out.println(execute);
			
			String selectSql="select id,hscode,content from hscodes where hscode='"+hscode+"'";
			System.out.println(selectSql);
			ResultSet resultSet = createStatement.executeQuery(selectSql);
			int total=0;
			while (resultSet.next()) {
				total++;
				request.setAttribute("id", resultSet.getInt("id"));
				request.setAttribute("hscode", resultSet.getString("hscode"));
				request.setAttribute("content", resultSet.getString("content"));
			}
			if(total==0){
				request.setAttribute("hscode", hscode);
				request.setAttribute("content", "");
			}
			RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/explain.jsp");
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

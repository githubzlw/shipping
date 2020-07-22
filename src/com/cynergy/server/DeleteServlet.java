package com.cynergy.server;
/**
 * 删除项目 用户录入的项目
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cynergy.main.DBHelper;
import com.cynergy.main.MainSql;

public class DeleteServlet extends HttpServlet {

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
		String ids = request.getParameter("id");
		int id=Integer.parseInt(ids);
		try {
			Statement createStatement = connection.createStatement();
			String sql1="delete products where id="+id;
			String sql2="delete items where proId="+id;
			String sql3="delete contract where proId="+id;
			String sql4="delete contract_items where proId="+id;
			createStatement.execute(sql1);
			createStatement.execute(sql2);
			createStatement.execute(sql3);
			createStatement.execute(sql4);
			out.println("删除成功");
//			RequestDispatcher homeDispatcher = request.getRequestDispatcher("InfoServlet");
//			homeDispatcher.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			out.println("删除失败");
		}finally {
 			DBHelper.returnConnection(connection);
 		}
		out.flush();
		out.close();
	}

}

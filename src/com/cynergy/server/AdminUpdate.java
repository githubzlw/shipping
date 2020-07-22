package com.cynergy.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cynergy.main.DBHelper;

public class AdminUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Connection connection = DBHelper.getConnection();
		String ids = request.getParameter("id");
		int id=Integer.parseInt(ids);
		try {
			String name=request.getParameter("name");
			String pass=request.getParameter("pass");
			int auth=0;
			String authStr=request.getParameter("auth");
			if(authStr!=null&&!authStr.trim().equals("")){
				auth=Integer.parseInt(authStr);
			}
			System.out.println(name+"==="+pass+"==="+id);
			if(name!=null&&!name.trim().equals("")&&pass!=null&&!pass.trim().equals("")){
				String sql2="update admin set name=?,pass=?,auth=? where id=?";
				PreparedStatement statement2 = connection.prepareStatement(sql2);
				statement2.setString(1, name);
				statement2.setString(2, pass);
				statement2.setInt(3, auth);
				statement2.setInt(4, id);
				statement2.executeUpdate();
				statement2.close();
			}
			
			RequestDispatcher homeDispatcher = request.getRequestDispatcher("AdminList");
			homeDispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			out.println("失败");
		}finally {
 			DBHelper.returnConnection(connection);
 		}
		out.flush();
		out.close();
	}
	

}

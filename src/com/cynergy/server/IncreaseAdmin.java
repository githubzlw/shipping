package com.cynergy.server;
/**
 * 增加用户
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cynergy.main.DBHelper;
import com.cynergy.main.MainSql;

public class IncreaseAdmin extends HttpServlet {
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
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		if((userName!=null&&!userName.trim().equals("")&&(password!=null&&!password.trim().equals("")))){
			try {
				if(userName!=null){
					userName=userName.trim();
				}
				if(password!=null){
					password=password.trim();
				}
				String ss="insert into admin(name,pass,auth) values(?,?,0)";
				PreparedStatement statement = connection.prepareStatement(ss,Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, userName);
				statement.setString(2, password);
				int executeUpdate = statement.executeUpdate();
				if(executeUpdate>0){
					RequestDispatcher homeDispatcher = request.getRequestDispatcher("AdminList");
					homeDispatcher.forward(request, response);
				}else{
					RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/increase.jsp");
					homeDispatcher.forward(request, response);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
	 			DBHelper.returnConnection(connection);
	 		}
		}else{
			out.println("添加用户失败");
		}
		out.flush();
		out.close();
	}
}

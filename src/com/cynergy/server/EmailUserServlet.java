package com.cynergy.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cynergy.main.Base64Encode;
import com.cynergy.main.DBHelper;






public class EmailUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try{
		PrintWriter out = response.getWriter();
		String userInfo = request.getParameter("userInfo");
		String name=Base64Encode.getFromBase64(userInfo);
		String []mad=name.split(",");
		String userName=mad[0];
		String password=mad[1];
		
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(-1);
		if((userName!=null&&!userName.trim().equals(""))&&(password!=null&&!password.trim().equals(""))){
			Connection connection = DBHelper.getConnection();
			System.out.println(userName+"==="+password);
			int userid=0;
			int auth=0;
			String adminName="";
			String proIdsString="";
			try {
				Statement createStatement = connection.createStatement();
				String sql="select id,name,pass,auth,proIds from admin where name='"+userName+"' and pass='"+password+"'";
				ResultSet res = createStatement.executeQuery(sql);
				while (res.next()) {
					userid=res.getInt("id");
					auth=res.getInt("auth");
					adminName=res.getString("name");
					proIdsString = res.getString("proIds");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
	 			DBHelper.returnConnection(connection);
	 		}
			System.out.println(userid+"===="+auth+"===="+proIdsString);
			if(userid!=0){
				session.setAttribute("auth", auth);
				session.setAttribute("adminId", userid);
				session.setAttribute("adminName", adminName);
				
				
			   //??????cookie
	 		   Cookie cookie1 = new Cookie("auth",auth+"");           
	 		   cookie1.setPath("/");
	 		   cookie1.setMaxAge(60*60*24*365);
	 		   response.addCookie(cookie1);
	 		   Cookie cookie2 = new Cookie("adminId",userid+"");           
	 		   cookie2.setPath("/");
	 		   cookie2.setMaxAge(60*60*24*365);
	 		   response.addCookie(cookie2);
				if(proIdsString==null){
					session.setAttribute("proIds", "");
				}else{
					session.setAttribute("proIds", proIdsString);
				}
				RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/main.jsp");
				homeDispatcher.forward(request, response);
			}else{
				RequestDispatcher homeDispatcher = request.getRequestDispatcher("index.jsp");
				homeDispatcher.forward(request, response);
			}
		}else{
			Integer aaa = (Integer) session.getAttribute("adminId");
			if(aaa!=null&&aaa.intValue()!=0){
				RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/main.jsp");
				homeDispatcher.forward(request, response);
			}else{
				RequestDispatcher homeDispatcher = request.getRequestDispatcher("index.jsp");
				homeDispatcher.forward(request, response);
			}
		}
		out.flush();
		out.close();
			
		
		}catch(Exception e){
				}

	}   
}


package com.cynergy.server;
/**
 * 保存 hscode 解释
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

public class SaveExplainServlet extends HttpServlet {
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
		
		try {
			Statement createStatement = connection.createStatement();
			String hscode=request.getParameter("hscode");
			if(hscode!=null){
				hscode=hscode.trim();
			}
			String content=request.getParameter("content");
			if(content!=null){
				content=content.trim();
			}
			String ids = request.getParameter("id");
			if(ids!=null){
				int id=Integer.parseInt(ids);
				String sql2="update hscodes set hscode='"+hscode+"',content='"+content+ "' where id="+id;
//				System.out.println("修改语句："+sql2);
				createStatement.executeUpdate(sql2);
			}else{
				String ss33="insert into hscodes(hscode,content) values('"+hscode+"','"+content+"')";
//				System.out.println("插入语句："+ss33);
				createStatement.execute(ss33);
			}
			out.println("成功");
//			RequestDispatcher homeDispatcher = request.getRequestDispatcher("InfoServlet");
//			homeDispatcher.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			out.println("失败");
		}finally {
 			DBHelper.returnConnection(connection);
 		}
		out.flush();
		out.close();
	}

}

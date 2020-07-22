package com.cynergy.server;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cynergy.main.DBHelper;
import com.cynergy.main.MainSql;

/**
 * 删除项目
 */
public class DeleteProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public DeleteProjectServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		String proId = request.getParameter("proId");
		if(proId!=null){
			proId=proId.trim();
		}
		System.out.println(proId);
		Connection connection = DBHelper.getConnection();
		try {
			Statement createStatement = connection.createStatement();
			String sql1 =  "select id from project where id='" + proId + "' and display=0 ";
			System.out.println("sql:"+sql1);
			ResultSet rs = createStatement.executeQuery(sql1);
			int id = 0;
			while (rs.next()) {
				 id = rs.getInt("id");
					System.out.println(id);
			}
			if(id == 0){
				PrintWriter out = response.getWriter();
				out.print("<script type='text/javascript'>alert('项目ID不存在');document.location.href='SendDeleteServlet';</script>");
				out.close();
				return ;
			}
			String sql = "update  project set display = 1 where id = " + proId;
			createStatement.executeUpdate(sql);
			PrintWriter out = response.getWriter();
			out.print("<script type='text/javascript'>alert('删除成功');document.location.href='SendDeleteServlet';</script>");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
 			DBHelper.returnConnection(connection);
 		}
	}

	
	 
}

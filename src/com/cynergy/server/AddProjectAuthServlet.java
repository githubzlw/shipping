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
import org.apache.commons.lang.StringUtils;
import com.cynergy.main.DBHelper;

public class AddProjectAuthServlet extends HttpServlet {
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
			Integer authId = Integer.parseInt(request.getParameter("authId"));
			String proId = request.getParameter("proId");	
			String proIds = "";
			Statement createStatement = connection.createStatement();			
			String sql = "select proIds from admin where id="+authId;
			ResultSet res = createStatement.executeQuery(sql);
			while (res.next()) {
			       proIds = res.getString("proIds");
			       if(StringUtils.isNotBlank(proIds)){
				       proIds = proIds + "," + proId;
			       }else{
			    	   proIds = "," + proId; 
			       }
			}
           
			
			//添加项目号
			if(StringUtils.isNotBlank(proId)){
				sql = "update admin set proIds = '"+proIds+"' where id ="+authId;
				createStatement.executeUpdate(sql);
				createStatement.close();
			}
			
		            
		            
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

}

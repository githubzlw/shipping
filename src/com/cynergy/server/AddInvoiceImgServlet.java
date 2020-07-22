package com.cynergy.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

import com.cynergy.main.DBHelper;

public class AddInvoiceImgServlet extends HttpServlet {
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
		JSONObject jsonobj = new JSONObject(); 
		PrintWriter out = response.getWriter();
        String factoryName = request.getParameter("factoryName");
        String fileName = request.getParameter("fileName");
		String idString = request.getParameter("proId");
		int proId = Integer.parseInt(idString);
        
		try {		            		
        		//如果合同为空，则提示
        		//如果合同不为空，则插入表
        		if(StringUtils.isBlank(idString)){
        			jsonobj.put("message", "未获取到主键id");	
        		}else{
        			Connection connection = DBHelper.getConnection();
        			String sql = "insert into invoice_pic(proId,factory_name,pic_name,create_time) values(?,?,?,getdate())";
        			PreparedStatement statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        			statement.setInt(1, proId);
        			statement.setString(2, factoryName);
        			statement.setString(3, fileName);      			
        			statement.executeUpdate();
        			int invoiceId=0;
        			ResultSet res = statement.getGeneratedKeys();
        			if (res.next()){
        				invoiceId = res.getInt(1);
        			}
        			statement.close();
        			jsonobj.put("invoiceId", invoiceId);
        		}		            				            		
 			    // 输出数据
	 		    out = response.getWriter();
	 		    out.println(jsonobj);
		            
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

}

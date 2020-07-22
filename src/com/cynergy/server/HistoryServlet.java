package com.cynergy.server;
/**
 * 获取用户录入历史
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.cynergy.main.DBHelper;
import com.cynergy.main.MainSql;

public class HistoryServlet extends HttpServlet {
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
		HttpSession session = request.getSession();
		try {
			Statement createStatement = connection.createStatement();
			int total = 0;
			String proIds = (String) session.getAttribute("proIds");
			System.out.println(proIds+"===HistoryServlet=====");
			String[] split = proIds.split(",");
			for(String ids:split){
				if(ids!=null&&!ids.equals("")&&!ids.equals("0")){
					int id = Integer.parseInt(ids);
					String sql = "select timeDate,id,clientName,nonum,s.status,excel_path,s1.itemCount,s2.contractCount from products p LEFT JOIN "+  
									"("+
									"select count(1) as status,min(proId)as proId from items where items.proId = "+id+" and (hscode is null or hscode ='')"+
									")s on p.id = s.proId "
									+"LEFT JOIN "
									+"("
									+"select count(1) as itemCount,min(proId)as proId from items where items.proId = "+id+""
									+")s1 on p.id = s1.proId "
									+"LEFT JOIN "
									+"("
									+"select count(1) as contractCount,min(proId)as proId from contract where contract.proId = "+id+"" 
									+")s2 on p.id = s2.proId " 
									+ "where p.id = "+ id;
					ResultSet res = createStatement.executeQuery(sql);
					while (res.next()) {
						
						//是否允许更新
						int queryStatus = res.getInt("status");
						Timestamp timestamp = res.getTimestamp("timeDate");
						int itemCount = res.getInt("itemCount");
						int contractCount = res.getInt("contractCount");
						String excelPath = res.getString("excel_path");
						long time = timestamp.getTime();
						long currentTimeMillis = System.currentTimeMillis();
						if(currentTimeMillis>time+20*24*3600000 && queryStatus == 0){
								if(itemCount>1 && contractCount>1 && StringUtils.isBlank(excelPath)){
									request.setAttribute("status"+ total, 1);							
								}else{
									request.setAttribute("status"+ total, 0);
								}							
						}else{
							request.setAttribute("status"+ total, 1);
						}
						request.setAttribute("clientName" + total, res.getString("clientName"));
						request.setAttribute("timeDate" + total, res.getString("timeDate"));
						request.setAttribute("id" + total, res.getString("id"));
						request.setAttribute("nonum" + total, res.getString("nonum"));
						total++;
					}
				}
			}
			
			
			//查询所有跟单
			int totalAuth = 0;
			String sql = "select name,id from admin where auth_post = 10";
			ResultSet res = createStatement.executeQuery(sql);
			while (res.next()) {
				request.setAttribute("authName" + totalAuth, res.getString("name"));
				request.setAttribute("authId" + totalAuth, res.getString("id"));
				totalAuth++;
			}
		
			
			request.setAttribute("total", total);
			request.setAttribute("totalAuth", totalAuth);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
 			DBHelper.returnConnection(connection);
 		}
		RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/listbycookie.jsp");
		homeDispatcher.forward(request, response);
		out.flush();
		out.close();
	}
}

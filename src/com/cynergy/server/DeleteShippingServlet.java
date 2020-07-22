package com.cynergy.server;
/**
 * 删除电子出货单
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

import org.apache.commons.lang.StringUtils;

import com.cynergy.main.DBHelper;

public class DeleteShippingServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Connection connection = DBHelper.getConnection();
		String ids = request.getParameter("id");
		//如果电子出货单删除，则删除记录
		String sids = request.getParameter("sid");
		int id=Integer.parseInt(ids);
		Integer sid = null;
		if(StringUtils.isNotBlank(sids)){
			sid = Integer.parseInt(sids);
		}
		try {
			Statement createStatement = connection.createStatement();
			String sql="delete shipping_contract where id="+ id;
			if(sid!=null){
				sql="delete shipping_contract where sid="+ sid;
			}
			
			createStatement.execute(sql);
			out.println("删除成功");

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

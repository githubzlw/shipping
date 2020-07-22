package com.cynergy.server;
/**
 * 进入查询界面时 
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.cynergy.main.DBHelper;
import com.cynergy.main.MainSql;

public class QuestFirstServlet extends HttpServlet {
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
		
//		Integer checkpwd = (Integer) request.getSession().getAttribute("checkpwd");
//		if(checkpwd!=null&&checkpwd==1){
			Connection connection = DBHelper.getConnection();
			try {
				Statement createStatement = connection.createStatement();
				HttpSession session = request.getSession();
				String adminName = (String)session.getAttribute("adminName");
	//			SELECT TOP 10 UserID FROM TB_User ORDER BY UserID DESC(最大前10)
				String sql="select TOP 30 min(timeDate)timeDate,products.id,min(nonum)nonum,min(order_status)order_status,min(adminName)adminName,min(items.trueprice)trueprice,min(items.hscode)hscode,min(items.rate)rate from products "
                             +"left join items on products.id = items.proId GROUP BY products.id order by timeDate desc";
				if("ERIC".equals(adminName)){
					sql="select TOP 30 min(timeDate)timeDate,products.id,min(nonum)nonum,min(order_status)order_status,min(adminName)adminName,min(items.trueprice)trueprice,min(items.hscode)hscode,min(items.rate)rate from products "   
                        +"left join items on products.id = items.proId where adminName = 'ERIC' GROUP BY products.id  order by timeDate desc";
				}
				ResultSet res = createStatement.executeQuery(sql);
				int total = 0;
				while (res.next()) {
					request.setAttribute("nonum"+total, res.getString("nonum"));
					request.setAttribute("adminName"+total, res.getString("adminName"));
					request.setAttribute("timeDate"+total, res.getString("timeDate"));
					request.setAttribute("id"+total, res.getString("id"));
					request.setAttribute("trueprice"+total, res.getString("trueprice"));
					request.setAttribute("hscode"+total, res.getString("hscode"));
					request.setAttribute("rate"+total, res.getString("rate"));
					request.setAttribute("orderStatus"+total, res.getInt("order_status"));
					
					
					Statement createStatement1 = connection.createStatement();
					String sql1="select s.id,c.purno,s.serial_number,s.is_complete,s.sid,c.is_extra_invoice from contract c LEFT JOIN shipping_contract s on c.proId = s.proId and c.purno like '%'+replace(s.purno,'SHS','')+'%' where c.proId ="+ res.getString("id");
					ResultSet res1 = createStatement1.executeQuery(sql1);
					int state = 0;                     //出运单状态 0：未录   1：已录已确认  2：已录未确认
					Boolean complete = true;           //是否全部完成
					int totalCount = 0;
					while (res1.next()) {
						String serialNumber = res1.getString("serial_number");
						int isComplete = res1.getInt("is_complete");
						//如果是带票不需要出货单
					   int isExtraInvoice = res1.getInt("is_extra_invoice");
						
						if(StringUtils.isNotBlank(serialNumber)){
							if(isComplete == 0){
								state = 2;
								complete = false;
							}
						}else{
							if(isExtraInvoice == 0){
								state = 0;
								complete = false;
							}
						}
						totalCount++;
					}	
					request.setAttribute("state"+total, state);
					request.setAttribute("complete"+total, totalCount == 0 ? false :complete);
					total++;
				}
				request.setAttribute("total", total);
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
	 			DBHelper.returnConnection(connection);
	 		}
			RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/query.jsp");
			homeDispatcher.forward(request, response);
//		}else{
//			request.getSession().setAttribute("whereview", "query");
//			RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/check.jsp");
//			homeDispatcher.forward(request, response);
//		}
		out.flush();
		out.close();
	}

}

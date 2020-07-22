package com.cynergy.server;
/**
 * 查询 条件查询
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

import org.apache.commons.lang.StringUtils;

import com.cynergy.main.DBHelper;
import com.cynergy.main.MainSql;

public class QuestServlet extends HttpServlet {
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
		String nonum = request.getParameter("nonum");
		String hscode = request.getParameter("hscode");
		String clientName = request.getParameter("clientName");
		String purno = request.getParameter("purno");
		String adminName = request.getParameter("adminName");
		String goodsEng = request.getParameter("goodsEng");
		String goodsChn = request.getParameter("goodsChn");
		String time1 = request.getParameter("time1");
		String time2 = request.getParameter("time2");
		String time3 = request.getParameter("time3");
		String time4 = request.getParameter("time4");
		String isShippingStr = request.getParameter("isShipping");
//		System.out.println("参数nonum:"+nonum+",hscode:"+hscode+",clientName:"+clientName+",purno:"+purno);
		if(nonum!=null&&!nonum.trim().equals("")){
			Connection connection = DBHelper.getConnection();
			try {
				Statement createStatement = connection.createStatement();
				//"select id,nonum,timeDate,adminName from products where nonum like '%"+nonum.trim()+"%'";
				String sql=  "select min(timeDate)timeDate,products.id,min(nonum)nonum,min(order_status)order_status,min(adminName)adminName,min(items.trueprice)trueprice,min(items.hscode)hscode,min(items.rate)rate from products "
                        +"left join items on products.id = items.proId where nonum like '%"+nonum.trim()+"%' GROUP BY products.id order by timeDate desc"; 
				
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
					String sql1="select s.id,c.purno,s.serial_number,s.is_complete,s.sid,c.is_extra_invoice from contract c LEFT JOIN shipping_contract s on  c.proId = s.proId and c.purno like '%'+replace(s.purno,'SHS','')+'%' where c.proId ="+ res.getString("id");
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
		}
		if(hscode!=null&&!hscode.trim().equals("")){
			Connection connection = DBHelper.getConnection();
			try {
				Statement createStatement = connection.createStatement();
//				String sql="select proId,nonum,timeDate from items where hscode like '%"+hscode.trim()+"%'";
//				select id,nonum from products where id in (select proId from items where hscode like '%5%')
//				String sql="select id,nonum,timeDate,adminName from products as p where p.id in (select proId from items where hscode like '%"+hscode.trim()+"%')";
				
				String sql=  "select min(timeDate)timeDate,products.id,min(nonum)nonum,min(order_status)order_status,min(adminName)adminName,min(items.trueprice)trueprice,min(items.hscode)hscode,min(items.rate)rate from products "
                        +"left join items on products.id = items.proId where hscode like '%"+hscode.trim()+"%' GROUP BY products.id order by timeDate desc"; 
				
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
							if(isExtraInvoice==0){
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
		}
		if(goodsEng!=null&&!goodsEng.trim().equals("")){
			Connection connection = DBHelper.getConnection();
			try {
				Statement createStatement = connection.createStatement();
//				String sql="select id,nonum,timeDate,adminName from products as p where p.id in (select proId from items where itemeng like '%"+goodsEng.trim()+"%')";
				
				String sql=  "select min(timeDate)timeDate,products.id,min(nonum)nonum,min(order_status)order_status,min(adminName)adminName,min(items.trueprice)trueprice,min(items.hscode)hscode,min(items.rate)rate from products "
                        +"left join items on products.id = items.proId where itemeng like '%"+goodsEng.trim()+"%' GROUP BY products.id order by timeDate desc"; 
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
							if(isExtraInvoice==0){
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
		}
		if(goodsChn!=null&&!goodsChn.trim().equals("")){
			Connection connection = DBHelper.getConnection();
			try {
				Statement createStatement = connection.createStatement();
//				String sql="select id,nonum,timeDate,adminName from products as p where p.id in (select proId from items where itemchn like '%"+goodsChn.trim()+"%')";
				String sql=  "select min(timeDate)timeDate,products.id,min(nonum)nonum,min(order_status)order_status,min(adminName)adminName,min(items.trueprice)trueprice,min(items.hscode)hscode,min(items.rate)rate from products "
                        +"left join items on products.id = items.proId where itemchn like '%"+goodsChn.trim()+"%' GROUP BY products.id order by timeDate desc"; 
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
		}
		if(clientName!=null&&!clientName.trim().equals("")){
			Connection connection = DBHelper.getConnection();
			try {
				Statement createStatement = connection.createStatement();
//				String sql="select id,nonum,timeDate,adminName from products where clientName like '%"+clientName.trim()+"%'";
				String sql=  "select min(timeDate)timeDate,products.id,min(nonum)nonum,min(order_status)order_status,min(adminName)adminName,min(items.trueprice)trueprice,min(items.hscode)hscode,min(items.rate)rate from products "
                        +"left join items on products.id = items.proId where clientName like '%"+clientName.trim()+"%' GROUP BY products.id order by timeDate desc"; 
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
		}
		if(purno!=null&&!purno.trim().equals("")){
			Connection connection = DBHelper.getConnection();
			try {
				Statement createStatement = connection.createStatement();
//				String sql="select id,nonum,timeDate,adminName from products as p where p.id in (select proId from contract where purno like '%"+purno.trim()+"%')";
				String sql= "select min(timeDate)timeDate,p.id,min(p.nonum)nonum,min(order_status)order_status,min(p.adminName)adminName, "
				+"min(items.trueprice)trueprice,min(items.hscode)hscode,min(items.rate)rate from  "
				+"("
				+"select id,nonum,timeDate,adminName,order_status from products as p where p.id in (select proId from contract where purno like '%"+purno.trim()+"%') "
				+")p "  
				+"left join items on p.id = items.proId  GROUP BY p.id  order by timeDate desc ";
				//				String sql="select id,nonum,timeDate from products p (select proId from contract where purno like '%"+purno.trim()+"%') as tt wehre tt.proId==p.id";
//				String sql="select proId from contract where purno like '%"+purno.trim()+"%'";
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
		}
		if(adminName!=null&&!adminName.trim().equals("")){
			Connection connection = DBHelper.getConnection();
			try {
				Statement createStatement = connection.createStatement();
//				String sql="select id,nonum,timeDate,adminName from products where adminName like '%"+adminName.trim()+"%'";
				String sql=  "select min(timeDate)timeDate,products.id,min(nonum)nonum,min(order_status)order_status,min(adminName)adminName,min(items.trueprice)trueprice,min(items.hscode)hscode,min(items.rate)rate from products "
                        +"left join items on products.id = items.proId where adminName like '%"+adminName.trim()+"%' GROUP BY products.id order by timeDate desc"; 
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
		}
		
		
		if(StringUtils.isNotBlank(time1) || StringUtils.isNotBlank(time2) || StringUtils.isNotBlank(time3) || StringUtils.isNotBlank(time4)){
			Integer isShipping = Integer.parseInt(isShippingStr);
			Connection connection = DBHelper.getConnection();
			try {
				Statement createStatement = connection.createStatement();
//				String sql="select id,nonum,timeDate,adminName from products where adminName like '%"+adminName.trim()+"%'";
				String sql= "select min(timeDate)timeDate,products.id,min(nonum)nonum,min(order_status)order_status,min(adminName)adminName,min(items.trueprice)trueprice,min(items.hscode)hscode,min(items.rate)rate from products "
	                        +"left join items on products.id = items.proId where 1=1  "
	                        		+((StringUtils.isNotBlank(time1) && StringUtils.isNotBlank(time2)) ? "and timeDate >'"+time1+"' and timeDate <= '"+ time2 +"'" : "")
	                        		+((StringUtils.isNotBlank(time1) && StringUtils.isBlank(time2)) ? "and timeDate > '"+time1+"'" : "")
	                        		+((StringUtils.isBlank(time1) && StringUtils.isNotBlank(time2)) ? "and timeDate <= '"+time2+"'" : "")
	                                +((StringUtils.isNotBlank(time3) && StringUtils.isNotBlank(time4)) ? "and saildate > '"+time3+"' and saildate <= '"+time4 +"'" : "")
	                        		+((StringUtils.isNotBlank(time3) && StringUtils.isBlank(time4)) ? " and saildate > '"+time3+"'" : "")
	                        		+((StringUtils.isBlank(time3) && StringUtils.isNotBlank(time4)) ? "and saildate <= '"+time4+"'" : "")
	                        		+ "  GROUP BY products.id order by timeDate desc"; 				

				ResultSet res = createStatement.executeQuery(sql);
				int total = 0;
				while (res.next()) {
							
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
					if(isShipping == 0){
						request.setAttribute("nonum"+total, res.getString("nonum"));
						request.setAttribute("adminName"+total, res.getString("adminName"));
						request.setAttribute("timeDate"+total, res.getString("timeDate"));
						request.setAttribute("id"+total, res.getString("id"));
						request.setAttribute("trueprice"+total, res.getString("trueprice"));
						request.setAttribute("hscode"+total, res.getString("hscode"));
						request.setAttribute("rate"+total, res.getString("rate"));
						request.setAttribute("orderStatus"+total, res.getInt("order_status"));
						total++;
					}else if(isShipping == 1 && complete == true){
						request.setAttribute("nonum"+total, res.getString("nonum"));
						request.setAttribute("adminName"+total, res.getString("adminName"));
						request.setAttribute("timeDate"+total, res.getString("timeDate"));
						request.setAttribute("id"+total, res.getString("id"));
						request.setAttribute("trueprice"+total, res.getString("trueprice"));
						request.setAttribute("hscode"+total, res.getString("hscode"));
						request.setAttribute("rate"+total, res.getString("rate"));
						request.setAttribute("orderStatus"+total, res.getInt("order_status"));
						total++;
					}else if(isShipping == 2 && complete == false){
						request.setAttribute("nonum"+total, res.getString("nonum"));
						request.setAttribute("adminName"+total, res.getString("adminName"));
						request.setAttribute("timeDate"+total, res.getString("timeDate"));
						request.setAttribute("id"+total, res.getString("id"));
						request.setAttribute("trueprice"+total, res.getString("trueprice"));
						request.setAttribute("hscode"+total, res.getString("hscode"));
						request.setAttribute("rate"+total, res.getString("rate"));
						request.setAttribute("orderStatus"+total, res.getInt("order_status"));
						total++;
					}
				}
				request.setAttribute("total", total);
				request.setAttribute("time1", time1);
				request.setAttribute("time2", time2);
				request.setAttribute("time3", time3);
				request.setAttribute("time4", time4);
				request.setAttribute("isShipping", isShipping);
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
	 			DBHelper.returnConnection(connection);
	 		}
		}
		
		
		RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/query.jsp");
		homeDispatcher.forward(request, response);
		out.flush();
		out.close();
	}

}

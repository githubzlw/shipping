package com.cynergy.server;
/**
 * 修改项目时 先获取信息
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
import com.cynergy.main.WebCookie;

public class InfoServlet extends HttpServlet {
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
		String idString = request.getParameter("id");
		int id = Integer.parseInt(idString);
		
		HttpSession session = request.getSession();
		//添加权限管理  每个人只能看到自己项目   Author:polo   2017/11/30
		String auth = WebCookie.getCookieByName(request, "auth");
		String proIds = session.getAttribute("proIds").toString();
		
		String pds = "";
		if(!(proIds == null || "".equals(proIds))){
			pds = proIds.substring(1);
		}
		
		
		Connection connection = DBHelper.getConnection();
		try {
			Statement createStatement = connection.createStatement();
			String sql1;
			if(Integer.parseInt(auth.toString()) == 1){
				sql1="select * from products where id ="+id;
			}else{
			    sql1="select * from products where id ="+id+" and id in ("+pds+")";
			}
			
			ResultSet res1 = createStatement.executeQuery(sql1);
			while (res1.next()) {
				request.setAttribute("purchase", res1.getString("purchase"));
				request.setAttribute("id", res1.getString("id"));
				request.setAttribute("sale", res1.getString("sale"));
				request.setAttribute("clientName", res1.getString("clientName"));
				request.setAttribute("address", res1.getString("address"));
				request.setAttribute("hopeDate", res1.getString("hopeDate"));
				request.setAttribute("estimateDate", res1.getString("estimateDate"));
				request.setAttribute("transaction1", res1.getString("transaction1"));
				request.setAttribute("transaction2", res1.getString("transaction2"));
				request.setAttribute("volume", res1.getString("volume"));
				request.setAttribute("totalGW", res1.getString("totalGW"));
				request.setAttribute("totalNW", res1.getString("totalNW"));
				request.setAttribute("saildate", res1.getString("saildate"));
				request.setAttribute("arriveDate", (res1.getString("arrive_date") == null ? "" : res1.getString("arrive_date")));
				request.setAttribute("fromwhere", res1.getString("fromwhere"));
				request.setAttribute("towhere", res1.getString("towhere"));
				request.setAttribute("package", res1.getString("package"));
				request.setAttribute("packageNum", res1.getString("packageNum"));
//				System.out.println("currency:"+res1.getString("currency"));
				request.setAttribute("currency", res1.getString("currency"));
				request.setAttribute("detailed", res1.getString("detailed"));
				request.setAttribute("frieght", res1.getString("frieght"));
				request.setAttribute("Nonum", res1.getString("nonum"));
				request.setAttribute("date", res1.getString("date"));
				request.setAttribute("huodai", res1.getString("huodai"));
				request.setAttribute("yunfei", res1.getString("yunfei"));
				request.setAttribute("yunfeifs", res1.getString("yunfeifs"));
				request.setAttribute("premium", res1.getString("premium"));
				request.setAttribute("waixiaotime", res1.getString("waixiaotime"));
				request.setAttribute("attrSource", res1.getInt("attr_source"));
				request.setAttribute("palletDimension", (res1.getString("pallet_dimension") == null ? "" : res1.getString("pallet_dimension")));
				request.setAttribute("casketSize", (res1.getString("casket_size") == null ? "" : res1.getString("casket_size")));
				request.setAttribute("casketQuantity", res1.getInt("casket_quantity"));
				request.setAttribute("casketType", res1.getString("casket_type"));
				request.setAttribute("freightInfo", (res1.getString("freight_info") == null ? "" : res1.getString("freight_info")));
				request.setAttribute("companyName", (res1.getString("company_name") == null ? "" : res1.getString("company_name")));
				request.setAttribute("excelPath", (res1.getString("excel_path") == null ? "" : res1.getString("excel_path")));
				request.setAttribute("exportPlace", (res1.getString("export_place") == null ? "" : res1.getString("export_place")));
				request.setAttribute("orderStatus", (res1.getInt("order_status")));
			}
			String sql2;
			if(Integer.parseInt(auth.toString()) == 1){
				sql2="select * from items where proId ="+id;
			}else{
				sql2="select * from items where proId ="+id +" and proId in ("+pds+")";
			}
			
			ResultSet res2 = createStatement.executeQuery(sql2);
			int totalpro2=0;
			while (res2.next()) {
//				res2.getString("");
				totalpro2++;
				request.setAttribute("itemid"+totalpro2, res2.getString("id"));
				request.setAttribute("itemeng"+totalpro2, res2.getString("itemeng"));
				request.setAttribute("itemchn"+totalpro2, res2.getString("itemchn"));
				request.setAttribute("quantity"+totalpro2, res2.getString("quantity"));
				request.setAttribute("purprice"+totalpro2, res2.getString("purprice"));
				request.setAttribute("unitprice"+totalpro2, res2.getString("unitprice"));
				request.setAttribute("trueprice"+totalpro2, res2.getString("trueprice"));
				request.setAttribute("shopingmark"+totalpro2, res2.getString("shopingmark"));
				request.setAttribute("hscode"+totalpro2, res2.getString("hscode"));
				request.setAttribute("nw"+totalpro2, res2.getString("nw"));
//				request.setAttribute("gw"+totalpro2, res2.getString("gw"));
//				request.setAttribute("pageNum"+totalpro2, res2.getString("pageNum"));
				request.setAttribute("rate"+totalpro2, res2.getString("rate"));
				request.setAttribute("unitpriceall"+totalpro2, res2.getString("unitpriceall"));
				request.setAttribute("sourceDestination"+totalpro2, res2.getString("source_destination") == null ? "" : res2.getString("source_destination"));
				request.setAttribute("unit"+totalpro2, res2.getString("unit") == null ? "PCS" : res2.getString("unit"));
			}
			String sql3;
			if(Integer.parseInt(auth.toString()) == 1){
				sql3="select * from contract where proId ="+id;
			}else{
				sql3="select * from contract where proId ="+id+" and proId in ("+pds+")";
			}
			
			ResultSet res3 = createStatement.executeQuery(sql3);
			int totalpro3=0;
			while (res3.next()) {
				totalpro3++;
				request.setAttribute("conid"+totalpro3, res3.getString("id"));
				request.setAttribute("purno"+totalpro3, res3.getString("purno"));
				request.setAttribute("factory"+totalpro3, res3.getString("factory"));
				request.setAttribute("money"+totalpro3, res3.getString("money"));
				request.setAttribute("times"+totalpro3, res3.getString("times"));
				request.setAttribute("totaltimes"+totalpro3, res3.getString("totaltimes"));
				request.setAttribute("rmb"+totalpro3, res3.getString("rmb"));
				request.setAttribute("orderId"+totalpro3, res3.getString("order_id"));
				request.setAttribute("isExtraInvoice"+totalpro3, res3.getString("is_extra_invoice"));
			}
			

			//获取当前出运合同数
			request.setAttribute("totalSize",totalpro3);
			
			
			//查询电子出货单记录
			String sql4;
			if(Integer.parseInt(auth.toString()) == 1){
				sql4="select s.id,c.purno,s.purno as projectNo,s.serial_number,s.is_complete,s.sid,c.is_extra_invoice from contract c LEFT JOIN shipping_contract s on c.proId = s.proId and c.purno like '%'+replace(s.purno,'SHS','')+'%' where c.proId ="+id;
			}else{
				sql4="select s.id,c.purno,s.purno as projectNo,s.serial_number,s.is_complete,s.sid,c.is_extra_invoice from contract c LEFT JOIN shipping_contract s on c.proId = s.proId and c.purno like '%'+replace(s.purno,'SHS','')+'%' where c.proId ="+id +" and c.proId in ("+pds+")";
			}
			
			ResultSet res4 = createStatement.executeQuery(sql4);
			int total4=0;
			while (res4.next()) {	
				total4++;
				String serialNumber = res4.getString("serial_number");
				request.setAttribute("shippingId"+total4, res4.getInt("id"));
											
				request.setAttribute("isComplete"+total4, res4.getInt("is_complete"));
				//如果是带票不需要出货单
				if(res4.getInt("is_extra_invoice")==1){
					request.setAttribute("isComplete"+total4, 1);
					total4--;
					continue;
				}
				request.setAttribute("sid"+total4, res4.getInt("sid"));
				//获取项目号
				String projectNo = "";
				if(StringUtils.isBlank(serialNumber)){
					String purno = res4.getString("purno");
					purno = purno.replaceAll("[a-zA-Z]","");
					purno = purno.replace("合", "");
					projectNo = "SHS"+ purno;
					request.setAttribute("projectNo"+total4, projectNo);	
				}else{
					request.setAttribute("projectNo"+total4, res4.getString("projectNo"));	
				}
				request.setAttribute("serialNumber"+total4, serialNumber);
//				request.setAttribute("projectNo"+total4, projectNo);
				
			}
			request.setAttribute("total4",total4);
			
			
			
			String sql5 = "select * from invoice_pic where proId ="+id;		
			ResultSet res5 = createStatement.executeQuery(sql5);
			int totalpro5=0;
			while (res5.next()) {
				totalpro5++;
				request.setAttribute("invoiceId"+totalpro5, res5.getString("id"));
				request.setAttribute("factoryName"+totalpro5, res5.getString("factory_name"));
				request.setAttribute("picName"+totalpro5, res5.getString("pic_name"));
			}
			request.setAttribute("totalpro5",totalpro5);
			
			
			RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/update.jsp");
			homeDispatcher.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
 			DBHelper.returnConnection(connection);
 		}
		out.flush();
		out.close();
	}

}

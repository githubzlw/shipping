package com.cynergy.server;
/**
 * 新增或修改项目后的预览页面
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

import com.cynergy.main.DBHelper;
import com.cynergy.main.MainSql;

public class PreprintServlet extends HttpServlet {
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
		Integer ids = (Integer) request.getAttribute("id");
		int id = ids==null?0:ids;
		System.out.println("PreprintServlet:id="+id);
		Connection connection = DBHelper.getConnection();
		try {
			Statement createStatement = connection.createStatement();
			request.setAttribute("id", id);
			String sql1="select * from products where id ="+id;
			System.out.println(sql1);
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
				request.setAttribute("arriveDate", res1.getString("arrive_date"));
				request.setAttribute("palletDimension", res1.getString("pallet_dimension"));
				request.setAttribute("casketQuantity", res1.getInt("casket_quantity"));
				request.setAttribute("casketType", res1.getString("casket_type"));
				request.setAttribute("companyName", res1.getString("company_name"));
				request.setAttribute("exportPlace", res1.getString("export_place"));
			}
			String sql2="select * from items where proId ="+id;
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
				request.setAttribute("sourceDestination"+totalpro2, res2.getString("source_destination"));
				request.setAttribute("unit"+totalpro2, (res2.getString("unit") == null ? "PCS" : res2.getString("unit")));
			}
			String sql3="select * from contract where proId ="+id;
			ResultSet res3 = createStatement.executeQuery(sql3);
			int totalpro3=0;
			while (res3.next()) {
//				res3.getString("");
				totalpro3++;
				request.setAttribute("conid"+totalpro3, res3.getString("id"));
				request.setAttribute("purno"+totalpro3, res3.getString("purno"));
				request.setAttribute("factory"+totalpro3, res3.getString("factory"));
				request.setAttribute("money"+totalpro3, res3.getString("money"));
				request.setAttribute("times"+totalpro3, res3.getString("times"));
				request.setAttribute("totaltimes"+totalpro3, res3.getString("totaltimes"));
				request.setAttribute("rmb"+totalpro3, res3.getString("rmb"));
			}
			
			//获取用户权限
			HttpSession session = request.getSession();
			request.setAttribute("auth",session.getAttribute("auth"));
			
			//获取当前出运合同数
			request.setAttribute("totalSize",totalpro3);
			
			RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/preprint.jsp");
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

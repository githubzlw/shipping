package com.cynergy.server;
/**
 * 修改项目时 先获取信息
 */

import com.cynergy.main.DBHelper;
import com.cynergy.main.WebCookie;
import com.cynergy.mapper.InvoiceInfoMapper;
import com.cynergy.mapper.InvoiceInfoMapperImpl;
import com.cynergy.pojo.CaseFund;
import com.cynergy.pojo.ContractItem;
import com.cynergy.pojo.ContractItemOther;
import com.cynergy.pojo.ContractWrap;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemOfContractServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DecimalFormat df = new DecimalFormat("#0.00");
	private InvoiceInfoMapper invoiceInfoMapper = new InvoiceInfoMapperImpl();

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
		String idString = request.getParameter("cproid");
		int id = Integer.parseInt(idString);

		HttpSession session = request.getSession();
		//添加权限管理  每个人只能看到自己项目   Author:polo   2017/11/30
		String auth = WebCookie.getCookieByName(request, "auth");
		String proIds = session.getAttribute("proIds").toString();
		
		String pds = "";
		if(!(proIds == null || "".equals(proIds))){
			pds = proIds.substring(1);
		}
		String stritemid = request.getParameter("itemid");
		int itemid = StringUtils.isNotBlank(stritemid) ? Integer.parseInt(stritemid):0;
		System.out.println("itemid"+request.getParameter("itemid"));
		request.setAttribute("itemid",request.getParameter("itemid"));
		request.setAttribute("rate",request.getParameter("rate"));
		request.setAttribute("hscode",request.getParameter("hscode"));
		request.setAttribute("trueprice",request.getParameter("trueprice"));
		request.setAttribute("sourceDestination",request.getParameter("sourceDestination"));
		request.setAttribute("nw",request.getParameter("nw"));
		request.setAttribute("shopingmark",request.getParameter("shopingmark"));
		request.setAttribute("unitpriceall",request.getParameter("unitpriceall"));
		request.setAttribute("unitprice",request.getParameter("unitprice"));
		request.setAttribute("purprice",request.getParameter("purprice"));
		request.setAttribute("unit",request.getParameter("unit"));
		request.setAttribute("quantity",request.getParameter("quantity"));
		request.setAttribute("itemchn",request.getParameter("itemchn"));
		request.setAttribute("itemeng",request.getParameter("itemeng"));
		request.setAttribute("cproid",request.getParameter("cproid"));

		Connection connection = DBHelper.getConnection();
		try {
			List<String> purnos = contract(connection,id,pds,auth);
			List<ContractItem> items = contractItem(connection,itemid);
			request.setAttribute("items",items);
			request.setAttribute("purnos",purnos);
			request.setAttribute("size",purnos.size()-items.size());
			request.setAttribute("sdix",items.size());
			request.setAttribute("pdix",purnos.size());
			RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/itemcontract.jsp");
			homeDispatcher.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
 			DBHelper.returnConnection(connection);
 		}
		out.flush();
		out.close();
	}

	private List<ContractItem> contractItem(Connection connection,int itemid) throws SQLException{
		List<ContractItem> items = new ArrayList<ContractItem>();
		if(itemid < 1){
			return items;
		}
		String sql = "select * from contract_items where item_id="+itemid +"";
		Statement createStatement = connection.createStatement();
		ResultSet res3 = createStatement.executeQuery(sql);
		ContractItem item = null;
		while (res3.next()) {
			item = new ContractItem();
			item.setId( res3.getString("id"));
			item.setPurno(res3.getString("purno"));
			item.setProid(res3.getString("proid"));
			item.setItemchn(res3.getString("itemchn"));
			item.setItemId(res3.getString("item_id"));
			item.setQuantity(res3.getString("quantity"));
			item.setAmount(res3.getString("amount"));
			items.add(item);
		}
		DBHelper.closeResource(createStatement,res3);
		return items;
	}


	private List<String> contract(Connection connection,int id,String pds,String auth) throws SQLException{
		Statement createStatement = connection.createStatement();

		List<String> purnos = new ArrayList<String>();
		String sql3;
		if(Integer.parseInt(auth) == 1){
			sql3="select purno from contract where proId ="+id;
		}else{
			sql3="select purno from contract where proId ="+id+" and proId in ("+pds+")";
		}
		ResultSet res3 = createStatement.executeQuery(sql3);
		while (res3.next()) {
			purnos.add(res3.getString("purno"));
		}
		DBHelper.closeResource(createStatement,res3);
		return purnos;


	}
}

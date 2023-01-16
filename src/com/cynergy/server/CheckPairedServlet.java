package com.cynergy.server;
/**
 * 在提交的时候，如果 各个品种金额之和 与 总配对的银行到账差 100元，就提醒使用者
 */

import com.cynergy.main.DBHelper;
import com.cynergy.main.PropertisUtil;
import com.cynergy.mapper.ContractItemMapperImpl;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CheckPairedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static PropertisUtil propertisUtil = new PropertisUtil("config.properties");
	ContractItemMapperImpl contractItemsMapper = new ContractItemMapperImpl();

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

		JSONObject jsonobj = new JSONObject();
		jsonobj.put("isContains",0);
		String proId = request.getParameter("proId");
		String bsFlag = request.getParameter("bsFlag");
		try {
			Connection connectionErp = DBHelper.getConnectionERP();
			Statement createStatement = connectionErp.createStatement();
			//配好的总金额
//			String sql5="select sum(paired_amount) as sumPairedAmount from PreparatorEntryForm where  bs_flag="+bsFlag+" and shipping_id="+proId ;
			String sql5="select sum(paired_amount) as sumPairedAmount from PreparatorEntryForm where shipping_id="+proId ;
			ResultSet resErp5 = createStatement.executeQuery(sql5);
			int sumPairedAmount =0;
			while (resErp5.next()) {
				// 配好的总金额
				sumPairedAmount = resErp5.getInt("sumPairedAmount");
			}


			jsonobj.put("isContains",sumPairedAmount);

//			if(StringUtils.isNotEmpty(factory) && StringUtils.isNotEmpty(productName)){
//				List<String> lstName = checkFactoryItemName(factory,productName);
//				if(lstName.contains(productName)){
//					jsonobj.put("isContains",1);
//				}
//				StringBuilder sb = new StringBuilder();
//				for(int i=0,size=lstName.size();i<size;i++){
//					sb.append(lstName.get(i)).append(i<size-1?",":"");
//				}
//				jsonobj.put("lstName",sb.toString());
//			}

		} catch (SQLException e) {
			jsonobj.put("isContains",0);
			e.printStackTrace();
		} catch (Exception e) {
			jsonobj.put("isContains",0);
			e.printStackTrace();
		}finally {
			out.println(jsonobj);
			out.flush();
			out.close();
 		}
	}




}

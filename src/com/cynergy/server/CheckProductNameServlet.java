package com.cynergy.server;
/**
 * 检查输入的 品名不在这个工厂能开的 品名列表里面
 */

import com.cynergy.main.DBHelper;
import com.cynergy.main.PropertisUtil;
import com.cynergy.main.ReadExcelUtils;
import com.cynergy.main.ReadExcelVO;
import com.cynergy.mapper.ContractItemMapperImpl;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CheckProductNameServlet extends HttpServlet {
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
		String factory = request.getParameter("factory");
		String productName = request.getParameter("name");
		try {
			if(StringUtils.isNotEmpty(factory) && StringUtils.isNotEmpty(productName)){
				List<String> lstName = checkFactoryItemName(factory,productName);
				if(lstName.contains(productName)){
					jsonobj.put("isContains",1);
				}
				StringBuilder sb = new StringBuilder();
				for(int i=0,size=lstName.size();i<size;i++){
					sb.append(lstName.get(i)).append(i<size-1?",":"");
				}
				jsonobj.put("lstName",sb.toString());
			}

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


	/**
	 * 检查品名
	 * @param factory
	 * @param productName
	 * @return
	 * @throws SQLException
	 */
	public List<String> checkFactoryItemName(String factory,String productName) throws SQLException{
		List<String> lstName = new ArrayList<>();

		Connection connection = DBHelper.getConnectionERP();
		String sql = "select a.Invoice_name from VIEW_Factory_Money a ,factoryinfo b " +
				"where a.Factory_id=b.id " +
				"and b.FactoryName=? group by a.Invoice_name";

		PreparedStatement st = connection.prepareStatement(sql);
		st.setString(1,factory);
		ResultSet resultSet = st.executeQuery();
		while(resultSet.next()){
			lstName.add(resultSet.getString("Invoice_name"));
		}
		DBHelper.closeResource(st,resultSet);
		DBHelper.returnConnection(connection);
		return lstName;
	}



}

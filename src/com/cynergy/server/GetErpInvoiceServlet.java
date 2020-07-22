package com.cynergy.server;


import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

import com.cynergy.main.DBHelper;

/**
 * 获取erp invoice数据
 * @ClassName GetErpInvoiceServlet 
 * @Description
 * @author zj
 * @date 2018年9月4日 
 */
public class GetErpInvoiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
        String purnos = request.getParameter("purnos");

		Connection connection = DBHelper.getConnectionERP();
		try {
			
			//产品总金额不加模具费
			//总产品数量
			JSONObject jsonobj = new JSONObject(); 
			Double totalPrice = 0.0;
			int totalQty = 0;  
			Double uPrice = 0.0;
			String product = "";
			String[] split = purnos.split(",");
			for (int i = 0; i < split.length; i++) {
				    String invoiceNo = "";
				    String purno = split[i];
			        if(StringUtils.isNotBlank(purno)){
			        	invoiceNo = purno.replace("合", "INV");
			        }
					Statement createStatement = connection.createStatement();
					String sql = "select product,price,amount from moju where invoice =\'"+invoiceNo+"\'";
					ResultSet res1 = createStatement.executeQuery(sql);
					
                   //计算模具表下产品金额
					while (res1.next()) {
						Double unitPrice = res1.getDouble("price");
						int qty = res1.getInt("amount");
						//默认去除数量为1的数据(一般为模具)
						if(qty != 1){
							totalQty +=qty; 
							totalPrice +=new BigDecimal(unitPrice).multiply(new BigDecimal(qty)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
							product = res1.getString("product");
						}
					}
			}
	
			//产品最终单价
			if(totalQty != 0){
				uPrice = new BigDecimal(totalPrice).divide(new BigDecimal(totalQty),3, BigDecimal.ROUND_HALF_UP).doubleValue();

			}
			
			jsonobj.put("totalPrice", totalPrice);
			jsonobj.put("totalQty", totalQty);
			jsonobj.put("uPrice", uPrice);
			jsonobj.put("product", product);
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

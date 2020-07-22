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
import net.sf.json.JSONObject;

import com.cynergy.main.DBHelper;

/**
 * 获取出运合同总金额（用于判断是否出货完成）
 * @ClassName GetContractAmountServlet 
 * @Description TODO
 * @author zj
 * @date 2017年12月8日 上午11:16:41
 */
public class GetContractAmountServlet extends HttpServlet {
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
        String purno = request.getParameter("purno");
        Double currentRmb = Double.parseDouble(request.getParameter("rmb"));
		Connection connection = DBHelper.getConnection();
		try {
			
			Statement createStatement = connection.createStatement();
			String sql = "select money,rmb from contract where purno =\'"+purno+"\'";
			ResultSet res1 = createStatement.executeQuery(sql);
			
			//出口总金额
			Double totalRmb = 0.0;
			//合同金额
			Double money = 0.0;
			//是否满足（合同金额 >= 出口总金额）
			Boolean flag = true;
			JSONObject jsonobj = new JSONObject(); 
			while (res1.next()) {
				Double rmb = new BigDecimal(res1.getString("rmb")).doubleValue();
				totalRmb +=rmb;	
			}
			money = Double.parseDouble(request.getParameter("money"));
			totalRmb = new BigDecimal(totalRmb).add(new BigDecimal(currentRmb)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
			if(totalRmb <= money){
				flag = true;
			}else{
				flag = false;
			}
			jsonobj.put("flag", flag);
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

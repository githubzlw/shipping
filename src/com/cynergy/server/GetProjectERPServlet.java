package com.cynergy.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.cynergy.main.DBHelper;


/**
 * 根据合同号查询erp合同表，查询是否已有合同，合同金额数据
 * @ClassName GetProjectERPServlet 
 * @Description
 * @author zj
 * @date 2018年7月12日 上午11:39:30
 */
public class GetProjectERPServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetProjectERPServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Connection connection = DBHelper.getConnectionERP();
		Connection connectionShipping = DBHelper.getConnection();  
		String purNo=request.getParameter("purno");
		
		//获取用户权限
		HttpSession session = request.getSession();
		int auth = (Integer) session.getAttribute("auth");
		JSONObject jsonobj = new JSONObject(); 
		try {
			/*
			 * 通过查询erp Bargain表查询产品价格、模具价、样品价格
			 */
			Boolean haveBargain = true; //用于判断合同是否存在
			Double totalPrice = 0.0;     //合同总金额
			String factoryName = "";     //合同工厂名
			Statement createStatement = connection.createStatement();
			String sql = "select friMoney from FactoryFund where BargainNo = '"+purNo+"'";			 
			ResultSet res1 = createStatement.executeQuery(sql);
            
			while(res1.next()){
				Double price = res1.getDouble("friMoney");
				totalPrice +=price;
			}
			if(totalPrice == 0.0){
				//SQL查询结果为空
				haveBargain = false;
			}
			totalPrice = new BigDecimal(totalPrice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			jsonobj.put("haveBargain", haveBargain);
			jsonobj.put("totalPrice", totalPrice);
			/**
			 * 根据发票表获取合同工厂名
			 */
			String sqlFactoryName = "SELECT * FROM  (select BargainNo,min(info.FactoryName)FactoryName from factoryfund ff left join factoryinfo info on ff.fid=info.id group by ff.BargainNo ) a where a.BargainNo='"+purNo+"'  ";
			ResultSet res2 = createStatement.executeQuery(sqlFactoryName);
			if(res2.next()) {
				factoryName = res2.getString("FactoryName");
			}
			jsonobj.put("factoryName", factoryName);
			/**
			 * 查询当前出货次数，出货总金额
			 * 连接shipping数据库
			 */			
			Statement statement = connectionShipping.createStatement();
			String times = "";  
			String totaltimes = "";
			Double totalPay = 0.0; //已出货金额
//			String sql3 = "select a.times,a.totaltimes,b.totalPay from "+
//								"( "+
//								"select times,totaltimes from contract where id in (SELECT max(id)as maxid from contract where purno = '"+purNo+"') "+ 
//								")a, "+
//								"(select SUM(cast(rmb as numeric(12,3)))as totalPay from contract left join products on contract.proId = products.id "
//								+ "where purno = '"+purNo+"' and  convert(char,dateadd(DD,-5,getdate()),111) > products.timeDate)b";			 
//			if(auth == 1){
			String sql3 = "select a.times,a.totaltimes,b.totalPay from "+
						"( "+
						"select times,totaltimes from contract where id in (SELECT max(id)as maxid from contract where purno = '"+purNo+"') "+ 
						")a, "+
						"(select SUM(cast(rmb as numeric(12,3)))as totalPay from contract "
						+ "where purno = '"+purNo+"')b";			 
//			}
			
			ResultSet res3 = statement.executeQuery(sql3);
			while (res3.next()) {
				times = res3.getString("times");
				totalPay = res3.getDouble("totalPay");
				totaltimes = res3.getString("totaltimes");
			}

			jsonobj.put("times", times == "" ? '0' : times);
			jsonobj.put("totaltimes", totaltimes == "" ? '0' : totaltimes);
			jsonobj.put("totalPay", totalPay);
			//剩余未出货金额
			double balancePrice = new BigDecimal(totalPrice).subtract(new BigDecimal(totalPay)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			//有些合同有零头，当小于1 默认已出完
			if(balancePrice < 1){
				balancePrice = 0;
			}
			jsonobj.put("balancePrice", balancePrice);
		    // 输出数据
		    out = response.getWriter();
		    out.println(jsonobj);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
 			DBHelper.returnConnection(connection);
 			DBHelper.returnConnection(connectionShipping);
 			out.flush();
 			out.close();
 		}
	}

}

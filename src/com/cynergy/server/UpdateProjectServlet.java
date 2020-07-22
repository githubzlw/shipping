package com.cynergy.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cynergy.main.DBHelper;
import com.cynergy.main.MainSql;

/**
 * 修改项目
 */
public class UpdateProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public UpdateProjectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		String qualityDepict = request.getParameter("qualityDepict");
		String deliveryDepict = request.getParameter("deliveryDepict");
		String expoundPurchase = request.getParameter("expoundPurchase");
		String expoundTesting = request.getParameter("expoundTesting");
		String expoundSale = request.getParameter("expoundSale");
		String liabilityRatio = request.getParameter("liabilityRatio");
		String lossesExpound = request.getParameter("lossesExpound");
		String lossesMonney = request.getParameter("lossesMonney");
		String FactoryDeduction = request.getParameter("FactoryDeduction");
		String FactoryCompute = request.getParameter("FactoryCompute");
		String delayDay = request.getParameter("delayDay"); 
		String newestDeliveryTeason = request.getParameter("newestDeliveryTeason"); 
		String state = request.getParameter("state");  
		String newestDelivery = request.getParameter("newestDelivery"); 
		String qualityLiabilityRatio = request.getParameter("qualityLiabilityRatio"); 
		String firmDelayDay = request.getParameter("firmDelayDay"); 
		String proName = request.getParameter("proName"); 
		String purchase = request.getParameter("purchase"); 
		String proThisUSD = request.getParameter("proThisUSD"); 
		String proYearUSD = request.getParameter("proYearUSD");
		String documentarySales = request.getParameter("documentarySales");
		String classification = request.getParameter("classification");
		String deductMoney = request.getParameter("deductMoney"); 
		String instruction = request.getParameter("instruction"); 
		String explain = request.getParameter("explain");
		String tdeductMoney = request.getParameter("tdeductMoney"); 
		String psdeductMoney = request.getParameter("psdeductMoney"); 
		String qualityName = request.getParameter("qualityName");
		String correcting = request.getParameter("correcting");
		String isend = request.getParameter("isend"); 
		System.out.println("...."+qualityName);

		if(isend==null){
			isend = 0+"";
		}
		if(correcting == null){
			correcting = 0+"";
		}
		if(qualityName!=null){
			qualityName=qualityName.trim();
		}
		if(psdeductMoney!=null){
			psdeductMoney=psdeductMoney.trim();
		}
		if(tdeductMoney!=null){
			tdeductMoney=tdeductMoney.trim();
		}
		if(explain!=null){
			explain=explain.trim();
		}
		if(instruction!=null){
			instruction=instruction.trim();
		}
		if(deductMoney!=null){
			deductMoney=deductMoney.trim();
		}
		if(documentarySales!=null){
			documentarySales=documentarySales.trim();
		}
		if(classification!=null){
			classification=classification.trim();
		}
		if(proName!=null){
			proName=proName.trim();
		}
		if(purchase!=null){
			purchase=purchase.trim();
		}
		if(proThisUSD!=null){
			proThisUSD=proThisUSD.trim();
		}
		if(proYearUSD!=null){
			proYearUSD=proYearUSD.trim();
		}
		if(qualityLiabilityRatio!=null){
			qualityLiabilityRatio=qualityLiabilityRatio.trim();
		}
		if(firmDelayDay!=null){
			firmDelayDay=firmDelayDay.trim();
		}
		
		if(newestDelivery!=null){
			newestDelivery=newestDelivery.trim();
		}
		if(state!=null){
			state=state.trim();
		}
		if(newestDeliveryTeason!=null){
			newestDeliveryTeason=newestDeliveryTeason.trim();
		}
		if(qualityDepict!=null){
			qualityDepict=qualityDepict.trim();
		}
		if(deliveryDepict!=null){
			deliveryDepict=deliveryDepict.trim();
		}
		if(expoundPurchase!=null){
			expoundPurchase=expoundPurchase.trim();
		}
		if(expoundTesting!=null){
			expoundTesting=expoundTesting.trim();
		}
		if(expoundSale!=null){
			expoundSale=expoundSale.trim();
		}
		if(liabilityRatio!=null){
			liabilityRatio=liabilityRatio.trim();
		}
		if(lossesExpound!=null){
			lossesExpound=lossesExpound.trim();
		}
		if(lossesMonney!=null){
			lossesMonney=lossesMonney.trim();
		}
		if(FactoryDeduction!=null){
			FactoryDeduction=FactoryDeduction.trim();
		}
		if(FactoryCompute!=null){
			FactoryCompute=FactoryCompute.trim();
		}
		if(delayDay!=null){
			delayDay=delayDay.trim();
		}
		Connection connection = DBHelper.getConnection();
		try {
			System.out.println("修改项目");
			String sql = "update  project set qualityDepict=?,deliveryDepict=?,expoundPurchase=?,expoundTesting=?,expoundSale=?,liabilityRatio=?,lossesExpound=?,lossesMonney=?,FactoryDeduction=?,FactoryCompute=?,delayDay=?,newestDeliveryTeason=?,state=?,newestDelivery=?,qualityLiabilityRatio=?,firmDelayDay=?,proName=?,purchase=?,proThisUSD=?,proYearUSD=?,documentarySales=?,classification=?,deductMoney=?,instruction=?,statetime=?,explain=?,tdeductMoney=?,psdeductMoney=?,qualityName=?,correcting=?,isend=?  where id="+id;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, qualityDepict);
			statement.setString(2, deliveryDepict);
			statement.setString(3, expoundPurchase);
			statement.setString(4, expoundTesting);
			statement.setString(5, expoundSale);
			statement.setString(6, liabilityRatio);
			statement.setString(7, lossesExpound);
			statement.setString(8, lossesMonney);
			statement.setString(9, FactoryDeduction);
			statement.setString(10, FactoryCompute);
			statement.setString(11, delayDay);
			statement.setString(12, newestDeliveryTeason);
			statement.setString(13, state);
			statement.setString(14, newestDelivery);
			statement.setString(15, qualityLiabilityRatio);
			statement.setString(16, firmDelayDay);
			statement.setString(17, proName);
			statement.setString(18, purchase);
			statement.setString(19, proThisUSD);
			statement.setString(20, proYearUSD);
			statement.setString(21, documentarySales);
			statement.setString(22, classification);
			statement.setString(23, deductMoney);
			statement.setString(24, instruction);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String statetime = df.format(new Date());
			statement.setString(25, statetime);
			statement.setString(26, explain);
			statement.setString(27, tdeductMoney);
			statement.setString(28, psdeductMoney);
			statement.setString(29, qualityName);
			statement.setString(30, correcting);
			statement.setString(31, isend);
			statement.executeUpdate();
			PrintWriter out = response.getWriter();
			out.print("<script type='text/javascript'>alert('修改成功');document.location.href='GetProjectServlet?state=0';</script>");
			 //response.sendRedirect("GetProjectServlet?state=0");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
 			DBHelper.returnConnection(connection);
 		}
	}
public static void main(String[] args) {
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	System.out.println(df.format(new Date()));
}
}

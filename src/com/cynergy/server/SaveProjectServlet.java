package com.cynergy.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cynergy.main.DBHelper;

/**
 * 录入项目
 */
public class SaveProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SaveProjectServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		Connection connection = DBHelper.getConnection();
		try {
			String proId=request.getParameter("proId");
			if(proId!=null){
				proId=proId.trim();
			}
			String proName=request.getParameter("proName");
			System.err.println(proName);
			if(proName!=null){
				proName=proName.trim();
			}
			String proThisUSD=request.getParameter("proThisUSD");
			if(proThisUSD!=null){
				proThisUSD=proThisUSD.trim();
			}
			String proYearUSD=request.getParameter("proYearUSD");
			if(proYearUSD!=null){
				proYearUSD=proYearUSD.trim();
			}
			String qualityDepict=request.getParameter("qualityDepict");
			if(qualityDepict!=null){
				qualityDepict=qualityDepict.trim();
			}
			String qualityTesting=request.getParameter("qualityTesting");
			if(qualityTesting!=null){
				qualityTesting=qualityTesting.trim();
			}
			String deliveryDepict=request.getParameter("deliveryDepict");
			if(deliveryDepict!=null){
				deliveryDepict=deliveryDepict.trim();
			}
			String scheduledDelivery=request.getParameter("scheduledDelivery");
			if(scheduledDelivery!=null){
				scheduledDelivery=scheduledDelivery.trim();
			}
			String newestDelivery=request.getParameter("newestDelivery");
			if(newestDelivery!=null){
				newestDelivery=newestDelivery.trim();
			}
			String documentarySales=request.getParameter("documentarySales");
			if(documentarySales!=null){
				documentarySales=documentarySales.trim();
			}
			String purchase=request.getParameter("purchase");
			if(purchase!=null){
				purchase=purchase.trim();
			}
			String classification=request.getParameter("classification");
			if(classification!=null){
				classification=classification.trim();
			}
			String isend=request.getParameter("isend");
			if(isend==null){
				isend = 0+"";
			}
			System.out.println("11111111111:"+isend);
			int deductMoney = 0;
			if(scheduledDelivery!=""){
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd"); 
				Date date = new Date();
				Date date1 = sf.parse(scheduledDelivery);
				 long diff = date.getTime() - date1.getTime();
				 long days = diff / (1000 * 60 * 60 * 24);
				 deductMoney = (int) (days*30);
			}
			String sql = "insert into project(proId,proName,proThisUSD,proYearUSD,qualityDepict,qualityTesting,deliveryDepict,scheduledDelivery,newestDelivery,documentarySales,purchase,classification,createtime,deductMoney,isend) values(?,?,?,?,?,?,?,?,?,?,?,?,getdate(),?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			System.out.println("保存录入：");
			statement.setString(1, proId);
			statement.setString(2, proName);
			statement.setString(3, proThisUSD);
			statement.setString(4, proYearUSD);
			statement.setString(5, qualityDepict);
			statement.setString(6, qualityTesting);
			statement.setString(7, deliveryDepict);
			statement.setString(8, scheduledDelivery);
			statement.setString(9, newestDelivery);
			statement.setString(10, documentarySales);
			statement.setString(11, purchase);
			statement.setString(12, classification);
			statement.setString(13, deductMoney+"");
			statement.setString(14, isend);
			statement.executeUpdate(); 
		 
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
 			DBHelper.returnConnection(connection);
 		}
//		RequestDispatcher homeDispatcher = request.getRequestDispatcher("GetProjectServlet?state=0");
//		homeDispatcher.forward(request, response);
		response.sendRedirect("GetProjectServlet?state=0");
	}

	public static void main(String[] args) throws ParseException {
		String timeString = "2014-05-20";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = new Date();
		Date date1 = sf.parse(timeString);
		 long diff = date.getTime() - date1.getTime();
		    long days = diff / (1000 * 60 * 60 * 24);
		System.out.println(days);
	}
}

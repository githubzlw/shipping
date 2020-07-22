package com.cynergy.server;
/**
 * 退税汇总
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.cynergy.main.DBHelper;
import com.cynergy.main.ProjectStatisticsPrint;
import com.cynergy.main.ProjectStatisticsVO;

public class DrawBackPrintServlet extends HttpServlet {
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
		String year = request.getParameter("select1");
		String month = request.getParameter("select2");
		String ymString=year+"-"+month;
		Connection connection = DBHelper.getConnection();
		try {
			Statement createStatement = connection.createStatement();
			String sql="select * from products p  LEFT JOIN items on p.id = items.proId  "
					+ "LEFT JOIN contract_items c on p.id = c.proId and c.item_id = items.id  where p.saildate like '"+ymString+"%' and p.date != ''"  
                    + "order by p.date";
			
			System.out.println("退税汇总："+sql);
			ResultSet resultSet = createStatement.executeQuery(sql);

			List<ProjectStatisticsVO> list = new ArrayList<ProjectStatisticsVO>();
			DecimalFormat df2=new DecimalFormat("¥###,##0.00");
			int id = 0;
			int proId = 0;
			while (resultSet.next()) {		
				ProjectStatisticsVO project = new ProjectStatisticsVO();
				int proId1 = resultSet.getInt("id");
				String saildate = resultSet.getString("saildate");
				String purno = resultSet.getString("purno");
				String sales = resultSet.getString("sale");		
				String rate = resultSet.getString("rate");	
				String totalAmount = resultSet.getString("purprice");
				String itemchn = resultSet.getString("itemchn");
				String truePrice = resultSet.getString("truePrice");
				String hscode = resultSet.getString("hscode");
				Double refundAmount = resultSet.getDouble("refund_amount");

				project.setRate(rate);				
				project.setExportDate(saildate);
				project.setTotalAmount(df2.format(Double.parseDouble(totalAmount)));
				//同一批出运显示相同id
				if(proId == proId1){
				   project.setId(id);
				}else{
				   proId=proId1;
				   project.setId(++id);
				}
				
				project.setMonth(ymString);
				project.setHscode(hscode);
				project.setItemchn(itemchn);
				project.setRefundAmount(df2.format(refundAmount));
				project.setSales(sales);
				project.setTruePrice(df2.format(Double.parseDouble(truePrice)));
				if(StringUtils.isNotBlank(purno)){
					purno = purno.replaceAll("[a-zA-Z]","");
					purno = purno.replace("合", "");
					project.setProjectNo("SHS"+ purno);					
				}				
				list.add(project);
			}
			
			//生成的excel路径
			String excelPath = ProjectStatisticsPrint.printExcel(request, list);
			
			File outFile = new File(excelPath);  
			InputStream  fis = new BufferedInputStream(new FileInputStream(outFile));  
			byte[] buffer = new byte[fis.available()];  
			fis.read(buffer);  
			fis.close();  
			//清空response  
			response.reset();  
			//设置response的Header  
			String fileName = ymString + "退税汇总.xls";
			fileName = URLEncoder.encode(fileName, "utf-8");                                  //这里要用URLEncoder转下才能正确显示中文名称  
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);  
			response.addHeader("Content-Length", "" + outFile.length());  
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());  
			response.setContentType("application/octet-stream");  
			toClient.write(buffer);  
			toClient.flush();
			//导出完成删除临时文件
			ProjectStatisticsPrint.deleteFile(excelPath);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
 			DBHelper.returnConnection(connection);
 		}
	}

}

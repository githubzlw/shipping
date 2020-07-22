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
import com.cynergy.main.ProjectInvoiceMonthPrint;
import com.cynergy.main.ProjectStatisticsPrint;
import com.cynergy.main.ProjectStatisticsVO;

public class InvoicePrintServlet extends HttpServlet {
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
			String sql= "select min(pid)pid,  STUFF(( SELECT  ',' + x.purno from products s  left join contract x on s.id=x.proId  "
						            +"left join contract_items y "
								        +" on s.id = y.proId   WHERE y.itemchn = a.itemchn and x.factory=a.factory and s.id=a.pid group by x.purno  FOR XML PATH('')) ,1,1,'') AS  purno "
										 +",min(amount) amount,min(a.weight) as weight,a.itemchn,a.factory,min(a.saildate)saildate,unit,min(date)date "
										 +" from ("
						  +"select min(p.id) as pid,c.amount,c.weight,max(c.itemchn)itemchn,min(c.factory)as factory,min(p.saildate) as saildate,items.unit,min(p.date)date  from products p  "						
						 +"LEFT JOIN  (select sum(CAST(amount AS decimal(11,2)))amount,sum( CAST(weight AS decimal(11,0)))weight,min(c1.item_id)item_id,c1.itemchn,c2.factory,c1.proId from contract_items c1 left join contract c2 on c1.proId = c2.proId and c1.purno = c2.purno GROUP BY factory,c1.proId,c1.itemchn)c on p.id = c.proId  "						 
						 +"LEFT JOIN items on c.item_id = items.id "
						 +"where p.saildate like '"+ymString+"%' "
						 +"group by c.item_id,c.itemchn,c.factory,p.id,items.unit,c.amount,c.weight  "					
						 +")a group by a.itemchn,a.factory,a.pid,a.unit   order by date "
						;
			System.out.println("发票信息收取："+sql);
			ResultSet resultSet = createStatement.executeQuery(sql);

			List<ProjectStatisticsVO> list = new ArrayList<ProjectStatisticsVO>();
			DecimalFormat df2=new DecimalFormat("¥###,##0.00");
			int id = 0;
			int proId = 0;
			while (resultSet.next()) {		
				ProjectStatisticsVO project = new ProjectStatisticsVO();
				int proId1 = resultSet.getInt("pid");
				String saildate = resultSet.getString("saildate");
				String purno = resultSet.getString("purno");		
				String amount = resultSet.getString("amount");
				String itemchn = resultSet.getString("itemchn");
				String factoryName = resultSet.getString("factory");
//				int contractId = resultSet.getInt("contractId");
			
				project.setExportDate(saildate);
				if(amount!=null){
					project.setTotalAmount(df2.format(Double.parseDouble(amount)));
				}else{
					project.setTotalAmount("0");
				}
				
//				project.setContractId(contractId);
//				//同一批出运显示相同id
				if(proId == proId1){
				   project.setId(id);
				}else{
				   proId=proId1;
				   project.setId(++id);
				}		
//				project.setId(proId1);
				project.setMonth(ymString);
				project.setItemchn(itemchn);
				project.setFactoryName(factoryName);
				project.setUnit(resultSet.getString("unit"));
				project.setQuantity(resultSet.getInt("weight"));
				
				if(StringUtils.isNotBlank(purno)){
					purno = purno.replace("合", "").replace(",", "/");
					project.setProjectNo("SHS"+ purno);
				}				
				list.add(project);
			}
			

			//生成的excel路径
			String excelPath = ProjectInvoiceMonthPrint.printExcel(request, list, ymString);
		
			
			File outFile = new File(excelPath);  
			InputStream  fis = new BufferedInputStream(new FileInputStream(outFile));  
			byte[] buffer = new byte[fis.available()];  
			fis.read(buffer);  
			fis.close();  
			//清空response  
			response.reset();  
			//设置response的Header  
			String fileName = ymString + "开增值税发票信息表.xlsm";
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

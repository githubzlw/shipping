package com.cynergy.server;
/**
 * 模板excel生成
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

import com.cynergy.main.ContractVO;
import com.cynergy.main.DBHelper;
import com.cynergy.main.ProjectStatisticsPrint;
import com.cynergy.main.ProjectStatisticsVO;

public class GetProductExcelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Integer proId = Integer.parseInt(request.getParameter("proId"));
		Connection connection = DBHelper.getConnection();
		try {
			Statement createStatement = connection.createStatement();
			String sql="select * from contract where proId = "+ proId;
			System.out.println("退税汇总："+sql);
			ResultSet resultSet = createStatement.executeQuery(sql);

			List<ContractVO> list = new ArrayList<ContractVO>();
			while (resultSet.next()) {		
				ContractVO contract = new ContractVO();				
				String purno = resultSet.getString("purno");
				String factory = resultSet.getString("factory");		
				String money = resultSet.getString("rmb");	

				contract.setPurno(purno);			
				contract.setFactory(factory);
				contract.setAmount(money);
				list.add(contract);
			}		
			
			String excelPath = ProjectStatisticsPrint.getProductExcel(list);
			File outFile = new File(excelPath);  
			InputStream  fis = new BufferedInputStream(new FileInputStream(outFile));  
			byte[] buffer = new byte[fis.available()];  
			fis.read(buffer);  
			fis.close();  
			//清空response  
			response.reset();  
			//设置response的Header  
			String fileName = "拆分报关品名.xlsx";
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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

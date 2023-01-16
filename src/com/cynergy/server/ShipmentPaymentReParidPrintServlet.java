package com.cynergy.server;
/**
 * 汇总
 */

import com.cynergy.main.BankArrival;
import com.cynergy.main.DBHelper;
import com.cynergy.main.ProjectStatisticsPrint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ShipmentPaymentReParidPrintServlet extends HttpServlet {
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
		String year = request.getParameter("year2");
		String customerName = request.getParameter("customerName2");
		String parid = request.getParameter("parid2");

		try {
			ShipmentPaymentReParidServlet servletShipment = new ShipmentPaymentReParidServlet();

			List<BankArrival> bankAList = servletShipment.getBankAList(year,customerName,parid);
			//生成的excel路径
			String excelPath = ProjectStatisticsPrint.printExcelParid(request, bankAList);

			File outFile = new File(excelPath);
			InputStream fis = new BufferedInputStream(new FileInputStream(outFile));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			//清空response
			response.reset();
			//设置response的Header
			String fileName = "配对数据.xls";
			fileName = URLEncoder.encode(fileName, "utf-8");                                  //这里要用URLEncoder转下才能正确显示中文名称
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.addHeader("Content-Length", "" + outFile.length());
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			//导出完成删除临时文件
			ProjectStatisticsPrint.deleteFile(excelPath);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<BankArrival> getBankAList(String year){

		List<BankArrival> bankAList = new ArrayList<BankArrival>();
		Connection connectionErp = DBHelper.getConnectionERP();
		try {

			Statement createStatementErp1 = connectionErp.createStatement();

			String sqlErp2="select c.proId,(select customercode from itemCase where caseno=b.caseno) as customercode ,  " +
					"c.itemchn ,c.unitpriceall,c.trueprice,b.caseno,a.TransactionReferenceNumber,a.TransactionDate,a.TradeAmount,b.paired_amount " +
					"from AccountEntryForm  a  left join PreparatorEntryForm b on b.AmountClaimFormId=a.id and b.ifmoney!=0  "+
					"left join reportform.[dbo].items c on b.shipping_id= c.proId   "+
	//					" where b.update_time>='2022-06-28 17:33:00'  "+
					" where b.shipping_id is not null and c.trueprice !='' order by  caseno desc, TransactionDate desc  ";
			ResultSet resErp2 = createStatementErp1.executeQuery(sqlErp2);
			while (resErp2.next()) {

				BankArrival bankA = new BankArrival();
				//出运号
				bankA.setProId(resErp2.getInt("proId"));
				//客户id
				bankA.setErpCustomerId(resErp2.getInt("customercode"));
				//出运品名
				bankA.setItemchn(resErp2.getString("itemchn"));
				//实际销售额（清关金额）
				bankA.setUnitPriceall(resErp2.getInt("unitpriceall"));
				//实际对外发票金额（报关金额）
				bankA.setTruePrice(resErp2.getInt("trueprice"));
				//项目号
				bankA.setItemNo(resErp2.getString("caseno"));
				//银行序号
				bankA.setTransactionReferenceNumber(resErp2.getString("TransactionReferenceNumber"));
				//到账日期
				bankA.setTransactionDate(resErp2.getString("TransactionDate"));
				//到账金额
				bankA.setTradeAmount(resErp2.getInt("TradeAmount"));
				//配对金额
				bankA.setPairedAmount(resErp2.getInt("paired_amount"));

				bankAList.add(bankA);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBHelper.returnConnection(connectionErp);
		}
		return bankAList;
	}

}





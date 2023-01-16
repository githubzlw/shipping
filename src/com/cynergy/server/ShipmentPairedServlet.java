package com.cynergy.server;
/**
 * 汇总
 */

import com.cynergy.main.BankArrival;
import com.cynergy.main.DBHelper;
import com.cynergy.main.ReadExcelVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShipmentPairedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
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

		String amountClaimFormIdF = request.getParameter("amountClaimFormIdF");
		String itemNoF = request.getParameter("itemNoF");
		String declarationAmountF = request.getParameter("declarationAmountF");

		String shipmentNoF = request.getParameter("shipmentNoF");
		String erpCustomerIdF = request.getParameter("erpCustomerIdF");
		String actualSalesF = request.getParameter("actualSalesF");
		String actualInvoiceAmountF = request.getParameter("actualInvoiceAmountF");
		String pairedBankNoF = request.getParameter("pairedBankNoF");
		String generalDeclarationInvoiceF = request.getParameter("generalDeclarationInvoiceF");
        String idF = request.getParameter("idF");


		Connection connectionErp = DBHelper.getConnectionERP();
		try {
			//第4步：提交
			//表2 中被配对的 银行序号   要 改  “是否已经配对” 为 “已经配对”
			//已经配对金额 = 老的 已经配对金额 + 此次报关金额 TODO
			String sql2="update PreparatorEntryForm set paired='已配对' , paired_amount=? where AmountClaimFormId=? and caseno=? and id=?  ";
			PreparedStatement statement2 = connectionErp.prepareStatement(sql2);
			statement2.setDouble(1, Double.valueOf(declarationAmountF));
			statement2.setInt(2, Integer.parseInt(amountClaimFormIdF));
			statement2.setString(3, itemNoF);
            statement2.setString(4, idF);
			statement2.executeUpdate();
			statement2.close();

			// 表6 中被配对的发票   要 填写已经使用的金额”
			updateShipmentInvoice(itemNoF,declarationAmountF);
			//表4:出运配好对 插入
			insertBatchShipmentPaired(shipmentNoF,erpCustomerIdF,itemNoF,actualSalesF,actualInvoiceAmountF,pairedBankNoF,declarationAmountF,generalDeclarationInvoiceF);

//			out.println("成功");
			String result ="成功";
			request.setAttribute("result", result);
			RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/shipmentresult.jsp");
			homeDispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			out.println("失败");
		}finally {
			DBHelper.returnConnection(connectionErp);
		}
		out.flush();
		out.close();
	}

	// 表4:出运配好对 插入
	public void insertBatchShipmentPaired(String shipmentNoF,String erpCustomerIdF,String itemNoF,String actualSalesF,
										  String actualInvoiceAmountF,String pairedBankNoF,String declarationAmountF,String generalDeclarationInvoiceF) throws Exception {


		Connection connection = DBHelper.getConnection();
		try {

			String sql2 = "INSERT INTO shipment_paired(shipment_no,erp_customer_id,item_no,actual_sales,actual_invoice_amount,paired_bank_no,declaration_amount,general_declaration_invoice) VALUES (?,?, ?, ?, ?, ?,?,?);";
			PreparedStatement statement2 = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
//			for (ReadExcelVO readExcelVO : items) {
			statement2.setString(1, shipmentNoF);
			statement2.setInt(2, Integer.valueOf(erpCustomerIdF));
			statement2.setString(3, itemNoF);
			statement2.setDouble(4, Double.valueOf(actualSalesF));
			statement2.setDouble(5, Double.valueOf(actualInvoiceAmountF));
			statement2.setString(6, pairedBankNoF);
			statement2.setDouble(7, Double.valueOf(declarationAmountF));
			statement2.setDouble(8, Double.valueOf(generalDeclarationInvoiceF));

			statement2.executeUpdate();
//			}
			statement2.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			DBHelper.returnConnection(connection);
		}

	}

	// 表6 中被配对的发票   要 填写已经使用的金额”
	public void updateShipmentInvoice(String itemNoF,String declarationAmountF) throws Exception {

		Connection connection = DBHelper.getConnection();
		try {

			String sql2="update shipment_invoice set amount_used_part=? where item_no=? ";
			PreparedStatement statement2 = connection.prepareStatement(sql2);
			statement2.setDouble(1, Double.valueOf(declarationAmountF));
			statement2.setString(2, itemNoF);
			statement2.executeUpdate();
			statement2.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			DBHelper.returnConnection(connection);
		}

	}

}

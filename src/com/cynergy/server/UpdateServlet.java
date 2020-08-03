package com.cynergy.server;
/**
 * 修改用户录入的项目信息
 */
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cynergy.pojo.ContractItemOther;
import org.apache.commons.lang.StringUtils;

import com.cynergy.main.DBHelper;
import com.cynergy.main.MainSql;
import com.cynergy.main.PropertisUtil;
import com.cynergy.main.ReadExcelUtils;
import com.cynergy.main.ReadExcelVO;
import com.cynergy.mapper.ContractItemMapperImpl;

public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static PropertisUtil propertisUtil = new PropertisUtil("config.properties");
	ContractItemMapperImpl contractItemsMapper = new ContractItemMapperImpl();
	
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
		Connection connection = DBHelper.getConnection();
		String ids = request.getParameter("id");
		int id=Integer.parseInt(ids);
		
		HttpSession session = request.getSession();
		String adminName = (String) session.getAttribute("adminName");
		request.setAttribute("adminName", adminName);
		try {

			updateProducts(request,response,connection,id);

			updateContract(request,response,connection,id);

			updateItem(request,response,connection,id);

			//新增客户公司名称
			String excelPath = request.getParameter("fileName");
			excelPath = excelPath == null ? "" : excelPath;
			//增加合同对应产品数据
			if(StringUtils.isNotBlank(excelPath)){
				ReadExcelUtils readUtil = new ReadExcelUtils(propertisUtil.get("excel_path")+File.separator+excelPath, id);				
				try {
					List<ReadExcelVO> contents = readUtil.readExcelContent();
					contractItemsMapper.insertBatch(contents);
				} catch (Exception e) {
					e.printStackTrace();
					out.println("解析上传excel失败");
				}
			}else{
				contractItemsMapper.insertBatchSingle(id);
			}
			request.setAttribute("id", id);
			RequestDispatcher homeDispatcher = request.getRequestDispatcher("PreprintServlet");
			homeDispatcher.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			out.println("失败");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("失败");
		}finally {
 			DBHelper.returnConnection(connection);
 		}
		out.flush();
		out.close();
	}

	private void updateProducts(HttpServletRequest request, HttpServletResponse response,Connection connection,int id){
		PreparedStatement statement = null;
		try{
			String purchase = StringUtils.trim(request.getParameter("purchase"));
			String sale = StringUtils.trim(request.getParameter("sale"));
			String clientName = StringUtils.trim(request.getParameter("clientName"));
			String hopeDate = StringUtils.trim(request.getParameter("hopeDate"));
			String estimateDate = StringUtils.trim(request.getParameter("estimateDate"));
			String totalGW = StringUtils.trim(request.getParameter("totalGW"));
			String totalNW = StringUtils.trim(request.getParameter("totalNW"));
			String detailed = StringUtils.trim(request.getParameter("detailed"));
			String frieght = StringUtils.trim(request.getParameter("frieght"));
			String Nonum = StringUtils.trim(request.getParameter("Nonum"));
			String date = StringUtils.trim(request.getParameter("date"));
			String waixiaotime = StringUtils.trim(request.getParameter("waixiaotime"));
			String address = StringUtils.trim(request.getParameter("address"));
			String transaction1 = StringUtils.trim(request.getParameter("transaction1"));
			String transaction2 = StringUtils.trim(request.getParameter("transaction2"));
			String volume = StringUtils.trim(request.getParameter("volume"));
			String saildate = StringUtils.trim(request.getParameter("saildate"));
			String arriveDate = StringUtils.trim(request.getParameter("arriveDate"));
			String fromwhere = StringUtils.trim(request.getParameter("fromwhere"));
			String towhere = StringUtils.trim(request.getParameter("towhere"));
			String packagessss = StringUtils.trim(request.getParameter("package"));
			String packageNum = StringUtils.trim(request.getParameter("packageNum"));
			String currency = StringUtils.trim(request.getParameter("currency"));
			String huodai = StringUtils.trim(request.getParameter("huodai"));
			String yunfei = StringUtils.trim(request.getParameter("yunfei"));
			String yunfeifs = StringUtils.trim(request.getParameter("yunfeifs"));
			String premium = StringUtils.trim(request.getParameter("premium"));
			System.out.println(yunfeifs+"+=========");

			String palletDimension = StringUtils.trim(request.getParameter("palletDimension"));
			String casketSize = StringUtils.trim(request.getParameter("casketSize"));
			Integer casketQuantity = 0;
			if(!(request.getParameter("casketQuantity") == null || "".equals(request.getParameter("casketQuantity")) || "null".equals(request.getParameter("casketQuantity")))){
				casketQuantity = Integer.parseInt(request.getParameter("casketQuantity"));
			}
			String casketType = StringUtils.trim(request.getParameter("casketType"));
			String freightInfo = StringUtils.trim(request.getParameter("freightInfo"));
			String companyName = StringUtils.trim(request.getParameter("companyName"));
			companyName = companyName == null ? "" : companyName;

			String exportPlace = StringUtils.trim(request.getParameter("exportPlace"));
			exportPlace = exportPlace == null ? "" : exportPlace;

			//报关状态（预保存 0：正式保存：1）
			Integer orderStatus = 0;
			if(StringUtils.isNotBlank(request.getParameter("orderStatus"))){
				orderStatus = Integer.parseInt(request.getParameter("orderStatus"));
			}

			//新增客户公司名称
			String excelPath = request.getParameter("fileName");
			excelPath = excelPath == null ? "" : excelPath;

			String sql1 = "update products set purchase=?,sale=?,clientName=?," +
						"hopeDate=?,estimateDate=?,totalGW=?,totalNW=?,detailed=?," +
						"frieght=?,nonum=?,date=?,address=?,transaction1=?,transaction2=?," +
						"volume=?,saildate=?,fromwhere=?,towhere=?,package=?," +
						"packagenum=?,currency=?,huodai=?,yunfei=?,yunfeifs=?,premium=?,waixiaotime=?,arrive_date=?,pallet_dimension=?,casket_size=?,casket_quantity=?,casket_type=?,freight_info=?,company_name=?,export_place=?,order_status=?";

			sql1 = sql1 + (StringUtils.isNotBlank(excelPath) ? ",excel_path=? where id=?" : " where id=?");

			statement = connection.prepareStatement(sql1);
			statement.setString(1, purchase);
			statement.setString(2, sale);
			statement.setString(3, clientName);
			statement.setString(4, hopeDate);
			statement.setString(5, estimateDate);
			statement.setString(6, totalGW);
			statement.setString(7, totalNW);
			statement.setString(8, detailed);
			statement.setString(9, frieght);
			statement.setString(10, Nonum);
			statement.setString(11, date);
			statement.setString(12, address);
			statement.setString(13, transaction1);
			statement.setString(14, transaction2);
			statement.setString(15, volume);
			statement.setString(16, saildate);
			statement.setString(17, fromwhere);
			statement.setString(18, towhere);
			statement.setString(19, packagessss);
			statement.setString(20, packageNum);
			statement.setString(21, currency);
			statement.setString(22, huodai);
			statement.setString(23, yunfei);
			statement.setString(24, yunfeifs);
			statement.setString(25, premium);
			statement.setString(26, waixiaotime);
			statement.setString(27, arriveDate);
			statement.setString(28, palletDimension);
			statement.setString(29, casketSize);
			statement.setInt(30, casketQuantity);
			statement.setString(31, casketType);
			statement.setString(32, freightInfo);
			statement.setString(33, companyName);
			statement.setString(34, exportPlace);
			statement.setInt(35, orderStatus);
			//当重新上传合同品名表时，进行更新
			if(StringUtils.isNotBlank(excelPath)){
				statement.setString(36, excelPath);
				statement.setInt(37, id);
			}else{
				statement.setInt(36, id);
			}
			System.out.println("修改语句："+sql1);
			statement.executeUpdate();
		}catch (SQLException e){
			e.printStackTrace();
		}finally {
			DBHelper.closeResource(statement,null);
		}
	}

	private void updateContract(HttpServletRequest request, HttpServletResponse response,Connection connection,int id) throws SQLException{
		int totalSize = 10;
		if(StringUtils.isNotBlank(request.getParameter("totalSize"))){
			totalSize = Integer.parseInt(request.getParameter("totalSize"));
		}
		for(int j=1;j<=totalSize;j++){

			String conids = StringUtils.trim(request.getParameter("conid"+j));
			String purno = StringUtils.trim(request.getParameter("purno"+j));
			String factory = StringUtils.trim(request.getParameter("factory"+j));
			String money = StringUtils.trim(request.getParameter("money"+j));
			String times = StringUtils.trim(request.getParameter("times"+j));
			String totaltimes = StringUtils.trim(request.getParameter("totaltimes"+j));
			String rmb = StringUtils.trim(request.getParameter("rmb"+j));
			String orderId = StringUtils.trim(request.getParameter("orderId"+j));
			String isExtraInvoiceStr = StringUtils.trim(request.getParameter("isExtraInvoice"+j));
			Integer isExtraInvoice = isExtraInvoiceStr!=null ? Integer.parseInt(isExtraInvoiceStr) :  null;
			if(conids!=null && !"".equals(conids)){
				int conid=Integer.parseInt(conids);
				if(purno.equals("")){
					String sql2="delete from contract where id=?";
					System.out.println(sql2);
					PreparedStatement statement2 = connection.prepareStatement(sql2);
					statement2.setInt(1,conid);
					statement2.executeUpdate();
					statement2.close();
				}else{
					String sql2="update contract set purno=?,factory=?,money=?,times=?,totaltimes=?,rmb=?,order_id=?, is_extra_invoice = ? where id=?";
					PreparedStatement statement2 = connection.prepareStatement(sql2);
					statement2.setString(1, purno);
					statement2.setString(2, factory);
					statement2.setString(3, money);
					statement2.setString(4, times);
					statement2.setString(5, totaltimes);
					statement2.setString(6, rmb);
					statement2.setString(7,orderId);
					statement2.setInt(8,isExtraInvoice);
					statement2.setInt(9,conid);
					statement2.executeUpdate();
					statement2.close();
				}
			}else if(StringUtils.isNotBlank(purno)){
				String ss22="insert into contract(proId,purno,factory,money,times,totaltimes,rmb,is_extra_invoice)values(?,?,?,?,?,?,?,?)";
				PreparedStatement statement2 = connection.prepareStatement(ss22);
				statement2.setInt(1, id);
				statement2.setString(2, purno);
				statement2.setString(3, factory);
				statement2.setString(4, money);
				statement2.setString(5, times);
				statement2.setString(6, totaltimes);
				statement2.setString(7, rmb);
				statement2.setInt(8,isExtraInvoice);
				statement2.executeUpdate();
				statement2.close();
			}
		}


	}

	private void  updateItem(HttpServletRequest request, HttpServletResponse response,
				    Connection connection,int proid) throws Exception{
		List<ReadExcelVO> lst1 = new ArrayList<>();
		ReadExcelVO vo = null;
		for(int i=1;i<=30;i++){
			String itemeng = StringUtils.trim(request.getParameter("itemeng"+i));
			String itemids = StringUtils.trim(request.getParameter("itemid"+i));
			if(StringUtils.isEmpty(itemids) && StringUtils.isEmpty(itemeng)){
				continue;
			}
			String itemchn = StringUtils.trim(request.getParameter("itemchn"+i));
			String quantity = StringUtils.trim(request.getParameter("quantity"+i));
			String purprice = StringUtils.trim(request.getParameter("purprice"+i));
			String unitprice = StringUtils.trim(request.getParameter("unitprice"+i));
			String trueprice = StringUtils.trim(request.getParameter("trueprice"+i));
			trueprice = trueprice!= null ? trueprice : "";
			String shopingmark = StringUtils.trim(request.getParameter("shopingmark"+i));
			String hscode = StringUtils.trim(request.getParameter("hscode"+i));
			String nw = StringUtils.trim(request.getParameter("nw"+i));
			//新增货源地
			String sourceDestination = StringUtils.trim(request.getParameter("sourceDestination"+i));
			//数量单位
			String unit = StringUtils.trim(request.getParameter("unit"+i));
			String rate = StringUtils.trim(request.getParameter("rate"+i));
			String unitpriceall = StringUtils.trim(request.getParameter("unitpriceall"+i));
			int itemid = 0;
			if(itemids!=null){
				itemid = Integer.parseInt(itemids);
				if(itemeng.equals("")){
					String ss33="delete from items where id=?";
					System.out.println(ss33);
					PreparedStatement statement3 = connection.prepareStatement(ss33);
					statement3.setInt(1, itemid);
					statement3.executeUpdate();
					statement3.close();
				}else{

					String ss33="update items set itemeng=?,itemchn=?,quantity=?,purprice=?," +
							"unitprice=?,trueprice=?,shopingmark=?,hscode=?,nw=?,rate=?,unitpriceall=?,source_destination=?,unit=? where id=?";
					PreparedStatement statement3 = connection.prepareStatement(ss33);
					statement3.setString(1, itemeng);
					statement3.setString(2, itemchn);
					statement3.setString(3, quantity);
					statement3.setString(4, purprice);
					statement3.setString(5, unitprice);
					statement3.setString(6, trueprice);
					statement3.setString(7, shopingmark);
					statement3.setString(8, hscode);
					statement3.setString(9, nw);
					statement3.setString(10, rate);
					statement3.setString(11, unitpriceall);
					statement3.setString(12, sourceDestination);
					statement3.setString(13, unit);
					statement3.setInt(14, itemid);
					statement3.executeUpdate();
					statement3.close();
				}


			}else if(StringUtils.isNotEmpty(itemeng)){
//
				String ss33="insert into items(proId,itemeng,itemchn,quantity,purprice,unitprice,trueprice,shopingmark,hscode,nw,rate,unitpriceall,source_destination,unit)" +
						"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement statement3 = connection.prepareStatement(ss33);
				statement3.setInt(1, proid);
				statement3.setString(2, itemeng);
				statement3.setString(3, itemchn);
				statement3.setString(4, quantity);
				statement3.setString(5, purprice);
				statement3.setString(6, unitprice);
				statement3.setString(7, trueprice);
				statement3.setString(8, shopingmark);
				statement3.setString(9, hscode);
				statement3.setString(10, nw);
				statement3.setString(11, rate);
				statement3.setString(12, unitpriceall);
				statement3.setString(13, sourceDestination);
				statement3.setString(14, unit);
				statement3.executeUpdate();
				statement3.close();

				PreparedStatement stm4 = connection.prepareStatement("select id from items where proId=? and itemeng=? and itemchn=?");
				stm4.setInt(1, proid);
				stm4.setString(2, itemeng);
				stm4.setString(3, itemchn);
				ResultSet resultSet = stm4.executeQuery();
				if(resultSet.next()){
					itemid = resultSet.getInt("id");
				}
				stm4.close();
				resultSet.close();
			}

			String no = StringUtils.trim(request.getParameter("contractno"+i));
			String amount = StringUtils.trim(request.getParameter("contractamount"+i));
			String contractQuantity = StringUtils.trim(request.getParameter("contractquantity"+i));
			String declareid = StringUtils.trim(request.getParameter("contractid"+i));
			if(StringUtils.isEmpty(amount) || StringUtils.isEmpty(contractQuantity)){
				continue;
			}
			vo = new ReadExcelVO();
			vo.setAmount(Double.parseDouble(amount));
			vo.setItemchn(itemchn);
			vo.setProId(proid);
			vo.setPurno(no);
			vo.setId(StringUtils.isNotEmpty(declareid) ? Integer.parseInt(declareid) : 0);
			vo.setQuantity(Integer.parseInt(contractQuantity));
			vo.setItemId(itemid);
			lst1.add(vo);


		}
		contractItemsMapper.insertBatch(lst1);
	}

}

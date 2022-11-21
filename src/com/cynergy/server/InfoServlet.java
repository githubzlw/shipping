package com.cynergy.server;
/**
 * 修改项目时 先获取信息
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cynergy.mapper.InvoiceInfoMapper;
import com.cynergy.mapper.InvoiceInfoMapperImpl;
import com.cynergy.pojo.*;
import org.apache.commons.lang.StringUtils;

import com.cynergy.main.DBHelper;
import com.cynergy.main.WebCookie;

public class InfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DecimalFormat df = new DecimalFormat("#0.00");
	private InvoiceInfoMapper invoiceInfoMapper = new InvoiceInfoMapperImpl();

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
		String idString = request.getParameter("id");
		int id = Integer.parseInt(idString);
		request.setAttribute("cproid",idString);
		
		HttpSession session = request.getSession();
		//添加权限管理  每个人只能看到自己项目   Author:polo   2017/11/30
		String auth = WebCookie.getCookieByName(request, "auth");
		String proIds = session.getAttribute("proIds").toString();
		
		String pds = "";
		if(!(proIds == null || "".equals(proIds))){
			pds = proIds.substring(1);
		}
		
		
		Connection connection = DBHelper.getConnection();
		Connection connectionErp = DBHelper.getConnectionERP();
		try {
			Statement createStatement = connection.createStatement();
			Statement createStatementErp = connectionErp.createStatement();
			String sql1;
			if(Integer.parseInt(auth.toString()) == 1){
				sql1="select * from products where id ="+id;
			}else{
			    sql1="select * from products where id ="+id+" and id in ("+pds+")";
			}

			ResultSet res1 = createStatement.executeQuery(sql1);
			while (res1.next()) {
				request.setAttribute("purchase", res1.getString("purchase"));
				request.setAttribute("id", res1.getString("id"));
				request.setAttribute("sale", res1.getString("sale"));
				request.setAttribute("clientName", res1.getString("clientName"));
				request.setAttribute("address", res1.getString("address"));
				request.setAttribute("hopeDate", res1.getString("hopeDate"));
				request.setAttribute("estimateDate", res1.getString("estimateDate"));
				request.setAttribute("transaction1", res1.getString("transaction1"));
				request.setAttribute("transaction2", res1.getString("transaction2"));
				request.setAttribute("volume", res1.getString("volume"));
				request.setAttribute("totalGW", res1.getString("totalGW"));
				request.setAttribute("totalNW", res1.getString("totalNW"));
				request.setAttribute("saildate", res1.getString("saildate"));
				request.setAttribute("arriveDate", (res1.getString("arrive_date") == null ? "" : res1.getString("arrive_date")));
				request.setAttribute("fromwhere", res1.getString("fromwhere"));
				request.setAttribute("towhere", res1.getString("towhere"));
				request.setAttribute("package", res1.getString("package"));
				request.setAttribute("packageNum", res1.getString("packageNum"));
//				System.out.println("currency:"+res1.getString("currency"));
				request.setAttribute("currency", res1.getString("currency"));
				request.setAttribute("detailed", res1.getString("detailed"));
				request.setAttribute("frieght", res1.getString("frieght"));
				request.setAttribute("Nonum", res1.getString("nonum"));
				request.setAttribute("date", res1.getString("date"));
				request.setAttribute("huodai", res1.getString("huodai"));
				request.setAttribute("yunfei", res1.getString("yunfei"));
				request.setAttribute("yunfeifs", res1.getString("yunfeifs"));
				request.setAttribute("premium", res1.getString("premium"));
				request.setAttribute("waixiaotime", res1.getString("waixiaotime"));
				request.setAttribute("attrSource", res1.getInt("attr_source"));
				request.setAttribute("palletDimension", (res1.getString("pallet_dimension") == null ? "" : res1.getString("pallet_dimension")));
				request.setAttribute("casketSize", (res1.getString("casket_size") == null ? "" : res1.getString("casket_size")));
				request.setAttribute("casketQuantity", res1.getInt("casket_quantity"));
				request.setAttribute("casketType", res1.getString("casket_type"));
				request.setAttribute("freightInfo", (res1.getString("freight_info") == null ? "" : res1.getString("freight_info")));
				request.setAttribute("companyName", (res1.getString("company_name") == null ? "" : res1.getString("company_name")));
				request.setAttribute("excelPath", (res1.getString("excel_path") == null ? "" : res1.getString("excel_path")));
				request.setAttribute("exportPlace", (res1.getString("export_place") == null ? "" : res1.getString("export_place")));
				request.setAttribute("orderStatus", (res1.getInt("order_status")));
				request.setAttribute("ladingReminder", Integer.valueOf(res1.getInt("lading_reminder")));
                request.setAttribute("brandInfo", Integer.valueOf(res1.getInt("brand_info")));

				//20220801 add start
				//银行到账用的是“代理”的情况下，报关文件需要 有双抬头
				//代理名
				String agentName ="";
				//代理公司名
				String agentCpName ="";
				String clientName = res1.getString("clientName");
				if(StringUtils.isNotBlank(clientName)){
					Statement createStatement1 = connection.createStatement();
					String shSql2 ="select agent_name,agent_cp_name from shipment_object where customer_name='"+clientName+"' and agent_status=2";
					ResultSet resSh2 = createStatement1.executeQuery(shSql2);
					while (resSh2.next()) {
						agentName = resSh2.getString("agent_name");
						agentCpName = resSh2.getString("agent_cp_name");

					}
				}
//				request.setAttribute("agentName",agentName);
//				request.setAttribute("agentCpName",agentCpName);

				// 20220905 代理处理 start
				if(StringUtils.isNotBlank(agentCpName)){
					String strAddS = res1.getString("address");
					strAddS = strAddS.substring(0,strAddS.indexOf("\r\n"));
					strAddS = strAddS+"/"+agentName;
					String strAddE = res1.getString("address");
					strAddE = strAddE.substring(strAddE.indexOf("\r\n"));

					request.setAttribute("address", strAddS+strAddE);
				}
				// 20220905 代理处理 end



				//20220801 add end

			}
			String sql2;
			if(Integer.parseInt(auth.toString()) == 1){
				sql2="select  i.* from  items i  where i.proId ="+id;
			}else{
				sql2="select  i.* from  items i where i.proId ="+id +" and i.proId in ("+pds+")";
			}
			
			ResultSet res2 = createStatement.executeQuery(sql2);
			List<ContractProduct> items = new ArrayList<>();
			int totalpro2=0;
			ContractProduct item = null;
			while (res2.next()) {
				totalpro2++;
				item = new ContractProduct();
				item.setItemid(res2.getInt("id"));
				item.setItemeng(res2.getString("itemeng"));
				item.setItemchn( res2.getString("itemchn"));
				item.setQuantity( res2.getString("quantity"));
				item.setPurprice(res2.getString("purprice"));
				item.setUnitprice(res2.getString("unitprice"));
				item.setTrueprice(res2.getString("trueprice"));
				item.setShopingmark(res2.getString("shopingmark"));
				item.setHscode(res2.getString("hscode"));
				item.setNw(res2.getString("nw"));
				item.setRate(res2.getString("rate"));
				item.setUnitpriceall(res2.getString("unitpriceall"));
				item.setSourceDestination(res2.getString("source_destination") == null ? "" : res2.getString("source_destination"));
				item.setUnit(res2.getString("unit") == null ? "PCS" : res2.getString("unit"));
				if(StringUtils.isNotEmpty(item.getPurprice())){
					Double truePrice = Double.parseDouble(item.getPurprice());
					item.setHbFive(df.format(truePrice/1.13/5));
					item.setHbSenven(df.format(truePrice/1.13/7));
				}
				items.add(item);
			}

			List<String> lstCase = new ArrayList<>();
			List<ContractWrap> purnos = new ArrayList<ContractWrap>();
			String sql3;
			if(Integer.parseInt(auth.toString()) == 1){
				sql3="select * from contract where proId ="+id;
			}else{
				sql3="select * from contract where proId ="+id+" and proId in ("+pds+")";
			}
			
			ResultSet res3 = createStatement.executeQuery(sql3);
			int totalpro3=0;
			ContractWrap contract = null;
			while (res3.next()) {
				totalpro3++;
				contract = new ContractWrap();
				contract.setConid( res3.getString("id"));
				contract.setFactory(res3.getString("factory"));
				contract.setMoney(res3.getString("money"));
				contract.setTotaltimes(res3.getString("totaltimes"));
				contract.setTimes(res3.getString("times"));
				contract.setRmb(res3.getString("rmb"));
				contract.setPurno(res3.getString("purno"));
				contract.setOrderId(res3.getString("order_id"));
				contract.setIsExtraInvoice(res3.getString("is_extra_invoice"));
				request.setAttribute("conid"+totalpro3, res3.getString("id"));
				request.setAttribute("purno"+totalpro3, res3.getString("purno"));
				request.setAttribute("factory"+totalpro3, res3.getString("factory"));
				request.setAttribute("money"+totalpro3, res3.getString("money"));
				request.setAttribute("times"+totalpro3, res3.getString("times"));
				request.setAttribute("totaltimes"+totalpro3, res3.getString("totaltimes"));
				request.setAttribute("rmb"+totalpro3, res3.getString("rmb"));
				request.setAttribute("orderId"+totalpro3, res3.getString("order_id"));
				request.setAttribute("isExtraInvoice"+totalpro3, res3.getString("is_extra_invoice"));
				purnos.add(contract);
				String no = res3.getString("purno");
				no = no.replace("合","SHS");
				String[] split = no.split("-");
				if(split.length > 1){
					no = split[0]+"-"+ split[1].replaceAll("([a-zA-Z])+","");
				}
				if(!lstCase.contains(no)){
					lstCase.add(no);
				}

				//20220801 add start
				//每个合同后面再显示一下 该合同是否有收到发票
				String invNo = res3.getString("purno");
				invNo = invNo.replace("合", "INV");
				String shSql1 ="select count(1) as cn  from  invoiceinfo where iinvno= '"+invNo +"' and iurl is not null";
				ResultSet resSh1 = createStatementErp.executeQuery(shSql1);
				while (resSh1.next()) {
					int cn = resSh1.getInt("cn");
					if(cn>0){
						request.setAttribute("invoicepdf"+totalpro3, "是");
					}else{
						request.setAttribute("invoicepdf"+totalpro3, "否");
					}

				}
				//20220801 add end


			}

			Map<String, CaseFund> contractMoney = invoiceInfoMapper.getContractMoney(lstCase);
			for(ContractProduct i:items){
				String no = i.getContractNo();
				if(StringUtils.isEmpty(no)){
					continue;
				}
				no = no.replace("合","SHS");
				String[] split = no.split("-");
				if(split.length > 1){
					no = split[0]+"-"+ split[1].replaceAll("([a-zA-Z])+","");
				}
				CaseFund caseFund = contractMoney.get(no);
				if(caseFund != null){
					i.setOrderActualMoney(caseFund.getOrderActualMoney());
					i.setOrderAmountReceived(caseFund.getOrderAmountReceived());
				}
			}

			request.setAttribute("items",items);
			request.setAttribute("itemsSize",items.size());
			//获取当前出运合同数
			request.setAttribute("totalSize",totalpro3);

			request.setAttribute("purnos",purnos);

			//统计出货批次
			for(int z=0,l=purnos.size();z<l;z++){
				List<String> ship = shipmentBatch(connection, purnos.get(z).getPurno());
				request.setAttribute("shipBatch"+(z+1),ship);
			}


			//查询电子出货单记录
			String sql4;
			if(Integer.parseInt(auth.toString()) == 1){
				sql4="select s.id,c.purno,s.purno as projectNo,s.serial_number,s.is_complete,s.sid,c.is_extra_invoice from contract c LEFT JOIN shipping_contract s on c.proId = s.proId and c.purno like '%'+replace(s.purno,'SHS','')+'%' where c.proId ="+id;
			}else{
				sql4="select s.id,c.purno,s.purno as projectNo,s.serial_number,s.is_complete,s.sid,c.is_extra_invoice from contract c LEFT JOIN shipping_contract s on c.proId = s.proId and c.purno like '%'+replace(s.purno,'SHS','')+'%' where c.proId ="+id +" and c.proId in ("+pds+")";
			}
			
			ResultSet res4 = createStatement.executeQuery(sql4);
			int total4=0;
			while (res4.next()) {	
				total4++;
				String serialNumber = res4.getString("serial_number");
				request.setAttribute("shippingId"+total4, res4.getInt("id"));
											
				request.setAttribute("isComplete"+total4, res4.getInt("is_complete"));
				//如果是带票不需要出货单
				if(res4.getInt("is_extra_invoice")==1){
					request.setAttribute("isComplete"+total4, 1);
					total4--;
					continue;
				}
				request.setAttribute("sid"+total4, res4.getInt("sid"));
				//获取项目号
				String projectNo = "";
				if(StringUtils.isBlank(serialNumber)){
					String purno = res4.getString("purno");
					purno = purno.replaceAll("[a-zA-Z]","");
					purno = purno.replace("合", "");
					projectNo = "SHS"+ purno;
					request.setAttribute("projectNo"+total4, projectNo);	
				}else{
					request.setAttribute("projectNo"+total4, res4.getString("projectNo"));	
				}
				request.setAttribute("serialNumber"+total4, serialNumber);
//				request.setAttribute("projectNo"+total4, projectNo);
				
			}
			request.setAttribute("total4",total4);
			
			
			
			String sql5 = "select * from invoice_pic where proId ="+id;		
			ResultSet res5 = createStatement.executeQuery(sql5);
			int totalpro5=0;
			while (res5.next()) {
				totalpro5++;
				request.setAttribute("invoiceId"+totalpro5, res5.getString("id"));
				request.setAttribute("factoryName"+totalpro5, res5.getString("factory_name"));
				request.setAttribute("picName"+totalpro5, res5.getString("pic_name"));
			}
			request.setAttribute("totalpro5",totalpro5);
			
			
			RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/update.jsp");
			homeDispatcher.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
 			DBHelper.returnConnection(connection);
			DBHelper.returnConnection(connectionErp);

 		}
		out.flush();
		out.close();
	}

	private List<ContractItemOther> queryContractDeclare(int itemid,Connection connection) throws SQLException{
		List<ContractItemOther> items = new ArrayList<>();

		String sql = "select id,purno,amount,quantity,item_id from contract_items" +
				" where item_id=?";
		PreparedStatement statement3 = connection.prepareStatement(sql);
		statement3.setInt(1, itemid);
		ResultSet resultSet = statement3.executeQuery();
		ContractItemOther item = null;
		while(resultSet.next()){

			item = new ContractItemOther()
					.setId(resultSet.getInt("id"))
					.setContractNo(resultSet.getString("purno"))
					.setDeclareAmount(resultSet.getString("amount"))
					.setDeclareQuantity(resultSet.getString("quantity"))
					.setItemid(resultSet.getInt("item_id"));
			items.add(item);
		}
		statement3.close();
		return items;
	}

	private List<String> shipmentBatch(Connection connection,String contractNo) throws SQLException{
		List<String> items = new ArrayList<>();

		String sql = "select money from contract where purno=?";
		PreparedStatement statement3 = connection.prepareStatement(sql);
		statement3.setString(1, contractNo);
		ResultSet resultSet = statement3.executeQuery();
		ContractItemOther item = null;
		while(resultSet.next()){
			items.add(resultSet.getString("money"));
		}
		DBHelper.closeResource(statement3,resultSet);
		return items;
	}

}

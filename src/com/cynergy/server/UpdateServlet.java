package com.cynergy.server;
/**
 * 修改用户录入的项目信息
 */
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
//			Statement createStatement = connection.createStatement();
			String purchase=request.getParameter("purchase");
			if(purchase!=null){
				purchase=purchase.trim();
			}
			String sale=request.getParameter("sale");
			if(sale!=null){
				sale=sale.trim();
			}
			String clientName=request.getParameter("clientName");
			if(clientName!=null){
				clientName=clientName.trim();
			}
			String hopeDate=request.getParameter("hopeDate");
			if(hopeDate!=null){
				hopeDate=hopeDate.trim();
			}
			String estimateDate=request.getParameter("estimateDate");
			if(estimateDate!=null){
				estimateDate=estimateDate.trim();
			}
			String totalGW=request.getParameter("totalGW");
			if(totalGW!=null){
				totalGW=totalGW.trim();
			}
			String totalNW=request.getParameter("totalNW");
			if(totalNW!=null){
				totalNW=totalNW.trim();
			}
			String detailed=request.getParameter("detailed");
			if(detailed!=null){
				detailed=detailed.trim();
			}
			String frieght=request.getParameter("frieght");
			if(frieght!=null){
				frieght=frieght.trim();
			}
			String Nonum=request.getParameter("Nonum");
			if(Nonum!=null){
				Nonum=Nonum.trim();
			}
			String date=request.getParameter("date");
			if(date!=null){
				date=date.trim();
			}
			String waixiaotime=request.getParameter("waixiaotime");
			if(waixiaotime!=null){
				waixiaotime=waixiaotime.trim();
			}
			String address=request.getParameter("address");
			if(address!=null){
				address=address.trim();
			}
			String transaction1=request.getParameter("transaction1");
			if(transaction1!=null){
				transaction1=transaction1.trim();
			}
			String transaction2=request.getParameter("transaction2");
			if(transaction2!=null){
				transaction2=transaction2.trim();
			}
			String volume=request.getParameter("volume");
			if(volume!=null){
				volume=volume.trim();
			}
			String saildate=request.getParameter("saildate");
			if(saildate!=null){
				saildate=saildate.trim();
			}
			String arriveDate=request.getParameter("arriveDate");
			if(arriveDate!=null){
				arriveDate=arriveDate.trim();
			}
			String fromwhere=request.getParameter("fromwhere");
			if(fromwhere!=null){
				fromwhere=fromwhere.trim();
			}
			String towhere=request.getParameter("towhere");
			if(towhere!=null){
				towhere=towhere.trim();
			}
			String packagessss=request.getParameter("package");
			if(packagessss!=null){
				packagessss=packagessss.trim();
			}
			String packageNum=request.getParameter("packageNum");
			if(packageNum!=null){
				packageNum=packageNum.trim();
			}
			String currency=request.getParameter("currency");
			if(currency!=null){
				currency=currency.trim();
			}
			String huodai=request.getParameter("huodai");
			if(huodai!=null){
				huodai=huodai.trim();
			}
			String yunfei=request.getParameter("yunfei");
			if(yunfei!=null){
				yunfei=yunfei.trim();
			}
			String yunfeifs=request.getParameter("yunfeifs");
			if(yunfeifs!=null){
				yunfeifs=yunfeifs.trim();
			}
			String premium=request.getParameter("premium");
			if(premium!=null){
				premium=premium.trim();
			}
			System.out.println(yunfeifs+"+=========");
			
			String palletDimension = request.getParameter("palletDimension");
			if(palletDimension != null){
				palletDimension = palletDimension.trim();
			}
			String casketSize = request.getParameter("casketSize");
			if(casketSize != null){
				casketSize = casketSize.trim();
			}
			Integer casketQuantity = 0;
			if(!(request.getParameter("casketQuantity") == null || "".equals(request.getParameter("casketQuantity")) || "null".equals(request.getParameter("casketQuantity")))){
				casketQuantity = Integer.parseInt(request.getParameter("casketQuantity"));
			}
			String casketType = request.getParameter("casketType");
			if(casketType != null){
				casketType = casketType.trim();
			}
			String freightInfo = request.getParameter("freightInfo");
			if(freightInfo != null){
				freightInfo = freightInfo.trim();
			}
			
			String companyName = request.getParameter("companyName");
			if(companyName == null){
				companyName = "";
			}
			String exportPlace = request.getParameter("exportPlace");
			if(exportPlace == null){
				exportPlace = "";
			}
			//报关状态（预保存 0：正式保存：1）
			Integer orderStatus = 0;
			if(StringUtils.isNotBlank(request.getParameter("orderStatus"))){
				orderStatus = Integer.parseInt(request.getParameter("orderStatus"));
			}
			
			//新增客户公司名称
			String excelPath = request.getParameter("fileName");
			if(excelPath == null){
				excelPath = "";
			}
			//合同关联产品
			List<ReadExcelVO> contents = null;
			
//			java.util.Date date2=new java.util.Date();
//			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//			String format2 = format.format(date2);
			
//			String sql1="update products set purchase='"+purchase+"',sale='"+sale+"',clientName='"+clientName+"',hopeDate='"+hopeDate+"',estimateDate='"+
//			estimateDate+"',totalGW='"+totalGW+"',totalNW='"+totalNW+"',detailed='"+detailed+"',frieght='"+frieght+"',nonum='"+Nonum+"',date='"+
//			date+"',address='"+address+"',transaction1='"+transaction1+"',transaction2='"+transaction2+"',volume='"+
//			volume+"',saildate='"+saildate+"',fromwhere='"+fromwhere+"',towhere='"+towhere+"',package='"+packagessss+"',packagenum='"+packageNum+"',currency='"+currency+"',huodai='"+huodai+"',yunfei='"+yunfei+"',yunfeifs='"+yunfeifs+"' where id="+id;
//			System.out.println(sql1);
//			createStatement.executeUpdate(sql1);
			String sql1 = "";
			if(StringUtils.isNotBlank(excelPath)){
				sql1="update products set purchase=?,sale=?,clientName=?," +
						"hopeDate=?,estimateDate=?,totalGW=?,totalNW=?,detailed=?," +
						"frieght=?,nonum=?,date=?,address=?,transaction1=?,transaction2=?," +
						"volume=?,saildate=?,fromwhere=?,towhere=?,package=?," +
						"packagenum=?,currency=?,huodai=?,yunfei=?,yunfeifs=?,premium=?,waixiaotime=?,arrive_date=?,pallet_dimension=?,casket_size=?,casket_quantity=?,casket_type=?,freight_info=?,company_name=?,export_place=?,order_status=?,excel_path=? where id=?";
			}else{
				sql1="update products set purchase=?,sale=?,clientName=?," +
						"hopeDate=?,estimateDate=?,totalGW=?,totalNW=?,detailed=?," +
						"frieght=?,nonum=?,date=?,address=?,transaction1=?,transaction2=?," +
						"volume=?,saildate=?,fromwhere=?,towhere=?,package=?," +
						"packagenum=?,currency=?,huodai=?,yunfei=?,yunfeifs=?,premium=?,waixiaotime=?,arrive_date=?,pallet_dimension=?,casket_size=?,casket_quantity=?,casket_type=?,freight_info=?,company_name=?,export_place=?,order_status=? where id=?";
			}
			
			
			PreparedStatement statement = connection.prepareStatement(sql1);
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
			statement.close();
//			String ss="insert into products(purchase,sale,clientName,hopeDate,estimateDate,totalGW,
//			totalNW,detailed,frieght,nonum,date,address,transaction1,transaction2,volume,saildate,fromwhere,towhere,package,packagenum,currency,dateTime)" +
//					"values('"+purchase+"','"+sale+"','"+clientName+"','"+hopeDate+"','"+estimateDate+"','"+totalGW+"','"+totalNW+"','"+detailed+"','"+frieght+"','"+Nonum+"','"+date+"','"+address
//			+"','"+transaction1+"','"+transaction2+"','"+volume+"','"+saildate+"','"+fromwhere+"','"+towhere+"','"+packagessss+"','"+packageNum+"','"+currency+"','"+format2+"')";
//			System.out.println("插入商品语句："+ss);
//			createStatement.execute(ss);
			
			
//			String getIdSql="select id from products where dateTime='"+format2+"' and nonum='"+Nonum+"'";
//			ResultSet resId = createStatement.executeQuery(getIdSql);
//			int proId=0;
//			while(resId.next()){
//				proId = resId.getInt("id");
//			}
			
			
			int totalSize = 10;
			if(StringUtils.isNotBlank(request.getParameter("totalSize"))){
				 totalSize = Integer.parseInt(request.getParameter("totalSize"));
			}
			for(int j=1;j<=totalSize;j++){
				
				String conids = request.getParameter("conid"+j);
				
				String purno = request.getParameter("purno"+j);
//				if(purno==null||purno.trim().equals("")){
//					continue;
//				}
//				purno=purno.trim();
				if(purno!=null){
					purno=purno.trim();
				}
				String factory = request.getParameter("factory"+j);
				if(factory!=null){
					factory=factory.trim();
				}
				String money = request.getParameter("money"+j);
				if(money!=null){
					money=money.trim();
				}
				String times = request.getParameter("times"+j);
				if(times!=null){
					times=times.trim();
				}
				String totaltimes = request.getParameter("totaltimes"+j);
				if(totaltimes!=null){
					totaltimes=totaltimes.trim();
				}
				String rmb = request.getParameter("rmb"+j);
				if(rmb!=null){
					rmb=rmb.trim();
				}
				String orderId = request.getParameter("orderId"+j);
				if(orderId!=null){
					orderId=orderId.trim();
				}
				Integer isExtraInvoice = null;
				String isExtraInvoiceStr = request.getParameter("isExtraInvoice"+j);
				if(isExtraInvoiceStr!=null){
					isExtraInvoice = Integer.parseInt(isExtraInvoiceStr);
				}
//				String conids = request.getParameter("conid"+j);
				if(conids!=null && !"".equals(conids)){
//					conid=conid.trim();
					int conid=Integer.parseInt(conids);
					if(purno.equals("")){
						String sql2="delete from contract where id=?";
						System.out.println(sql2);
						PreparedStatement statement2 = connection.prepareStatement(sql2);
						statement2.setInt(1,conid);
						statement2.executeUpdate();
						statement2.close();
					}else{
	//					String sql2="update contract set purno='"+purno+"',factory='"+factory+"',money='"+money+"',times='"+times+"',totaltimes='"+totaltimes+"',rmb='"+rmb+"' where id="+conid;
	////					System.out.println("修改合同语句："+sql2);
	//					createStatement.executeUpdate(sql2);
						String sql2="update contract set purno=?,factory=?,money=?,times=?,totaltimes=?,rmb=?,order_id=?, is_extra_invoice = ? where id=?";
	////				System.out.println("修改合同语句："+sql2);
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
				}else{
//					String ss22="insert into contract(proId,purno,factory,money,times,totaltimes,rmb)values("+id+",'"+purno
//					+"','"+factory+"','"+money+"','"+times+"','"+totaltimes+"','"+rmb+"')";
////					System.out.println("新加合同语句："+ss22);
//					createStatement.execute(ss22);
					if(StringUtils.isNotBlank(purno)){
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
			
			for(int i=1;i<=13;i++){
				String itemids = request.getParameter("itemid"+i);
				String itemeng = request.getParameter("itemeng"+i);
//				if(itemeng==null||itemeng.trim().equals("")){
//					continue;
//				}
//				itemeng=itemeng.trim();
				if(itemeng!=null){
					itemeng=itemeng.trim();
				}
				String itemchn = request.getParameter("itemchn"+i);
				if(itemchn!=null){
					itemchn=itemchn.trim();
				}
				String quantity = request.getParameter("quantity"+i);
				if(quantity!=null){
					quantity=quantity.trim();
				}
				String purprice = request.getParameter("purprice"+i);
				if(purprice!=null){
					purprice=purprice.trim();
				}
				String unitprice = request.getParameter("unitprice"+i);
				if(unitprice!=null){
					unitprice=unitprice.trim();
				}
				String trueprice = request.getParameter("trueprice"+i);
				if(trueprice!=null){
					trueprice=trueprice.trim();
				}else{
					trueprice="";
				}
				String shopingmark = request.getParameter("shopingmark"+i);
				if(shopingmark!=null){
					shopingmark=shopingmark.trim();
				}
				String hscode = request.getParameter("hscode"+i);
				if(hscode!=null){
					hscode=hscode.trim();
				}
				String nw = request.getParameter("nw"+i);
				if(nw!=null){
					nw=nw.trim();
				}
				//新增货源地
				String sourceDestination = request.getParameter("sourceDestination"+i);
				if(sourceDestination!=null){
					sourceDestination=sourceDestination.trim();
				}
				//数量单位
				String unit = request.getParameter("unit"+i);
				if(unit!=null){
					unit=unit.trim();
				}
				
				String rate = request.getParameter("rate"+i);
				if(rate!=null){
					rate=rate.trim();
				}
				String unitpriceall = request.getParameter("unitpriceall"+i);
				if(unitpriceall!=null){
					unitpriceall=unitpriceall.trim();
				}
//				String itemids = request.getParameter("itemid"+i);
				if(itemids!=null){
					int itemid=Integer.parseInt(itemids);
					if(itemeng.equals("")){
						String ss33="delete from items where id=?";
						System.out.println(ss33);
						PreparedStatement statement3 = connection.prepareStatement(ss33);
						statement3.setInt(1, itemid);
						statement3.executeUpdate();
						statement3.close();
					}else{
	//					String sql2="update items set itemeng='"+itemeng+"',itemchn='"+itemchn+"',quantity='"+quantity+"',purprice='"+purprice+
	//					"',unitprice='"+unitprice+"',trueprice='"+trueprice+ "',shopingmark='"+shopingmark+ "',hscode='"+hscode+ "',nw='"+nw+ "',rate='"+rate+ "',unitpriceall='"+unitpriceall+ "' where id="+itemid;
	//					System.out.println("修改物品语句："+sql2);
	//					createStatement.executeUpdate(sql2);
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
				}else{
//					java.util.Date date2=new java.util.Date();
//					SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//					String format2 = format.format(date2);
//					String ss33="insert into items(proId,itemeng,itemchn,quantity,purprice,unitprice,trueprice,shopingmark,hscode,nw,rate,unitpriceall)values("+id+",'"+itemeng+"','"+itemchn
//					+"','"+quantity+"','"+purprice+"','"+unitprice+"','"+trueprice+"','"+shopingmark+"','"+hscode+"','"+nw+"','"+rate+"','"+unitpriceall+"')";
//					createStatement.execute(ss33);
//					System.out.println("插入物品语句："+ss33);
					if(!itemeng.equals("")){
						String ss33="insert into items(proId,itemeng,itemchn,quantity,purprice,unitprice,trueprice,shopingmark,hscode,nw,rate,unitpriceall,source_destination,unit)" +
						"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						PreparedStatement statement3 = connection.prepareStatement(ss33);
						statement3.setInt(1, id);
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
					}
				}
			}
			
			
			
			//增加合同对应产品数据
			if(StringUtils.isNotBlank(excelPath)){
				ReadExcelUtils readUtil = new ReadExcelUtils(propertisUtil.get("excel_path")+File.separator+excelPath, id);				
				try {
					contents = readUtil.readExcelContent();
					contractItemsMapper.insertBatch(contents);
				} catch (Exception e) {
					e.printStackTrace();
					out.println("解析上传excel失败");
				}
			}else{
				contractItemsMapper.insertBatchSingle(id);
			}
			
		
			
//			out.println("成功");
//			RequestDispatcher homeDispatcher = request.getRequestDispatcher("QuestFirstServlet");
//			homeDispatcher.forward(request, response);
			request.setAttribute("id", id);
			RequestDispatcher homeDispatcher = request.getRequestDispatcher("PreprintServlet");
			homeDispatcher.forward(request, response);
//			RequestDispatcher homeDispatcher = request.getRequestDispatcher("InfoServlet");
//			homeDispatcher.forward(request, response);
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

}

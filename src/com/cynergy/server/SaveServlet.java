package com.cynergy.server;
/**
 * 保存 用户录入的项目
 */
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.cynergy.main.DBHelper;
import com.cynergy.main.PropertisUtil;
import com.cynergy.main.ReadExcelUtils;
import com.cynergy.main.ReadExcelVO;
import com.cynergy.mapper.ContractItemMapperImpl;
import com.cynergy.mapper.ContractItemsMapper;

public class SaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static PropertisUtil propertisUtil = new PropertisUtil("config.properties");
	private static String PATH =propertisUtil.get("excel_path");
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
//		String sql1="create table products (id int identity (1,1) primary key,adminName varchar(50),nonum varchar(50)," +
//				"date varchar(50),address varchar(200),transaction1 varchar(50),transaction2 varchar(50)," +
//				"volume varchar(50),saildate varchar(50),fromwhere varchar(50),towhere varchar(50)," +
//				"package varchar(50),packagenum varchar(10),currency varchar(5),timeDate datetime," +
//				"purchase varchar(50),sale varchar(50),clientName varchar(50),hopeDate varchar(50)," +
//				"estimateDate varchar(50),totalGW varchar(50),totalNW varchar(50),detailed varchar(200),frieght varchar(200),huodai varchar(50),yunfei varchar(10),yunfeifs varchar(10),premium varchar(10));";
//		String sql2="create table items (id int identity (1,1) primary key,proId int," +
//				"itemeng varchar(50),itemchn varchar(50),quantity varchar(50),purprice varchar(50),trueprice varchar(50),unitprice varchar(200),shopingmark varchar(50)," +
//				"hscode varchar(50),nw varchar(10),gw varchar(10),pageNum varchar(10),rate varchar(10),unitpriceall varchar(10));";
//		String sql3="create table contract (id int identity (1,1) primary key,proId int,purno varchar(50),factory varchar(50),money varchar(50),times varchar(5),totaltimes varchar(5),rmb varchar(15));";
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		Connection connection = DBHelper.getConnection();
		Integer adminIdInt = (Integer) session.getAttribute("adminId");
		if(adminIdInt==null){
			return;
		}
		String adminName = (String) session.getAttribute("adminName");
		int adminId = adminIdInt.intValue();
		try {
//			Statement createStatement = connection.createStatement();
//			createStatement.execute("drop table products;");
//			createStatement.execute("drop table items;");
//			createStatement.execute("drop table contract;");
//			boolean execute1 = createStatement.execute(sql1);
//			boolean execute2 = createStatement.execute(sql2);
//			boolean execute3 = createStatement.execute(sql3);
//			System.out.println(execute1);
//			System.out.println(execute2);
//			System.out.println(execute3);
			
			String purchase=request.getParameter("purchase");
			if(purchase!=null){
				purchase=purchase.trim();
				System.out.println("purchase="+purchase.length());
			}
			String sale=request.getParameter("sale");
			if(sale!=null){
				sale=sale.trim();
				System.out.println("sale="+sale.length());
			}
			String clientName=request.getParameter("clientName");
			if(clientName!=null){
				clientName=clientName.trim();
				System.out.println("clientName="+clientName.length());
			}
			String hopeDate=request.getParameter("hopeDate");
			if(hopeDate!=null){
				hopeDate=hopeDate.trim();
				System.out.println("hopeDate="+hopeDate.length());
			}
			String estimateDate=request.getParameter("estimateDate");
			if(estimateDate!=null){
				estimateDate=estimateDate.trim();
				System.out.println("estimateDate="+estimateDate.length());
			}
			String totalGW=request.getParameter("totalGW");
			if(totalGW!=null){
				totalGW=totalGW.trim();
				System.out.println("totalGW="+totalGW.length());
			}
			String totalNW=request.getParameter("totalNW");
			if(totalNW!=null){
				totalNW=totalNW.trim();
				System.out.println("totalNW="+totalNW.length());
			}
			String detailed=request.getParameter("detailed");
			if(detailed!=null){
				detailed=detailed.trim();
				System.out.println("detailed="+detailed.length());
			}
			String frieght=request.getParameter("frieght");
			if(frieght!=null){
				frieght=frieght.trim();
				System.out.println("frieght="+frieght.length());
			}
			String Nonum=request.getParameter("Nonum");
			if(Nonum!=null){
				Nonum=Nonum.trim();
				System.out.println("Nonum="+Nonum.length());
			}
			String date=request.getParameter("date");
			if(date!=null){
				date=date.trim();
				System.out.println("date="+date.length());
			}
			String waixiaotime=request.getParameter("waixiaotime");
			if(waixiaotime!=null){
				waixiaotime=waixiaotime.trim();
				System.out.println("waixiaotime="+waixiaotime.length());
			}
			String address=request.getParameter("address");
			if(address!=null){
				address=address.trim();
				System.out.println("address="+address.length());
			}
			String transaction1=request.getParameter("transaction1");
			if(transaction1!=null){
				transaction1=transaction1.trim();
				System.out.println("transaction1="+transaction1.length());
			}
			String transaction2=request.getParameter("transaction2");
			if(transaction2!=null){
				transaction2=transaction2.trim();
				System.out.println("transaction2="+transaction2.length());
			}
			String volume=request.getParameter("volume");
			if(volume!=null){
				volume=volume.trim();
				System.out.println("volume="+volume.length());
			}
			String saildate=request.getParameter("saildate");
			if(saildate!=null){
				saildate=saildate.trim();
				System.out.println("saildate="+saildate.length());
			}
			String fromwhere=request.getParameter("fromwhere");
			if(fromwhere!=null){
				fromwhere=fromwhere.trim();
				System.out.println("fromwhere="+fromwhere.length());
			}
			String towhere=request.getParameter("towhere");
			if(towhere!=null){
				towhere=towhere.trim();
				System.out.println("towhere="+towhere.length());
			}
			String packagessss=request.getParameter("package");
			if(packagessss!=null){
				packagessss=packagessss.trim();
				System.out.println("packagessss="+packagessss.length());
			}
			String packageNum=request.getParameter("packageNum");
			if(packageNum!=null){
				packageNum=packageNum.trim();
				System.out.println("packageNum="+packageNum.length());
			}
			String huodai=request.getParameter("huodai");
			if(huodai!=null){
				huodai=huodai.trim();
				System.out.println("huodai="+huodai.length());
			}
			String yunfei=request.getParameter("yunfei");
			if(yunfei!=null){
				yunfei=yunfei.trim();
				System.out.println("yunfei="+yunfei.length());
			}
			String yunfeifs=request.getParameter("yunfeifs");
			if(yunfeifs!=null){
				yunfeifs=yunfeifs.trim();
				System.out.println("yunfeifs="+yunfeifs.length());
			}
			String premium=request.getParameter("premium");
			if(premium!=null){
				premium=premium.trim();
				System.out.println("premium="+premium.length());
			}
			String currency=request.getParameter("currency");
//			System.out.println("保存时currency的值："+currency);
			if(currency!=null){
				currency=currency.trim();
				System.out.println("currency="+currency.length());
			}
			Integer attrSource = 0;
			if(!(request.getParameter("attrSource") == null || "".equals(request.getParameter("attrSource")))){
				attrSource=Integer.parseInt(request.getParameter("attrSource"));
				System.out.println("attrSource="+attrSource);
			}
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
				System.out.println(request.getParameter("casketQuantity"));
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
			//新增客户公司名称
			String companyName = request.getParameter("companyName");
			if(companyName == null){
				companyName = "";
			}
			//发货地址
			String exportPlace = request.getParameter("exportPlace");
			if(exportPlace == null){
				exportPlace = "";
			}
			//新增客户公司名称
			String excelPath = request.getParameter("fileName");
			if(excelPath == null){
				excelPath = "";
			}
			//报关状态（预保存 0：正式保存：1）
			Integer orderStatus = 0;
			if(StringUtils.isNotBlank(request.getParameter("orderStatus"))){
				orderStatus = Integer.parseInt(request.getParameter("orderStatus"));
			}
			//合同关联产品
			List<ReadExcelVO> contents = null;
			
			
			java.util.Date date2=new java.util.Date();
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String format2 = format.format(date2);
//			String ss="insert into products(purchase,sale,clientName,hopeDate,estimateDate,totalGW,totalNW,detailed,frieght,nonum,date,address,transaction1,transaction2,volume,saildate,fromwhere,towhere,package,packagenum,currency,huodai,yunfei,yunfeifs,timeDate)" +
//					"values('"+purchase+"','"+sale+"','"+clientName+"','"+hopeDate+"','"+estimateDate+"','"+totalGW+"','"+totalNW+"','"+detailed+"','"+frieght+"','"+Nonum+"','"+date+"','"+address
//			+"','"+transaction1+"','"+transaction2+"','"+volume+"','"+saildate+"','"+fromwhere+"','"+towhere+"','"+packagessss+"','"+packageNum+"','"+currency+"','"+huodai+"','"+yunfei+"','"+yunfeifs+"','"+format2+"')";
			String ss="insert into products(purchase,sale,clientName,hopeDate,estimateDate,totalGW," +
					"totalNW,detailed,frieght,nonum,date,address,transaction1,transaction2,volume," +
					"saildate,fromwhere,towhere,package,packagenum,currency,huodai,yunfei,yunfeifs,premium,timeDate,adminName,waixiaotime,attr_source,pallet_dimension,casket_size,casket_quantity,casket_type,freight_info,company_name,excel_path,export_place,order_status) " +
					"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			PreparedStatement statement = connection.prepareStatement(ss,Statement.RETURN_GENERATED_KEYS);
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
			statement.setString(26, format2);
			statement.setString(27, adminName);
			statement.setString(28, waixiaotime);
			statement.setInt(29, attrSource);
			statement.setString(30, palletDimension);
			statement.setString(31, casketSize);
			statement.setInt(32, casketQuantity);
			statement.setString(33, casketType);
			statement.setString(34, freightInfo);
			statement.setString(35, companyName);
			statement.setString(36, excelPath);
			statement.setString(37, exportPlace);
			statement.setInt(38, orderStatus);
			System.out.println("插入商品语句："+ss);
//			createStatement.execute(ss);
			statement.executeUpdate();
			/*Statement createStatement = connection.createStatement();
			String getIdSql="select id from products where timeDate='"+format2+"' and nonum='"+Nonum+"'";
			ResultSet resId = createStatement.executeQuery(getIdSql);
			int proId=0;
			while(resId.next()){
				proId = resId.getInt("id");
			}*/
			int proId=0;
			ResultSet res = statement.getGeneratedKeys();
			if (res.next()){
				proId = res.getInt(1);
			}
			statement.close();
			request.setAttribute("id", proId);
			System.out.println("iddddddddd="+proId);
//			Statement createStatement = connection.createStatement();
			int totalSize = 10;
			if(StringUtils.isNotBlank(request.getParameter("totalSize"))){
				 totalSize = Integer.parseInt(request.getParameter("totalSize"));
			}
			for(int j=1;j<=totalSize;j++){
				String purno = request.getParameter("purno"+j);
				if(purno==null||purno.trim().equals("")){
					continue;
				}
				purno=purno.trim();
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
//				String ss22="insert into contract(proId,purno,factory,money,times,totaltimes,rmb)values("+proId+",'"+purno
//				+"','"+factory+"','"+money+"','"+times+"','"+totaltimes+"','"+rmb+"')";
//				System.out.println("采购插入语句："+ss22);
//				createStatement.execute(ss22);
				String ss22="insert into contract(proId,purno,factory,money,times,totaltimes,rmb,order_id)values(?,?,?,?,?,?,?,?)";
				PreparedStatement statement2 = connection.prepareStatement(ss22);
				statement2.setInt(1, proId);
				statement2.setString(2, purno);
				statement2.setString(3, factory);
				statement2.setString(4, money);
				statement2.setString(5, times);
				statement2.setString(6, totaltimes);
				statement2.setString(7, rmb);
				statement2.setString(8, orderId);
				statement2.executeUpdate();
				statement2.close();
			}
			for(int i=1;i<=13;i++){
				String itemeng = request.getParameter("itemeng"+i);
				if(itemeng==null||itemeng.trim().equals("")){
					continue;
				}
				itemeng=itemeng.trim();
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
					trueprice = "";
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
				String rate = request.getParameter("rate"+i);
				if(rate!=null){
					rate=rate.trim();
				}
				String unitpriceall = request.getParameter("unitpriceall"+i);
				if(unitpriceall!=null){
					unitpriceall=unitpriceall.trim();
				}
				String unit = request.getParameter("unit"+i);
				if(unit!=null){
					unit=unit.trim();
				}
//				String ss33="insert into items(proId,itemeng,itemchn,quantity,purprice,unitprice,trueprice,shopingmark,hscode,nw,rate,unitpriceall)values("+proId+",'"+itemeng+"','"+itemchn
//				+"','"+quantity+"','"+purprice+"','"+unitprice+"','"+trueprice+"','"+shopingmark+"','"+hscode+"','"+nw+"','"+rate+"','"+unitpriceall+"')";
//				createStatement.execute(ss33);
//				System.out.println("插入物品语句："+ss33);
				String ss33="insert into items(proId,itemeng,itemchn,quantity,purprice,unitprice,trueprice,shopingmark,hscode,nw,rate,unitpriceall,source_destination,unit)" +
						"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement statement3 = connection.prepareStatement(ss33);
				statement3.setInt(1, proId);
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
			
		
			
			
			
			
			Cookie[] cookies = request.getCookies();
			
			Set<Integer> cookieSet=new HashSet<Integer>();
			StringBuffer cookieValue=new StringBuffer();
			for(Cookie c:cookies){
				String name = c.getName();
				if(name.equals("shipcookie")){
					cookieValue.append(c.getValue());
					break;
				}
			}
			cookieSet.add(proId);
			String[] split = cookieValue.toString().split("&");
			for(String sss:split){
				if(sss!=null&&!sss.trim().equals("")){
					cookieSet.add(Integer.parseInt(sss));
				}
			}
			StringBuffer ssBuffer=new StringBuffer();
			for(Integer a:cookieSet){
				ssBuffer.append(a+"&");
			}
			Cookie cookie=new Cookie("shipcookie", ssBuffer.toString());
			cookie.setMaxAge(3600*24*30);
			cookie.setPath("/");
			response.addCookie(cookie);
//			out.println("成功");
			request.setAttribute("id", proId);
			
//			int adminId = (Integer) session.getAttribute("adminId");
			String proIds = (String) session.getAttribute("proIds");
			proIds=proIds+","+proId;
			System.out.println(proIds+"===============");
			session.setAttribute("proIds", proIds);
			Statement createStatement = connection.createStatement();
			String sqladmin="update admin set proIds='"+proIds+"' where id="+adminId;
			System.out.println("sqlAdmin:"+sqladmin);
			createStatement.execute(sqladmin);
			createStatement.close();
			
			
			//增加合同对应产品数据
			if(StringUtils.isNotBlank(excelPath)){
				ReadExcelUtils readUtil = new ReadExcelUtils(PATH+File.separator+excelPath, proId);				
				try {
					contents = readUtil.readExcelContent();
					contractItemsMapper.insertBatch(contents);
				} catch (Exception e) {
					e.printStackTrace();
					out.println("解析上传excel失败");
				}
			}else{
				contractItemsMapper.insertBatchSingle(proId);
			}
			
			
			
			RequestDispatcher homeDispatcher = request.getRequestDispatcher("PreprintServlet");
			homeDispatcher.forward(request, response);
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

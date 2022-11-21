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
import java.util.ArrayList;
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
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		Connection connection = DBHelper.getConnection();
		Integer adminIdInt = (Integer) session.getAttribute("adminId");
		if(adminIdInt==null){
			return;
		}
		List<ReadExcelVO> lst1 = new ArrayList<>();
		ReadExcelVO vo = null;
		String adminName = (String) session.getAttribute("adminName");
		int adminId = adminIdInt.intValue();
		try {
			String purchase=request.getParameter("purchase");
			purchase = purchase!=null ? purchase.trim() : purchase;

			String sale=request.getParameter("sale");
			sale = sale!=null ? sale.trim() : null;

			String clientName=request.getParameter("clientName");
			clientName = clientName!=null ? clientName.trim() : null;

			String hopeDate=request.getParameter("hopeDate");
			hopeDate = hopeDate!=null ? hopeDate.trim() : null;

			String estimateDate=request.getParameter("estimateDate");
			estimateDate = estimateDate!=null ? estimateDate.trim() : null;

			String totalGW=request.getParameter("totalGW");
			totalGW = totalGW!=null ? totalGW.trim() : null;

			String totalNW=request.getParameter("totalNW");
			totalNW = totalNW!=null ? totalNW.trim() : null;

			String detailed=request.getParameter("detailed");
			detailed = detailed!=null ? detailed.trim() : null;

			String frieght=request.getParameter("frieght");
			frieght = frieght!=null ? frieght.trim() : null;

			String Nonum=request.getParameter("Nonum");
			Nonum = Nonum!=null ? Nonum.trim() : null;

			String date=request.getParameter("date");
			date = date!=null ? date.trim() : null;

			String waixiaotime=request.getParameter("waixiaotime");
			waixiaotime = waixiaotime!=null ? waixiaotime.trim() : null;

			String address=request.getParameter("address");
			address = address!=null ? address.trim() : null;

			String transaction1=request.getParameter("transaction1");
			transaction1 = transaction1!=null ? transaction1.trim() : null;

			String transaction2=request.getParameter("transaction2");
			transaction2 = transaction2!=null ? transaction2.trim() : null;

			String volume=request.getParameter("volume");
			volume = volume!=null ? volume.trim() : null;

			String saildate=request.getParameter("saildate");
			saildate = saildate!=null ? saildate.trim() : null;

			String fromwhere=request.getParameter("fromwhere");
			fromwhere = fromwhere!=null ? fromwhere.trim() : null;

			String towhere=request.getParameter("towhere");
			towhere = towhere!=null ? towhere.trim() : null;

			String packagessss=request.getParameter("package");
			packagessss = packagessss!=null ? packagessss.trim() : null;

			String packageNum=request.getParameter("packageNum");
			packageNum = packageNum!=null ? packageNum.trim() : null;

			String huodai=request.getParameter("huodai");
			huodai = huodai!=null ? huodai.trim() : null;

			String yunfei=request.getParameter("yunfei");
			yunfei = yunfei!=null ? yunfei.trim() : null;

			String yunfeifs = request.getParameter("yunfeifs");
			yunfeifs = yunfeifs!=null ? yunfeifs.trim() : null;

			String premium = request.getParameter("premium");
			premium = premium!=null ? premium.trim() : null;

			String currency = request.getParameter("currency");
			currency = currency!=null ? currency.trim() : null;

			Integer attrSource = 0;
			if(!(request.getParameter("attrSource") == null || "".equals(request.getParameter("attrSource")))){
				attrSource = Integer.parseInt(request.getParameter("attrSource"));
			}
			String palletDimension = request.getParameter("palletDimension");
			palletDimension = palletDimension!=null ? palletDimension.trim() : null;

			String casketSize = request.getParameter("casketSize");
			casketSize = casketSize!=null ? casketSize.trim() : null;

			Integer casketQuantity = 0;
			if(!(request.getParameter("casketQuantity") == null || "".equals(request.getParameter("casketQuantity")) || "null".equals(request.getParameter("casketQuantity")))){
				casketQuantity = Integer.parseInt(request.getParameter("casketQuantity"));
			}
			String casketType = request.getParameter("casketType");
			casketType = casketType != null ? casketType.trim() : null;

			String freightInfo = request.getParameter("freightInfo");
			freightInfo = freightInfo != null ? freightInfo.trim() : null;

			//新增客户公司名称
			String companyName = request.getParameter("companyName");
			companyName = companyName == null? "" : companyName;

			//发货地址
			String exportPlace = request.getParameter("exportPlace");
			exportPlace = exportPlace == null ? "" : exportPlace;

			//新增客户公司名称
			String excelPath = request.getParameter("fileName");
			excelPath = excelPath == null ? "" : excelPath;

			//提单说明
			int ladingReminder1 = 0;
			String ladingReminder = request.getParameter("ladingReminder");
			if (ladingReminder != null && !"".equalsIgnoreCase(ladingReminder)){
				ladingReminder1 = Integer.parseInt(ladingReminder);
			}

			//品牌
			Integer brandInfo = Integer.valueOf(0);
			if (StringUtils.isNotBlank(request.getParameter("brandInfo"))){
				brandInfo = Integer.valueOf(Integer.parseInt(request.getParameter("brandInfo")));
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
			String ss="insert into products(purchase,sale,clientName,hopeDate,estimateDate,totalGW," +
					"totalNW,detailed,frieght,nonum,date,address,transaction1,transaction2,volume," +
					"saildate,fromwhere,towhere,package,packagenum,currency,huodai,yunfei,yunfeifs,premium,timeDate,adminName,waixiaotime,attr_source,pallet_dimension,casket_size,casket_quantity,casket_type,freight_info,company_name,excel_path,export_place,order_status,lading_reminder,brand_info) " +
					"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
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
			statement.setInt(39, ladingReminder1);
			statement.setInt(40, brandInfo);
			System.out.println("插入商品语句："+ss);
			statement.executeUpdate();

			int proId=0;
			ResultSet res = statement.getGeneratedKeys();
			if (res.next()){
				proId = res.getInt(1);
			}
			statement.close();
			request.setAttribute("id", proId);
			System.out.println("iddddddddd="+proId);
			int totalSize = 10;
			if(StringUtils.isNotBlank(request.getParameter("totalSize"))){
				 totalSize = Integer.parseInt(request.getParameter("totalSize"));
			}
			for(int j=1;j<=totalSize;j++){
				String purno = StringUtils.trim(request.getParameter("purno"+j));
				if(purno==null||purno.trim().equals("")){
					continue;
				}
				String factory = StringUtils.trim(request.getParameter("factory"+j));

				String money = StringUtils.trim(request.getParameter("money"+j));

				String times = StringUtils.trim(request.getParameter("times"+j));

				String totaltimes = StringUtils.trim(request.getParameter("totaltimes"+j));

				String rmb = StringUtils.trim(request.getParameter("rmb"+j));

				String orderId = StringUtils.trim(request.getParameter("orderId"+j));

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
				String itemeng =  StringUtils.trim(request.getParameter("itemeng"+i));
				if(itemeng==null||itemeng.trim().equals("")){
					continue;
				}
				String itemchn =  StringUtils.trim(request.getParameter("itemchn"+i));
				String quantity =  StringUtils.trim(request.getParameter("quantity"+i));
				String purprice =  StringUtils.trim(request.getParameter("purprice"+i));
				String unitprice =  StringUtils.trim(request.getParameter("unitprice"+i));
				String trueprice =  StringUtils.trim(request.getParameter("trueprice"+i));
				trueprice = trueprice!=null ?trueprice : "";
				String shopingmark =  StringUtils.trim(request.getParameter("shopingmark"+i));
				String hscode =  StringUtils.trim(request.getParameter("hscode"+i));
				String nw =  StringUtils.trim(request.getParameter("nw"+i));
				//新增货源地
				String sourceDestination =  StringUtils.trim(request.getParameter("sourceDestination"+i));
				String rate =  StringUtils.trim(request.getParameter("rate"+i));
				String unitpriceall =  StringUtils.trim(request.getParameter("unitpriceall"+i));
				String unit =  StringUtils.trim(request.getParameter("unit"+i));


				String ss33="insert into items(proId,itemeng,itemchn,quantity,purprice,unitprice,trueprice,shopingmark,hscode,nw,rate,unitpriceall,source_destination,unit)" +
						"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement statement3 = connection.prepareStatement(ss33,Statement.RETURN_GENERATED_KEYS);
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


				int itemid = 0;
				ResultSet rs = statement3.getGeneratedKeys();
				if (rs.next()) {
					itemid = rs.getInt(1);
				}
				rs.close();
				statement3.close();

				/*String no = StringUtils.trim(request.getParameter("contractno"+i));
				String amount = StringUtils.trim(request.getParameter("contractamount"+i));
				String contractQuantity = StringUtils.trim(request.getParameter("contractquantity"+i));
				String declareid = StringUtils.trim(request.getParameter("contractid"+i));
				if(StringUtils.isEmpty(amount) || StringUtils.isEmpty(contractQuantity)){
					continue;
				}
				vo = new ReadExcelVO();
				vo.setAmount(Double.parseDouble(amount));
				vo.setItemchn(itemchn);
				vo.setProId(proId);
				vo.setPurno(no);
				vo.setId(StringUtils.isNotEmpty(declareid) ? Integer.parseInt(declareid) : 0);
				vo.setQuantity(Integer.parseInt(contractQuantity));
				vo.setItemId(itemid);
				lst1.add(vo);*/
			}
			/*contractItemsMapper.insertBatch(lst1);*/

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
			/*if(StringUtils.isNotBlank(excelPath)){
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
			}*/
			
			
			/*if(proId > 0){
				RequestDispatcher homeDispatcher = request.getRequestDispatcher("InfoServlet?id="+proId);
				homeDispatcher.forward(request, response);
			}else{

			}*/
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

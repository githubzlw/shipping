package com.cynergy.server;
/**
 * 汇总
 */

import com.cynergy.main.BankArrival;
import com.cynergy.main.DBHelper;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;
public class ShipmentPaymentNServlet extends HttpServlet {
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
		//出货品种id
		String proId = request.getParameter("proId");
		//标识flag：1跟单2财务
		String bsFlag = request.getParameter("bsFlag");

//		Connection connection = DBHelper.getConnection();
		Connection connectionErp = DBHelper.getConnectionERP();
		try {
//			Statement createStatement1 = connection.createStatement();

			Statement createStatementErp1 = connectionErp.createStatement();
			Statement createStatementErp2 = connectionErp.createStatement();
			PreparedStatement stmt = null;

			List<BankArrival> bankAList = new ArrayList<BankArrival>();

			//账项目号 和 本出运单项目号一致
			String sqlErp1="select a.TradeCurrency,b.AmountClaimFormId,a.PayersName, b.id,a.id as aid,a.TransactionReferenceNumber,a.TransactionDate,a.NBEmailId,b.caseno, a.TradeAmount,b.ifmoney,b.iimoney,b.paired,b.paired_amount,b.paired_lcamount, " +
					"b.paired_ssamount,(select customercode from itemCase where caseno=b.caseno) as customercode,b.invoice,c.kingdee_id ,b.paired_ys ,con.is_extra_invoice "+
					"from AccountEntryForm  a  "+
					"left join PreparatorEntryForm b on b.AmountClaimFormId=a.id and b.ifmoney!=0  "+
					"left join kingdee_info c on a.payersname=c.kingdee_name "+
                    "INNER  join (select   left(replace(purno,'合','SHS'),len(replace(purno,'合','SHS'))-1) as caseno, max(is_extra_invoice) is_extra_invoice from reportform.[dbo].contract where proId=  "+proId +"  "+
                    "group by  left(replace(purno,'合','SHS'),len(replace(purno,'合','SHS'))-1)) con  on b.caseno=con.caseno "+
//					" where b.caseno in ( select  left(replace(purno,'合','SHS'),len(replace(purno,'合','SHS'))-1) as caseno from reportform.[dbo].contract where proId= "+proId +" )   "+
					"order by  caseno desc, TransactionDate desc ";
			ResultSet resErp1 = createStatementErp1.executeQuery(sqlErp1);
			String payersName = "";
			int customerCode=0;
			StringBuilder amountClaimFormIds =new StringBuilder();
			while (resErp1.next()) {

				BankArrival bankA = new BankArrival();
				//银行序号
				bankA.setTransactionReferenceNumber(resErp1.getString("TransactionReferenceNumber"));
				bankA.setErpCustomerId(resErp1.getInt("customercode"));
				String pushtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

				bankA.setTransactionDate(formatStr(resErp1.getString("TransactionDate")));
				bankA.setItemNo(resErp1.getString("caseno"));
				//到账总金额
				bankA.setTradeAmount(resErp1.getInt("TradeAmount"));
				// 认领金额
				bankA.setDeclarationAmountF(resErp1.getInt("ifmoney"));
				bankA.setPaired(resErp1.getString("paired"));
				//已配对金额
				bankA.setPairedAmount(resErp1.getInt("paired_amount"));
				//本次配对金额
				bankA.setPairedLcamount(resErp1.getInt("paired_lcamount"));
				//本次销售配对金额
				bankA.setPairedSsamount(resErp1.getInt("paired_ssamount"));
				//应收金额
				bankA.setPairedYs(resErp1.getInt("paired_ys"));

				// 总应收
//				bankA.setTotalReceipts(resErp2.getInt("totalreceipts"));

				//认领表id
				bankA.setrId(resErp1.getInt("id"));
				//到账表id
				bankA.setaId(resErp1.getInt("aid"));
				//金蝶id
				bankA.setKingdeeId(resErp1.getInt("kingdee_id"));
				//发票号
				bankA.setInvoice(resErp1.getString("invoice"));
				bankA.setTradeCurrency(resErp1.getString("TradeCurrency"));
				//是否带票
                bankA.setIsExtraInvoice(resErp1.getInt("is_extra_invoice"));

				bankAList.add(bankA);
				//到账客户名
				payersName = resErp1.getString("PayersName");
				amountClaimFormIds.append(",'").append(resErp1.getInt("AmountClaimFormId")).append("'");
				customerCode = resErp1.getInt("customercode");

			}
			String amountClaimForm_Ids = amountClaimFormIds.toString();
			amountClaimForm_Ids = amountClaimForm_Ids.startsWith(",") ? amountClaimForm_Ids.substring(1) : amountClaimForm_Ids;

			// 财务场合 增加亟待配对数据
			if("2".equals(bsFlag)){

				// 跟单填写实际产品总价
				String sqlErp11="select unitpriceall from  reportform.[dbo].items itm where itm.proId= "+proId ;
				ResultSet resErp11 = createStatementErp1.executeQuery(sqlErp11);
				//汇总实际产品总价
				double sumUnitpriceall=0;
				while (resErp11.next()) {
					String unitpriceall = resErp11.getString("unitpriceall");
					if(StringUtils.isBlank(unitpriceall.trim())){
						unitpriceall="0";
					}
					sumUnitpriceall +=Double.valueOf(unitpriceall);

				}

				//账项目号 和 本出运单项目号不一致,当前客户所有到账认领数据
				String sqlErp2="select a.TradeCurrency,b.AmountClaimFormId,a.PayersName, b.id,a.id as aid,a.TransactionReferenceNumber,a.TransactionDate,a.NBEmailId,b.caseno, a.TradeAmount,b.ifmoney,b.iimoney,b.paired,b.paired_amount,b.paired_lcamount, " +
						"b.paired_ssamount,(select customercode from itemCase where caseno=b.caseno) as customercode,b.invoice,c.kingdee_id,b.paired_ys  "+
						"from AccountEntryForm  a  "+
						"left join PreparatorEntryForm b on b.AmountClaimFormId=a.id and b.ifmoney!=0  "+
						"left join kingdee_info c on a.payersname=c.kingdee_name "+
						"where a.PayersName=? and a.id not in (?)  "+
						"order by  caseno desc, TransactionDate desc ";

				ResultSet resErp2 = null;
				stmt = connectionErp.prepareStatement(sqlErp2.replace("(?)", "(" + amountClaimForm_Ids + ")"));
				stmt.setString(1, payersName);
				resErp2 = stmt.executeQuery();

				while (resErp2.next()) {

					BankArrival bankA = new BankArrival();
					//银行序号
					bankA.setTransactionReferenceNumber(resErp2.getString("TransactionReferenceNumber"));
					bankA.setErpCustomerId(resErp2.getInt("customercode"));
					bankA.setTransactionDate(formatStr(resErp2.getString("TransactionDate")));
					bankA.setItemNo(resErp2.getString("caseno"));
					//到账总金额
					bankA.setTradeAmount(resErp2.getInt("TradeAmount"));
					// 认领金额
					bankA.setDeclarationAmountF(resErp2.getInt("ifmoney"));
					bankA.setPaired(resErp2.getString("paired"));
					//已配对金额
					bankA.setPairedAmount(resErp2.getInt("paired_amount"));
					//本次配对金额
					bankA.setPairedLcamount(resErp2.getInt("paired_lcamount"));
					//认领表id
					bankA.setrId(resErp2.getInt("id"));
					//到账表id
					bankA.setaId(resErp2.getInt("aid"));
					//金蝶id
					bankA.setKingdeeId(resErp2.getInt("kingdee_id"));
					//发票号
					bankA.setInvoice(resErp2.getString("invoice"));
					bankA.setTradeCurrency(resErp2.getString("TradeCurrency"));
                    //是否带票
                    bankA.setIsExtraInvoice(8);


					//亟待配对
                    String sqlErp3="select count(1) cn from AccountEntryForm  " +
                            "where DATEADD(yy,-1,getdate()) < (select top 1 createtime from  AccountEntryForm where PayersName =? order by createtime desc ) ";

                    ResultSet resErp3 = null;
                    stmt = connectionErp.prepareStatement(sqlErp3);
                    stmt.setString(1, payersName);
                    resErp3 = stmt.executeQuery();

                    while (resErp3.next()) {

                        int count = resErp3.getInt("cn");
                        if(count==0){
                            bankA.setNeedPaired("亟待配对");
                        }
                    }
//
					//本次财务配对金额
					bankA.setPairedSsamount(resErp2.getInt("paired_ssamount"));
					//应收金额
					bankA.setPairedYs(resErp2.getInt("paired_ys"));

					//到账金额大于报关金额（跟单填写的产品总价）5% 提示
					if(sumUnitpriceall>0 && bankA.getTradeAmount()>(sumUnitpriceall+sumUnitpriceall*0.05) ){
						bankA.setBsFlag(1);
					}

					bankAList.add(bankA);
				}
			}



			//代理客户数据
			String sqlErp3="select agent_id,agent_name from reportform.[dbo].shipment_object where agent_status=2 and erp_customer_id=?  ";

			ResultSet resErp3 = null;
			stmt = connectionErp.prepareStatement(sqlErp3);
			stmt.setInt(1, customerCode);
			resErp3 = stmt.executeQuery();

			while (resErp3.next()) {

				String agentName = resErp3.getString("agent_name");
				if(StringUtils.isNotBlank(agentName)){
					// 当前客户代理客户所有到账认领数据
					String sqlErp4="select a.TradeCurrency,b.AmountClaimFormId,a.PayersName, b.id,a.id as aid,a.TransactionReferenceNumber,a.TransactionDate,a.NBEmailId,b.caseno, a.TradeAmount,b.ifmoney,b.iimoney,b.paired,b.paired_amount,b.paired_lcamount, " +
							"b.paired_ssamount,(select customercode from itemCase where caseno=b.caseno) as customercode,b.invoice,c.kingdee_id,b.paired_ys  "+
							"from AccountEntryForm  a  "+
							"left join PreparatorEntryForm b on b.AmountClaimFormId=a.id and b.ifmoney!=0  "+
							"left join kingdee_info c on a.payersname=c.kingdee_name "+
							"where a.PayersName=?  "+
							"order by  caseno desc, TransactionDate desc ";

					ResultSet resErp4 = null;
					stmt = connectionErp.prepareStatement(sqlErp4);
					stmt.setString(1, agentName);
					resErp4 = stmt.executeQuery();

					while (resErp4.next()) {

						BankArrival bankA = new BankArrival();
						//银行序号
						bankA.setTransactionReferenceNumber(resErp4.getString("TransactionReferenceNumber"));
						bankA.setErpCustomerId(resErp4.getInt("customercode"));
						bankA.setTransactionDate(formatStr(resErp4.getString("TransactionDate")));
						bankA.setItemNo(resErp4.getString("caseno"));
						//到账总金额
						bankA.setTradeAmount(resErp4.getInt("TradeAmount"));
						// 认领金额
						bankA.setDeclarationAmountF(resErp4.getInt("ifmoney"));
						bankA.setPaired(resErp4.getString("paired"));
						//已配对金额
						bankA.setPairedAmount(resErp4.getInt("paired_amount"));
						//本次配对金额
						bankA.setPairedLcamount(resErp4.getInt("paired_lcamount"));
						//认领表id
						bankA.setrId(resErp4.getInt("id"));
						//到账表id
						bankA.setaId(resErp4.getInt("aid"));
						//金蝶id
						bankA.setKingdeeId(resErp4.getInt("kingdee_id"));
						//发票号
						bankA.setInvoice(resErp4.getString("invoice"));
						bankA.setTradeCurrency(resErp4.getString("TradeCurrency"));
						//本次财务配对金额
						bankA.setPairedSsamount(resErp4.getInt("paired_ssamount"));
						//应收金额
						bankA.setPairedYs(resErp4.getInt("paired_ys"));
                        //是否带票
                        bankA.setIsExtraInvoice(8);
						bankAList.add(bankA);
					}

				}
			}


			//过去2年银行进账 减去 报关金额
			int sumCzPrice =0;
			int sumtrueprice =0;
			int sumTradeAmount =0;

			//根据id，查到报关金额
			String sqlErp5="select sum(cast(trueprice as decimal)) as sumtrueprice from reportform.[dbo].items   "
					+"where proId in (select id from products where clientName =(select clientName from products where id=?) and timeDate>'20200101')  and trueprice!='' ";
//            +"where proId in (select id from products where clientName =(select clientName from products where id=?) )  and trueprice!='' ";

			ResultSet resErp5 = null;
			stmt = connectionErp.prepareStatement(sqlErp5);
			stmt.setString(1, proId);
			resErp5 = stmt.executeQuery();

			while (resErp5.next()) {

				sumtrueprice = resErp5.getInt("sumtrueprice");
//					String sqlErp6="select sum(cast(TradeAmount as decimal)) as sumTradeAmount from AccountEntryForm  " +
//							"where  TransactionReferenceNumber is not null and createTime>'20200101'    "+
//							"and PayersName in (select a.PayersName from AccountEntryForm  a    "+
//							"left join PreparatorEntryForm b on b.AmountClaimFormId=a.id and b.ifmoney!=0   "+
//							"where   b.caseno in ( select  left(replace(purno,'合','SHS'),len(replace(purno,'合','SHS'))-1) as caseno from contract where proId in (?) ) "+
//							"and a.TransactionReferenceNumber is not null  "+
//							"group by a.PayersName) ";

                String sqlErp6="select sum(cast(TradeAmount as decimal)) as sumTradeAmount from AccountEntryForm  " +
                        "where  TransactionReferenceNumber is not null and createTime>'20200101'    "+
                        "and NBEmailId in (select a.NBEmailId from AccountEntryForm  a    "+
                        "left join PreparatorEntryForm b on b.AmountClaimFormId=a.id and b.ifmoney!=0   "+
                        "where   b.caseno in ( select  left(replace(purno,'合','SHS'),len(replace(purno,'合','SHS'))-1) as caseno from contract where proId in (?) ) "+
                        "and a.TransactionReferenceNumber is not null  "+
                        "group by a.NBEmailId) ";

					ResultSet resErp6 = null;
					stmt = connectionErp.prepareStatement(sqlErp6);
					stmt.setString(1, proId);
					resErp6 = stmt.executeQuery();

					while (resErp6.next()) {

						//银行进账总额
						sumTradeAmount = resErp6.getInt("sumTradeAmount");
					}

//				}
			}

			//过去2年银行进账 减去 报关金额
			sumCzPrice=sumTradeAmount -sumtrueprice;





            //未收到但本次要出口的金额
            String sqlErp6="select distinct con.proId,left(replace(con.purno,'合','SHS'),len(replace(con.purno,'合','SHS'))-1) as caseno,con.is_extra_invoice ,ar.uncollected_amount   "
                    +"from reportform.[dbo].contract con  "
                    +"left join reportform.[dbo].accounts_receivable ar on con.proId= ar.pro_id and left(replace(con.purno,'合','SHS'),len(replace(con.purno,'合','SHS'))-1) = ar.case_no "
                    +"where con.proId=? ";

            ResultSet resErp6 = null;
            stmt = connectionErp.prepareStatement(sqlErp6);
            stmt.setString(1, proId);
            resErp6 = stmt.executeQuery();

            List<BankArrival> bankYsList = new ArrayList<BankArrival>();
            while (resErp6.next()) {

                BankArrival bankA = new BankArrival();
                //银行序号
                bankA.setProId(Integer.valueOf(proId));
                bankA.setCaseNo(resErp6.getString("caseno"));
                bankA.setIsExtraInvoice(resErp6.getInt("is_extra_invoice"));
                bankA.setPairedYs(resErp6.getInt("uncollected_amount"));
                bankYsList.add(bankA);

            }
			request.setAttribute("bankAList", bankAList);
			request.setAttribute("bankAListSize", bankAList.size());
			request.setAttribute("proId", proId);
			request.setAttribute("bsFlag", bsFlag);
			request.setAttribute("sumCzPrice", sumCzPrice);
            request.setAttribute("bankYsList", bankYsList);
            request.setAttribute("bankYsListSize", bankYsList.size());



		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
// 			DBHelper.returnConnection(connection);
			DBHelper.returnConnection(connectionErp);
 		}
		RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/shipmentpaymentn.jsp");
		homeDispatcher.forward(request, response);
		out.flush();
		out.close();
	}


	public String formatStr(String date){
		if(StringUtils.isNotBlank(date)){
			date = date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8);
		}
		return date;
	}


}

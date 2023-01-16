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
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShipmentPaymentReParidNServlet extends HttpServlet {
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

		if(StringUtils.isNotEmpty(request.getParameter("bankAListSize"))){
            int bankAListSize = Integer.valueOf(request.getParameter("bankAListSize"));
            int proId = Integer.valueOf(request.getParameter("proId"));
            int bsFlag = Integer.valueOf(request.getParameter("bsFlag"));




            Connection connectionErp = DBHelper.getConnectionERP();

            try {

//			String sql2="update PreparatorEntryForm set paired_amount=?,paired_lcamount=?,shipping_id=?,paired_ssamount=?,update_time=getdate() where id=?  ";
                String sql2="update PreparatorEntryForm set paired_amount=?,paired_lcamount=?,shipping_id=?,update_time=getdate(),bs_flag=?,paired_ssamount=?,is_extra_invoice=? where id=?  ";
                PreparedStatement statement2 = connectionErp.prepareStatement(sql2);


                for(int i=1; i<= bankAListSize; i++){

                    String  paired = request.getParameter("paired" + i);
//				if(!"已配对".equals(paired)){
                    // 到账金额
                    double  tradeAmount = Double.valueOf(request.getParameter("tradeAmount" + i));
                    // 已配对金额
                    double  pairedAmount = Double.valueOf(request.getParameter("pairedAmount" + i));
                    //本次配多少
                    double pairedLcamount = Double.valueOf(request.getParameter("pairedLcamount" + i));
                    //财务按需修改金额
                    double pairedSsamount = Double.valueOf(request.getParameter("pairedSsamount" + i));
                    //应收金额
//					double pairedYs = Double.valueOf(request.getParameter("pairedYs" + i));
                    // 认领表id
                    int rId = Integer.parseInt(request.getParameter("rId" + i));

                    // 到账表id
                    int aId = Integer.parseInt(request.getParameter("aId" + i));

                    // 是否带票
                    int isExtraInvoice = Integer.parseInt(request.getParameter("isExtraInvoice" + i));

                    //在表2中，所有有配对行为的银行到账下
                    //已经配对金额 = 老的 已经配对金额 + 此次报关金额
                    statement2.setDouble(1, pairedAmount+pairedLcamount);
                    statement2.setDouble(2, pairedLcamount);
                    // 出运单id
                    statement2.setInt(3, proId);
                    //跟单或者财务标识
                    statement2.setInt(4, bsFlag);
                    //财务按需修改金额
                    statement2.setDouble(5, pairedSsamount);
                    //应收金额
//					statement2.setDouble(6, pairedYs);
                    //是否带票
                    statement2.setInt(6, isExtraInvoice);
                    //认领表id
                    statement2.setInt(7, rId);

//					// 更新 本次配多少！=0或者财务按需修改金额！=0 或者应收金额！=0
//					if(pairedLcamount!=0 || pairedSsamount!=0 || pairedYs!=0){
                    statement2.executeUpdate();
//					}



                    // 修改配对状态
                    Statement createStatement = connectionErp.createStatement();
                    String sql3="select paired_amount from  PreparatorEntryForm where AmountClaimFormId= "+aId ;
                    ResultSet resErp3 = createStatement.executeQuery(sql3);
                    double sumPairedAmountEd=0;
                    while (resErp3.next()) {

                        // 已配对金额
                        double pairedAmountEd = resErp3.getDouble("paired_amount");
                        sumPairedAmountEd +=pairedAmountEd;

                    }

                    if(sumPairedAmountEd > 0){
                        String sql4="update PreparatorEntryForm set paired=?  where paired_amount!=0 and AmountClaimFormId=?  ";
                        PreparedStatement statement4 = connectionErp.prepareStatement(sql4);

                        // 配对状态改成  “已经配对” 或者 “部分配对” （已配对<到账时）
                        if(sumPairedAmountEd<tradeAmount){
                            statement4.setString(1, "部分配对");
                        }else if(sumPairedAmountEd==tradeAmount){
                            statement4.setString(1, "已配对");
                        }
                        statement4.setInt(2, aId);
                        statement4.executeUpdate();
                        statement4.close();
                    }

                    //每次配对明细数据插入
                    if(pairedLcamount!=0){

                        String  itemNo = String.valueOf(request.getParameter("itemNo" + i));
                        //插入数据
                        String sql1 = "insert into reportform.[dbo].shipping_paried(pro_id,dz_id,case_no,paried_amount,is_flag,create_time) values(?,?,?,?,?,getdate())";
                        PreparedStatement statement1 = connectionErp.prepareStatement(sql1,Statement.RETURN_GENERATED_KEYS);

                        statement1.setInt(1, proId);
                        statement1.setInt(2, aId);
                        statement1.setString(3, itemNo);
                        statement1.setDouble(4, pairedLcamount);
                        statement1.setInt(5, bsFlag);
                        statement1.executeUpdate();
                        statement1.close();
                    }

//				}


                }
                statement2.close();

                //如果 出货是一个品种，”报关金额” = 配好的总金额
                //如果 出货是一个多品种，”报关金额” = 配好的总金额 平分到几个品种，但人可改
                // 跟单情况下处理
                if(bsFlag==2){

                    Statement createStatement = connectionErp.createStatement();
                    //配好的总金额
                    String sql5="select sum(paired_ssamount) as sumPairedAmount from PreparatorEntryForm where  shipping_id="+proId ;
                    ResultSet resErp5 = createStatement.executeQuery(sql5);
                    int sumPairedAmount =0;
                    while (resErp5.next()) {
                        // 配好的总金额
                        sumPairedAmount = resErp5.getInt("sumPairedAmount");
                    }

                    //当前出运单 出货品名个数查询
                    String sql6="select count(1) cn from reportform.[dbo].items  where proId="+proId ;
                    ResultSet resErp6 = createStatement.executeQuery(sql6);
                    int cn =0;
                    while (resErp6.next()) {
                        // 品种个数
                        cn = resErp6.getInt("cn");
                    }
                    // 配好的总金额 平分到几个品种
                    int reSumPairedAmount = sumPairedAmount/cn;

                    String sql7="update  reportform.[dbo].items  set trueprice=? where proId = ? ";
                    PreparedStatement statement7 = connectionErp.prepareStatement(sql7);
                    statement7.setInt(1, reSumPairedAmount);
                    statement7.setInt(2, proId);
                    statement7.executeUpdate();
                    statement7.close();
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                DBHelper.returnConnection(connectionErp);
            }
        }


		// 应收处理
        Connection connection = DBHelper.getConnection();
        int bankYsListSize = Integer.valueOf(request.getParameter("bankYsListSize"));
        int proIdYs = Integer.valueOf(request.getParameter("proIdYs"));

        PreparedStatement stmt = null;
        try{

            //未收到但本次要出口的金额
            String sqlErp6="select count(1) cn from reportform.[dbo].accounts_receivable where case_no=? and pro_id=? "  ;

            ResultSet resErp6 = null;
            stmt = connection.prepareStatement(sqlErp6);
            for(int y=1 ;y<=bankYsListSize;y++){
                //项目号
                String caseNo = String.valueOf(request.getParameter("caseNo" + y));
                //应收金额
                double pairedYs = Double.valueOf(request.getParameter("pairedYs" + y));
                //是否带票
                int isExtraInvoice = Integer.valueOf(request.getParameter("isExtraInvoice" + y));

//                if(pairedYs!=0){
                    stmt.setString(1, caseNo);
                    stmt.setInt(2, proIdYs);
                    resErp6 = stmt.executeQuery();

                    int count =0;
                    while (resErp6.next()) {
                        count = resErp6.getInt("cn");
                    }


                    if(count==0){
                        //插入数据
                        String sql1 = "insert into accounts_receivable(pro_id,case_no,uncollected_amount,is_extra_invoice,update_time) values(?,?,?,?,getdate())";
                        PreparedStatement statement1 = connection.prepareStatement(sql1,Statement.RETURN_GENERATED_KEYS);

                        statement1.setInt(1, proIdYs);
                        statement1.setString(2, caseNo);
                        statement1.setDouble(3, pairedYs);
                        statement1.setInt(4, isExtraInvoice);
                        statement1.executeUpdate();
                        statement1.close();

                    }else{
                        //更新数据
                        String sql2="update accounts_receivable set uncollected_amount=?,is_extra_invoice=?,update_time=getdate() where case_no=? and pro_id=?  ";
                        PreparedStatement statement2 = connection.prepareStatement(sql2);

                        statement2.setDouble(1, pairedYs);
                        statement2.setInt(2, isExtraInvoice);
                        statement2.setString(3, caseNo);
                        statement2.setInt(4, proIdYs);
                        statement2.executeUpdate();
                        statement2.close();

                    }
//                }

            }

            String result ="成功";
            request.setAttribute("result", result);
            RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/shipmentresult.jsp");
            homeDispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBHelper.returnConnection(connection);
        }

		out.flush();
		out.close();
	}




}

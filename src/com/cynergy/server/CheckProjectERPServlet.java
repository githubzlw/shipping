package com.cynergy.server;

import com.cynergy.main.DBHelper;
import com.cynergy.pojo.ContractFundWrap;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 根据合同号查询erp合同表，查询是否已有合同，合同金额数据
 * @ClassName GetProjectERPServlet 
 * @Description
 * @author zj
 * @date 2018年7月12日 上午11:39:30
 */
public class CheckProjectERPServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");

    public CheckProjectERPServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		List<ContractFundWrap> lst = contractStatictise();
		request.setAttribute("lst",lst);
		RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/checkcontract.jsp");
		homeDispatcher.forward(request, response);
	}

	DecimalFormat df=new DecimalFormat("#0.000");

	private  String total(Connection connectionShipping,String purno) throws SQLException{

		Statement statement = connectionShipping.createStatement();
		Double totalPay = 0.0; //已出货金额
		String sql3 = "select purno,rmb  from contract where purno='"+purno+"'";
		ResultSet res3 = statement.executeQuery(sql3);
		while (res3.next()) {
			String rmb = res3.getString("rmb");
			if(StringUtils.isNotEmpty(rmb)){
				totalPay +=Double.parseDouble(rmb);
			}
		}
		DBHelper.closeResource(statement,res3);
		return df.format(totalPay);

	}


	public List<ContractFundWrap> contractStatictise(){
		//获取用户权限
		List<ContractFundWrap> lst = new ArrayList<>();
		Connection connection = DBHelper.getConnectionERP();
		Connection connectionShipping = DBHelper.getConnection();
		String thiYear = simpleDateFormat.format(new Date());
		thiYear = thiYear +"-01-01 00:00:00";
		try {
			Statement createStatement = connection.createStatement();
			String sql = "select BargainNo,sum(friMoney) friMoney from FactoryFund " +
					"where friFacDate>'"+thiYear+"' group by BargainNo";
			ResultSet res1 = createStatement.executeQuery(sql);
			ContractFundWrap wrap = null;
			while(res1.next()){
				wrap = new ContractFundWrap();
				String puro = res1.getString("BargainNo");
				wrap.setContractno(puro);
				wrap.setFriMoney(res1.getString("friMoney"));
				wrap.setTotalPrice(total(connectionShipping,puro));

				if(Double.parseDouble(wrap.getTotalPrice()) > Double.parseDouble(wrap.getFriMoney()) + 0.00099999){
					wrap.setFlag(1);
				}
				wrap.setCurrency("RMB");
				lst.add(wrap);
			}
			DBHelper.closeResource(createStatement,res1);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBHelper.returnConnection(connection);
			DBHelper.returnConnection(connectionShipping);
		}
		return lst;
	}

}

package com.cynergy.server;
/**
 * 修改项目时 先获取信息
 */
import com.cynergy.main.DBHelper;
import com.cynergy.main.ReadExcelVO;
import com.cynergy.main.WebCookie;
import com.cynergy.mapper.ContractItemMapperImpl;
import com.cynergy.mapper.InvoiceInfoMapper;
import com.cynergy.mapper.InvoiceInfoMapperImpl;
import com.cynergy.pojo.ContractItem;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AddItemOfContractServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DecimalFormat df = new DecimalFormat("#0.00");
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
		String cproid = request.getParameter("cproid");

		HttpSession session = request.getSession();
		//添加权限管理  每个人只能看到自己项目   Author:polo   2017/11/30
		String auth = WebCookie.getCookieByName(request, "auth");
		String proIds = session.getAttribute("proIds").toString();
		
		String pds = "";
		if(!(proIds == null || "".equals(proIds))){
			pds = proIds.substring(1);
		}
		String stritemid = request.getParameter("itemid");
		int itemid = StringUtils.isNotBlank(stritemid) ? Integer.parseInt(stritemid):0;

		Connection connection = DBHelper.getConnection();
		try {
			String stritemsize = request.getParameter("itemsize");
			int itemsize = Integer.parseInt(stritemsize);
			ReadExcelVO vo;
			String itemchn = request.getParameter("itemchn");
			if(StringUtils.isNotEmpty(itemchn)){
				//判断是乱码 (GBK包含全部中文字符；UTF-8则包含全世界所有国家需要用到的字符。)
				if (!(java.nio.charset.Charset.forName("GBK").newEncoder().canEncode(itemchn))) {
					itemchn = new String(itemchn.getBytes("ISO-8859-1"), "utf-8"); //转码UTF8
				}
			}

			int proid = Integer.parseInt(cproid);
			List<ReadExcelVO> lst1 = new ArrayList<>();
			for(int i=0;i<itemsize;i++){
				String amount = request.getParameter("itemcontractamount"+i);
				String no = request.getParameter("itemcontractpurno"+i);
				String declareid = request.getParameter("itemcontractid"+i);
				String contractQuantity = request.getParameter("itemcontractquantity"+i);
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
			RequestDispatcher homeDispatcher = request.getRequestDispatcher("../shipping/InfoServlet?id="+cproid);
			homeDispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
 			DBHelper.returnConnection(connection);
 		}
		out.flush();
		out.close();
	}
}

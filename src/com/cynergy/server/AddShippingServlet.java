package com.cynergy.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

import com.cynergy.main.DBHelper;

public class AddShippingServlet extends HttpServlet {
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
		JSONObject jsonobj = new JSONObject(); 
		PrintWriter out = response.getWriter();
        String serialNumber = request.getParameter("serialNumber");
        String contractList = request.getParameter("contractList");
		String idString = request.getParameter("proId");
		int proId = Integer.parseInt(idString);
        
		try {
			    PrintWriter out1 = null;
		        BufferedReader in1 = null;
		        String result2 = "";  
//		         URL realUrl1 = new URL("http://192.168.1.151:8080/port/getShippingNum");
		         URL realUrl1 = new URL("http://117.144.21.74:10010/port/getShippingNum");
//		         URL realUrl1 = new URL("http://192.168.1.62:8080/NBEmail/helpServlet?action=queryAddress&className=ExternalInterfaceServlet");
		            // 打开和URL之间的连接
		            URLConnection conn1 = realUrl1.openConnection();
		            // 设置通用的请求属性
		           // conn1.setRequestProperty("accept", "*/*");
				    conn1.setRequestProperty("connection", "Keep-Alive");
		            conn1.setRequestProperty("user-agent",
		                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		            // 发送POST请求必须设置如下两行
		            conn1.setDoOutput(true);
		            conn1.setDoInput(true);
		            // 获取URLConnection对象对应的输出流
		            out1 = new PrintWriter(conn1.getOutputStream());
		            // 发送请求参数
		            out1.print("serialNumber="+serialNumber.trim());
		            // flush输出流的缓冲
		            out1.flush();
		            // 定义BufferedReader输入流来读取URL的响应
		            in1 = new BufferedReader(
		                    new InputStreamReader(conn1.getInputStream()));
		            String line;
		            while ((line = in1.readLine()) != null) {
		                result2 += line;
		            }
		            
		            //任务系统查询的电子出货单
		            String flag = "fasle";
		            int isComplete = 0;
		            int sid = 0;
		            if(StringUtils.isNotBlank(result2)){
		            	result2 = result2.replace("\"", "");
		            	String[] split = result2.split(",",-1);
		            	flag = split[0];
		            	if(split.length > 0 && StringUtils.isNotBlank(split[1])){
		            		isComplete = Integer.parseInt(split[1]);
		            	}
		            	if(split.length > 1 && StringUtils.isNotBlank(split[2])){
		            		sid = Integer.parseInt(split[2]);
		            	}
		            }
		            //如果查询是正确的
		            if("true".equals(flag)){
		            	if(serialNumber!=null){
		            		//获取出货单编号项目号码，用于和合同比较
		            		String newNumber = serialNumber.toUpperCase().replace("SHS", "").split("QR")[0]; 
		            		//获取项目号
		            		String projectNo = serialNumber.toUpperCase().split("QR")[0]; 
		            		//电子出货单合同
		            		String purno = null;
		            		if(contractList!=null){
		            			String[] split = contractList.split(",");
		            			for (String string : split) {
									if(string.contains(newNumber)){
										purno = string;
									}
								}
		            	     }		            		
		            		//如果合同为空，则提示
		            		//如果合同不为空，则插入表
		            		if(purno == null){
		            			jsonobj.put("message", "非本联系单项目添加失败");	
		            		}else{
		            			Connection connection = DBHelper.getConnection();
		            			String sql = "insert into shipping_contract(proId,purno,serial_number,add_date,is_complete,sid) values(?,?,?,getdate(),?,?)";
		            			PreparedStatement statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		            			statement.setInt(1, proId);
		            			statement.setString(2, projectNo);
		            			statement.setString(3, serialNumber);
		            			statement.setInt(4, isComplete);
		            			statement.setInt(5, sid);
		            			statement.executeUpdate();
		            			int shippingId=0;
		            			ResultSet res = statement.getGeneratedKeys();
		            			if (res.next()){
		            				shippingId = res.getInt(1);
		            			}
		            			statement.close();
		            			jsonobj.put("shippingId", shippingId);
		            			jsonobj.put("isComplete", isComplete);
		            			jsonobj.put("projectNo", projectNo);
		            			jsonobj.put("sid", sid);
		            		}		            				            		
		            	}		            	
		            }else{
		            	jsonobj.put("message", "未获取到电子出货单");
		            }	
	 			    // 输出数据
		 		    out = response.getWriter();
		 		    out.println(jsonobj);
		            
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

}

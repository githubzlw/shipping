package com.cynergy.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cynergy.main.Base64Encode;

public class GetShippingAttrPortServlet extends HttpServlet {
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
        String eid = request.getParameter("eid");
		try {
			    PrintWriter out1 = null;
		        BufferedReader in1 = null;
		        String result2 = "";  
		         URL realUrl1 = new URL("http://117.144.21.74:43887/NBEmail/helpServlet?action=queryAddress&className=ExternalInterfaceServlet");
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
		            out1.print("result="+"&&eid="+eid);
		            // flush输出流的缓冲
		            out1.flush();
		            // 定义BufferedReader输入流来读取URL的响应
		            in1 = new BufferedReader(
		                    new InputStreamReader(conn1.getInputStream()));
		            String line;
		            while ((line = in1.readLine()) != null) {
		                result2 += line;
		            }
		            if(!(result2 == null && "".equals(result2))){	
		            	result2 = Base64Encode.getFromBase64(result2);                      
		            	System.out.println(result2);
		            	out.print(result2);
		            }
		            
		            
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

}

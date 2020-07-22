package com.cynergy.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SyncShippingDateToCrmServlet extends HttpServlet {
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
        String orderIds = request.getParameter("orderIds");
        String saildate = request.getParameter("saildate");
        String arriveDate = request.getParameter("arriveDate");
        String destinationPort = request.getParameter("destinationPort");
		try {
			    PrintWriter out1 = null;
		        BufferedReader in1 = null;
		        String result2 = "";  
		         URL realUrl1 = new URL("http://www.importx.com/supplier/shippingPort/syncShippingDate.do");
//		         URL realUrl1 = new URL("http://192.168.1.54:8099/supplier/shippingPort/syncShippingDate.do");
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
		            out1.print("result="+"&&orderIds="+orderIds+"&&saildate="+saildate+"&&arriveDate="+arriveDate+"&&destinationPort="+destinationPort);
		            // flush输出流的缓冲
		            out1.flush();
		            // 定义BufferedReader输入流来读取URL的响应
		            in1 = new BufferedReader(
		                    new InputStreamReader(conn1.getInputStream()));
		            String line;
		            while ((line = in1.readLine()) != null) {
		                result2 += line;
		            }
		            System.out.println(result2);
		            
		            
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

}

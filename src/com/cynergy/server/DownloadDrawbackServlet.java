package com.cynergy.server;
/**
 * 退税汇总
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cynergy.main.PropertisUtil;

public class DownloadDrawbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static PropertisUtil propertisUtil = new PropertisUtil("config.properties");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		String fileName = request.getParameter("fileName");
		 //处理请求  
        //读取要下载的文件  
        File f = new File(propertisUtil.get("excel_path")+File.separator+fileName);  
        if(f.exists()){  
            FileInputStream  fis = new FileInputStream(f);  
            String filename=URLEncoder.encode(f.getName(),"utf-8"); //解决中文文件名下载后乱码的问题  
            byte[] b = new byte[fis.available()];  
            fis.read(b);  
            response.setCharacterEncoding("utf-8");  
            response.setHeader("Content-Disposition","attachment; filename="+filename+"");  
            //获取响应报文输出流对象  
            ServletOutputStream  out =response.getOutputStream();  
            //输出  
            out.write(b);  
            out.flush();  
            out.close();  
        }     
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

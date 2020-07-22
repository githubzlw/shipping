package com.cynergy.listener;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.cynergy.main.WebCookie;

/**
 * 验证用户是否登录(2017/11/30)
 * @author polo
 *
 */
public class AccessFilter implements Filter{

	public void destroy() {
		
		
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;	
	     //拦截器，实现未登录用户无法使用
		 StringBuffer url = request.getRequestURL();
		 String path = url.toString();
		 
			 if(path.endsWith("index.jsp")){
				 chain.doFilter(req, res);
				 return;
			 }
			 if(path.endsWith("Login")){
				 chain.doFilter(req, res);
				 return;
			 }
			 if(path.endsWith("ToLogin")){
				 chain.doFilter(req, res);
				 return;
			 }  
			 if(path.endsWith("SyncShippingDateToCrmServlet")){
				 chain.doFilter(req, res);
				 return;
			 }  
			 if(path.endsWith("GetShippingAttrServlet")){
				 chain.doFilter(req, res);
				 return;
			 }  
			 if(path.endsWith("QueryByProjectIdServlet")){
				 chain.doFilter(req, res);
				 return;
			 } 
			 if(path.endsWith("EmailUserServlet")) {//登录的action也无需过滤
					chain.doFilter(req, res);
					return;
				}
			  
			 if(path.indexOf("/img/")>0 || path.indexOf("/js/")>0 || path.indexOf("/My97DatePicker/")>0 || path.indexOf("/My97DatePicker/")>0){
				 chain.doFilter(req, res);
				 return;
			 }  
			 processAccessControl(request,response,chain);
			 return;
		 
	}



	/**
	 * 处理访问控制
	 * @throws IOException 
	 * @throws ServletException 
	 * 
	 */
	private void processAccessControl(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		StringBuffer url = request.getRequestURL();
		String path1 = url.toString();
		//如果token验证不合理，去登录页面
		String path = request.getContextPath();
		String login = path+"/index.jsp";
		//检查cookie中是否有 没有就去登录
		String auth = WebCookie.getCookieByName(request, "auth");
		String adminId = WebCookie.getCookieByName(request, "adminId");
		
        if(adminId == null || "".equals(adminId)){
        	response.sendRedirect(login);
        	return;
        }
        if(auth == null || "".equals(auth)){
        	response.sendRedirect(login);
        	return;
        }else if(Integer.parseInt(auth) != 1 && Integer.parseInt(adminId) != 32){
        	 if(path1.contains("InvoiceServlet")){
        		 response.sendRedirect(login);
             	 return; 
        	 }
        	 if(path1.endsWith("QingGuanInvoiceServlet")){
        		 response.sendRedirect(login);
        		 return; 
        	 }
        	 if(path1.endsWith("PackingServlet")){
        		 response.sendRedirect(login);
        		 return; 
        	 }
        	 if(path1.endsWith("contract")){
        		 response.sendRedirect(login);
        		 return; 
        	 }
        	 if(path1.endsWith("adminInfo")){
        		 response.sendRedirect(login);
        		 return; 
        	 }
        	 if(path1.endsWith("adminDelete")){
        		 response.sendRedirect(login);
        		 return; 
        	 }
        	 if(path1.endsWith("adminUpdate")){
        		 response.sendRedirect(login);
        		 return; 
        	 }
        	 if(path1.endsWith("forOnRecord")){
        		 response.sendRedirect(login);
        		 return; 
        	 }
        	 if(path1.endsWith("CostomServlet")){
        		 response.sendRedirect(login);
        		 return; 
        	 }
        	 if(path1.endsWith("DetailListServlet")){
        		 response.sendRedirect(login);
        		 return; 
        	 }
        }
        chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
	
		
	}
   
}

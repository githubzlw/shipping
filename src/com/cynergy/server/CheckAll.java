package com.cynergy.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckAll extends HttpServlet {
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
		
//		Integer checkpwd = (Integer) request.getSession().getAttribute("checkpwd");
		String view = (String) request.getParameter("view");
//		if(checkpwd!=null&&checkpwd==1){
			RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/"+view+".jsp");
			homeDispatcher.forward(request, response);
//		}else{
//			request.getSession().setAttribute("whereview", view);
//			RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/check.jsp");
//			homeDispatcher.forward(request, response);
//		}
		out.flush();
		out.close();
	}
}

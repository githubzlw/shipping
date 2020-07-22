package com.cynergy.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckServlet extends HttpServlet {
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
		HttpSession session = request.getSession();
		String password = request.getParameter("password");
		try {
			System.out.println("password:"+password);
			if(password.trim().equals("rong2014")){
				session.setAttribute("checkpwd", 1);
				String whereview = (String) session.getAttribute("whereview");
				if(whereview.equals("query")){
					RequestDispatcher homeDispatcher = request.getRequestDispatcher("QuestFirstServlet");
					homeDispatcher.forward(request, response);
				}else{
					RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/"+whereview+".jsp");
					homeDispatcher.forward(request, response);
				}
			}else{
				request.setAttribute("code", "请输入正确的密码，注意大小写!");
				RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/check.jsp");
				homeDispatcher.forward(request, response);
			}
//			RequestDispatcher homeDispatcher = request.getRequestDispatcher("view/explain.jsp");
//			homeDispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

}

package com.cynergy.server;
/**
 * 退税汇总
 */

import com.cynergy.main.ProjectStatisticsPrint;
import com.cynergy.main.SummaryVO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

public class SummaryPrintEveryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String year = request.getParameter("select1");
		String month = request.getParameter("select2");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		SummaryServlet summaryServlet = new SummaryServlet();
		try {
			List<SummaryVO> summaryVOList = summaryServlet.getSummaryEveryList(year, month);

			//生成的excel路径
			String excelPath = ProjectStatisticsPrint.printExcelSummaryEvery(request, summaryVOList);

			File outFile = new File(excelPath);
			InputStream fis = new BufferedInputStream(new FileInputStream(outFile));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			//清空response  
			response.reset();
			//设置response的Header  
			String fileName = "汇总.xls";
			fileName = URLEncoder.encode(fileName, "utf-8");                                  //这里要用URLEncoder转下才能正确显示中文名称  
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.addHeader("Content-Length", "" + outFile.length());
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			//导出完成删除临时文件
			ProjectStatisticsPrint.deleteFile(excelPath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

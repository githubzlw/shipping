package com.cynergy.main;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cynergy.pojo.ContractFundWrap;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 * @Description: 导出项目统计数据excel
 * @Author: polo
 * @CreateDate:2018/08/24
 */

public class ProjectStatisticsPrint {

//	private static final String TEMP_PATH = "G:\\drawBack\\temp";
	private static PropertisUtil propertisUtil = new PropertisUtil("config.properties");

	/**
	 * pdf打印,使用excel编辑，生成pdf
	 * 
	 * @throws Exception
	 */
	public static String printExcel(HttpServletRequest request, List<ProjectStatisticsVO> list)throws Exception {
		String temp_path = 	propertisUtil.get("excel_temp");
         //样品完结数量
		 int tl = 0;
		 if(list != null && list.size() >0){
			 tl = list.size();
		 }
		
        //创建workbook  
         HSSFWorkbook wb = new HSSFWorkbook();             
         //创建sheet  
         HSSFSheet sheet = wb.createSheet("退税统计表");  
          
         HSSFFont font = wb.createFont();
         font.setFontName("黑体");
         font.setFontHeightInPoints((short) 14);//设置字体大小
         font.setBold(true);
         
         
         HSSFFont font2 = wb.createFont();
         font2.setFontName("黑体");
         font2.setFontHeightInPoints((short) 14);//设置字体大小
         font2.setColor(HSSFColor.RED.index);
         
         //创建行row:添加表头0行  
         HSSFRow row = sheet.createRow(0);  
         HSSFCellStyle style = wb.createCellStyle();      
         style.setFont(font2);
         //表头样式
         HSSFCellStyle cStyle = wb.createCellStyle();
         cStyle.setAlignment(HorizontalAlignment.CENTER);
         cStyle.setBorderBottom(BorderStyle.THIN);
         cStyle.setBottomBorderColor(HSSFColor.BLACK.index);
         cStyle.setBorderLeft(BorderStyle.THIN);
         cStyle.setLeftBorderColor(HSSFColor.BLACK.index);
         cStyle.setBorderRight(BorderStyle.THIN);
         cStyle.setRightBorderColor(HSSFColor.BLACK.index);
         cStyle.setBorderTop(BorderStyle.THIN);
         cStyle.setTopBorderColor(HSSFColor.BLACK.index);
         cStyle.setFillForegroundColor(HSSFColor.RED.index);// 设置背景色
         cStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         cStyle.setFont(font);
         
         //创建边框样式
         HSSFCellStyle boderStyle = wb.createCellStyle();
         boderStyle.setAlignment(HorizontalAlignment.CENTER);
         boderStyle.setBorderBottom(BorderStyle.THIN);
         boderStyle.setBottomBorderColor(HSSFColor.BLACK.index);
         boderStyle.setBorderLeft(BorderStyle.THIN);
         boderStyle.setLeftBorderColor(HSSFColor.BLACK.index);
         boderStyle.setBorderRight(BorderStyle.THIN);
         boderStyle.setRightBorderColor(HSSFColor.BLACK.index);
         boderStyle.setBorderTop(BorderStyle.THIN);
         boderStyle.setTopBorderColor(HSSFColor.BLACK.index);
         
         //退税金额使用样式
         HSSFCellStyle lastStyle = wb.createCellStyle();
         lastStyle.setAlignment(HorizontalAlignment.CENTER);
         lastStyle.setBorderBottom(BorderStyle.THIN);
         lastStyle.setBottomBorderColor(HSSFColor.BLACK.index);
         lastStyle.setBorderLeft(BorderStyle.THIN);
         lastStyle.setLeftBorderColor(HSSFColor.BLACK.index);
         lastStyle.setBorderRight(BorderStyle.THIN);
         lastStyle.setRightBorderColor(HSSFColor.BLACK.index);
         lastStyle.setBorderTop(BorderStyle.THIN);
         lastStyle.setTopBorderColor(HSSFColor.BLACK.index);
         lastStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);// 设置背景色
         lastStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         
         //创建单元格  
         HSSFCell cell = row.createCell(0); //第一个单元格    
         //创建标题
         cell = row.createCell(0); //获取单元格 
    	 cell.setCellValue("项目负责人"); 
    	 cell.setCellStyle(cStyle);
    	 cell = row.createCell(1); //获取单元格 
    	 cell.setCellStyle(cStyle);
    	 cell.setCellValue("序号");  
    	 cell = row.createCell(2); //获取单元格 
    	 cell.setCellStyle(cStyle);
    	 cell.setCellValue("项目号");  
    	 cell = row.createCell(3); //获取单元格 
    	 cell.setCellStyle(cStyle);
    	 cell.setCellValue("项目名称");  
    	 cell = row.createCell(4); //获取单元格 
    	 cell.setCellStyle(cStyle);
    	 cell.setCellValue("出口日期");  
    	 cell = row.createCell(5); //获取单元格 
    	 cell.setCellStyle(cStyle);
    	 cell.setCellValue("增票（合同）总金额");  
    	 cell = row.createCell(6); //获取单元格 
    	 cell.setCellStyle(cStyle);
    	 cell.setCellValue("报关总金额");  
    	 cell = row.createCell(7); //获取单元格 
    	 cell.setCellStyle(cStyle);
    	 cell.setCellValue("海关编码");  
    	 cell = row.createCell(8); //获取单元格 
    	 cell.setCellStyle(cStyle);
    	 cell.setCellValue("退税率");  
    	 cell = row.createCell(9); //获取单元格
    	 cell.setCellStyle(cStyle);
    	 cell.setCellValue("估计退税金额");  
    	 
         
    	 row = sheet.createRow(1); 
         cell = row.createCell(0); //获取单元格 
         // 合并单元格
 		 CellRangeAddress cra =new CellRangeAddress(1, 1, 0, 9); // 起始行, 终止行, 起始列, 终止列
 		 sheet.addMergedRegion(cra);
 		 cell.setCellStyle(style);
     	 cell.setCellValue(list.get(0).getMonth()); 
         //退税数据               
         for (int i=0;i<tl;i++){
        	row = sheet.createRow(i+2); 
        	row.setHeight((short) (25 * 20));
        	cell = row.createCell(0);
        	cell.setCellStyle(boderStyle);
        	cell.setCellValue(list.get(i).getSales()); 
        	cell = row.createCell(1);
        	cell.setCellStyle(boderStyle);
        	cell.setCellValue(list.get(i).getId()); 
        	cell = row.createCell(2);
        	cell.setCellStyle(boderStyle);
        	cell.setCellValue(list.get(i).getProjectNo()); 
        	cell = row.createCell(3);
        	cell.setCellStyle(boderStyle);
        	cell.setCellValue(list.get(i).getItemchn()); 
        	cell = row.createCell(4);
        	cell.setCellStyle(boderStyle);
        	cell.setCellValue(list.get(i).getExportDate()); 
        	cell = row.createCell(5);
        	cell.setCellStyle(boderStyle);
        	cell.setCellValue(list.get(i).getTotalAmount()); 
        	cell = row.createCell(6);
        	cell.setCellStyle(boderStyle);
        	cell.setCellValue(list.get(i).getTruePrice()); 
        	cell = row.createCell(7);
        	cell.setCellStyle(boderStyle);
        	cell.setCellValue(list.get(i).getHscode()); 
        	cell = row.createCell(8);
        	cell.setCellStyle(boderStyle);
        	cell.setCellValue(list.get(i).getRate()); 
        	cell = row.createCell(9);
        	cell.setCellStyle(lastStyle);
        	cell.setCellValue(list.get(i).getRefundAmount());   	        
        }        
         
       
         //自动调整列宽
        sheet.autoSizeColumn((short)0);
        sheet.autoSizeColumn((short)1);
        sheet.autoSizeColumn((short)2);
        sheet.autoSizeColumn((short)3);
        sheet.autoSizeColumn((short)4);
        sheet.autoSizeColumn((short)5);
        sheet.autoSizeColumn((short)6);
        sheet.autoSizeColumn((short)7);
        sheet.autoSizeColumn((short)8);
        sheet.autoSizeColumn((short)9);
        
		
		File tempPath = new File(temp_path);
		if (!tempPath.exists() || !tempPath.isDirectory()) {
			tempPath.mkdir(); // 如果不存在，则创建该文件夹
		}
		FileOutputStream fs = new FileOutputStream(temp_path + File.separator + "退税.xls",false);
		wb.write(fs);
		fs.close();		

		return temp_path + File.separator + "退税.xls";
	}



	/**
	 * pdf打印,使用excel编辑，生成pdf
	 *
	 * @param list
	 * @throws Exception
	 */
	public static String printContractExcel(HttpServletRequest request, List<ContractFundWrap> list)throws Exception {
		String temp_path = 	propertisUtil.get("excel_temp");
		//样品完结数量
		int tl = 0;
		if(list != null && list.size() >0){
			tl = list.size();
		}

		//创建workbook
		HSSFWorkbook wb = new HSSFWorkbook();
		//创建sheet
		HSSFSheet sheet = wb.createSheet("退税统计表");

		HSSFFont font = wb.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 14);//设置字体大小
		font.setBold(true);


		HSSFFont font2 = wb.createFont();
		font2.setFontName("黑体");
		font2.setFontHeightInPoints((short) 14);//设置字体大小
		font2.setColor(HSSFColor.RED.index);

		//创建行row:添加表头0行
		HSSFRow row = sheet.createRow(0);
		/*HSSFCellStyle style = wb.createCellStyle();
		style.setFont(font2);*/
		//表头样式
		HSSFCellStyle cStyle = wb.createCellStyle();
		cStyle.setAlignment(HorizontalAlignment.CENTER);
		cStyle.setBorderBottom(BorderStyle.THIN);
		cStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		cStyle.setBorderLeft(BorderStyle.THIN);
		cStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		cStyle.setBorderRight(BorderStyle.THIN);
		cStyle.setRightBorderColor(HSSFColor.BLACK.index);
		cStyle.setBorderTop(BorderStyle.THIN);
		cStyle.setTopBorderColor(HSSFColor.BLACK.index);
		cStyle.setFillForegroundColor(HSSFColor.RED.index);// 设置背景色
		cStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cStyle.setFont(font);

		//表头样式
		HSSFCellStyle boderStyle2 = wb.createCellStyle();
		boderStyle2.setAlignment(HorizontalAlignment.CENTER);
		boderStyle2.setBorderBottom(BorderStyle.THIN);
		boderStyle2.setBottomBorderColor(HSSFColor.BLACK.index);
		boderStyle2.setBorderLeft(BorderStyle.THIN);
		boderStyle2.setLeftBorderColor(HSSFColor.BLACK.index);
		boderStyle2.setBorderRight(BorderStyle.THIN);
		boderStyle2.setRightBorderColor(HSSFColor.BLACK.index);
		boderStyle2.setBorderTop(BorderStyle.THIN);
		boderStyle2.setTopBorderColor(HSSFColor.BLACK.index);
		boderStyle2.setFillForegroundColor(HSSFColor.RED.index);// 设置背景色


		//创建边框样式
		HSSFCellStyle boderStyle = wb.createCellStyle();
		boderStyle.setAlignment(HorizontalAlignment.CENTER);
		boderStyle.setBorderBottom(BorderStyle.THIN);
		boderStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		boderStyle.setBorderLeft(BorderStyle.THIN);
		boderStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		boderStyle.setBorderRight(BorderStyle.THIN);
		boderStyle.setRightBorderColor(HSSFColor.BLACK.index);
		boderStyle.setBorderTop(BorderStyle.THIN);
		boderStyle.setTopBorderColor(HSSFColor.BLACK.index);


		//创建单元格
		HSSFCell cell = row.createCell(0); //第一个单元格
		//创建标题
		cell = row.createCell(0); //获取单元格
		cell.setCellValue("");
		cell.setCellStyle(cStyle);
		cell = row.createCell(1); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("合同号");
		cell = row.createCell(2); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("合同金额");
		cell = row.createCell(3); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("报关金额");
		cell = row.createCell(4); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("货币");

		/*row = sheet.createRow(1);
		cell = row.createCell(0); //获取单元格
		// 合并单元格
		CellRangeAddress cra =new CellRangeAddress(1, 1, 0, 9); // 起始行, 终止行, 起始列, 终止列
		sheet.addMergedRegion(cra);
		cell.setCellStyle(style);
		cell.setCellValue("");*/
		//退税数据
		for (int i=0;i<tl;i++){
			boolean flag = list.get(i).getFlag() == 1;

			row = sheet.createRow(i+1);
			row.setHeight((short) (25 * 20));
			cell = row.createCell(0);
			cell.setCellStyle(flag?boderStyle2 : boderStyle);
			cell.setCellValue("");
			cell = row.createCell(1);
			cell.setCellStyle(flag?boderStyle2 : boderStyle);
			cell.setCellValue(list.get(i).getContractno());
			cell = row.createCell(2);
			cell.setCellStyle(flag?boderStyle2 : boderStyle);
			cell.setCellValue(list.get(i).getFriMoney());
			cell = row.createCell(3);
			cell.setCellStyle(flag?boderStyle2 : boderStyle);
			cell.setCellValue(list.get(i).getTotalPrice());
			cell = row.createCell(4);
			cell.setCellStyle(flag?boderStyle2 : boderStyle);
			cell.setCellValue(list.get(i).getCurrency());
		}

		//自动调整列宽
		sheet.autoSizeColumn((short)0);
		sheet.autoSizeColumn((short)1);
		sheet.autoSizeColumn((short)2);
		sheet.autoSizeColumn((short)3);
		sheet.autoSizeColumn((short)4);


		String path = temp_path + File.separator + "汇总.xls";
		File tempPath = new File(temp_path);
		if (!tempPath.exists() || !tempPath.isDirectory()) {
			boolean mkdir = tempPath.mkdirs();// 如果不存在，则创建该文件夹
			System.out.println(mkdir);
		}
		FileOutputStream fs = new FileOutputStream(path,false);
		wb.write(fs);
		fs.close();

		return path;
	}
	
	
	
	
	/**
	 * 
	 * 生成拆分报关品名excel
	 * @throws Exception
	 */
	public static String getProductExcel(List<ContractVO> list)throws Exception {
		String temp_path = 	propertisUtil.get("excel_temp");
		//报关合同
		 int tl = 0;
		 if(list != null && list.size() >0){
			 tl = list.size();
		 }
		
        //创建workbook  
         XSSFWorkbook wb = new XSSFWorkbook();             
         //创建sheet  
         XSSFSheet sheet = wb.createSheet("报关品名拆分");  
 		
          
         XSSFFont font = wb.createFont();
         font.setFontName("黑体");
         font.setFontHeightInPoints((short) 14);//设置字体大小
         font.setBold(true);
         
         
         XSSFFont font2 = wb.createFont();
         font2.setFontName("黑体");
         font2.setFontHeightInPoints((short) 14);//设置字体大小
         font2.setColor(HSSFColor.RED.index);
         
         //创建行row:添加表头0行  
         XSSFRow row = sheet.createRow(0);  
         XSSFCellStyle style = wb.createCellStyle();      
         style.setFont(font2);
         //表头样式
         XSSFCellStyle cStyle = wb.createCellStyle();
         cStyle.setAlignment(HorizontalAlignment.CENTER);
         cStyle.setBorderBottom(BorderStyle.THIN);
         cStyle.setBottomBorderColor(HSSFColor.BLACK.index);
         cStyle.setBorderLeft(BorderStyle.THIN);
         cStyle.setLeftBorderColor(HSSFColor.BLACK.index);
         cStyle.setBorderRight(BorderStyle.THIN);
         cStyle.setRightBorderColor(HSSFColor.BLACK.index);
         cStyle.setBorderTop(BorderStyle.THIN);
         cStyle.setTopBorderColor(HSSFColor.BLACK.index);
//         cStyle.setFillForegroundColor(HSSFColor.RED.index);// 设置背景色
//         cStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         cStyle.setFont(font);
         
         //创建边框样式
         XSSFCellStyle boderStyle = wb.createCellStyle();
         boderStyle.setAlignment(HorizontalAlignment.CENTER);
         boderStyle.setBorderBottom(BorderStyle.THIN);
         boderStyle.setBottomBorderColor(HSSFColor.BLACK.index);
         boderStyle.setBorderLeft(BorderStyle.THIN);
         boderStyle.setLeftBorderColor(HSSFColor.BLACK.index);
         boderStyle.setBorderRight(BorderStyle.THIN);
         boderStyle.setRightBorderColor(HSSFColor.BLACK.index);
         boderStyle.setBorderTop(BorderStyle.THIN);
         boderStyle.setTopBorderColor(HSSFColor.BLACK.index);


         //创建单元格  
         XSSFCell cell = row.createCell(0); //第一个单元格    
         //创建标题
         cell = row.createCell(0); //获取单元格 
    	 cell.setCellValue("品名"); 
    	 cell.setCellStyle(cStyle);
    	 cell = row.createCell(1); //获取单元格 
    	 cell.setCellValue("数量"); 
    	 cell.setCellStyle(cStyle);
    	 cell = row.createCell(2); //获取单元格 
    	 cell.setCellStyle(cStyle);
    	 cell.setCellValue("金额");  
    	 cell = row.createCell(3); //获取单元格 
    	 cell.setCellStyle(cStyle);
    	 cell.setCellValue("工厂名称");  
    	 cell = row.createCell(4); //获取单元格 
    	 cell.setCellStyle(cStyle);
    	 cell.setCellValue("合同号");  
    	 
    	 
         
    	 row = sheet.createRow(1); 
         cell = row.createCell(0); //获取单元格 

         //合同数据               
         for (int i=0;i<tl;i++){
        	row = sheet.createRow(i+1); 
        	cell = row.createCell(0);
        	cell.setCellStyle(boderStyle);
        	cell = row.createCell(1);
        	cell.setCellStyle(boderStyle);
        	cell.setCellValue(list.get(i).getQuantity());
        	cell = row.createCell(2);
        	cell.setCellStyle(boderStyle);
        	cell.setCellValue(list.get(i).getAmount() == null ? "" : list.get(i).getAmount()); 
        	cell = row.createCell(3);
        	cell.setCellStyle(boderStyle);
        	cell.setCellValue(list.get(i).getFactory()); 
        	cell = row.createCell(4);
        	cell.setCellStyle(boderStyle);
        	cell.setCellValue(list.get(i).getPurno());        		        
        }        
       //自动调整列宽
/*        sheet.autoSizeColumn((short)0);
        sheet.autoSizeColumn((short)1);
        sheet.autoSizeColumn((short)2);
        sheet.autoSizeColumn((short)3);*/
         sheet.setColumnWidth(0, 30 * 256);
         sheet.setColumnWidth(1, 30 * 256);
         sheet.setColumnWidth(2, 40 * 256);
         sheet.setColumnWidth(3, 30 * 256);
		
		File tempPath = new File(temp_path);
		if (!tempPath.exists() || !tempPath.isDirectory()) {
			tempPath.mkdir(); // 如果不存在，则创建该文件夹
		}
		FileOutputStream fs = new FileOutputStream(temp_path + File.separator + "拆分报关品名.xlsx",false);
		wb.write(fs);
		fs.close();		

		return temp_path + File.separator + "拆分报关品名.xlsx";
	}

	public static void deleteFile(String path) {
		File file = new File(path);
		file.delete();
	}

	/**
	 * pdf打印,使用excel编辑，生成pdf
	 *
	 * @throws Exception
	 */
	public static String printExcelSummary(HttpServletRequest request, List<SummaryVO> list)throws Exception {
		String temp_path = 	propertisUtil.get("excel_temp");
		//样品完结数量
		int tl = 0;
		if(list != null && list.size() >0){
			tl = list.size();
		}

		//创建workbook
		HSSFWorkbook wb = new HSSFWorkbook();
		//创建sheet
		HSSFSheet sheet = wb.createSheet("汇总表");

		HSSFFont font = wb.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 14);//设置字体大小
		font.setBold(true);


		HSSFFont font2 = wb.createFont();
		font2.setFontName("黑体");
		font2.setFontHeightInPoints((short) 14);//设置字体大小
		font2.setColor(HSSFColor.RED.index);

		//创建行row:添加表头0行
		HSSFRow row = sheet.createRow(0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setFont(font2);
		//表头样式
		HSSFCellStyle cStyle = wb.createCellStyle();
		cStyle.setAlignment(HorizontalAlignment.CENTER);
		cStyle.setBorderBottom(BorderStyle.THIN);
		cStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		cStyle.setBorderLeft(BorderStyle.THIN);
		cStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		cStyle.setBorderRight(BorderStyle.THIN);
		cStyle.setRightBorderColor(HSSFColor.BLACK.index);
		cStyle.setBorderTop(BorderStyle.THIN);
		cStyle.setTopBorderColor(HSSFColor.BLACK.index);
		cStyle.setFillForegroundColor(HSSFColor.RED.index);// 设置背景色
		cStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cStyle.setFont(font);

		//创建边框样式
		HSSFCellStyle boderStyle = wb.createCellStyle();
		boderStyle.setAlignment(HorizontalAlignment.CENTER);
		boderStyle.setBorderBottom(BorderStyle.THIN);
		boderStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		boderStyle.setBorderLeft(BorderStyle.THIN);
		boderStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		boderStyle.setBorderRight(BorderStyle.THIN);
		boderStyle.setRightBorderColor(HSSFColor.BLACK.index);
		boderStyle.setBorderTop(BorderStyle.THIN);
		boderStyle.setTopBorderColor(HSSFColor.BLACK.index);

		//退税金额使用样式
		HSSFCellStyle lastStyle = wb.createCellStyle();
		lastStyle.setAlignment(HorizontalAlignment.CENTER);
		lastStyle.setBorderBottom(BorderStyle.THIN);
		lastStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		lastStyle.setBorderLeft(BorderStyle.THIN);
		lastStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		lastStyle.setBorderRight(BorderStyle.THIN);
		lastStyle.setRightBorderColor(HSSFColor.BLACK.index);
		lastStyle.setBorderTop(BorderStyle.THIN);
		lastStyle.setTopBorderColor(HSSFColor.BLACK.index);
		lastStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);// 设置背景色
		lastStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		//创建单元格
		HSSFCell cell = row.createCell(0); //第一个单元格
		//创建标题
		cell = row.createCell(0); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("序号");
		cell = row.createCell(1); //获取单元格
		cell.setCellValue("发票号");
		cell.setCellStyle(cStyle);
		cell = row.createCell(2); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("出口日期");
		cell = row.createCell(3); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("运输方式");
		cell = row.createCell(4); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("发货港");
		cell = row.createCell(5); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("目的港");
		cell = row.createCell(6); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("HS");
		cell = row.createCell(7); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("英文名称");
		cell = row.createCell(8); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("中文名称");
		cell = row.createCell(9); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("成交方式");
		cell = row.createCell(10); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("报关总金额");
		cell = row.createCell(11); //获取单元格
		cell.setCellValue("清关总金额");
		cell.setCellStyle(cStyle);
		cell = row.createCell(12); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("销售");
		cell = row.createCell(13); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("采购");
		cell = row.createCell(14); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("合同号");
		cell = row.createCell(15); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("合同金额");
		cell = row.createCell(16); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("出口批次");
		cell = row.createCell(17); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("客户名称");
		cell = row.createCell(18); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("退税率");
		cell = row.createCell(19); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("货代");
		cell = row.createCell(20); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("运费");


		//row = sheet.createRow(1);
		//cell = row.createCell(0); //获取单元格
		// 合并单元格
		//CellRangeAddress cra =new CellRangeAddress(1, 1, 0, 20); // 起始行, 终止行, 起始列, 终止列
		//sheet.addMergedRegion(cra);
		//cell.setCellStyle(style);
		//cell.setCellValue(list.get(0).get);
		//汇总数据
		for (int i = 0; i < tl; i++) {
			row = sheet.createRow(i + 1);
			row.setHeight((short) (25 * 20));
			cell = row.createCell(0);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getNum());
			cell = row.createCell(1);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getNonum());
			cell = row.createCell(2);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getSaildate());
			cell = row.createCell(3);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getTran2());
			cell = row.createCell(4);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getFromwhere());
			cell = row.createCell(5);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getTowhere());
			cell = row.createCell(6);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getHscode());
			cell = row.createCell(7);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getItemeng());
			cell = row.createCell(8);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getItemchn());
			cell = row.createCell(9);
			cell.setCellStyle(lastStyle);
			cell.setCellValue(list.get(i).getTran1());
			cell = row.createCell(10);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getTotalmoney());
			cell = row.createCell(11);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getUnitpriceall());
			cell = row.createCell(12);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getSale());
			cell = row.createCell(13);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getPurchase());
			cell = row.createCell(14);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getPurnoall());
			cell = row.createCell(15);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getMoneyall());
			cell = row.createCell(16);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getTimesall());
			cell = row.createCell(17);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getClientName());
			cell = row.createCell(18);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getRate());
			cell = row.createCell(19);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getHuodai());
			cell = row.createCell(20);
			cell.setCellStyle(lastStyle);
			cell.setCellValue(list.get(i).getYunfei());
		}


		//自动调整列宽
		sheet.autoSizeColumn((short) 0);
		sheet.autoSizeColumn((short) 1);
		sheet.autoSizeColumn((short) 2);
		sheet.autoSizeColumn((short) 3);
		sheet.autoSizeColumn((short) 4);
		sheet.autoSizeColumn((short) 5);
		sheet.autoSizeColumn((short) 6);
		sheet.autoSizeColumn((short) 7);
		sheet.autoSizeColumn((short) 8);
		sheet.autoSizeColumn((short) 9);
		sheet.autoSizeColumn((short) 10);
		sheet.autoSizeColumn((short) 11);
		sheet.autoSizeColumn((short) 12);
		sheet.autoSizeColumn((short) 13);
		sheet.autoSizeColumn((short) 14);
		sheet.autoSizeColumn((short) 15);
		sheet.autoSizeColumn((short) 16);
		sheet.autoSizeColumn((short) 17);
		sheet.autoSizeColumn((short) 18);
		sheet.autoSizeColumn((short) 19);
		sheet.autoSizeColumn((short) 20);


		File tempPath = new File(temp_path);
		if (!tempPath.exists() || !tempPath.isDirectory()) {
			tempPath.mkdir(); // 如果不存在，则创建该文件夹
		}
		FileOutputStream fs = new FileOutputStream(temp_path + File.separator + "汇总.xls", false);
		wb.write(fs);
		fs.close();

		return temp_path + File.separator + "汇总.xls";
	}


	public static String printExcelParid(HttpServletRequest request,List<BankArrival> list)throws Exception {
		String temp_path = 	propertisUtil.get("excel_temp");
		//样品完结数量
		int tl = 0;
		if(list != null && list.size() >0){
			tl = list.size();
		}

		//创建workbook
		HSSFWorkbook wb = new HSSFWorkbook();
		//创建sheet
		HSSFSheet sheet = wb.createSheet("配对数据");

		HSSFFont font = wb.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 14);//设置字体大小
		font.setBold(true);


		HSSFFont font2 = wb.createFont();
		font2.setFontName("黑体");
		font2.setFontHeightInPoints((short) 14);//设置字体大小
		font2.setColor(HSSFColor.RED.index);

		//创建行row:添加表头0行
		HSSFRow row = sheet.createRow(0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setFont(font2);
		//表头样式
		HSSFCellStyle cStyle = wb.createCellStyle();
		cStyle.setAlignment(HorizontalAlignment.CENTER);
		cStyle.setBorderBottom(BorderStyle.THIN);
		cStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		cStyle.setBorderLeft(BorderStyle.THIN);
		cStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		cStyle.setBorderRight(BorderStyle.THIN);
		cStyle.setRightBorderColor(HSSFColor.BLACK.index);
		cStyle.setBorderTop(BorderStyle.THIN);
		cStyle.setTopBorderColor(HSSFColor.BLACK.index);
		cStyle.setFillForegroundColor(HSSFColor.RED.index);// 设置背景色
		cStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cStyle.setFont(font);

		//创建边框样式
		HSSFCellStyle boderStyle = wb.createCellStyle();
		boderStyle.setAlignment(HorizontalAlignment.CENTER);
		boderStyle.setBorderBottom(BorderStyle.THIN);
		boderStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		boderStyle.setBorderLeft(BorderStyle.THIN);
		boderStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		boderStyle.setBorderRight(BorderStyle.THIN);
		boderStyle.setRightBorderColor(HSSFColor.BLACK.index);
		boderStyle.setBorderTop(BorderStyle.THIN);
		boderStyle.setTopBorderColor(HSSFColor.BLACK.index);

		//退税金额使用样式
		HSSFCellStyle lastStyle = wb.createCellStyle();
		lastStyle.setAlignment(HorizontalAlignment.CENTER);
		lastStyle.setBorderBottom(BorderStyle.THIN);
		lastStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		lastStyle.setBorderLeft(BorderStyle.THIN);
		lastStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		lastStyle.setBorderRight(BorderStyle.THIN);
		lastStyle.setRightBorderColor(HSSFColor.BLACK.index);
		lastStyle.setBorderTop(BorderStyle.THIN);
		lastStyle.setTopBorderColor(HSSFColor.BLACK.index);
		lastStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);// 设置背景色
		lastStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		//创建单元格
		HSSFCell cell = row.createCell(0); //第一个单元格
		//创建标题
		cell = row.createCell(0); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("出运号");
		cell = row.createCell(1); //获取单元格
		cell.setCellValue("客户id");
		cell.setCellStyle(cStyle);
		cell = row.createCell(2); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("出运品名");
		cell = row.createCell(3); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("实际销售额（清关金额）");
		cell = row.createCell(4); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("实际对外发票金额（报关金额）");
		cell = row.createCell(5); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("项目号");
		cell = row.createCell(6); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("银行序号");
		cell = row.createCell(7); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("到账日期");
		cell = row.createCell(8); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("银行到账金额");
		cell = row.createCell(9); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("分配金额");
		//汇总数据
		for (int i = 0; i < tl; i++) {
			row = sheet.createRow(i + 1);
			row.setHeight((short) (25 * 20));
			cell = row.createCell(0);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getProId());
			cell = row.createCell(1);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getErpCustomerId());
			cell = row.createCell(2);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getItemchn());
			cell = row.createCell(3);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getUnitPriceall());
			cell = row.createCell(4);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getTruePrice());
			cell = row.createCell(5);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getItemNo());
			cell = row.createCell(6);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getTransactionReferenceNumber());
			cell = row.createCell(7);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getTransactionDate());
			cell = row.createCell(8);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getTradeAmount());
			cell = row.createCell(9);
			cell.setCellStyle(lastStyle);
			cell.setCellValue(list.get(i).getPairedAmount());
		}


		//自动调整列宽
		sheet.autoSizeColumn((short) 0);
		sheet.autoSizeColumn((short) 1);
		sheet.autoSizeColumn((short) 2);
		sheet.autoSizeColumn((short) 3);
		sheet.autoSizeColumn((short) 4);
		sheet.autoSizeColumn((short) 5);
		sheet.autoSizeColumn((short) 6);
		sheet.autoSizeColumn((short) 7);
		sheet.autoSizeColumn((short) 8);
		sheet.autoSizeColumn((short) 9);


		File tempPath = new File(temp_path);
		if (!tempPath.exists() || !tempPath.isDirectory()) {
			tempPath.mkdir(); // 如果不存在，则创建该文件夹
		}
		FileOutputStream fs = new FileOutputStream(temp_path + File.separator + "配对数据.xls", false);
		wb.write(fs);
		fs.close();

		return temp_path + File.separator + "配对数据.xls";
	}


	/**
	 * pdf打印,使用excel编辑，生成pdf
	 *
	 * @throws Exception
	 */
	public static String printExcelSummaryEvery(HttpServletRequest request, List<SummaryVO> list) throws Exception {
		String temp_path = propertisUtil.get("excel_temp");
		//样品完结数量
		int tl = 0;
		if (list != null && list.size() > 0) {
			tl = list.size();
		}

		//创建workbook
		HSSFWorkbook wb = new HSSFWorkbook();
		//创建sheet
		HSSFSheet sheet = wb.createSheet("汇总明细表");

		HSSFFont font = wb.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 14);//设置字体大小
		font.setBold(true);


		HSSFFont font2 = wb.createFont();
		font2.setFontName("黑体");
		font2.setFontHeightInPoints((short) 14);//设置字体大小
		font2.setColor(HSSFColor.RED.index);

		//创建行row:添加表头0行
		HSSFRow row = sheet.createRow(0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setFont(font2);
		//表头样式
		HSSFCellStyle cStyle = wb.createCellStyle();
		cStyle.setAlignment(HorizontalAlignment.CENTER);
		cStyle.setBorderBottom(BorderStyle.THIN);
		cStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		cStyle.setBorderLeft(BorderStyle.THIN);
		cStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		cStyle.setBorderRight(BorderStyle.THIN);
		cStyle.setRightBorderColor(HSSFColor.BLACK.index);
		cStyle.setBorderTop(BorderStyle.THIN);
		cStyle.setTopBorderColor(HSSFColor.BLACK.index);
		cStyle.setFillForegroundColor(HSSFColor.RED.index);// 设置背景色
		cStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cStyle.setFont(font);

		//创建边框样式
		HSSFCellStyle boderStyle = wb.createCellStyle();
		boderStyle.setAlignment(HorizontalAlignment.CENTER);
		boderStyle.setBorderBottom(BorderStyle.THIN);
		boderStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		boderStyle.setBorderLeft(BorderStyle.THIN);
		boderStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		boderStyle.setBorderRight(BorderStyle.THIN);
		boderStyle.setRightBorderColor(HSSFColor.BLACK.index);
		boderStyle.setBorderTop(BorderStyle.THIN);
		boderStyle.setTopBorderColor(HSSFColor.BLACK.index);

		//退税金额使用样式
		HSSFCellStyle lastStyle = wb.createCellStyle();
		lastStyle.setAlignment(HorizontalAlignment.CENTER);
		lastStyle.setBorderBottom(BorderStyle.THIN);
		lastStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		lastStyle.setBorderLeft(BorderStyle.THIN);
		lastStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		lastStyle.setBorderRight(BorderStyle.THIN);
		lastStyle.setRightBorderColor(HSSFColor.BLACK.index);
		lastStyle.setBorderTop(BorderStyle.THIN);
		lastStyle.setTopBorderColor(HSSFColor.BLACK.index);
		lastStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);// 设置背景色
		lastStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		//创建单元格
		HSSFCell cell = row.createCell(0); //第一个单元格
		//创建标题
		cell = row.createCell(0); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("序号");
		cell = row.createCell(1); //获取单元格
		cell.setCellValue("发票号");
		cell.setCellStyle(cStyle);
		cell = row.createCell(2); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("出口日期");
		cell = row.createCell(3); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("运输方式");
		cell = row.createCell(4); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("发货港");
		cell = row.createCell(5); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("目的港");
		cell = row.createCell(6); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("HS");
		cell = row.createCell(7); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("英文名称");
		cell = row.createCell(8); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("中文名称");
		cell = row.createCell(9); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("成交方式");
		cell = row.createCell(10); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("报关总金额");
		cell = row.createCell(11); //获取单元格
		cell.setCellValue("清关总金额");
		cell.setCellStyle(cStyle);
		cell = row.createCell(12); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("销售");
		cell = row.createCell(13); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("采购");
		cell = row.createCell(14); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("总合同号");
		cell = row.createCell(15); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("合同总金额");
		cell = row.createCell(16); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("出口批次");
		cell = row.createCell(17); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("客户名称");
		cell = row.createCell(18); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("退税率");
		cell = row.createCell(19); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("货代");
		cell = row.createCell(20); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("运费");
		cell = row.createCell(21); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("合同号");
		cell = row.createCell(22); //获取单元格
		cell.setCellStyle(cStyle);
		cell.setCellValue("合同分配金额");


		//row = sheet.createRow(1);
		//cell = row.createCell(0); //获取单元格
		// 合并单元格
		//CellRangeAddress cra =new CellRangeAddress(1, 1, 0, 20); // 起始行, 终止行, 起始列, 终止列
		//sheet.addMergedRegion(cra);
		//cell.setCellStyle(style);
		//cell.setCellValue(list.get(0).get);
		//汇总数据
		for (int i = 0; i < tl; i++) {
			row = sheet.createRow(i + 1);
			row.setHeight((short) (25 * 20));
			cell = row.createCell(0);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getNum());
			cell = row.createCell(1);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getNonum());
			cell = row.createCell(2);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getSaildate());
			cell = row.createCell(3);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getTran2());
			cell = row.createCell(4);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getFromwhere());
			cell = row.createCell(5);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getTowhere());
			cell = row.createCell(6);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getHscode());
			cell = row.createCell(7);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getItemeng());
			cell = row.createCell(8);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getItemchn());
			cell = row.createCell(9);
			cell.setCellStyle(lastStyle);
			cell.setCellValue(list.get(i).getTran1());
			cell = row.createCell(10);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getTotalmoney());
			cell = row.createCell(11);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getUnitpriceall());
			cell = row.createCell(12);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getSale());
			cell = row.createCell(13);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getPurchase());
			cell = row.createCell(14);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getPurnoall());
			cell = row.createCell(15);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getMoneyall());
			cell = row.createCell(16);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getTimesall());
			cell = row.createCell(17);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getClientName());
			cell = row.createCell(18);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getRate());
			cell = row.createCell(19);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getHuodai());
			cell = row.createCell(20);
			cell.setCellStyle(lastStyle);
			cell.setCellValue(list.get(i).getYunfei());
			cell = row.createCell(21);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getPurno());
			cell = row.createCell(22);
			cell.setCellStyle(boderStyle);
			cell.setCellValue(list.get(i).getMoney());
		}


		//自动调整列宽
		sheet.autoSizeColumn((short) 0);
		sheet.autoSizeColumn((short) 1);
		sheet.autoSizeColumn((short) 2);
		sheet.autoSizeColumn((short) 3);
		sheet.autoSizeColumn((short) 4);
		sheet.autoSizeColumn((short) 5);
		sheet.autoSizeColumn((short) 6);
		sheet.autoSizeColumn((short) 7);
		sheet.autoSizeColumn((short) 8);
		sheet.autoSizeColumn((short) 9);
		sheet.autoSizeColumn((short) 10);
		sheet.autoSizeColumn((short) 11);
		sheet.autoSizeColumn((short) 12);
		sheet.autoSizeColumn((short) 13);
		sheet.autoSizeColumn((short) 14);
		sheet.autoSizeColumn((short) 15);
		sheet.autoSizeColumn((short) 16);
		sheet.autoSizeColumn((short) 17);
		sheet.autoSizeColumn((short) 18);
		sheet.autoSizeColumn((short) 19);
		sheet.autoSizeColumn((short) 20);
		sheet.autoSizeColumn((short) 21);
		sheet.autoSizeColumn((short) 22);


		File tempPath = new File(temp_path);
		if (!tempPath.exists() || !tempPath.isDirectory()) {
			tempPath.mkdir(); // 如果不存在，则创建该文件夹
		}
		FileOutputStream fs = new FileOutputStream(temp_path + File.separator + "汇总明细.xls", false);
		wb.write(fs);
		fs.close();

		return temp_path + File.separator + "汇总明细.xls";
	}


}

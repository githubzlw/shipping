package com.cynergy.main;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	 * @param path
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
	 * 
	 * 生成拆分报关品名excel
	 * @param path
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


}

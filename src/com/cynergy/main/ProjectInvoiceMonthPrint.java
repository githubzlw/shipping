package com.cynergy.main;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 * @Description: 导出月份发票信息表
 * @Author: polo
 * @CreateDate:2019/03/15
 */

public class ProjectInvoiceMonthPrint {

//	private static final String TEMP_PATH = "G:\\drawBack\\temp";
	private static PropertisUtil propertisUtil = new PropertisUtil("config.properties");

	public static String printExcel(HttpServletRequest request, List<ProjectStatisticsVO> list,String importDate)throws Exception {
		 String temp_path = propertisUtil.get("excel_temp");		 		 
         //出口合同数量
		 int tl = 0;
		 if(list != null && list.size() >0){
			 tl = list.size();
		 }
		 File file = new File(temp_path+File.separator+"demo.xlsm");
		 FileInputStream fi = new FileInputStream(file);
		 XSSFWorkbook wb = new XSSFWorkbook(fi);
		 XSSFSheet sheet = wb.getSheetAt(0); // 定位到当前工作表
		 // 设置工作簿名称
		 wb.setSheetName(0, importDate);
		 wb.setSheetName(1, importDate.split("-")[1]+"月份"); 
		 sheet.setForceFormulaRecalculation(true);
		 //行
		 int rowNo = 0;
		 //列
		 int colNo = 0;
		 Row row = sheet.getRow(rowNo);
		 Cell cell = row.getCell(colNo);
		 int count=0;
         //退税数据               
         for (int i=0;i<tl;i++){        	
        	 int r = (int)Math.floor(i/3);   
        	 int c = i%3;
        	 //当前写入列
        	 colNo = c*4+3;
        	 //录入id信息
        	 row = sheet.getRow(r*21);
        	 //复制开始行        	
        	 if(r*21>2160){
        		 for(int j=21;j>=0;j--){
        			 Row rowFrom = sheet.getRow(r*21-j);
            		 Row rowTo = sheet.getRow(r*21+(21-j));
            		 if(rowTo == null){
            			 rowTo = sheet.createRow(r*21+(21-j));  
            		 }
            		 copyRow(wb,rowFrom,rowTo,true);
            		 count++;
        		 }       		
        	 }
    		 cell = row.getCell(c*4);
    		 if(cell!=null){
    			 cell.setCellValue(list.get(i).getId()); 
    		 }      	
        	 
        	 if(StringUtils.isNotBlank(list.get(i).getItemchn())){
        		 //品名写入列
        		 rowNo = r*21+9;       		
        		 row = sheet.getRow(rowNo);
        		 if(row==null){
        			 row = sheet.createRow(rowNo);
        		 }
        		 cell = row.getCell(colNo);
        		 if(cell!=null){
        			 cell.setCellValue(list.get(i).getItemchn()); 
        		 }      		 
        	 }
        	 if(StringUtils.isNotBlank(list.get(i).getUnit())){
        		 //单位写入列
        		 rowNo = r*21+10;
        		 row = sheet.getRow(rowNo);
        		 cell = row.getCell(colNo);
        		 if(cell!=null){
        		    cell.setCellValue("千克");
        		 }
        	 }
        	 if(StringUtils.isNotBlank(list.get(i).getUnit())){
        		 //数量写入列
        		 rowNo = r*21+11;
        		 row = sheet.getRow(rowNo);
        		 cell = row.getCell(colNo);
        		 if(cell!=null){
        			 cell.setCellValue(new BigDecimal(list.get(i).getQuantity()).setScale(2).doubleValue());
        		 }
        	 }
        	 if(StringUtils.isNotBlank(list.get(i).getFactoryName())){
        		 //工厂名写入列
        		 rowNo = r*21+12;
        		 row = sheet.getRow(rowNo);
        		 cell = row.getCell(colNo);
        		 if(cell!=null){
        			 cell.setCellValue(list.get(i).getFactoryName()+"("+ list.get(i).getProjectNo() +")");
        		 }
        	 }
        	 if(list.get(i).getTotalAmount()!=null){
        		 //价格写入列
        		 rowNo = r*21+13;
        		 row = sheet.getRow(rowNo);
        		 cell = row.getCell(colNo);
        		 if(cell!=null){
        		 cell.setCellValue(list.get(i).getTotalAmount());
        		 }
        	 }
         }            
		
		File tempPath = new File(temp_path);
		if (!tempPath.exists() || !tempPath.isDirectory()) {
			tempPath.mkdir(); // 如果不存在，则创建该文件夹
		}
		//wb.removeSheetAt(0);	   //删除模板sheet
		//wb.removeSheetAt(1);	   //删除模板sheet
		FileOutputStream fs = new FileOutputStream(temp_path + File.separator + "开票信息表.xlsm",false);
		wb.write(fs);
		fs.close();		

		return temp_path + File.separator + "开票信息表.xlsm ";
	}
	
	
	

    //根据路径删除文件
	public static void deleteFile(String path) {
		File file = new File(path);
		file.delete();
	}
	/**
     * 行复制功能
     *
     * @param fromRow
     * @param toRow
     */
    public static void copyRow(Workbook wb, Row fromRow, Row toRow, boolean copyValueFlag) {
        toRow.setHeight(fromRow.getHeight());
        for(Iterator cellIt = fromRow.cellIterator(); cellIt.hasNext(); ) {
            Cell tmpCell = (Cell) cellIt.next();
            Cell newCell = toRow.createCell(tmpCell.getColumnIndex());
            copyCell(wb, tmpCell, newCell, copyValueFlag);
        }
        Sheet worksheet = fromRow.getSheet();
        for(int i = 0; i < worksheet.getNumMergedRegions(); i++) {
            CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
            if(cellRangeAddress.getFirstRow() == fromRow.getRowNum()) {
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(toRow.getRowNum(), (toRow.getRowNum() +
                        (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())), cellRangeAddress
                        .getFirstColumn(), cellRangeAddress.getLastColumn());
                worksheet.addMergedRegionUnsafe(newCellRangeAddress);
            }
        }
    }

    /**
     * 复制单元格
     *
     * @param srcCell
     * @param distCell
     * @param copyValueFlag true则连同cell的内容一起复制
     */
    public static void copyCell(Workbook wb, Cell srcCell, Cell distCell, boolean copyValueFlag) {
        CellStyle newStyle = wb.createCellStyle();
        CellStyle srcStyle = srcCell.getCellStyle();
        newStyle.cloneStyleFrom(srcStyle);
        newStyle.setFont(wb.getFontAt(srcStyle.getFontIndex()));
        //样式
        distCell.setCellStyle(newStyle);
        //评论
        if(srcCell.getCellComment() != null) {
            distCell.setCellComment(srcCell.getCellComment());
        }
        // 不同数据类型处理
        CellType srcCellType = srcCell.getCellTypeEnum();
        distCell.setCellType(srcCellType);
        if(copyValueFlag) {
            if(srcCellType == CellType.NUMERIC) {
                if(DateUtil.isCellDateFormatted(srcCell)) {
                    distCell.setCellValue(srcCell.getDateCellValue());
                } else {
                    distCell.setCellValue(srcCell.getNumericCellValue());
                }
            } else if(srcCellType == CellType.STRING) {
                distCell.setCellValue(srcCell.getRichStringCellValue());
            } else if(srcCellType == CellType.BLANK) {

            } else if(srcCellType == CellType.BOOLEAN) {
                distCell.setCellValue(srcCell.getBooleanCellValue());
            } else if(srcCellType == CellType.ERROR) {
                distCell.setCellErrorValue(srcCell.getErrorCellValue());
            } else if(srcCellType == CellType.FORMULA) {
                distCell.setCellFormula(srcCell.getCellFormula());
            } else {
            }
        }
    }
 
}

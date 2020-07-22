package com.cynergy.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * 读取Excel
 * 
 * @author polo
 */
public class ReadExcelUtils {
	private Workbook wb;
	private Sheet sheet;
	private Row row;
	private String gen;
	private int proId;

	public ReadExcelUtils(String filepath,int pid) {
		if (filepath == null) {
			return;
		}
		String ext = filepath.substring(filepath.lastIndexOf("."));
		try {
			InputStream is = new FileInputStream(filepath);
			if (".xls".equals(ext)) {
				wb = new HSSFWorkbook(is);
				gen = "xls";
			} else if (".xlsx".equals(ext)) {
				wb = new XSSFWorkbook(is);
				gen = "xlsx";
			} else if (".xlsm".equals(ext)) {
				wb = new XSSFWorkbook(is);
				gen = "xlsm";
			} else {
				wb = null;
			}
			proId = pid;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 读取Excel数据内容 
	 * 
	 * @param InputStream
	 * @return Map 包含单元格数据内容的Map对象
	 * @author polo
	 */
	public List<ReadExcelVO> readExcelContent()throws Exception {
		if (wb == null) {
			throw new Exception("Workbook对象为空！");
		}
		List<ReadExcelVO> content = new ArrayList<ReadExcelVO>();

		sheet = wb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		// 获取品名所在列		
		int col_itemchn = 0;
		// 获取数量所在列	
		int col_quantity = 1;
		// 获取金额所在列	
		int col_amount = 2;
		//获取合同所在列
		int col_purno = 4;		
		//品名
		String itemchn = "";
		//品名
		int quantity = 0;
		//金额
		Double amount = 0.0;
		//合同号
		String purno = "";

		for (int i = 0; i < rowNum; i++) {
			ReadExcelVO excelVO = new ReadExcelVO();			
			row = sheet.getRow(i+1);
			if(row==null){
				continue;
			}
			if(row.getCell(col_itemchn) == null){
				continue;
			}
	        Object obj = getCellFormatValue(row.getCell(col_itemchn));
	        if(obj!=null&&!"".equals(obj)){
	        	itemchn = obj.toString();
	        }	
	        Object obj3 = getCellFormatValue(row.getCell(col_quantity));
	        if(obj3!=null&&!"".equals(obj3)){
	        	String str = obj3.toString();
	        	quantity = (int)Double.parseDouble(str);
	        }	
	        Object obj1 = getCellFormatValue(row.getCell(col_amount));
	        boolean flag = isNumeric(obj1.toString());
	        if (flag) {
	        	amount = Double.parseDouble(obj1.toString());
	        }else{
	        	amount = 0.0;
	        }	
	        Object obj2 = getCellFormatValue(row.getCell(col_purno));
	        if(obj2!=null){
	        	purno = obj2.toString();
	        }else{
	        	purno = null;
	        	continue;
	        }

	        //保存到列表
	        excelVO.setAmount(amount);
	        excelVO.setQuantity(quantity);
	        excelVO.setItemchn(itemchn);
	        excelVO.setPurno(purno);
	        excelVO.setProId(proId);
	        content.add(excelVO);
		}

		return content;
	}

	/**
	 * 
	 * 根据Cell类型设置数据
	 * 
	 * @param cell
	 * @return
	 * @author polo
	 */
	private Object getCellFormatValue(Cell cell) {
		Object cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:// 如果当前Cell的Type为NUMERIC
			case Cell.CELL_TYPE_FORMULA: {
				// 判断当前的cell是否为Date
				if (DateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式
					// data格式是带时分秒的：2013-7-10 0:00:00
					// cellvalue = cell.getDateCellValue().toLocaleString();
					// data格式是不带带时分秒的：2013-7-10
					Date date = cell.getDateCellValue();
					cellvalue = date;
				} else {// 如果是纯数字

					// 取得当前Cell的数值
					cellvalue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			}
			case Cell.CELL_TYPE_STRING:// 如果当前Cell的Type为STRING
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			default:// 默认的Cell值
				cellvalue = "";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;
	}

	/**  
	 * 判断字符串是否为数字  
	 *   
	 * @param str  
	 * @return  
	 */  
	public static boolean isNumeric(String str) {  
	    Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");  
	    Matcher isNum = pattern.matcher(str);  
	    if (!isNum.matches()) {  
	        return false;  
	    }  
	    return true;  
	}  

	
	/** 
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty 
     *  
     * @param obj 
     * @return 
     */  
    public static boolean isNullOrEmpty(Object obj) {  
        if (obj == null)  
            return true;  
  
        if (obj instanceof CharSequence)  
            return ((CharSequence) obj).length() == 0;  
  
        if (obj instanceof Collection)  
            return ((Collection) obj).isEmpty();  
  
        if (obj instanceof Map)  
            return ((Map) obj).isEmpty();  
  
        if (obj instanceof Object[]) {  
            Object[] object = (Object[]) obj;  
            if (object.length == 0) {  
                return true;  
            }  
            boolean empty = true;  
            for (int i = 0; i < object.length; i++) {  
                if (!isNullOrEmpty(object[i])) {  
                    empty = false;  
                    break;  
                }  
            }  
            return empty;  
        }  
        return false;  
    } 
	
    public List<ReadExcelVO> readExcelContent1()throws Exception {
		if (wb == null) {
			throw new Exception("Workbook对象为空！");
		}
		boolean save=false;
		List<ReadExcelVO> content = new ArrayList<ReadExcelVO>();

		sheet = wb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		// 获取品名所在列		
		int col_itemchn = 0;
		// 获取数量所在列	
		int col_quantity = 1;
		// 获取金额所在列	
		int col_amount = 2;
		//获取合同所在列
		int col_purno = 4;		
		//品名
		String itemchn = "";
		//品名
		int quantity = 0;
		//金额
		Double amount = 0.0;
		//合同号
		String purno = "";
		for (int i = 0; i < rowNum; i++) {
			ReadExcelVO excelVO = new ReadExcelVO();			
			row = sheet.getRow(i+1);
			if(row==null){
				continue;
			}
			if(row.getCell(col_itemchn) == null){
				continue;
			}
	        Object obj = getCellFormatValue(row.getCell(col_itemchn));
	        if(obj!=null&&!"".equals(obj)){
	        	itemchn = obj.toString();
	        }	
	        Object obj3 = getCellFormatValue(row.getCell(col_quantity));
	        if(obj3!=null&&!"".equals(obj3)){
	        	String str = obj3.toString();
	        	quantity = (int)Double.parseDouble(str);
	        }	
	        Object obj1 = getCellFormatValue(row.getCell(col_amount));
	        boolean flag = isNumeric(obj1.toString());
	        if (flag) {
	        	amount = Double.parseDouble(obj1.toString());
	        }else{
	        	amount = 0.0;
	        }	
	        Object obj2 = getCellFormatValue(row.getCell(col_purno));
	        if(obj2!=null){
	        	purno = obj2.toString();
	        }else{
	        	purno = null;
	        	continue;
	        }
	        if(itemchn!=null&&!"".equalsIgnoreCase(itemchn)&&quantity!=0 &&amount!=0&&purno!=null){
			save=true;	
			}else{
				save=false;	
			}  
	       
		}
		
		if(save==true){
		
		for (int i = 0; i < rowNum; i++) {
			ReadExcelVO excelVO = new ReadExcelVO();			
			row = sheet.getRow(i+1);
			if(row==null){
				continue;
			}
			if(row.getCell(col_itemchn) == null){
				continue;
			}
	        Object obj = getCellFormatValue(row.getCell(col_itemchn));
	        if(obj!=null&&!"".equals(obj)){
	        	itemchn = obj.toString();
	        }	
	        Object obj3 = getCellFormatValue(row.getCell(col_quantity));
	        if(obj3!=null&&!"".equals(obj3)){
	        	String str = obj3.toString();
	        	quantity = (int)Double.parseDouble(str);
	        }	
	        Object obj1 = getCellFormatValue(row.getCell(col_amount));
	        boolean flag = isNumeric(obj1.toString());
	        if (flag) {
	        	amount = Double.parseDouble(obj1.toString());
	        }else{
	        	amount = 0.0;
	        }	
	        Object obj2 = getCellFormatValue(row.getCell(col_purno));
	        if(obj2!=null){
	        	purno = obj2.toString();
	        }else{
	        	purno = null;
	        	continue;
	        }
            
	        //保存到列表
	        excelVO.setAmount(amount);
	        excelVO.setQuantity(quantity);
	        excelVO.setItemchn(itemchn);
	        excelVO.setPurno(purno);
	        excelVO.setProId(proId);
	        content.add(excelVO);
            
		}
		}
		return content;
	}
	

}

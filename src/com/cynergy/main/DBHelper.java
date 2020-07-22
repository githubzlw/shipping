
package com.cynergy.main;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBHelper {

  
    public static Connection getConnection(){
        try {
        	
			//采用相对定位方法
			InputStream ins=MainSql.class.getResourceAsStream("../../../jdbc.properties");
	        // 生成properties对象  
	        Properties p = new Properties();  
	        try {  
	            p.load(ins);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
            Class.forName(p.getProperty("DRIVER"));
            Connection con=DriverManager.getConnection(p.getProperty("URL"), p.getProperty("USER"),p.getProperty("PWD"));
            //System.out.println("con success");
            return con;
        } catch (ClassNotFoundException e) {
        	
            e.printStackTrace();
        } catch (SQLException e) {
        	
            
            e.printStackTrace();
        }
        return null;
    }
    
    //连接ERP数据库
    public static Connection getConnectionERP(){
        try {
        	
			//采用相对定位方法
			InputStream ins=MainSql.class.getResourceAsStream("../../../jdbc.properties");
	        // 生成properties对象  
	        Properties p = new Properties();  
	        try {  
	            p.load(ins);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
            Class.forName(p.getProperty("DRIVER"));
            Connection con=DriverManager.getConnection(p.getProperty("URL1"), p.getProperty("USER1"),p.getProperty("PWD1"));
            //System.out.println("con success");
            return con;
        } catch (ClassNotFoundException e) {
        	
            e.printStackTrace();
        } catch (SQLException e) {
        	
            
            e.printStackTrace();
        }
        return null;
    }
    
    
     
    public static void close(Connection con,Statement stm,ResultSet rs){
        if(con!=null){
            try {
                con.close();
            } catch (SQLException e) {
               
                e.printStackTrace();
            }
        }
        if(stm!=null){
            try {
                stm.close();
            } catch (SQLException e) {
              
                e.printStackTrace();
            }
        }
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                
                e.printStackTrace();
            }
        }
    }
 

	public static void returnConnection(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
     

}
package com.cynergy.main;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MainSql {
//	static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//	static final String URL = "jdbc:sqlserver://192.168.0.168:1433;databaseName=reportform";
//	static final String USER = "sa";
//	static final String PWD = "admin!@#2013";
	public static Connection connection=null;
	public static void main(String[] args) {
		Connection connection = getConnection();
		Statement createStatement;
		try {
			createStatement = connection.createStatement();
			String sql="select * from test";
			boolean execute = createStatement.execute(sql);
			System.out.println(execute);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void Init(){
		connection=getConnection();
	}
	public static Connection getConnection() {
			Connection con = null;
			try {
				//采用绝对路径方法
				 // 生成文件对象  
//		        File pf = new File(System.getProperty("user.dir")+ "/src/jdbc.properties");
				
				//采用相对定位方法
				InputStream ins=MainSql.class.getResourceAsStream("../../../jdbc.properties");
		        // 生成文件输入流  
//		        FileInputStream inpf = null;  
//		        try {  
//		            inpf = new FileInputStream(ins);  
//		        } catch (Exception e) {  
//		            e.printStackTrace();  
//		        }  
		        // 生成properties对象  
		        Properties p = new Properties();  
		        try {  
		            p.load(ins);  
		        } catch (Exception e) {  
		            e.printStackTrace();  
		        }  
		        // 输出properties文件的内容  
//		        System.out.println("name:" + p.getProperty("DRIVER"));  
//		        System.out.println("password:" + p.getProperty("URL"));  
		    
				Class.forName(p.getProperty("DRIVER"));
				con = DriverManager.getConnection(p.getProperty("URL"), p.getProperty("USER"),p.getProperty("PWD"));
//				Class.forName(DRIVER);
//				con = DriverManager.getConnection(URL, USER,PWD);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return con;
		}
}

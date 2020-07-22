package com.cynergy.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.cynergy.main.DBHelper;
import com.cynergy.main.MainSql;

public class Test {

	public static void main(String[] args) throws SQLException {
		String sql2="select itemeng,itemchn,hscode,quantity,trueprice,nw from items where proId=1";
		Connection connection = DBHelper.getConnection();
		Statement createStatement = connection.createStatement();
		ResultSet resultSet2 = createStatement.executeQuery(sql2);
	}
}

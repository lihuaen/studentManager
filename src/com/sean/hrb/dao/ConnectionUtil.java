package com.sean.hrb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	private static Connection conn=null;
	public static Connection getConnection() {
		if(conn==null) {
			System.out.println("new connection");
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/studentManager?useUnicode=true&characterEncoding=UTF8&serverTimezone=UTC","root","root");
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("not new conncetion");
		}
		return conn;
	}

}
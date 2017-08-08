package com.ibm.asset.logview.core.db;

import java.sql.*;
/**
 * <p>
 * Created on Aug 01, 2017
 * <p>
 * Description:This action will be called when user get connection on DB .
 * 
 * @author 
 */
public class DBConnection {

    private static DBConnection dbIsntance;
	String url="jdbc:odbc:mydsn";
	String driverClassName = "sun.jdbc.odbc.JdbcOdbcDriver";
	
	
    private DBConnection() {
      // private constructor //
    	try{
    		Class.forName(driverClassName);
    	}catch(Exception e){e.printStackTrace();}
    	
    }

    public static DBConnection getInstance(){
    if(dbIsntance==null){
        dbIsntance= new DBConnection();
    }
    return dbIsntance;
    }

    public  Connection getConnection(){
    	
    	Connection con=null;
    	try{
    		System.out.println("getting connection..");
     	    con =DriverManager.getConnection(url);
    	}catch(SQLException se){
    		se.printStackTrace();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return con;
    }
}
package com.ibm.asset.logview.core.db;

import java.sql.*;

import org.apache.log4j.Logger;

import com.ibm.asset.logview.web.actions.UserController;

public class SingletonDB {
	static Logger log = Logger.getLogger(SingletonDB.class);
    private static SingletonDB dbIsntance;
 //   private static Connection con ;
	String url="jdbc:odbc:mydsn";
	String driverClassName = "sun.jdbc.odbc.JdbcOdbcDriver";
	
	
    private SingletonDB() {
      // private constructor //
    	try{
    		Class.forName(driverClassName);
    	}catch(Exception e){e.printStackTrace();}
    	
    }

    public static SingletonDB getInstance(){
    if(dbIsntance==null){
        dbIsntance= new SingletonDB();
    }
    return dbIsntance;
    }

    public  Connection getConnection(){
    	
    	Connection con=null;
    	try{
    		log.debug("getting connection..");
     	    con =DriverManager.getConnection(url);
    	}catch(SQLException se){
    		se.printStackTrace();
    	}catch(Exception e){
    		e.printStackTrace();
    	}

//        if(con==null){
//            try {
//                System.out.println("am getting connection..");
//     	       String url="jdbc:odbc:mydsn";  
//    	       Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");  
//    	       con =DriverManager.getConnection(url);               
//                
//            } catch (SQLException se) {
//            	System.out.println(se);
//            }catch (Exception e) {
//            	System.out.println(e);
//            }
//        }else{System.out.println("returning existing conn..");}

        return con;
    }
}
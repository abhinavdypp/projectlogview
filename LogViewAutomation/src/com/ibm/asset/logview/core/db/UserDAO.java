package com.ibm.asset.logview.core.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class UserDAO {
	
	public Map<String, String> getUserApplications(String uname) {
		Map<String, String> appNamesMap = null;
		PreparedStatement selectStmt = null;
		ResultSet rs = null;
	
		String sql = "select * from Application_details "
				+"where Application_id IN "
				+"(Select ApplictionId from User_Appliction_details "
				+"where UserId ="
				+"(Select ID from Login where username= ?))";
		try {
			selectStmt = SingletonDB.getInstance().getConnection().prepareStatement(sql);
			selectStmt.setString(1, uname);
			 rs = selectStmt.executeQuery();
			appNamesMap = new HashMap<String, String>();
			while (rs.next()) {
				int id = rs.getInt(1);
				 String appName=rs.getString(2);
				 appNamesMap.put(String.valueOf(id), appName);
				 System.out.println("ID  : "+id);
				
			}

		} catch (SQLException e) {
			System.out.println("Error while getting application names for userID");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (null != rs)
					rs.close();
				if (null != selectStmt)
					selectStmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}	


		return appNamesMap;

	}
	
	public Map<String, String> getUsersubApplications(String uname,String appId) {
		Map<String, String> subAppNamesMap = null;
		
		PreparedStatement pst = null;
		ResultSet subApps = null;
	
		String sql = "select * from Sub_Application_details "
				+"where app_id = ? "
				+"AND user_id=(Select ID from Login where username=?)";
		try {
			pst = SingletonDB.getInstance().getConnection().prepareStatement(sql);
			pst.setString(1, appId);
			pst.setString(2, uname);
			subApps = pst.executeQuery();
			subAppNamesMap = new HashMap<String, String>();
			while (subApps.next()) {
				subAppNamesMap.put(subApps.getString(1),
						subApps.getString(2));
			}
			
		} catch (SQLException e) {
			System.out.println("Error while getting  sub application names for userID");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (null != subApps)
					subApps.close();
				if (null != pst)
					pst.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}	


		return subAppNamesMap;

	}
	
	public ArrayList<String> getappEnvirnment(String appId)
	{
		ArrayList<String> envt = new ArrayList<String>();
				PreparedStatement ps = null;
				ResultSet environment = null;
				try {
					 String sqlQuery = "Select distinct Enviornment from Server_Details where Application_Id=?";
						ps = SingletonDB.getInstance().getConnection().prepareStatement(sqlQuery);
					
					ps.setString(1, appId);
					
					environment = ps.executeQuery();
					
					while (environment.next()) {
						
						envt.add(environment.getString(1));
						
					}
		 
		
				} catch (SQLException ex) {
					 ex.printStackTrace();
			       } finally {
					try {
						if (null != ps)
							ps.close();						
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

		
		return envt;
	}
	
	public ArrayList<String> getsubappEnvirnment(String subappId)
	{
		ArrayList<String> envt = new ArrayList<String>();
				PreparedStatement ps = null;
				ResultSet environment = null;
				try {
					 String sqlQuery = "Select distinct Enviornment from Server_Details where Sub_app_id=?";
						ps = SingletonDB.getInstance().getConnection().prepareStatement(sqlQuery);
					
					
					ps.setString(1, subappId);
					environment = ps.executeQuery();
					
					while (environment.next()) {
						
						envt.add(environment.getString(1));
						
					}
		 
		
				} catch (SQLException ex) {
					 ex.printStackTrace();
			       } finally {
					try {
						if (null != ps)
							ps.close();						
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

		
		return envt;
	}
	
	
	public ArrayList<String> getLogpath(String environment, int appId, String sub_app_id)
	{
		ArrayList<String> logs = null;
		PreparedStatement ps = null;
		ResultSet logpath = null;
		
		try{
			String LogpathQuery="Select LogPath from LogPathDetails where Server_ID = "+
					"(Select ID from Server_Details where Enviornment=? AND ApplicationId=? AND Sub_app_id=?) ";
			
			ps = SingletonDB.getInstance().getConnection().prepareStatement(LogpathQuery);
			System.out.println("hi 1");
			
			ps.setString(1, environment);
			ps.setInt(2, appId);
			ps.setString(3, sub_app_id);

			System.out.println("hi 2");
    		logpath=ps.executeQuery();
    		System.out.println("hi 3");
    		logs = new ArrayList<String>();
    		
    		while (logpath.next()) {
				
				logs.add(logpath.getString(1));
				
			}
			System.out.println("hi 4");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				
				}		
		
		return logs;
	}
		
	
	

	
	public void commitTransaction()
	{
	 
		try {

			if (null != SingletonDB.getInstance().getConnection())			
			    SingletonDB.getInstance().getConnection().commit();
				System.out.println("Transactions commited.");
			 
				} catch (SQLException ex) {
					 ex.printStackTrace();
			    }finally {
					try {
						    System.out.println("Closing connection.");
						if (null != SingletonDB.getInstance().getConnection())
							SingletonDB.getInstance().getConnection().close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} 		
	}	

	public void rollBackTransaction()
	{
	 
		try {

			if (null != SingletonDB.getInstance().getConnection())			
			SingletonDB.getInstance().getConnection().rollback();
			System.out.println("Transactions rolled back!");
			
				} catch (SQLException ex) {
					 ex.printStackTrace();
			    } 		
	}
	
	
	public static int getMaximumValue(String tableName, String columnName)
	{
		ResultSet rs = null;
		Statement stmt=null;
		int result = 0;
		try {
		stmt = SingletonDB.getInstance().getConnection().createStatement();
		String query = "select max(" + columnName + ") from " + tableName;
		
			rs = stmt.executeQuery(query);
			if(rs.next())
			{
				
				result = rs.getInt(1);
				System.out.println("max value " + result);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if (null != stmt)
					stmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		return result;
	}

	

}

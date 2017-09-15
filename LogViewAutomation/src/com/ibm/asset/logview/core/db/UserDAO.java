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
	
		String sql = "select * from ApplicationDetails "
				+"where ApplicationID IN "
				+"(Select ApplicationId from UserApplicationDetails "
				+"where GroupId IN "
				+"(Select distinct(GroupId) from UserGroupDetails where UserId ="
				+"(Select UserID from UserDetails where UserName= ?)))";
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
	
		String sql = "select * from SubApplicationDetails "
				+"where ApplicationID = ? "
				+ "AND GroupID IN "
				+ "(Select GroupId from UserGroupDetails where "
				+"UserID=(Select UserID from UserDetails where UserName=?))";
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
					 String sqlQuery = "Select distinct Enviornment from ServerDetails where ApplicationID=?";
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
					 String sqlQuery = "Select distinct Enviornment from ServerDetails where SubAppID=?";
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
			String LogpathQuery="Select LogPath from LogPathDetails where ServerID = "+
					"(Select ServerID from ServerDetails where Enviornment=? AND ApplicationID=? AND SubAppID=?) ";
			
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

	public Map<String, String> getServerNames(int appId , int subAppId, String envName)
	{
		Map<String, String> serverNameMap = new HashMap<String, String>();
				PreparedStatement ps = null;
				ResultSet serverNames = null;
				try {
					 String sqlQuery = "Select ServerID, ServerName from ServerDetails where ApplicationID=? AND " 
							 + "SubAppId = ? AND Enviornment = ?";
						ps = SingletonDB.getInstance().getConnection().prepareStatement(sqlQuery);
					
					ps.setInt(1, appId);
					ps.setInt(2, subAppId);
					ps.setString(3, envName);
					
					serverNames = ps.executeQuery();
					
					while (serverNames.next()) {
						
						serverNameMap.put(serverNames.getString(1), serverNames.getString(2));
						
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

		
		return serverNameMap;
	}
	
	public ArrayList<String> getLogPaths(int serverId)
	{
		ArrayList<String> logPathList = new ArrayList<String>();
				PreparedStatement ps = null;
				ResultSet logpaths = null;
				try {
					 String sqlQuery = "Select LogPath from LogPathDetails where ServerID=?";
						ps = SingletonDB.getInstance().getConnection().prepareStatement(sqlQuery);
					
					ps.setInt(1, serverId);
					
					logpaths = ps.executeQuery();
					
					while (logpaths.next()) {
						
						logPathList.add(logpaths.getString(1));
						
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

		
		return logPathList;
	}
	
	public ArrayList<String> getfileNameByServerIDAndEnvironment(String serverID , String environment)
	{
		ArrayList<String> fileNames = new ArrayList<String>();
				PreparedStatement ps = null;
				ResultSet logFileName = null;
				try {
					 String sqlQuery = "SELECT LogFileName, IpAddress, ServerLoginId, ServerLoginPwd FROM ServerDetails where ServerID= ? AND Enviornment= ? ";
						ps = SingletonDB.getInstance().getConnection().prepareStatement(sqlQuery);
					
					ps.setInt(1, Integer.parseInt(serverID));
					ps.setString(2, environment);
					
					
					logFileName = ps.executeQuery();
					
					while (logFileName.next()) {
						
						fileNames.add(logFileName.getString(1));
						fileNames.add(logFileName.getString(2));
						fileNames.add(logFileName.getString(3));
						fileNames.add(logFileName.getString(4));
						
						
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

		
		return fileNames;
	}
	

}
 
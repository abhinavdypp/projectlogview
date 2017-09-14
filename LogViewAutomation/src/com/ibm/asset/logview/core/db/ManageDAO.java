package com.ibm.asset.logview.core.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibm.asset.logview.core.data.ApplicationBean;
import com.ibm.asset.logview.core.data.ServerData;
import com.ibm.asset.logview.core.data.SubApplicationBean;
import com.ibm.asset.logview.core.data.User;



public class ManageDAO {
	
	// To retrieve user details from Login table which return Map object with user details	
		public Map<String, String> getUserDetails()
		{
			Map<String, String> appNamesMap = new HashMap<String, String>();
			Statement selectStmt = null;
			ResultSet rs = null;
			 
			try {
				
				 selectStmt = SingletonDB.getInstance().getConnection().createStatement();
				 rs = selectStmt.executeQuery("select * from UserDetails");

				 while(rs.next())
				 {
					 int id = rs.getInt("UserID");
					 String userName=rs.getString("UserName");
					 appNamesMap.put(String.valueOf(id), userName);
					 System.out.println("ID  : "+id);				
				 }
			
				} catch (SQLException ex) {
						 ex.printStackTrace();
				} finally {
						try {
							if (null != rs)
								rs.close();
							if (null != selectStmt)
								selectStmt.close();
//							if (null != SingletonDB.getInstance().getConnection())
//								SingletonDB.getInstance().getConnection().close();

						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}		
			return appNamesMap;
		}	
		
		
		//***** Update Last Login of user in Login table****
				public Boolean LastLogin(String uname)
				{
					Boolean lastLogin=false;
					PreparedStatement pst = null;
					Date today = new Date(); 
					SimpleDateFormat Date_format= new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
								String timeStamp= Date_format.format(today);
					 			
					 
					try {
						
						
						  String sqlQuery = "UPDATE UserDetails SET LastLogin=? WHERE UserName=?";
							pst = SingletonDB.getInstance().getConnection().prepareStatement(sqlQuery);
							pst.setString(1, timeStamp);
							pst.setString(2, uname);
							  
							int rowCount = pst.executeUpdate();
							System.out.println(rowCount);
							if(rowCount>0)
								lastLogin = true;
						 
							} catch (SQLException ex) {
								 ex.printStackTrace();
						       } finally {
								try {
									if (null != pst)
										pst.close();						
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}

					
					return lastLogin;
				}


		
		
		public ArrayList<User> selectUserDetails()
		 {
				ArrayList<User> users = new ArrayList<User>();
				ResultSet UserDetails = null;
				Statement selectStmt = null;
				String getUserDetailsQuery = "SELECT UserID,UserName,Password,Role,AvailabilityStatus FROM UserDetails";
				try {
					 selectStmt = SingletonDB.getInstance().getConnection()
							.createStatement();
					 UserDetails = selectStmt
							.executeQuery(getUserDetailsQuery);
					while (UserDetails.next()) {
						// System.out.println("getting query results");
						User data = new User();
						data.setId(UserDetails.getInt("UserID"));
						data.setUsername(UserDetails.getString("UserName"));
						
						data.setRole(UserDetails.getString("Role"));
						data.setStatus(UserDetails.getString("AvailabilityStatus"));
									
						users.add(data);
					}
				} catch (Exception e) {
					System.out.println("Error while getting all user details");
					e.printStackTrace();
				}finally {
					try {
						if (null != UserDetails)
							UserDetails.close();
						if (null != selectStmt)
							selectStmt.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}	

				
				return users;
			}
			
		
		
	
	
	public Boolean addLoginDetails(String name, String password, String role)
	{
		Boolean addUser=false;
		PreparedStatement pst = null;
		//*****Adding Timestamp******
				Date today = new Date(); 
				SimpleDateFormat Date_format= new SimpleDateFormat("yyyy/MM/dd");
							String timeStamp= Date_format.format(today);
		try {
			
			int maxPKValue = getMaximumValue("UserDetails", "UserID");
			maxPKValue++;
			
			  String sqlQuery = "INSERT INTO UserDetails (UserID,UserName,Password,Role,AvailabilityStatus,CreationDate) VALUES (?, ?, ?, ?, ?, ?)";
				pst = SingletonDB.getInstance().getConnection().prepareStatement(sqlQuery);
				
				  pst.setInt(1, maxPKValue);  
		          pst.setString(2,name);        
		          pst.setString(3,password);
		          pst.setString(4,role);
		          pst.setString(5,"Yes");
		          pst.setString(6, timeStamp);
		          
				int rowCount = pst.executeUpdate();
		
				if(rowCount>0)
					addUser = true;
			 
				} catch (SQLException ex) {
					 ex.printStackTrace();
			       } finally {
					try {
						if (null != pst)
							pst.close();						
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

		
		return addUser;
	}


	
	public Boolean updateData( List<User>users)
	{
		PreparedStatement updateStmt = null;
		Boolean updateUser=false;
		
//		
//		
		try{
			
   
			updateStmt = SingletonDB.getInstance().getConnection().prepareStatement("UPDATE UserDetails SET Role=?, AvailabilityStatus=? WHERE UserID=?");
       		
    		for (int k = 0; k < users.size(); k++) {
    			User data=users.get(k);
				  
				//  updateStmt.setString(1, data.getPassword());
				  updateStmt.setString(1, data.getRole());
				  updateStmt.setString(2,data.getStatus());
				  updateStmt.setInt(3, data.getId());
				  updateStmt.addBatch();
				}
			 int[] row= updateStmt.executeBatch();
			 if(row!=null){
				 updateUser=true;
			 }
			 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				
				}		
		
		return updateUser;
	}
		
	

	public Boolean deleteUserDetails(String selectedRecords)
	{
		
		Boolean deleteUser=false;
		
		
		if (null != selectedRecords) {
			String[] ids = selectedRecords.split(",");
			int[] recordids = new int[ids.length];
			for (int j = 0; j < ids.length; j++) {
				recordids[j] = Integer.parseInt(ids[j]);
			}
			String sql = "DELETE from UserDetails where UserID = "; 
			for (int k = 0; k < recordids.length; k++) {
				if (k == 0)
					sql = sql + recordids[k];
				else
					sql = sql + " or UserID = " + recordids[k];
			}
			System.out.println(sql);			
					
		try{
			int record=SingletonDB.getInstance().getConnection().createStatement().executeUpdate(sql);
			if(record>0){
				deleteUser=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
			return deleteUser;
	}
		
	// To add new entry into application table which returns appId of inserted entry.
	public int addApplicationDetails(String appName)
	{
		int appId =0;
		PreparedStatement insertStmt = null;
		
		try {
			
			 	int maxAppId= getMaximumValue("ApplicationDetails", "ApplicationId");
			 	maxAppId ++;
			    System.out.println("maxAppId : " + maxAppId);

			    String sqlQuery = "INSERT INTO ApplicationDetails " +
						 //   "(Application id, Application Name) " +
		                      "VALUES(?,?)";
				insertStmt = SingletonDB.getInstance().getConnection().prepareStatement(sqlQuery);		
				insertStmt.setInt(1, maxAppId);
				insertStmt.setString(2, appName);				
				int rowCount = insertStmt.executeUpdate();
				
				if(rowCount>0)
			    appId =maxAppId;
			 
				} catch (SQLException ex) {
					 ex.printStackTrace();
			       } finally {
					try {
						if (null != insertStmt)
							insertStmt.close();		
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}		
		
		return appId;
	}	

	// To add new entry into sub application table which returns status of insertion.
	public Boolean addSubApplicationDetails(String subAppName, int appId, String groupId)
	{
		Boolean tranStatus=false;
		PreparedStatement insertStmt = null;
		 
		try {
			
				int maxSubAppId= getMaximumValue("SubApplicationDetails", "SubAppID");
				maxSubAppId ++;
				System.out.println("maxSubAppId : " + maxSubAppId);
				  
				  String sqlQuery = "INSERT INTO SubApplicationDetails " +
						 // 	"(sub_app_id, sub_application name, app_id, user_id) " +
		                   "VALUES(?,?,?,?)";
				insertStmt = SingletonDB.getInstance().getConnection().prepareStatement(sqlQuery);
				
				insertStmt.setInt(1, maxSubAppId);
				insertStmt.setString(2, subAppName);
				insertStmt.setInt(3, appId);
				insertStmt.setInt(4, Integer.parseInt(groupId));				
				int rowCount = insertStmt.executeUpdate();			

				if(rowCount>0)
				tranStatus = true;
			 
				} catch (SQLException ex) {
					 ex.printStackTrace();
			       } finally {
					try {
						if (null != insertStmt)
							insertStmt.close();		
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
		
		return tranStatus;
	}
	
	// To add new entry into user application table	which returns status of insertion.
	public Boolean addUserApplicationDetails(String groupId, int appId)
	{
		Boolean tranStatus=false;
		PreparedStatement insertStmt = null;
		 
		try {
			
			  String sqlQuery = "INSERT INTO UserApplicationDetails " +
						 // 	"(UserId, ApplicationId) " +
		                   "VALUES(?,?)";
				insertStmt = SingletonDB.getInstance().getConnection().prepareStatement(sqlQuery);
				
				insertStmt.setInt(1, Integer.parseInt(groupId));
				insertStmt.setInt(2, appId);
				
				int rowCount = insertStmt.executeUpdate();
		
				if(rowCount>0)
				tranStatus = true;
			 
				} catch (SQLException ex) {
					 ex.printStackTrace();
			       } finally {
					try {
						if (null != insertStmt)
							insertStmt.close();						
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

		
		return tranStatus;
	}

	/**
	 * This function will return Map with application Id as Key and Application name as value
	 * @return
	 */
	
	public HashMap<String, String> getAllAppNames() {
		Statement selectStmt = null;
		ResultSet dbAppnames = null;
		HashMap<String, String> appNamesMap = new HashMap<String, String>();
		try {
			selectStmt = SingletonDB.getInstance().getConnection()
					.createStatement();
			 dbAppnames = selectStmt
					.executeQuery("select * from ApplicationDetails");
			while (dbAppnames.next()) {
				appNamesMap.put(dbAppnames.getString(1),
						dbAppnames.getString(2));

			}

		} catch (SQLException e) {
			System.out
					.println("Error while getting all application names from database");
			e.printStackTrace();
		}finally {
			try {
				if (null != dbAppnames)
					dbAppnames.close();
				if (null != selectStmt)
					selectStmt.close();
//				if (null != SingletonDB.getInstance().getConnection())
//					SingletonDB.getInstance().getConnection().close();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}	

		return appNamesMap;
	}
	/**
	 * This function will return map with key as subappid and value as sub applicaito name.
	 * 
	 * @param appId : appid for which function will return respective sub application name
	 * @return
	 */
	public Map<String, String> getSubAppNames(String appId) {
		Map<String, String> subAppNamesMap = null;
		ResultSet dbSubAppnames = null;
		PreparedStatement prepStmt = null;
		String sql = "select * from SubApplicationDetails where ApplicationID = ?";
		try {
			prepStmt = SingletonDB.getInstance().getConnection()
					.prepareStatement(sql);
			prepStmt.setString(1, appId);
			 dbSubAppnames = prepStmt.executeQuery();
			subAppNamesMap = new HashMap<String, String>();
			while (dbSubAppnames.next()) {
				subAppNamesMap.put(dbSubAppnames.getString(1),
						dbSubAppnames.getString(2));
			}

		} catch (SQLException e) {
			System.out.println("Error while getting sub application details");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (null != dbSubAppnames)
					dbSubAppnames.close();
				if (null != prepStmt)
					prepStmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}	


		return subAppNamesMap;

	}
	
	
	/**
	 * This method will return loppath associated with the server
	 * @param serverId
	 * @return
	 */
	public List<String> getLogPathNames(int serverId) {
		List<String> logPathMap = null;
		ResultSet dbLogPathMap = null;
		PreparedStatement prepStmt = null;
		String sql = "select LogPath from LogPathDetails where ServerID = ?";
		try {
			prepStmt = SingletonDB.getInstance().getConnection()
					.prepareStatement(sql);
			prepStmt.setInt(1, serverId);
			dbLogPathMap = prepStmt.executeQuery();
			logPathMap = new ArrayList<String>();
			while (dbLogPathMap.next()) {
				logPathMap.add(dbLogPathMap.getString(1));
			}

		} catch (SQLException e) {
			System.out.println("Error while getting logpath details");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (null != dbLogPathMap)
					dbLogPathMap.close();
				if (null != prepStmt)
					prepStmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}	


		return logPathMap;

	}
	
	/*
	 * This function will return all server details.
	 */
	public ArrayList<ServerData> getServerDetails()
 {
		ArrayList<ServerData> servers = new ArrayList<ServerData>();
		ResultSet rsServerDetails = null;
		Statement selectStmt = null;
		String getServerDetailsQuery = "SELECT ServerDetails.[ServerID],ServerDetails.[ServerName], ServerDetails.[IpAddress], ServerDetails.Enviornment, "
				+ "SubApplicationDetails.[SubApplictionName],ApplicationDetails.[ApplicationName], "
				+ "ApplicationDetails.[ApplicationID], SubApplicationDetails.[SubAppID]"
				+ " FROM ApplicationDetails INNER JOIN "
				+ "(SubApplicationDetails INNER JOIN ServerDetails ON SubApplicationDetails.SubAppID = ServerDetails.SubAppID) "
				+ "ON (ApplicationDetails.ApplicationID = SubApplicationDetails.ApplicationID) AND "
				+ "(ApplicationDetails.ApplicationID = SubApplicationDetails.ApplicationID) AND "
				+ "(ApplicationDetails.ApplicationID = ServerDetails.[ApplicationID])";

		try {
			 selectStmt = SingletonDB.getInstance().getConnection()
					.createStatement();
			 rsServerDetails = selectStmt
					.executeQuery(getServerDetailsQuery);
			while (rsServerDetails.next()) {
				// System.out.println("getting query results");
				ServerData data = new ServerData();
				int serverId = rsServerDetails.getInt(1);
				data.setId(serverId);
				data.setServername(rsServerDetails.getString(2));
				data.setIpaddress(rsServerDetails.getString(3));
				data.setEnviornment(rsServerDetails.getString(4));				
				data.setSub_app_name(rsServerDetails.getString(5));
				data.setApplication_name(rsServerDetails.getString(6)); 
				int app_id = rsServerDetails.getInt(7);
				data.setApplication_id(app_id);
				data.setSub_app_id(rsServerDetails.getInt(8));

				Map<String, String> SubAppNamesMap = new HashMap<String, String>();
				SubAppNamesMap = getSubAppNames(Integer.toString(app_id));
				data.setSubapplist(SubAppNamesMap);
				System.out.println("getting logpath details for serverid " + serverId);
				List<String> LogPathNamesList =  new ArrayList<String>();
				LogPathNamesList = getLogPathNames(serverId);
				data.setLogpathlist(LogPathNamesList);
				
				/*List<String> loapathlist = new ArrayList<String>();
				loapathlist.add("logpath1");
				loapathlist.add("logpath2");
				loapathlist.add("logpath3");
				
				data.setLogpathlist(loapathlist);*/
				
				servers.add(data);
			}
		} catch (Exception e) {
			System.out.println("Error while getting all server details" + e.getMessage());
			e.printStackTrace();
		}finally {
			try {
				if (null != rsServerDetails)
					rsServerDetails.close();
				if (null != selectStmt)
					selectStmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}	

		
		return servers;
	}
	
	public int addNewServerDetails( String servername , String ipaddress , String environment, int app_id, int sub_app_id, String userId, String userPwd, String fileName)
 {
		int recordID = 0;
		PreparedStatement insertStmt = null;
		 int maxPKValue= getMaximumValue("ServerDetails", "ServerID");
		 int id = maxPKValue + 1 ;
		try {
			String sqlQuery = "INSERT INTO ServerDetails "
					+ "VALUES(?,?,?,?,?,?,?,?,?)";
			insertStmt = SingletonDB.getInstance().getConnection()
					.prepareStatement(sqlQuery);
			insertStmt.setInt(1, id);
			insertStmt.setString(2, servername);
			insertStmt.setString(3, ipaddress);
			insertStmt.setString(4, environment);
			insertStmt.setInt(5, app_id);
			insertStmt.setInt(6, sub_app_id);
			insertStmt.setString(7, userId);
			insertStmt.setString(8, userPwd);
			insertStmt.setString(9, fileName);
			
			System.out.println(id);
			System.out.println(servername+ " " +ipaddress + " "+environment);
			System.out.println(app_id + " " +sub_app_id);
			int rowCount = insertStmt.executeUpdate();
			if (rowCount > 0)
				recordID= id;
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (null != insertStmt)
					insertStmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return recordID;

	}
	
	public int deleteLogPathDetails(int serverId)
 {
		Statement delStmt = null;
		int recordcount = 0;
		if (serverId != 0) {
			String removesql = "DELETE from LogPathDetails where ServerID = " + serverId;
			
			try {
				delStmt = SingletonDB.getInstance().getConnection()
						.createStatement();
				  recordcount = delStmt.executeUpdate(removesql);
				 System.out.println(" recordcount " + recordcount);
			} catch (SQLException e) {
				System.out.println("Error while deleting logpath details");
				e.printStackTrace();
			} finally {
				try {
					if (null != delStmt)
						delStmt.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return recordcount;
	}
	
	public boolean addNewLogPathDetails(int serverID, List<String> logPaths)
	 {
			Boolean tranStatus = false;
			PreparedStatement insertStmt = null;
			 int maxPKValue= getMaximumValue("LogPathDetails", "LogPathID");
			 int id = maxPKValue ;
			try {
				String sqlQuery = "INSERT INTO LogPathDetails "
						+ "VALUES(?,?,?)";
				insertStmt = SingletonDB.getInstance().getConnection()
						.prepareStatement(sqlQuery);
			for (int k = 0; k < logPaths.size(); k++) {
				id++;
				insertStmt.setInt(1, id);
				insertStmt.setInt(2, serverID);
				System.out.println(" logPaths.get(k) " + logPaths.get(k));
				insertStmt.setString(3, logPaths.get(k));
				insertStmt.addBatch();
			}
			int[] rowCount = insertStmt.executeBatch();
			//	int rowCount = insertStmt.executeUpdate();
			//	if (rowCount > 0)
					tranStatus = true;
			} catch (SQLException ex) {
				ex.printStackTrace();
			} finally {
				try {
					if (null != insertStmt)
						insertStmt.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			return tranStatus;

		}
	
	
	public void updateServerDetails(List<ServerData> servers)throws SQLException
	{
		//Boolean tranStatus = false;
		PreparedStatement updateStmt = null;
		
		 String updateQuery =  "Update ServerDetails set ServerName = ?, IpAddress = ?, Enviornment = ?, " +
				  "ApplicationID = ?, SubAppID = ? where ServerID = ?";
		 
		 try {
			updateStmt = SingletonDB.getInstance().getConnection()
						.prepareStatement(updateQuery);
			  for (int k = 0; k < servers.size(); k++) {
				  ServerData data = servers.get(k);
				  updateStmt.setString(1, data.getServername());
				  updateStmt.setString(2, data.getIpaddress());
				  updateStmt.setString(3, data.getEnviornment());
				  updateStmt.setInt(4, data.getApplication_id());
				  updateStmt.setInt(5, data.getSub_app_id());
				  updateStmt.setInt(6, data.getId());
				  updateStmt.addBatch();
				}
			     updateStmt.executeBatch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}finally {
			try {
				if (null != updateStmt)
					updateStmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		 
		 
	}

	public void deleteServerDetails(int[] recordids)
 {
		Statement delStmt = null;
		if (recordids != null && recordids.length > 0) {
			String removesql = "DELETE from ServerDetails where ServerID = ";
			for (int k = 0; k < recordids.length; k++) {
				if (k == 0)
					removesql = removesql + recordids[k];
				else
					removesql = removesql + " or ServerID = " + recordids[k];
			}

			try {
				delStmt = SingletonDB.getInstance().getConnection()
						.createStatement();
				delStmt.executeUpdate(removesql);

			} catch (SQLException e) {
				System.out.println("Error while deleting server details");
				e.printStackTrace();
			} finally {
				try {
					if (null != delStmt)
						delStmt.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public void closeConnection()
	{
	 
		try {

			if (null != SingletonDB.getInstance().getConnection())
				SingletonDB.getInstance().getConnection().close();		
				System.out.println("Connection closed");			 
				} catch (SQLException ex) {
					 ex.printStackTrace();
			    } 		
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

	public ArrayList<ApplicationBean> getApplicationDetails()
	 {
			ArrayList<ApplicationBean> apps = new ArrayList<ApplicationBean>();
			ResultSet appDetails = null;
			Statement selectStmt = null;
			String getUserDetailsQuery = "SELECT * FROM ApplicationDetails";
			try {
				 selectStmt = SingletonDB.getInstance().getConnection()
						.createStatement();
				 appDetails = selectStmt.executeQuery(getUserDetailsQuery);
				while (appDetails.next()) {
					// System.out.println("getting query results");
					ApplicationBean appBean = new ApplicationBean();
					int appid = appDetails.getInt("ApplicationID");
					String appName = appDetails.getString("ApplicationName");
					System.out.println("appid : "+appid +" appName: "+appName);
					appBean.setApplicationid(appid);
					appBean.setApplicationname(appName);													
					apps.add(appBean);
				}
			} catch (Exception e) {
				System.out.println("Error while getting all user details");
				e.printStackTrace();
			}finally {
				try {
					if (null != appDetails)
						appDetails.close();
					if (null != selectStmt)
						selectStmt.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}	
			
			return apps;
		}

	public void deleteApplicationDetails(int[] recordids)
 {
		Statement delStmt = null;
		if (recordids != null && recordids.length > 0) {
			String app_removesql = "DELETE from ApplicationDetails where ApplicationID = ";
			String subApp_removesql = "DELETE from SubApplicationDetails where ApplicationID = ";
			String userApp_removesql = "DELETE from UserApplicationdetails where ApplicationId = ";
			for (int k = 0; k < recordids.length; k++) {
				if (k == 0){
					app_removesql = app_removesql + recordids[k];
						subApp_removesql = subApp_removesql + recordids[k];
							userApp_removesql = userApp_removesql + recordids[k];
				}else{
					app_removesql = app_removesql + " or ApplicationID = " + recordids[k];
						subApp_removesql = subApp_removesql + " or ApplicationID = " + recordids[k];
							userApp_removesql = userApp_removesql + " or ApplicationId = " + recordids[k];
				}  
			}

			      System.out.println("app_removesql : "+app_removesql);
			      	System.out.println("subApp_removesql : "+subApp_removesql);
			      		System.out.println("userApp_removesql : "+userApp_removesql);
			try {
				delStmt = SingletonDB.getInstance().getConnection()
						.createStatement();
				delStmt.executeUpdate(userApp_removesql);
				delStmt.executeUpdate(subApp_removesql);
				delStmt.executeUpdate(app_removesql);
				/*int result = delStmt.executeUpdate(userApp_removesql);
				if(result > 0)
				{
					result = delStmt.executeUpdate(subApp_removesql);
					if (result > 0) {
						delStmt.executeUpdate(app_removesql);
						commitTransaction();
					}else {
					rollBackTransaction();}
					
				}*/
				
				
				
				 
			/*	System.out.println("count " + count);
				if(delStmt.executeUpdate(userApp_removesql) > 0)
				{ System.out.println("deleted user app details");
					if (delStmt.executeUpdate(subApp_removesql) > 0)
						{System.out.println("subapp deleted");
						delStmt.executeUpdate(app_removesql);}
				} */
		
					
						

			} catch (SQLException e) {
				System.out.println("Error while deleting application details");
				e.printStackTrace();
			} finally {
				try {
					if (null != delStmt)
						delStmt.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}


	public ArrayList<SubApplicationBean> getSubApplicationDetails()
	 {
			ArrayList<SubApplicationBean> sapps = new ArrayList<SubApplicationBean>();
			ResultSet appDetails = null;
			Statement selectStmt = null;
			String getUserDetailsQuery = "SELECT * FROM SubApplicationDetails";
			try {
				 selectStmt = SingletonDB.getInstance().getConnection()
						.createStatement();
				 appDetails = selectStmt.executeQuery(getUserDetailsQuery);
				while (appDetails.next()) {
					// System.out.println("getting query results");
					SubApplicationBean sappBean = new SubApplicationBean();
					int sappid = appDetails.getInt("SubAppID");
					String sappName = appDetails.getString("SubApplictionName");
					System.out.println("sub_app_ID : "+sappid +" sub_appliction: "+sappName);
					sappBean.setSubapplicationid(sappid);
					sappBean.setSubapplicationname(sappName);													
					sapps.add(sappBean);
				}
			} catch (Exception e) {
				System.out.println("Error while getting all user details");
				e.printStackTrace();
			}finally {
				try {
					if (null != appDetails)
						appDetails.close();
					if (null != selectStmt)
						selectStmt.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}	
			
			return sapps;
		}

	public void deleteSubApplicationDetails(int[] recordids)
{
		Statement delStmt = null;
		if (recordids != null && recordids.length > 0) {
			String subApp_removesql = "DELETE from SubApplicationDetails where SubAppID = ";
			
			for (int k = 0; k < recordids.length; k++) {
				if (k == 0){
						subApp_removesql = subApp_removesql + recordids[k];
				}else{
						subApp_removesql = subApp_removesql + " or SubAppID = " + recordids[k];
				}  
			}

						System.out.println("subApp_removesql : "+subApp_removesql);
			try {
				delStmt = SingletonDB.getInstance().getConnection()
						.createStatement();
					delStmt.executeUpdate(subApp_removesql);
			} catch (SQLException e) {
				System.out.println("Error while deleting application details");
				e.printStackTrace();
			} finally {
				try {
					if (null != delStmt)
						delStmt.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	public Map<String, String> getGroupDetails()
	{
		Map<String, String> groupNamesMap = new HashMap<String, String>();
		Statement selectStmt = null;
		ResultSet rs = null;
		 
		try {
			
			 selectStmt = SingletonDB.getInstance().getConnection().createStatement();
			 rs = selectStmt.executeQuery("select * from GroupDetails");

			 while(rs.next())
			 {
				 int id = rs.getInt("GroupID");
				 String groupName=rs.getString("GroupName");
				 groupNamesMap.put(String.valueOf(id), groupName);
				 System.out.println("ID  : "+id);				
			 }
		
			} catch (SQLException ex) {
					 ex.printStackTrace();
			} finally {
					try {
						if (null != rs)
							rs.close();
						if (null != selectStmt)
							selectStmt.close();
//						if (null != SingletonDB.getInstance().getConnection())
//							SingletonDB.getInstance().getConnection().close();

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}		
		return groupNamesMap;
	}	
	
	public boolean grantAccessToUser(int userId , int groupId)
	 {
			Boolean tranStatus = false;
			PreparedStatement insertStmt = null;
			int maxPKValue = getMaximumValue("UserGroupDetails", "UserGroupID");
			int id = maxPKValue;
			id= id+1;
			try {
				String sqlQuery = "INSERT INTO UserGroupDetails " + "VALUES(?,?,?)";
				insertStmt = SingletonDB.getInstance().getConnection()
						.prepareStatement(sqlQuery);
				insertStmt.setInt(1, id);
				insertStmt.setInt(2, groupId);
				insertStmt.setInt(3, userId);

				// int[] rowCount = insertStmt.executeBatch();
				int rowCount = insertStmt.executeUpdate();
				if (rowCount > 0)
					tranStatus = true;
			} catch (SQLException ex) {
				ex.printStackTrace();
			} finally {
				try {
					if (null != insertStmt)
						insertStmt.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			return tranStatus;

		}
	
	

}

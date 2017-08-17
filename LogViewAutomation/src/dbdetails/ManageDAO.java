package dbdetails;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.dto.ServerData;
import main.java.dto.User;

import dbdetails.SingletonDB;

public class ManageDAO {
	
	// To retrieve user details from Login table which return Map object with user details	
		public Map<String, String> getUserDetails()
		{
			Map<String, String> appNamesMap = new HashMap<String, String>();
			Statement selectStmt = null;
			ResultSet rs = null;
			 
			try {
				
				 selectStmt = SingletonDB.getInstance().getConnection().createStatement();
				 rs = selectStmt.executeQuery("select * from Login");

				 while(rs.next())
				 {
					 int id = rs.getInt("ID");
					 String userName=rs.getString("username");
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
		
		public ArrayList<User> selectUserDetails()
		 {
				ArrayList<User> users = new ArrayList<User>();
				ResultSet UserDetails = null;
				Statement selectStmt = null;
				String getUserDetailsQuery = "SELECT ID,username,password,Role,availability_Status FROM Login";
				try {
					 selectStmt = SingletonDB.getInstance().getConnection()
							.createStatement();
					 UserDetails = selectStmt
							.executeQuery(getUserDetailsQuery);
					while (UserDetails.next()) {
						// System.out.println("getting query results");
						User data = new User();
						data.setId(UserDetails.getInt("id"));
						data.setUsername(UserDetails.getString("username"));
						
						data.setRole(UserDetails.getString("Role"));
						data.setStatus(UserDetails.getString("availability_Status"));
									
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
		 
		try {
			
			int maxPKValue = getMaximumValue("Login", "ID");
			maxPKValue++;
			
			  String sqlQuery = "INSERT INTO Login (ID,username,password,Role,availability_Status) VALUES (?, ?, ?, ?, ?)";
				pst = SingletonDB.getInstance().getConnection().prepareStatement(sqlQuery);
				
				  pst.setInt(1, maxPKValue);  
		          pst.setString(2,name);        
		          pst.setString(3,password);
		          pst.setString(4,role);
		          pst.setString(5,"Yes");
				
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
			
   
			updateStmt = SingletonDB.getInstance().getConnection().prepareStatement("UPDATE Login SET  password=?, Role=?, availability_Status=? WHERE ID=?");
       		
    		for (int k = 0; k < users.size(); k++) {
    			User data=users.get(k);
				  
				  updateStmt.setString(1, data.getPassword());
				  updateStmt.setString(2, data.getRole());
				  updateStmt.setString(3,data.getStatus());
				  updateStmt.setInt(4, data.getId());
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
			String sql = "DELETE from Login where id = "; 
			for (int k = 0; k < recordids.length; k++) {
				if (k == 0)
					sql = sql + recordids[k];
				else
					sql = sql + " or id = " + recordids[k];
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
			
			 	int maxAppId= getMaximumValue("Application_details", "Application_id");
			 	maxAppId ++;
			    System.out.println("maxAppId : " + maxAppId);

			    String sqlQuery = "INSERT INTO Application_details " +
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
	public Boolean addSubApplicationDetails(String subAppName, int appId, String userId)
	{
		Boolean tranStatus=false;
		PreparedStatement insertStmt = null;
		 
		try {
			
				int maxSubAppId= getMaximumValue("Sub_Application_details", "sub_app_ID");
				maxSubAppId ++;
				System.out.println("maxSubAppId : " + maxSubAppId);
				  
				  String sqlQuery = "INSERT INTO Sub_Application_details " +
						 // 	"(sub_app_id, sub_application name, app_id, user_id) " +
		                   "VALUES(?,?,?,?)";
				insertStmt = SingletonDB.getInstance().getConnection().prepareStatement(sqlQuery);
				
				insertStmt.setInt(1, maxSubAppId);
				insertStmt.setString(2, subAppName);
				insertStmt.setInt(3, appId);
				insertStmt.setInt(4, Integer.parseInt(userId));				
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
	public Boolean addUserApplicationDetails(String userId, int appId)
	{
		Boolean tranStatus=false;
		PreparedStatement insertStmt = null;
		 
		try {
			
			  String sqlQuery = "INSERT INTO User_Appliction_details " +
						 // 	"(UserId, ApplicationId) " +
		                   "VALUES(?,?)";
				insertStmt = SingletonDB.getInstance().getConnection().prepareStatement(sqlQuery);
				
				insertStmt.setInt(1, Integer.parseInt(userId));
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
					.executeQuery("select * from Application_details");
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
		String sql = "select * from Sub_Application_details where app_id = ?";
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
		String getServerDetailsQuery = "SELECT ServerDetails.[id],ServerDetails.[Server_Name], ServerDetails.[ip_address], ServerDetails.Enviornment, "
				+ "Sub_Application_details.[sub_appliction Name],Application_details.[Application Name],  "
				+ "Application_details.[application_id], Sub_Application_details.[sub_app_id]"
				+ " FROM Application_details INNER JOIN "
				+ "(Sub_Application_details INNER JOIN ServerDetails ON Sub_Application_details.sub_app_ID = ServerDetails.Sub_app_id) "
				+ ""
				+ "ON (Application_details.Application_id = Sub_Application_details.app_id) AND "
				+ "(Application_details.Application_id = Sub_Application_details.app_id) AND "
				+ "(Application_details.Application_id = ServerDetails.[Application_Id])";

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
			System.out.println("Error while getting all server details");
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
	
	public int addNewServerDetails( String servername , String ipaddress , String environment, int app_id, int sub_app_id)
 {
		int recordID = 0;
		PreparedStatement insertStmt = null;
		 int maxPKValue= getMaximumValue("ServerDetails", "id");
		 int id = maxPKValue + 1 ;
		try {
			String sqlQuery = "INSERT INTO ServerDetails "
					+ "VALUES(?,?,?,?,?,?)";
			insertStmt = SingletonDB.getInstance().getConnection()
					.prepareStatement(sqlQuery);
			insertStmt.setInt(1, id);
			insertStmt.setString(2, servername);
			insertStmt.setString(3, ipaddress);
			insertStmt.setString(4, environment);
			insertStmt.setInt(5, app_id);
			insertStmt.setInt(6, sub_app_id);
			
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
	
	
	public void updateServerDetails(List<ServerData> servers)
	{
		Boolean tranStatus = false;
		PreparedStatement updateStmt = null;
		
		 String updateQuery =  "Update ServerDetails set Server_Name = ?, ip_address = ?, Enviornment = ?, " +
				  "Application_Id = ?, Sub_app_id = ? where id = ?";
		 
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
			String removesql = "DELETE from ServerDetails where id = ";
			for (int k = 0; k < recordids.length; k++) {
				if (k == 0)
					removesql = removesql + recordids[k];
				else
					removesql = removesql + " or id = " + recordids[k];
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

	

}
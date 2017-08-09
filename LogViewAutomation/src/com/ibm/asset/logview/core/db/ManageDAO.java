package com.ibm.asset.logview.core.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibm.asset.logview.core.data.ServerData;
import com.ibm.asset.logview.core.data.User;
import com.ibm.asset.logview.core.db.DBConnection;


/**
 * <p>
 * Created on Aug 01, 2017
 * <p>
 * Description:This action will be called when user has to fetch data query from DB.
 * 
 * @author 
 */
public class ManageDAO {
	
	// To retrieve user details from Login table which return Map object with user details	
		public Map<String, String> getUserDetails()
		{
			Map<String, String> appNamesMap = new HashMap<String, String>();
			Statement selectStmt = null;
			ResultSet rs = null;
			 
			try {
				
				 selectStmt = DBConnection.getInstance().getConnection().createStatement();
				 rs = selectStmt.executeQuery("select * from Login");

				 while(rs.next())
				 {
					 int id = rs.getInt("UserID");
					 String userName=rs.getString("UserName");
					 appNamesMap.put(String.valueOf(id), userName);
					 System.out.println("UserID  : "+id);				
				 }
			
				} catch (SQLException ex) {
						 ex.printStackTrace();
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
		
		public ArrayList<User> selectUserDetails()
		 {
				ArrayList<User> users = new ArrayList<User>();
				ResultSet UserDetails = null;
				Statement selectStmt = null;
				String getUserDetailsQuery = "SELECT UserID,UserName,Password,Role,AvailabilityStatus FROM Login";
				try {
					 selectStmt = DBConnection.getInstance().getConnection()
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
		 
		try {
			
			int maxPKValue = getMaximumValue("Login", "UserID");
			maxPKValue++;
			
			  String sqlQuery = "INSERT INTO Login (UserID,UserName,Password,Role,AvailabilityStatus) VALUES (?, ?, ?, ?, ?)";
				pst = DBConnection.getInstance().getConnection().prepareStatement(sqlQuery);
				
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
			
   
			updateStmt = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Login SET  Password=?, Role=?, AvailabilityStatus=? WHERE UserID=?");
       		
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
			String sql = "DELETE from Login where UserID = "; 
			for (int k = 0; k < recordids.length; k++) {
				if (k == 0)
					sql = sql + recordids[k];
				else
					sql = sql + " or UserID = " + recordids[k];
			}
			System.out.println(sql);			
					
		try{
			int record=DBConnection.getInstance().getConnection().createStatement().executeUpdate(sql);
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
			
			 	int maxAppId= getMaximumValue("ApplicationDetails", "ApplicationID");
			 	maxAppId ++;
			    System.out.println("maxAppId : " + maxAppId);

			    String sqlQuery = "INSERT INTO ApplicationDetails " +
						 //   "(Application id, Application Name) " +
		                      "VALUES(?,?)";
				insertStmt = DBConnection.getInstance().getConnection().prepareStatement(sqlQuery);		
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
			
				int maxSubAppId= getMaximumValue("SubApplicationDetails", "SubAppID");
				maxSubAppId ++;
				System.out.println("maxSubAppId : " + maxSubAppId);
				  
				  String sqlQuery = "INSERT INTO SubApplicationDetails " +
						 // 	"(sub_app_id, sub_application name, app_id, user_id) " +
		                   "VALUES(?,?,?,?)";
				insertStmt = DBConnection.getInstance().getConnection().prepareStatement(sqlQuery);
				
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
			
			  String sqlQuery = "INSERT INTO UserApplicationDetails " +
						 // 	"(UserId, ApplicationId) " +
		                   "VALUES(?,?)";
				insertStmt = DBConnection.getInstance().getConnection().prepareStatement(sqlQuery);
				
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
			selectStmt = DBConnection.getInstance().getConnection()
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
		String sql = "select * from SubApplicationDetails where AppID = ?";
		try {
			prepStmt = DBConnection.getInstance().getConnection()
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
	/*
	 * This function will return all server details.
	 */
	public ArrayList<ServerData> getServerDetails()
 {
		ArrayList<ServerData> servers = new ArrayList<ServerData>();
		ResultSet rsServerDetails = null;
		Statement selectStmt = null;
		String getServerDetailsQuery = "SELECT ServerDetails.[ServerID],ServerDetails.[ServerName], ServerDetails.[IpAddress], ServerDetails.Enviornment, "
				+ "ServerDetails.LogPath, SubApplicationDetails.[SubApplictionName],ApplicationDetails.[ApplicationName],  "
				+ "ApplicationDetails.[ApplicationID], SubApplicationDetails.[SubAppID]"
				+ " FROM ApplicationDetails INNER JOIN "
				+ "(SubApplicationDetails INNER JOIN ServerDetails ON SubApplicationDetails.SubAppID = ServerDetails.SubAppID) "
				+ ""
				+ "ON (ApplicationDetails.ApplicationID = SubApplicationDetails.AppID) AND "
				+ "(ApplicationDetails.ApplicationID = SubApplicationDetails.AppID) AND "
				+ "(ApplicationDetails.ApplicationID = ServerDetails.[ApplicationID])";

		try {
			 selectStmt = DBConnection.getInstance().getConnection()
					.createStatement();
			 rsServerDetails = selectStmt
					.executeQuery(getServerDetailsQuery);
			while (rsServerDetails.next()) {
				// System.out.println("getting query results");
				ServerData data = new ServerData();
				data.setId(rsServerDetails.getInt(1));
				data.setServername(rsServerDetails.getString(2));
				data.setIpaddress(rsServerDetails.getString(3));
				data.setEnviornment(rsServerDetails.getString(4));
				data.setLogpath(rsServerDetails.getString(5));
				data.setSub_app_name(rsServerDetails.getString(6));
				data.setApplication_name(rsServerDetails.getString(7));
				int app_id = rsServerDetails.getInt(8);
				data.setApplication_id(app_id);
				data.setSub_app_id(rsServerDetails.getInt(9));
				data.setSub_app_id(3);

				Map<String, String> SubAppNamesMap = new HashMap<String, String>();
				SubAppNamesMap = getSubAppNames(Integer.toString(app_id));
				data.setSubapplist(SubAppNamesMap);
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
	
	public boolean addNewServerDetails( String servername , String ipaddress , String environment, int app_id, int sub_app_id, String logPath)
 {
		Boolean tranStatus = false;
		PreparedStatement insertStmt = null;
		 int maxPKValue= getMaximumValue("ServerDetails", "id");
		 int id = maxPKValue + 1 ;
		try {
			String sqlQuery = "INSERT INTO ServerDetails "
					+ "VALUES(?,?,?,?,?,?,?)";
			insertStmt = DBConnection.getInstance().getConnection()
					.prepareStatement(sqlQuery);
			insertStmt.setInt(1, id);
			insertStmt.setString(2, servername);
			insertStmt.setString(3, ipaddress);
			insertStmt.setString(4, environment);
			insertStmt.setInt(5, app_id);
			insertStmt.setInt(6, sub_app_id);
			insertStmt.setString(7, logPath);
			System.out.println(id);
			System.out.println(servername+ " " +ipaddress + " "+environment + " "+logPath);
			System.out.println(app_id + " " +sub_app_id);
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
	
	public void updateServerDetails(List<ServerData> servers)
	{
		Boolean tranStatus = false;
		PreparedStatement updateStmt = null;
		
		 String updateQuery =  "Update ServerDetails set ServerName = ?, IpAddress = ?, Enviornment = ?, " +
				  "ApplicationID = ?, SubAppID = ?, LogPath= ? where ServerID = ?";
		 
		 try {
			updateStmt = DBConnection.getInstance().getConnection()
						.prepareStatement(updateQuery);
			  for (int k = 0; k < servers.size(); k++) {
				  ServerData data = servers.get(k);
				  updateStmt.setString(1, data.getServername());
				  updateStmt.setString(2, data.getIpaddress());
				  updateStmt.setString(3, data.getEnviornment());
				  updateStmt.setInt(4, data.getApplication_id());
				  updateStmt.setInt(5, data.getSub_app_id());
				  updateStmt.setString(6, data.getLogpath());
				  updateStmt.setInt(7, data.getId());
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
			String removesql = "DELETE from ServerDetails where ServerID = ";
			for (int k = 0; k < recordids.length; k++) {
				if (k == 0)
					removesql = removesql + recordids[k];
				else
					removesql = removesql + " or ServerID = " + recordids[k];
			}

			try {
				delStmt = DBConnection.getInstance().getConnection()
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

			if (null != DBConnection.getInstance().getConnection())
				DBConnection.getInstance().getConnection().close();		
				System.out.println("Connection closed");			 
				} catch (SQLException ex) {
					 ex.printStackTrace();
			    } 		
	}	
	
	
	public void commitTransaction()
	{
	 
		try {

			if (null != DBConnection.getInstance().getConnection())			
			    DBConnection.getInstance().getConnection().commit();
				System.out.println("Transactions commited.");
			 
				} catch (SQLException ex) {
					 ex.printStackTrace();
			    }finally {
					try {
						    System.out.println("Closing connection.");
						if (null != DBConnection.getInstance().getConnection())
							DBConnection.getInstance().getConnection().close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} 		
	}	

	public void rollBackTransaction()
	{
	 
		try {

			if (null != DBConnection.getInstance().getConnection())			
			DBConnection.getInstance().getConnection().rollback();
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
		stmt = DBConnection.getInstance().getConnection().createStatement();
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

package main.java;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.java.dto.ServerData;
import main.java.dto.User;

import dbdetails.SingletonDB;
import dbdetails.ManageDAO;




public class AdminController extends HttpServlet 
{
    private static final long serialVersionUID = 1L;


    public AdminController() 
    {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	String action = request.getParameter("action");
    	
    	 if(action.equalsIgnoreCase("updateUserDetails")) 
    	 {
    		 System.out.println("In admin controller");
    				HttpSession session = request.getSession(true);
    				ManageDAO dao = new ManageDAO();
    				ArrayList<User> users = dao.selectUserDetails();

    				
    				session.setAttribute("userlist", users);
    				
    				System.out.println("addes user vars");
    				RequestDispatcher rd = request.getRequestDispatcher("/UpdateUser.jsp");
					rd.forward(request, response);

    			}
    		
    		}
    	
    	
    	
    	
//    	if(action.equalsIgnoreCase("updateUserDetails")) 
//    		prepareStatement pst=null;
//		{
//			System.out.println("Update user details");
//			
//			ResultSet UserDetails = SingletonDB.getInstance().getConnection().prepareStatement("SELECT ID,username,password,Role,availability_Status FROM Login");
//			HttpSession session = request.getSession(true);
//			session.setAttribute("launchNow", "0");
//			ArrayList<User> users = new ArrayList<User>();
//			try {
//				while(UserDetails.next())
//				{
//					System.out.println("getting query results");
//					User data = new User();
//					data.setId(UserDetails.getInt("id"));
//					data.setUsername(UserDetails.getString("username"));
//					data.setPassword(UserDetails.getString("password"));
//					data.setRole(UserDetails.getString("Role"));
//					data.setStatus(UserDetails.getString("availability_Status"));
//					
//					System.out.println("updated data");		
//					
//					users.add(data);
//				}
//				
//					session.setAttribute("userlist", users);
//					RequestDispatcher rd = request.getRequestDispatcher("/UpdateUser.jsp");
//					rd.forward(request, response);
//					
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}

        
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {

        response.setContentType("text/html");  
        PrintWriter pw = response.getWriter(); 
          
       
		
		
        
        if (request.getParameter("action").equals("Add")) {
        	 String name= request.getParameter("name");
     		String password = request.getParameter("password");
     		String role = request.getParameter("role");
     		String status= request.getParameter("status");
        	System.out.println("In admin controller");
        	ManageDAO mDAO=new ManageDAO();
        	
        	if(mDAO.addLoginDetails(name, password, role)==true){
        		System.out.println("Record has been inserted");
        		RequestDispatcher rd = getServletContext()
						.getRequestDispatcher("/AdminHome.jsp");
				rd.forward(request, response);
        	}else{
				 mDAO.rollBackTransaction();
				 System.out.println("Record insertion failed ");
				 } 					 
		 
        }
//		ConnectionDetails con = new ConnectionDetails();
//		Connection c=   con.getConnection();
//		
//		try{  
//			int maxPKValue = con.getMaximumValue("Login", "ID");
//			maxPKValue++;
//          PreparedStatement pst =c.prepareStatement("INSERT INTO Login (ID,username,password,Role,availability_Status) VALUES (?, ?, ?, ?, ?)");
//          pst.setInt(1, maxPKValue);  
//          pst.setString(2,name);        
//          pst.setString(3,password);
//          pst.setString(4,role);
//          pst.setString(5,"Yes");
//                          
//          int i = pst.executeUpdate();
//          RequestDispatcher rd = getServletContext().getRequestDispatcher("/admin.jsp");
//			rd.forward(request, response);
//          c.commit(); 
//          
//          if(i!=0){  
//            System.out.println("Record has been inserted");
//           
//          }  
//          else{  
//        	  System.out.println("Failed to insert the data");
//               }  
//          pst.close();
//        }  
//        catch (Exception e){  
//          pw.println(e);  
//        }  
//        }
        
        else if(request.getParameter("action").equals("Update")){
        	System.out.println("In admin controller");
        	if(null!= request.getParameter("update")){
        	ArrayList<User>lstUserUpdate=new ArrayList<User>();
             	String selectedRecordsForUpdate = request.getParameter("selectedids");
             	if (null != selectedRecordsForUpdate) {
        			String[] idsForUpdate = selectedRecordsForUpdate.split(",");
        			int[] recordidsForUpdate = new int[idsForUpdate.length];
        			for (int j = 0; j < idsForUpdate.length; j++) {
        				recordidsForUpdate[j] = Integer.parseInt(idsForUpdate[j]);
        			
        			User data=new User();
        			data.setId(Integer.parseInt(idsForUpdate[j]));
        			data.setPassword( request.getParameter("password"+Integer.parseInt(idsForUpdate[j])));
        			data.setRole(request.getParameter("role"+Integer.parseInt(idsForUpdate[j])));
        			data.setStatus(request.getParameter("status"+Integer.parseInt(idsForUpdate[j])));
        			lstUserUpdate.add(data);
        			}
        			}
        			ManageDAO mDAO=new ManageDAO();
        			
        	if(	mDAO.updateData(lstUserUpdate)==true){
        		RequestDispatcher rd = getServletContext()
						.getRequestDispatcher("/AdminHome.jsp");
				rd.forward(request, response);
				System.out.println("Records updated");
        	}else{
				 mDAO.rollBackTransaction();
				 System.out.println("Record insert");
				  }		 
        	}
        	
        else if (null != request.getParameter("delete")) {
				System.out.println("delete records"
							+ request.getParameter("selectedids"));

				String selectedRecords = request.getParameter("selectedids");
				ManageDAO mDAO=new ManageDAO();
					if(mDAO.deleteUserDetails(selectedRecords)==true){
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdminHome.jsp");
					rd.forward(request, response);
					
		        System.out.println("Record has been deleted");
		        		
		        	}else{
						 mDAO.rollBackTransaction();
						 System.out.println("Record delettion failed ");
						 } 			
			 }
        	}
        }}
							
        	     	
    		
//    		else if (null != request.getParameter("delete")) {
//    			String selectedRecords = request.getParameter("selectedids");
//    			System.out.println("in controller");
//				if (null != selectedRecords) {
//					String[] ids = selectedRecords.split(",");
//					int[] recordids = new int[ids.length];
//					for (int j = 0; j < ids.length; j++) {
//						recordids[j] = Integer.parseInt(ids[j]);
//					}
//					String sql = "DELETE from Login where id = "; 
//					for (int k = 0; k < recordids.length; k++) {
//						if (k == 0)
//							sql = sql + recordids[k];
//						else
//							sql = sql + " or id = " + recordids[k];
//					}
//					System.out.println(sql);
//					ConnectionDetails con = new ConnectionDetails();
//	        		Connection c=   con.getConnection();
//					try {
//						c.createStatement().executeUpdate(sql);
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
			

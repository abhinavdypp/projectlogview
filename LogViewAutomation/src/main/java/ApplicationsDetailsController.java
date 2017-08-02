package main.java;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dbdetails.ManageDAO;

/**
 * Servlet implementation class ApplicationsDetailsController
 */
public class ApplicationsDetailsController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		System.out.println("calling doget" + request.getParameter("appName"));
		ManageDAO mDAO = new ManageDAO();
		
		try {		    
			    HttpSession session = request.getSession(true);
		        session.setAttribute("usernames", mDAO.getUserDetails());
			    RequestDispatcher rd = getServletContext().getRequestDispatcher("/ApplicationDetails.jsp");
			    rd.forward(request, response);
			    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		  System.out.println("I am into inserting application details -doPost..");		

		  String appName = request.getParameter("appName");
		  String subAppName = request.getParameter("subAppName");
		  String userId=request.getParameter("userNameList");

		  System.out.println("app Name :"+appName);
		  System.out.println("subAppName :"+subAppName);
		  System.out.println("user Id :"+userId);
		  	  
		  ManageDAO mDAO = new ManageDAO();
		  
		  try 
		  {
			 int appId=mDAO.addApplicationDetails(appName);			 
 			 System.out.println("Got appId :"+appId);
 			 System.out.println(request.getParameter("addApplicationDetails done"));
			
 			 if(appId>0){

 				 if(mDAO.addSubApplicationDetails(subAppName, appId, userId)== true){
		 			 System.out.println("addSubApplicationDetails done");
 					 
		 			 if(mDAO.addUserApplicationDetails(userId, appId )==true){
		 				 System.out.println("addUserApplicationDetails done");
		 				 System.out.println("Record insertion completed on APP, SUB APP, User APP tables!!!");		 				
		 				 mDAO.commitTransaction();
		 				 mDAO.closeConnection();
		 			 }else{
		 				 mDAO.rollBackTransaction();
		 				 System.out.println("Record insertion failed on User APP tables!!!");} 					 
 				 }else{
 					 mDAO.rollBackTransaction();
 					 System.out.println("Record insertion failed on SUB APP, User APP tables!!!"); } 			 
 			 }else{
			    mDAO.rollBackTransaction();
	 				System.out.println("Record insertion failed on APP, SUB APP, User APP tables!!!"); 				 
 			 }	
			 
 			 RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdminHome.jsp");
			 rd.forward(request, response);		 				 			 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			
		}		
	
}

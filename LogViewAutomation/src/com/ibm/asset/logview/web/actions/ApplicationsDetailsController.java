package com.ibm.asset.logview.web.actions;

import java.io.IOException;
import java.util.ArrayList;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.asset.logview.core.data.ApplicationBean;
import com.ibm.asset.logview.core.data.SubApplicationBean;
import com.ibm.asset.logview.core.db.ManageDAO;


/**
 * Servlet implementation class ApplicationsDetailsController
 */
public class ApplicationsDetailsController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
	System.out.println("calling doget" + request.getParameter("appName"));
	ManageDAO mDAO = new ManageDAO();
		
    String action = request.getParameter("action");
    
    System.out.println("action  +"+action);
    	
   	 if(action.equalsIgnoreCase("deleteAppDetails")) 
   	 {

 	    System.out.println("inside get app deleteAppDetails summary"); 		 
   		 
   		        System.out.println("In deleteAppDetails");
   				HttpSession session = request.getSession(true);
   				ManageDAO dao = new ManageDAO();
   				ArrayList<ApplicationBean> apps = dao.getApplicationDetails();
   				
  				System.out.println("apps list size : " +apps.size());

   				session.setAttribute("appslist", apps);
      			RequestDispatcher rd = request.getRequestDispatcher("/ApplicationSummary.jsp");
				rd.forward(request, response);

   			}else if(action.equalsIgnoreCase("deleteSubAppDetails")){

   		        System.out.println("In deleteSubAppDetails");
   				HttpSession session = request.getSession(true);
   				ManageDAO dao = new ManageDAO();
   				ArrayList<SubApplicationBean> sapps = dao.getSubApplicationDetails();
   				
  				System.out.println("sapps list size : " +sapps.size());

   				session.setAttribute("sappslist", sapps);
      			RequestDispatcher rd = request.getRequestDispatcher("/SubApplicationSummary.jsp");
				rd.forward(request, response);
   				
   			}
   			else{
		
		try {	
			    System.out.println("inside get user details");	    
			    HttpSession session = request.getSession(true);
		        session.setAttribute("groupnames", mDAO.getGroupDetails());
			   // RequestDispatcher rd = getServletContext().getRequestDispatcher("/ApplicationDetails.jsp");
			   // rd.forward(request, response);
			    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
   	}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		  System.out.println("I am into inserting application details -doPost..");		

			String action = request.getParameter("action");
			System.out.println("action : "+action);

	try 
	 {
			if (action!= null &&  action.equalsIgnoreCase("deleteApp")) {
				
				String selectedRecords = request.getParameter("selectedids");
				if (null != selectedRecords) {
					String[] ids = selectedRecords.split(",");
					int[] recordids = new int[ids.length];
					for (int j = 0; j < ids.length; j++) {
						recordids[j] = Integer.parseInt(ids[j]);
					}

					ManageDAO dao = new ManageDAO();
					dao.deleteApplicationDetails(recordids);
				}
			//	RequestDispatcher rd = getServletContext()
				//		.getRequestDispatcher("/AdminHome.jsp");
			//	rd.forward(request, response);
			  
		    }else if(action!= null &&  action.equalsIgnoreCase("deletesubApp")){

		    	
				String selectedRecords = request.getParameter("selectedids");
				if (null != selectedRecords) {

					String[] ids = selectedRecords.split(",");
					int[] recordids = new int[ids.length];
					for (int j = 0; j < ids.length; j++) {
						recordids[j] = Integer.parseInt(ids[j]);
					}

					ManageDAO dao = new ManageDAO();
					dao.deleteSubApplicationDetails(recordids);
				}
			//	RequestDispatcher rd = getServletContext()
			//			.getRequestDispatcher("/AdminHome.jsp");
			//	rd.forward(request, response);
		    	
		    }else if(action!= null &&  action.equalsIgnoreCase("Add")){

			  String appName = request.getParameter("appName");
			  String subAppName = request.getParameter("subAppName");
			  String groupId=request.getParameter("grpNameList");

			  System.out.println("app Name :"+appName);
			  System.out.println("subAppName :"+subAppName);
			  System.out.println("group Id :"+groupId);
			  	  
			  ManageDAO mDAO = new ManageDAO();
			  
				 int appId=mDAO.addApplicationDetails(appName);			 
	 			 System.out.println("Got appId :"+appId);
	 			 System.out.println(request.getParameter("addApplicationDetails done"));
				
	 			 if(appId>0){

	 				 if(mDAO.addSubApplicationDetails(subAppName, appId, groupId)== true){
			 			 System.out.println("addSubApplicationDetails done");
	 					 
			 			 if(mDAO.addUserApplicationDetails(groupId, appId )==true){
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
				 
	 		//	 RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdminHome.jsp");
			//	 rd.forward(request, response);			  			  
		  }

			
	 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
					
  }
	
}

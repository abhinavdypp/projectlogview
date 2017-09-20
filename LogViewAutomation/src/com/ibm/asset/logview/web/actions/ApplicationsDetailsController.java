package com.ibm.asset.logview.web.actions;

import java.io.IOException;
import java.util.ArrayList;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ibm.asset.logview.core.data.ApplicationBean;
import com.ibm.asset.logview.core.data.SubApplicationBean;
import com.ibm.asset.logview.core.db.ManageDAO;


/**
 * Servlet implementation class ApplicationsDetailsController
 */
public class ApplicationsDetailsController extends HttpServlet {
	static Logger log = Logger.getLogger(ApplicationsDetailsController.class);
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		log.debug("calling doget" + request.getParameter("appName"));
	ManageDAO mDAO = new ManageDAO();
		
    String action = request.getParameter("action");
    
    log.debug("action  +"+action);
    	
   	 if(action.equalsIgnoreCase("deleteAppDetails")) 
   	 {

   		log.debug("inside get app deleteAppDetails summary"); 		 
   		 
   		log.debug("In deleteAppDetails");
   				HttpSession session = request.getSession(true);
   				ManageDAO dao = new ManageDAO();
   				ArrayList<ApplicationBean> apps = dao.getApplicationDetails();
   				
   				log.debug("apps list size : " +apps.size());

   				session.setAttribute("appslist", apps);
      			RequestDispatcher rd = request.getRequestDispatcher("/ApplicationSummary.jsp");
				rd.forward(request, response);

   			}else if(action.equalsIgnoreCase("deleteSubAppDetails")){

   				log.debug("In deleteSubAppDetails");
   				HttpSession session = request.getSession(true);
   				ManageDAO dao = new ManageDAO();
   				ArrayList<SubApplicationBean> sapps = dao.getSubApplicationDetails();
   				
   				log.debug("sapps list size : " +sapps.size());

   				session.setAttribute("sappslist", sapps);
      			RequestDispatcher rd = request.getRequestDispatcher("/SubApplicationSummary.jsp");
				rd.forward(request, response);
   				
   			}
   			else if(action.equalsIgnoreCase("addSubAppDetails")){

   		   		try {	

   		   					log.debug("doGet > " + "Inside addSubAppDetails > getting Application and User details"); 	    
   		   				    
   		   				    HttpSession session = request.getSession(true);
   		   			        session.setAttribute("appnames", mDAO.getAppDetails());   				    
   		   			        session.setAttribute("groupnames", mDAO.getGroupDetails());
   		   				    RequestDispatcher rd = getServletContext().getRequestDispatcher("/SubApplicationDetails.jsp");
   		   				    rd.forward(request, response);
   		   				    
   		   			} catch (Exception e) {
   		   				// TODO Auto-generated catch block
   		   				e.printStackTrace();
   		   			}	
   		   }else{
		
		try {	
			log.debug("inside get user details");	    
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
		
		log.debug("I am into inserting application details -doPost..");		

			String action = request.getParameter("action");
			log.debug("action : "+action);

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
		    	
		    }else if(action!= null &&  action.equalsIgnoreCase("addSubApp")){
		    	
				  log.trace("doPost > Inside addSubApp action");

				  int appId = Integer.parseInt(request.getParameter("appNameList"));
				  String subAppName = request.getParameter("subAppName");
				  String groupId=request.getParameter("grpNameList");

				  log.trace("doPost > app Id : "+appId);
				  log.trace("doPost > subAppName : "+subAppName);
				  log.trace("doPost > groupId : "+groupId);
				  	  
				  ManageDAO mDAO = new ManageDAO();
				  
					 Boolean addSubApp=mDAO.addSubApplicationDetails(subAppName, appId, groupId);			 
  				     log.trace("doPost > appSubApp addition Status : "+addSubApp);
					
//		 			 RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdminHome.jsp");
//					 rd.forward(request, response);			  			  
			  		    			    	
		    }else if(action!= null &&  action.equalsIgnoreCase("Add")){

			  String appName = request.getParameter("appName");
			  String subAppName = request.getParameter("subAppName");
			  String groupId=request.getParameter("grpNameList");

			  log.debug("app Name :"+appName);
			  log.debug("subAppName :"+subAppName);
			  log.debug("group Id :"+groupId);
			  	  
			  ManageDAO mDAO = new ManageDAO();
			  
				 int appId=mDAO.addApplicationDetails(appName);			 
				 log.debug("Got appId :"+appId);
				 log.debug(request.getParameter("addApplicationDetails done"));
				
	 			 if(appId>0){

	 				 if(mDAO.addSubApplicationDetails(subAppName, appId, groupId)== true){
	 					log.debug("addSubApplicationDetails done");
	 					 
			 			 if(mDAO.addUserApplicationDetails(groupId, appId )==true){
			 				log.debug("addUserApplicationDetails done");
			 				log.debug("Record insertion completed on APP, SUB APP, User APP tables!!!");		 				
			 				 mDAO.commitTransaction();
			 				 mDAO.closeConnection();
			 			 }else{
			 				 mDAO.rollBackTransaction();
			 				log.debug("Record insertion failed on User APP tables!!!");} 					 
	 				 }else{
	 					 mDAO.rollBackTransaction();
	 					log.debug("Record insertion failed on SUB APP, User APP tables!!!"); } 			 
	 			 }else{
				    mDAO.rollBackTransaction();
				    log.debug("Record insertion failed on APP, SUB APP, User APP tables!!!"); 				 
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

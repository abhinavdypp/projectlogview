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

import com.ibm.asset.logview.core.data.User;
import com.ibm.asset.logview.core.db.ManageDAO;





public class AdminController extends HttpServlet 
{
    private static final long serialVersionUID = 1L;
    static Logger log = Logger.getLogger(AdminController.class);

    public AdminController() 
    {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	String action = request.getParameter("action");
    	
    	 if(action.equalsIgnoreCase("updateUserDetails")) 
    	 {
    		 log.debug("In admin controller");
    				HttpSession session = request.getSession(true);
    				ManageDAO dao = new ManageDAO();
    				ArrayList<User> users = dao.selectUserDetails();

    				
    				session.setAttribute("userlist", users);
    				
    				log.debug("addes user vars");
    				RequestDispatcher rd = request.getRequestDispatcher("/UpdateUser.jsp");
					rd.forward(request, response);

    			}
    		
    		}


        
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {

        response.setContentType("text/html");  
        try{
        	String action = request.getParameter("action");
        	log.debug("AdminController :: " + action);
        if (action!= null && action.equals("Add")) {
        	 String name= request.getParameter("name");
     		String password = request.getParameter("password");
     		String role = request.getParameter("role");
     		//String status= request.getParameter("status");
     		log.debug("In admin controller");
        	ManageDAO mDAO=new ManageDAO();
        	
        	if(mDAO.addLoginDetails(name, password, role)==true){
        		log.debug("Record has been inserted");
        		//RequestDispatcher rd = getServletContext()
				//		.getRequestDispatcher("/AdminHome.jsp");
				//rd.forward(request, response);
        	}else{
				 mDAO.rollBackTransaction();
				 log.debug("Record insertion failed ");
				 } 					 
		 
        }

        
        else if(request.getParameter("action").equals("Update")){
        	log.debug("In admin controller");
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
        		//RequestDispatcher rd = getServletContext()
				//		.getRequestDispatcher("/AdminHome.jsp");
				//rd.forward(request, response);
        		log.debug("Records updated");
        	}else{
				 mDAO.rollBackTransaction();
				 log.debug("Record insert");
				  }		 
        	}
        	
				else if (null != request.getParameter("delete")) {
					log.debug("delete records"
							+ request.getParameter("selectedids"));

					String selectedRecords = request
							.getParameter("selectedids");
					ManageDAO mDAO = new ManageDAO();
					if (mDAO.deleteUserDetails(selectedRecords) == true) {
						// RequestDispatcher rd =
						// getServletContext().getRequestDispatcher("/AdminHome.jsp");
						// rd.forward(request, response);

						log.debug("Record has been deleted");

					} else {
						mDAO.rollBackTransaction();
						log.debug("Record delettion failed ");
					}
				}
        	}
        }
	catch(Exception e )
	{
		log.debug("Admin Controller :: Exception occured " + e.getMessage());
		response.setStatus(400);
		 response.getWriter().write(e.getMessage());
	}
}
}//end of class			
        	     	
  			

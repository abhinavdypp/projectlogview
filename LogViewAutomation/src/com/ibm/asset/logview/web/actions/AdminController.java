package com.ibm.asset.logview.web.actions;

import java.io.IOException;


import java.util.ArrayList;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.asset.logview.core.data.User;
import com.ibm.asset.logview.core.db.ManageDAO;





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


        
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {

        response.setContentType("text/html");  
        try{
        	String action = request.getParameter("action");
        	System.out.println("AdminController :: " + action);
        if (action!= null && action.equals("Add")) {
        	 String name= request.getParameter("name");
     		String password = request.getParameter("password");
     		String role = request.getParameter("role");
     		//String status= request.getParameter("status");
        	System.out.println("In admin controller");
        	ManageDAO mDAO=new ManageDAO();
        	
        	if(mDAO.addLoginDetails(name, password, role)==true){
        		System.out.println("Record has been inserted");
        		//RequestDispatcher rd = getServletContext()
				//		.getRequestDispatcher("/AdminHome.jsp");
				//rd.forward(request, response);
        	}else{
				 mDAO.rollBackTransaction();
				 System.out.println("Record insertion failed ");
				 } 					 
		 
        }

        
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
        		//RequestDispatcher rd = getServletContext()
				//		.getRequestDispatcher("/AdminHome.jsp");
				//rd.forward(request, response);
				System.out.println("Records updated");
        	}else{
				 mDAO.rollBackTransaction();
				 System.out.println("Record insert");
				  }		 
        	}
        	
				else if (null != request.getParameter("delete")) {
					System.out.println("delete records"
							+ request.getParameter("selectedids"));

					String selectedRecords = request
							.getParameter("selectedids");
					ManageDAO mDAO = new ManageDAO();
					if (mDAO.deleteUserDetails(selectedRecords) == true) {
						// RequestDispatcher rd =
						// getServletContext().getRequestDispatcher("/AdminHome.jsp");
						// rd.forward(request, response);

						System.out.println("Record has been deleted");

					} else {
						mDAO.rollBackTransaction();
						System.out.println("Record delettion failed ");
					}
				}
        	}
        }
	catch(Exception e )
	{
		System.out.println("Admin Controller :: Exception occured " + e.getMessage());
		response.setStatus(400);
		 response.getWriter().write(e.getMessage());
	}
}
}//end of class			
        	     	
  			

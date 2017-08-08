package com.ibm.asset.logview.web.actions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ibm.asset.logview.core.db.DBConnection;


/**
 * <p>
 * Created on Aug 01, 2017
 * <p>
 * Description:This action will be called when user work on Application login .
 * 
 * @author 
 */
public class UserLoginController extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
		System.out.println("calling get");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		
		String uname=request.getParameter("uname");
		String pass=request.getParameter("pass");
					
		 Connection c = DBConnection.getInstance().getConnection();

		
		 String dbpwd = null;
			String dbrole=null;
			String availability=null;
			if(null!= c)
			{
				try {
					java.sql.PreparedStatement st =c.prepareStatement("select * from login where username = ? ");
					st.setString(1, uname);
					ResultSet rs = st.executeQuery();
					if (rs.next()) {
						 dbpwd = rs.getString(3);
						 dbrole=rs.getString(4);
						 availability=rs.getString(5);
	}
					if(pass.toString().equals(dbpwd.toString()))
					{
						if((availability.toString()).equalsIgnoreCase("yes"))
						{
					
						if((dbrole.toString()).equalsIgnoreCase("admin"))
						{
						response.sendRedirect("AdminHome.jsp");
						System.out.println("password matached");
						}
						else{
							response.sendRedirect("User.jsp");
						}
						}
						else{
							response.sendRedirect("BlockedUser.jsp");
						}
					
					}
					else
					{
						response.sendRedirect("Login_failed.jsp");
						
						
					}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}		
	
	}
			
	

}

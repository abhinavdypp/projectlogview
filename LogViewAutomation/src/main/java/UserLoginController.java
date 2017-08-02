package main.java;

import java.beans.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import dbdetails.SingletonDB;

public class UserLoginController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
		System.out.println("calling get");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		
		String uname=request.getParameter("uname");
		String pass=request.getParameter("pass");
					
		 Connection c = SingletonDB.getInstance().getConnection();

		
		 String dbpwd = null;
			String dbrole=null;
			String availability=null;
			String CheckValid=null;
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
////							CheckValid="Blocked";
////							HttpSession session=response.getSession();
//							session.setAttribute("Validation",CheckValid);
							response.sendRedirect("BlockedUser.jsp");
				
//							PrintWriter out=response.getWriter();
//							out.println("blocked user");
						}
					
					}
					else
					{
//						CheckValid="Invalid";
//						HttpSession session=request.getSession();
//						session.setAttribute("Validation",CheckValid);
						response.sendRedirect("Login_failed.jsp");
						
						
					}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}		
	
	}
			
	

}

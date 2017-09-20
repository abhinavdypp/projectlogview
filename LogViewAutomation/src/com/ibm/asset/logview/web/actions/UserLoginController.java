package com.ibm.asset.logview.web.actions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ibm.asset.logview.core.db.ManageDAO;
import com.ibm.asset.logview.core.db.SingletonDB;




public class UserLoginController extends HttpServlet {
	static Logger log = Logger.getLogger(UserLoginController.class);
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
		log.debug("calling get");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		
		String uname=request.getParameter("uname");
		String pass=request.getParameter("pass");
					
		 Connection c = SingletonDB.getInstance().getConnection();
		
		 String dbpwd = null;
			String dbrole=null;
			String availability=null;
			if(null!= c)
			{
				try {
					java.sql.PreparedStatement st =c.prepareStatement("select * from UserDetails where UserName = ? ");
					st.setString(1, uname);
					ResultSet rs = st.executeQuery();
					if (rs.next()) {
						 dbpwd = rs.getString(3);
						 dbrole=rs.getString(4);
						 availability=rs.getString(5);
	}
					if(pass.toString().equals(dbpwd.toString()))
					{
						 HttpSession session=request.getSession();
						 session.setAttribute("username",uname);
						
						if((availability.toString()).equalsIgnoreCase("yes"))
						{
					
						if((dbrole.toString()).equalsIgnoreCase("admin"))
						{
							// Sprint 3 Last Login
							Cookie loginCookie = new Cookie("user",uname);
						response.addCookie(loginCookie);
						response.sendRedirect("AdminHome.jsp");
						log.debug("password matached");
						
						//Last Login code
						ManageDAO dao = new ManageDAO();
						if(dao.LastLogin(uname)==true){
							log.debug("Last login updated");
				    		
						}
						}
						else{
						// Sprint 2 Demo	
							Cookie loginCookie = new Cookie("user",uname);
							response.addCookie(loginCookie);
							response.sendRedirect("User.jsp");
							//Last Login code
							ManageDAO dao = new ManageDAO();
							if(dao.LastLogin(uname)==true){
								log.debug("Last login updated");
						}
						}
						}
						else{
//							CheckValid="Blocked";
//							session.setAttribute("Validation",CheckValid);
//							RequestDispatcher rd = getServletContext().getRequestDispatcher("/UserLoginView.jsp");
//							rd.include(request, response);
//							PrintWriter out=response.getWriter();
//							out.println("<font color=red>blocked user.</font>");

							response.sendRedirect("BlockedUser.jsp");
							
							
						}
					
					}
					else
					{
//						CheckValid="Invalid";
//						
//						session.setAttribute("Validation",CheckValid);
//						RequestDispatcher rd = getServletContext().getRequestDispatcher("/UserLoginView.jsp");
//						PrintWriter out= response.getWriter();
//						out.println("<font color=red>Either user name or password is wrong.</font>");
//						rd.include(request, response);
						response.sendRedirect("Login_failed.jsp");
						
						
					}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}		
	}
	}
			
	



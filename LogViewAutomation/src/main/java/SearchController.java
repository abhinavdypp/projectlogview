package main.java;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchController  extends HttpServlet  {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
		System.out.println("calling get");
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/SearchCriteria.jsp");
		rd.forward(request, response);
	}
	
	
	

}

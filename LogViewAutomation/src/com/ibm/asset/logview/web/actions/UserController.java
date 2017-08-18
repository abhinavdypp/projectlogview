package com.ibm.asset.logview.web.actions;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.ibm.asset.logview.core.db.UserDAO;

import net.sf.json.JSONObject;


@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID=1L;
	
	public  UserController(){
		
	}
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		System.out.println("calling doget" + request.getParameter("action"));
		
		UserDAO uDAO = new UserDAO();

		
		
		String action = request.getParameter("action");
		
		 
		if (action.equalsIgnoreCase("getlogdetails")) {
			HttpSession session=request.getSession(true);
			String uname=session.getAttribute("username").toString();
			System.out.println(uname);
			Map<String, String> appNamesMap = new HashMap<String, String>();
			appNamesMap = uDAO.getUserApplications(uname);
			
			session.setAttribute("appnames", appNamesMap);
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/Checklog.jsp");
			rd.forward(request, response);
			
		}
			
		else if(action.equalsIgnoreCase("getSubApps")) 
		{
			if (null != request.getParameter("appName").toString()) {
				System.out.println("getting sub app details");
				
				HttpSession session=request.getSession(true);
			    String uname=session.getAttribute("username").toString();
				System.out.println(uname);
				
				JSONObject responseDetailsJson = new JSONObject();
				
				String appId=request.getParameter("appName").toString();
				System.out.println(appId);
						
				responseDetailsJson.accumulateAll(uDAO.getUsersubApplications(uname,appId));
				response.setContentType("application/json");
				response.getWriter().write(responseDetailsJson.toString());
			}
		}
		else if(action.equalsIgnoreCase("getappEnvironment")){ 
			
		
			if (null == request.getParameter("subAppname")){
				System.out.println("getting environment");
				
				
				String appId=request.getParameter("appName").toString();
				System.out.println("*** spp Id " + appId);
				Gson gson = new Gson();
				ArrayList<String> envt=new ArrayList<String>();
				envt=uDAO.getappEnvirnment(appId);
				JsonElement jsonElement = gson.toJsonTree(envt);
				System.out.println(jsonElement.isJsonArray());
				String json = gson.toJson(envt);
				System.out.println(json);
								
				response.setContentType("application/json");
				response.getWriter().write(json.toString());
				
			}
		}
		
		else if(action.equalsIgnoreCase("getsubappEnvironment")){ 
			
		
			if (null != request.getParameter("subAppname")){
				System.out.println("subapp is not null");
				
				String subappId=request.getParameter("subAppname").toString();
				System.out.println("subapplid" + subappId);
			Gson gson = new Gson();
			ArrayList<String> envt=new ArrayList<String>();
			envt=uDAO.getsubappEnvirnment(subappId);
			JsonElement jsonElement = gson.toJsonTree(envt);
			System.out.println(jsonElement.isJsonArray());
			String json = gson.toJson(envt);
			System.out.println(json);
					
	
			response.setContentType("application/json");
			response.getWriter().write(json.toString());
			
		
			}
		}
	}	
			
		
	
		protected void doPost(HttpServletRequest request,
				HttpServletResponse response) throws IOException, ServletException {
			
//			String action = request.getParameter("action");
			UserDAO uDAO = new UserDAO();

				if (request.getParameter("action").equals("Search")) {
					System.out.println("In user controller");
			
			
					//HttpSession session=request.getSession(true);			
					String environment = request.getParameter("enviormentList");
					String app_id = request.getParameter("appNameList");
					String sub_app_id = request.getParameter("subAppNameList");
					System.out.println(environment);
					System.out.println(app_id );
					System.out.println(sub_app_id);
					
			        	
					ArrayList<String> logs=new ArrayList<String>();
					logs =uDAO.getLogpath(environment,Integer.parseInt(app_id),sub_app_id);
//					Gson gson= new Gson();
//					JsonElement element= gson.toJsonTree(logs, new TypeToken<List<String>>(){}.getType());
//					
//					JsonArray jsonArray=element.getAsJsonArray();
//					response.setContentType("application/json");
//					response.getWriter().print(jsonArray);
					System.out.println("hi 5");
//					
					
					
					for(int i=0;i<logs.size();i++){
					    System.out.println(logs.get(i));
					} 
//			

			}
		}
}
	
		
	
	
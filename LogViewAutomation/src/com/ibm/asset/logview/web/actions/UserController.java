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

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.ibm.asset.logview.core.data.ServerData;
import com.ibm.asset.logview.core.db.ManageDAO;
import com.ibm.asset.logview.core.db.UserDAO;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;



public class UserController extends HttpServlet {
	private static final long serialVersionUID=1L;
	static Logger log = Logger.getLogger(UserController.class);
	public  UserController(){
		
	}
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		
		log.info("doget method called for" + request.getParameter("action"));
		
		UserDAO uDAO = new UserDAO();
		ManageDAO manageDAO = new ManageDAO();	
		
		String action = request.getParameter("action");
		
		 
		if (action!= null && action.equalsIgnoreCase("getlogdetails")) {
			HttpSession session=request.getSession(true);
			String uname=session.getAttribute("username").toString();
			log.debug(uname);
			Map<String, String> appNamesMap = new HashMap<String, String>();
			appNamesMap = uDAO.getUserApplications(uname);
			
			session.setAttribute("appnames", appNamesMap);
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/Checklog.jsp");
			rd.forward(request, response);
			
		}
			
		else if(action!= null && action.equalsIgnoreCase("getSubApps")) 
		{
			if (null != request.getParameter("appName").toString()) {
				log.debug("getting sub app details");
				
				HttpSession session=request.getSession(true);
			    String uname=session.getAttribute("username").toString();
				
				
				JSONObject responseDetailsJson = new JSONObject();
				
				String appId=request.getParameter("appName").toString();
				log.debug("Get subapp for appid " + appId);
						
				responseDetailsJson.accumulateAll(uDAO.getUsersubApplications(uname,appId));
				response.setContentType("application/json");
				response.getWriter().write(responseDetailsJson.toString());
			}
		}
		else if(action!= null && action.equalsIgnoreCase("getappEnvironment")){ 
			
		
			if (null == request.getParameter("subAppname")){
				
				String appId=request.getParameter("appName").toString();
				Gson gson = new Gson();
				ArrayList<String> envt=new ArrayList<String>();
				envt=uDAO.getappEnvirnment(appId);
				String json = gson.toJson(envt);
				response.setContentType("application/json");
				response.getWriter().write(json.toString());
				
			}
		}
		
		else if(action!= null && action.equalsIgnoreCase("getsubappEnvironment")){ 
			
		
			if (null != request.getParameter("subAppname")){
				log.debug("subapp is not null");
				
				String subappId=request.getParameter("subAppname").toString();
				log.debug("subapplid" + subappId);
			Gson gson = new Gson();
			ArrayList<String> envt=new ArrayList<String>();
			envt=uDAO.getsubappEnvirnment(subappId);
			JsonElement jsonElement = gson.toJsonTree(envt);
			log.debug(jsonElement.isJsonArray());
			String json = gson.toJson(envt);
			log.debug(json);
					
	
			response.setContentType("application/json");
			response.getWriter().write(json.toString());
			
		
			}
		}
		
		else if(action!= null && action.equalsIgnoreCase("getServerNames")){ 
			
			String subappId = request.getParameter("subAppname");
			String appId = request.getParameter("appName");
			String environment = request.getParameter("Environment");
			
			log.debug("subappId :: " + subappId + " appId :: " + appId + " environment :: " + environment);
			
			if (null != subappId && null!= appId && null!= environment){
				log.debug("subapp is not null");
				Gson gson = new Gson();
				Map<String,String> serverNameList=new HashMap<String, String>();
					serverNameList=uDAO.getServerNames(Integer.parseInt(appId), Integer.parseInt(subappId), environment);
			JsonElement jsonElement = gson.toJsonTree(serverNameList);
			log.debug(jsonElement.isJsonArray());
			String json = gson.toJson(serverNameList);
			log.debug(json);
					
	
			response.setContentType("application/json");
			response.getWriter().write(json.toString());
			
		
			}
		}
		
		else if(action!= null && action.equalsIgnoreCase("getLogPaths")){ 
			
			String serverId = request.getParameter("serverId");
			
			
			log.debug( "serverId :: " + serverId );
			
			if (null != serverId ){
				log.debug("subapp is not null");
				Gson gson = new Gson();
				ArrayList<String> logPathList=new ArrayList<String>();
				logPathList=uDAO.getLogPaths(Integer.parseInt(serverId));
			JsonElement jsonElement = gson.toJsonTree(logPathList);
			log.debug(jsonElement.isJsonArray());
			String json = gson.toJson(logPathList);
			log.debug(json);
					
	
			response.setContentType("application/json");
			response.getWriter().write(json.toString());
			
		
			}
		}
		
		else if(action != null && action.equalsIgnoreCase("grantAccess"))
		{	log.debug("Enter grant access");
			HttpSession session = request.getSession(true);
			
			Map<String, String> userNameMap = manageDAO.getUserDetails();
			session.setAttribute("usernames", userNameMap);

			Map<String, String> groupNameMap = manageDAO.getGroupDetails();
			session.setAttribute("groupnames", groupNameMap);
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/GrantAccess.jsp");
			rd.forward(request, response);
		}
	}	
			
		
	
		protected void doPost(HttpServletRequest request,
				HttpServletResponse response) throws IOException, ServletException {
			
//			String action = request.getParameter("action");
			//UserDAO uDAO = new UserDAO();
			ManageDAO mDao = new ManageDAO();
			String action = request.getParameter("action");
				if (action!=null && action.equals("Search")) {
			
			
					//HttpSession session=request.getSession(true);			
					String environment = request.getParameter("enviormentList");
					String app_id = request.getParameter("appNameList");
					String sub_app_id = request.getParameter("subAppNameList");
					String envName = request.getParameter("environmentList");
					String serverName = request.getParameter("serverNameList");
					String logpath = request.getParameter("logPathList");
					
					log.debug("EnvName: " + environment + "appId: " + app_id + "subappid: " +sub_app_id + " env : " +  envName + " server name "+ serverName + " logpath "  +logpath);
					
			        	
					//ArrayList<String> logs=new ArrayList<String>();
					//logs =uDAO.getLogpath(environment,Integer.parseInt(app_id),sub_app_id);
					ServerData serverData = new ServerData();
					//serverData.setLogpathlist(logs);
					serverData.setServername(serverName);
					serverData.setEnviornment(envName);
					serverData.setApplication_id(Integer.parseInt(app_id));
					serverData.setLogpath(logpath);
					serverData.setSub_app_id(Integer.parseInt(sub_app_id));
					request.setAttribute("serverData", serverData);
					
					RequestDispatcher dispatcher = request.getRequestDispatcher("/LogSearchCriteriaController");
					dispatcher.forward(request,response);
				
//					Gson gson= new Gson();
//					JsonElement element= gson.toJsonTree(logs, new TypeToken<List<String>>(){}.getType());
//					
//					JsonArray jsonArray=element.getAsJsonArray();
//					response.setContentType("application/json");
//					response.getWriter().print(jsonArray);
//					
					
			

			}
				
				if(action != null && action.equalsIgnoreCase("grantAccess"))
				 {
							if (null != request.getParameter("userNameList")
									&& null != request.getParameter("groupNameList")) {
								int userId = Integer.parseInt(request
										.getParameter("userNameList"));
								int groupId = Integer.parseInt(request
										.getParameter("groupNameList"));
				
								mDao.grantAccessToUser(userId, groupId);
							}
				
						}
				
		}
}
	
		
	
	
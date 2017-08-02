package main.java;

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



import main.java.dto.ServerData;

import net.sf.json.JSONObject;


import dbdetails.ManageDAO;

public class ServerDetailsController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		System.out.println("calling doget" + request.getParameter("appName"));
		ManageDAO mDAO = new ManageDAO();

		String action = request.getParameter("action");

		if (action.equalsIgnoreCase("addNewServerdetails")) {
			Map<String, String> appNamesMap = new HashMap<String, String>();
			appNamesMap = mDAO.getAllAppNames();
			HttpSession session = request.getSession(true);
			session.setAttribute("appnames", appNamesMap);
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/AddServerDetails.jsp");
			rd.forward(request, response);
		}
		else if(action.equalsIgnoreCase("getSubApps")) 
		{
			if (null != request.getParameter("appName").toString()) {
				System.out.println("getting sub app details");
				String json = null;

				JSONObject responseDetailsJson = new JSONObject();
				responseDetailsJson.accumulateAll(mDAO.getSubAppNames(request
						.getParameter("appName").toString()));
				response.setContentType("application/json");
				response.getWriter().write(responseDetailsJson.toString());
			}
		}
		else if(action.equalsIgnoreCase("updateServerDetails")) 
 {

			HttpSession session = request.getSession(true);
			ManageDAO dao = new ManageDAO();
			ArrayList<ServerData> servers = dao.getServerDetails();

			Map<String, String> appNamesMap = new HashMap<String, String>();
			appNamesMap = mDAO.getAllAppNames();
			session.setAttribute("serverlist", servers);
			session.setAttribute("appnames", appNamesMap);
			System.out.println("addes session vars");
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/EditServerDetails.jsp");
			rd.forward(request, response);

		}
	
	}// end of doget

	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
			String action = request.getParameter("action");
		if (action!= null &&  action.equalsIgnoreCase("Add")) {
			String servername = request.getParameter("servername");
			String ipaddress = request.getParameter("ipaddress");
			String environment = request.getParameter("enviormentList");
			String app_id = request.getParameter("appNameList");
			String sub_app_id = request.getParameter("subAppNameList");
			String logPath = request.getParameter("logpath");
			ManageDAO dao = new ManageDAO();
			dao.addNewServerDetails(servername, ipaddress, environment,
					Integer.parseInt(app_id), Integer.parseInt(sub_app_id),
					logPath);
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/AdminHome.jsp");
			rd.forward(request, response);

		} else if (null != action && action.equalsIgnoreCase("modifyRcords")) {
			if (null != request.getParameter("update")) {
				ArrayList<ServerData> lstServerToUpdate = new ArrayList<ServerData>();
				System.out.println("update/save values "
						+ request.getParameter("selectedids"));
				String selectedRecordsForUpdate = request
						.getParameter("selectedids");
				if (null != selectedRecordsForUpdate) {
					String[] idsForUpdate = selectedRecordsForUpdate.split(",");
					int[] recordidsForUpdate = new int[idsForUpdate.length];

					for (int j = 0; j < idsForUpdate.length; j++) {
						recordidsForUpdate[j] = Integer
								.parseInt(idsForUpdate[j]);
						ServerData data = new ServerData();
						data.setId(Integer.parseInt(idsForUpdate[j]));
						data.setServername(request.getParameter("servername"
								+ Integer.parseInt(idsForUpdate[j])));
						data.setIpaddress(request.getParameter("ipaddress"
								+ Integer.parseInt(idsForUpdate[j])));
						data.setEnviornment(request.getParameter("env"
								+ Integer.parseInt(idsForUpdate[j])));
						data.setApplication_id(Integer.parseInt(request
								.getParameter("appNameList"
										+ Integer.parseInt(idsForUpdate[j]))));
						data.setSub_app_id(Integer.parseInt(request
								.getParameter("subAppNameList"
										+ Integer.parseInt(idsForUpdate[j]))));
						data.setLogpath(request.getParameter("logpath"
								+ Integer.parseInt(idsForUpdate[j])));
						lstServerToUpdate.add(data);
					}
				}

				ManageDAO dao = new ManageDAO();
				dao.updateServerDetails(lstServerToUpdate);
				RequestDispatcher rd = getServletContext()
						.getRequestDispatcher("/AdminHome.jsp");
				rd.forward(request, response);
			} else if (null != request.getParameter("delete")) {

				String selectedRecords = request.getParameter("selectedids");
				if (null != selectedRecords) {
					String[] ids = selectedRecords.split(",");
					int[] recordids = new int[ids.length];
					for (int j = 0; j < ids.length; j++) {
						recordids[j] = Integer.parseInt(ids[j]);
					}

					ManageDAO dao = new ManageDAO();
					dao.deleteServerDetails(recordids);
				}
				RequestDispatcher rd = getServletContext()
						.getRequestDispatcher("/AdminHome.jsp");
				rd.forward(request, response);
			}
		}	
	}
}

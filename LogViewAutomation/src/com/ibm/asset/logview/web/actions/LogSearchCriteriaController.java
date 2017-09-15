package com.ibm.asset.logview.web.actions;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.asset.logview.core.data.ServerData;
import com.ibm.asset.logview.core.db.SearchCriteria;
import com.ibm.asset.logview.core.db.UserDAO;

/**
 * Created on Sep 11, 2017
 * <p>
 * Description: LogSearchCriteriaController is used to get log details from
 * specified server and user can search based on time and text value.
 * 
 * Author :Abhinav Jaiswal
 */
public class LogSearchCriteriaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogSearchCriteriaController() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String createFileName(String dateParameter, String fileName,
			String filePath) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		if (null != dateParameter && !dateParameter.isEmpty()
				&& !dateParameter.equalsIgnoreCase(dateFormat.format(date))) {
			return filePath + "/" + fileName + "." + dateParameter;
		} else {
			return filePath + "/" + fileName;
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		UserDAO userDao = new UserDAO();
		ArrayList<String> fileNameList = new ArrayList<String>();
		String fromDate = request.getParameter("fromDate");
		String searchText = request.getParameter("searchText");
		String timeSearch = request.getParameter("timeSearch");
		ServerData serverData = (ServerData) request.getAttribute("serverData");
		if (null !=serverData && null == session.getAttribute("serverData")) {
			session.setAttribute("serverData", serverData);
		}else if(null ==serverData){
			serverData =(ServerData)session.getAttribute("serverData");
		}
		fileNameList = userDao.getfileNameByServerIDAndEnvironment(serverData
				.getServername(), serverData.getEnviornment());

		String fileName = createFileName(fromDate, fileNameList.get(0),
				serverData.getLogpath());
		String host = fileNameList.get(1);
		String user = fileNameList.get(2);
		int port = 22;
		String command = "more " + fileName;

		if (null != fromDate && !fromDate.isEmpty()) {
			if ((null != timeSearch && !timeSearch.isEmpty())
					&& (null != searchText && !searchText.isEmpty())) {
				command = "awk '/" + fromDate + " " + timeSearch + "/{print}' "
						+ fileName + " | grep " + searchText + "";
			} else if (null != timeSearch && !timeSearch.isEmpty()) {
				command = "awk '/" + fromDate + " " + timeSearch + "/{print}' "
						+ fileName;
			} else if (null != searchText && !searchText.isEmpty()) {
				command = "awk '/" + searchText + "/{print}' " + fileName;
			} else {
				command = "awk '//{print}' " + fileName;
			}
		}
		try {

			System.out.println(" inside doGet SearchCriteria class");
			String logInfo = new SearchCriteria().getLogDetail(host, user,
					port, command, fileNameList.get(3));

			session.setAttribute("logInfo", logInfo);
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/DisplayLog.jsp");
			rd.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

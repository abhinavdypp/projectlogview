package com.ibm.asset.logview.web.actions;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.asset.logview.core.db.SearchCriteria;

public class LogSearchCriteriaController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogSearchCriteriaController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String fromDate = request.getParameter("fromDate");
		String searchText = request.getParameter("searchText");
		String timeSearch = request.getParameter("timeSearch");

		String fileName = "/opt/ibm/crt1/svc/wci/logs/wci.log";
		String host = "96.43.65.244";// "96.130.128.170";
		String user = "arcbot01";
		int port = 22;
		String command = "awk '//{print}' "  + fileName;

		if (null != fromDate) {
			if ((null != timeSearch && !timeSearch.isEmpty())
					&& (null != searchText && !searchText.isEmpty())) {
				command = "awk '/"
					+ fromDate + " " + timeSearch + "/{print}' " + fileName + " | awk '/"
					+ searchText + "/{print}' " + fileName;;
			} else if (null != timeSearch && !timeSearch.isEmpty()) {
				command = "sed -n '/" + fromDate + " " + timeSearch + "/,$p'"
						+ fileName;
			} else if (null != searchText && !searchText.isEmpty()) {
				command = "awk '/"
						+ searchText + "/{print}' " + fileName;
			} else {
				command = "awk '//{print}' " + fileName;
			}
		}
		try {

			System.out.println(" inside doGet SearchCriteria class");
			String logInfo = new SearchCriteria().getLogDetail(host, user,
					port, command);

			HttpSession session = request.getSession(true);
			session.setAttribute("logInfo", logInfo);
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/DisplayLog.jsp");
			rd.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

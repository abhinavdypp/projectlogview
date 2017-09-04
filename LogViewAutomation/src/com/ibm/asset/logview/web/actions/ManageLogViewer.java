package com.ibm.asset.logview.web.actions;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.asset.logview.core.db.ManageServer;



/**
 * Servlet implementation class ManageLogViewer
 */
public class ManageLogViewer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageLogViewer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		ManageServer msc = new ManageServer();
		String host ="96.43.65.244";//"96.130.128.169";//"96.130.128.170";
		String user ="arcbot01";
		int port =22;
//		String command ="tail -400 /var/log/Histlogs/Syslog/datapwr | grep -v acsaywg01001 | grep error";
		String command ="more /opt/ibm/crt1/svc/wci/logs/wci.log";//"more /opt/ibm/crt1/svc/wci/logs/wci.log";		
		try{
			
			System.out.println(" inside doGet ManageLogViewer class");
			String logInfo=msc.getLogDetail(host, user, port, command);
			
			HttpSession session = request.getSession(true);
			session.setAttribute("logInfo", logInfo);
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/DisplayLog.jsp");
			rd.forward(request, response);		
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

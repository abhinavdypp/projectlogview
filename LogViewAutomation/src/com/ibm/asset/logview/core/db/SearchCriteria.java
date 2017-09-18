package com.ibm.asset.logview.core.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
/**
 * Created on Sep 11, 2017
 * <p>
 * Description: SearchCriteria is used to get log details from specified server and user can search based on 
 * time and text value.
 * 
 * Author :Abhinav Jaiswal
 */
public class SearchCriteria {

	
		private static String serverPassword; 
    
public String getLogDetail(String host, String user, int port, String command, String password) {
		
        JSch jsch=new JSch(); 
        String outputLog = null;        
        serverPassword =password;
 		try{
	    	
	        System.out.println("user : "+ user + " host : "+host + " port : "+port + " command : "+command);
	        
	        Session session=jsch.getSession(user, host, port);	       
	        // username and password will be given via UserInfo interface.
	        UserInfo ui=new MyUserInfo();
	        session.setUserInfo(ui);
	        session.connect();	   
	        Channel channel=session.openChannel("exec");
	        ((ChannelExec)channel).setCommand(command);
	        channel.setInputStream(null);
	        ((ChannelExec)channel).setErrStream(System.err);	   
	        InputStream in=channel.getInputStream();	   
	        channel.connect();
	   
	        
	        while(true){
	        	System.out.println("inside first while");
	        	outputLog = readResponse(in);
	          if(channel.isClosed()){
	        	System.out.println("Inside close channel");
	            System.out.println("exit-status: "+channel.getExitStatus());
	            break;
	            	            
	          }
	          try{Thread.sleep(1000);}catch(Exception ee){}
	        }
	        channel.disconnect();
	        session.disconnect();
	      }
	      catch(Exception e){
	        System.out.println(e);
	      }

		return outputLog;
	}
private String readResponse(InputStream is){
	BufferedReader br = null;
	StringBuilder sb = new StringBuilder();

	String line;
	try {

		br = new BufferedReader(new InputStreamReader(is));
		while ((line = br.readLine()) != null) {
			System.out.println("inside while in available");
            sb.append(line);
		}

	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    System.out.println("outputLog :"+sb.toString());
    return sb.toString();
}
	
	public static String getServerPassword() {
		String password = serverPassword;		
		return password;
	}	
	

 public static class MyUserInfo implements UserInfo{

	 		public String getPassword(){ System.out.println("inside getPassword");return passwd; }
		 
		    public boolean promptYesNo(String str){
		    	System.out.println("inside promptYesNo");
		        str = "Yes";
		        return true;}
		   
		    String passwd;
		 
		    public String getPassphrase(){     	System.out.println("inside getPassphrase"); return null; }
		    public boolean promptPassphrase(String message){ System.out.println("inside promptPassphrase"); return true; }
		    public boolean promptPassword(String message){
		    	System.out.println("inside promptPassword");
		        passwd=getServerPassword(); // enter the password for the machine you want to connect.
		        return true;
		    }
		    public void showMessage(String message){
		    	System.out.println("inside showMessage");    
		    }
		  
		    }

}

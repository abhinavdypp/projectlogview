package com.ibm.asset.logview.core.db;

import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

public class SearchCriteria {

	
public String getLogDetail(String host, String user, int port, String command) {
		
        JSch jsch=new JSch(); 
        String outputLog = null;        
 		
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
	   
	        byte[] tmp=new byte[10240];
 
	        while(true){
	        	System.out.println("inside first while");
	          while(in.available()>0){
	        	System.out.println("inside while in available");
	            int i=in.read(tmp, 0, 10240);
	            if(i<0)break;
	            outputLog = new String(tmp, 0, i);
	            System.out.println("outputLog :"+outputLog.toString());
	          }
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
	
	public static String getServerPassword() {
		String password = null;
		password = "Archana0817";//"NewPwd123";//"Archana0617";		
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

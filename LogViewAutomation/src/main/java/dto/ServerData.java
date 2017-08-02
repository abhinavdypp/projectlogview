package main.java.dto;

import java.util.List;



public class ServerData {
	
	private int id;
	private String servername;
	private String ipaddress;
	private String enviornment;
	private String logpath;
	private String sub_app_name;
	private String application_name;
	private int application_id;
	private int sub_app_id;
	
	private java.util.Map<String,String> subapplist;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getServername() {
		return servername;
	}
	public void setServername(String servername) {
		this.servername = servername;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public String getEnviornment() {
		return enviornment;
	}
	public void setEnviornment(String enviornment) {
		this.enviornment = enviornment;
	}
	public String getLogpath() {
		return logpath;
	}
	public void setLogpath(String logpath) {
		this.logpath = logpath;
	}
	public String getSub_app_name() {
		return sub_app_name;
	}
	public void setSub_app_name(String sub_app_name) {
		this.sub_app_name = sub_app_name;
	}
	public String getApplication_name() {
		return application_name;
	}
	public void setApplication_name(String application_name) {
		this.application_name = application_name;
	}
	public int getApplication_id() {
		return application_id;
	}
	public void setApplication_id(int application_id) {
		this.application_id = application_id;
	}
	public int getSub_app_id() {
		return sub_app_id;
	}
	public void setSub_app_id(int sub_app_id) {
		this.sub_app_id = sub_app_id;
	}
	public java.util.Map<String, String> getSubapplist() {
		return subapplist;
	}
	public void setSubapplist(java.util.Map<String, String> subapplist) {
		this.subapplist = subapplist;
	}
	
	
	

}
